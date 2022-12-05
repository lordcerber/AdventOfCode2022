package aoc.solutions.Y2022;

import aoc.solutions.Solution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 extends Solution {

    HashMap<Integer, Deque<String>> table = new HashMap();

    @Override
    public String solveFirst(List<String> input) {
        ArrayDeque<String> header = new ArrayDeque();
        boolean isHeader = true;

        for (String in:input){
            //выбираем заголовок пока не найдем строку разделитель
            if (isHeader) {
                if (in.equals("")) {
                    isHeader=false;
                    readHeader(header);
                }
                else {
                    header.addFirst(in);
                }
            }
            //читаем инструкции и переставляем стопки
            else {
                //move 1 from 5 to 6
                Pattern pattern = Pattern.compile("(move )(?<count>\\d+)( from )(?<a1>\\d+)( to )(?<a2>\\d+)");
                Matcher matcher = pattern.matcher(in);
                matcher.matches();
                Integer a1 = Integer.valueOf(matcher.group("a1"));
                Integer a2 = Integer.valueOf(matcher.group("a2"));
                for (int i=Integer.valueOf(matcher.group("count")); i>0;i--) {
                    //перемещаем ящик, магия стопок
                    table.get(a2).addFirst(table.get(a1).pollFirst());
                }
            }
        }
        //красиво и надеюсь сработает собираем со стопок строку
        return table.values().stream().collect(StringBuilder::new, (x,y) -> x.append(y.peekFirst()),(a,b)->a.append(b)).toString();
    }



    private void readHeader(ArrayDeque<String> header) {
        //по первой(последней) строке посчитать размер
        int size = (header.pollFirst().length() + 2) / 4;
        //инициализируем табличку из стопок. начинаем с единички а не нуля.
        for (int i = 1; i<=size;i++){
            table.put(i, new ArrayDeque<String>());
        }
        //заполняем стопки
        while (!header.isEmpty()) {
            String line = header.pollFirst();
            for (int i = 1; i<=size;i++){
                int position = (i-1) * 4 + 1;
                if (line.length()>position) {
                    String letter = line.substring(position, position+1);
                    if (!letter.equals(" ")) {
                        //System.out.println("put "+letter+" to deque "+i);
                        table.get(i).addFirst(letter);
                    }
                }
            }
        }
    }


    @Override
    public String solveSecond(List<String> input) {
        ArrayDeque<String> header = new ArrayDeque();
        boolean isHeader = true;

        for (String in:input){
            //выбираем заголовок пока не найдем строку разделитель
            if (isHeader) {
                if (in.equals("")) {
                    isHeader=false;
                    readHeader(header);
                }
                else {
                    header.addFirst(in);
                }
            }
            //читаем инструкции и переставляем стопки
            else {
                //move 1 from 5 to 6
                Pattern pattern = Pattern.compile("(move )(?<count>\\d+)( from )(?<a1>\\d+)( to )(?<a2>\\d+)");
                Matcher matcher = pattern.matcher(in);
                matcher.matches();
                //difference is we move stack not a single crate. that is fwe lines difference
                Integer a1 = Integer.valueOf(matcher.group("a1"));
                Integer a2 = Integer.valueOf(matcher.group("a2"));
                ArrayDeque<String> stack = new ArrayDeque<>();
                for (int i=Integer.valueOf(matcher.group("count")); i>0;i--) {
                    //перемещаем ящик, магия стопок
                    stack.addLast(table.get(a1).pollFirst());
                }
                //add all works not while i want (i mixed up first and last)
                while (!stack.isEmpty()) {
                    table.get(a2).addFirst(stack.pollLast());
                }
            }
        }
        //красиво и надеюсь сработает собираем со стопок строку
        return table.values().stream().collect(StringBuilder::new, (x,y) -> x.append(y.peekFirst()),(a,b)->a.append(b)).toString();
    }


}
