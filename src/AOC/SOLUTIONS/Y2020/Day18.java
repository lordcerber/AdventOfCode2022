package aoc.solutions.Y2020;

import aoc.solutions.Solution;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Day18 extends Solution {
    private static final String PLUS = "+";
    private static final String MULT = "*";
    private static final String OB = "(";
    private static final String CB = ")";

    @Override
    public String solveFirst(List<String> input) {
        return String.valueOf(solveTask1( input));
    }

    @Override
    public String solveSecond(List<String> input) {
        return String.valueOf(solveTask2(input));
    }

    //a math interpritator.
    //luckly looking at condition i see there is no 2digit numbers
    private static volatile Day18 instance = null;
    private int position=0;

    public static Day18 getInstance() {
        if (instance == null) {
            synchronized (Day18.class) {
                if (instance == null) {
                    instance = new Day18();
                }
            }
        }
        return instance;
    }

    //public HashSet<Cell>cells = new HashSet<Cell>();


    public long solveTask1(List<String> input) {
        long summ = 0;
        for (String line: input) {
            position=0;
            summ += calculateLine(line);
            }
        return summ;
    }

    public long solveTask2(List<String> input) {
        long summ = 0;
        for (String line: input) {
            position=0;
            line = calculateLine2(line);
            position=0;
            summ += calculateLine(line);
        }
        return summ;
    }

    public long calculateLine(String line) {
        //first remove spaces i dont need them
        line = line.replace(" ","");
        long value = 0;
        String op = PLUS;
        //for some reason i want position to be global
        while (position<line.length()) {
            String s = line.substring(position, position + 1);
            if (StringUtils.isNumeric(s)) {
                value = op.equals(PLUS) ? value + Integer.valueOf(s) : value * Integer.valueOf(s);
                position++;
            }
            else if (s.equals(PLUS)) {
                op = PLUS;
                position++;
            }
            else if (s.equals(MULT)) {
                op = MULT;
                position++;
            }
            else if (s.equals(OB)) {
                position++;
                long num = calculateLine(line);
                value = (op.equals(PLUS) ? value + num : value * num);
            }
            else if (s.equals(CB)) {
                position++;
                return value;
            }
        }
        System.out.println(line +"="+value);
        return value;
    }

    public String calculateLine2(String line) {
        //first remove spaces i dont need them
        line = line.replace(" ","");
        line = line.substring(0,position)+OB+line.substring(position);
        position ++;
        while (position<line.length()) {
            String s = line.substring(position, position + 1);
            if (s.equals(MULT)) {
                line = line.substring(0,position)+CB+MULT+OB+line.substring(position+1);
                position+=3;
            }
            else if (s.equals(OB)) {
                position++;
                line = calculateLine2(line);
            }
            else if (s.equals(CB)) {
                line = line.substring(0,position)+CB+line.substring(position);
                position+=2;
                return line;
            }
            else {position++;}
        }
        line = line + CB;
        position = 0;
        return line;
    }


    public void reset() {
        position=0;
    }

}