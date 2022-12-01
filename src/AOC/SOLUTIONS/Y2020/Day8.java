package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Day8 extends Solution {
    /*
     * So todays task is about running a program
     * the Input is given as text with two operands in a line
     * there will be lots of jumping the Input
     * so hash map is not the choice.. lists will do
     * */
    public static final String NOP = "nop";
    public static final String ACC = "acc";
    public static final String JMP = "jmp";

    public static Long runCode(List<String> code) {
        List<Integer>log = new ArrayList<>();
        Long acc= 0L;
        Integer pos = 0;

        while (!log.contains(pos) && pos<code.size()) {
           String a =  code.get(pos);
           String operand = a.split(" ")[0];
           Integer value = Integer.parseInt(a.split(" ")[1]);

           log.add(pos);
           if (operand.equals(NOP)) {
               pos++;
           }
           else if (operand.equals(ACC)) {
               acc += value;
               pos++;
           }
           else if (operand.equals(JMP)){
               pos += value;
           }

        }
         return acc;
    }

    //ok task 2 looks somewhat tricky... how to "fix" the problem... lets start with direct iteration.

    //return true if code is looped
    public static Boolean isCodeLooped(List<String> code) {
        List<Integer>log = new ArrayList<>();
        Integer pos = 0;

        while (!log.contains(pos) && pos<code.size()) {
            String a =  code.get(pos);
            String operand = a.split(" ")[0];
            Integer value = Integer.parseInt(a.split(" ")[1]);

            log.add(pos);
            if (operand.equals(NOP)) {
                pos++;
            }
            else if (operand.equals(ACC)) {
                pos++;
            }
            else if (operand.equals(JMP)){
                pos += value;
            }

        }
        return pos < code.size();
    }

    public static List<String> substitute (List<String> code, Integer pos) {
        ArrayList<String> sub = new ArrayList<String>(code);

        String val = sub.get(pos);
        if (val.contains(NOP)) val = val.replace(NOP,JMP);
        else if (val.contains(JMP)) val = val.replace(JMP, NOP);
        sub.set(pos, val);
        return sub;
    }

    public static Long solveEndlessLoop (List<String> code) {

        //reverse loop will more likely find answer?
        for (int i=code.size()-1; i>=0; i--) {
            if (code.get(i).contains(NOP) || code.get(i).contains(JMP)) {
                List<String> sub = substitute(code, i);
                if (!isCodeLooped(sub)) return runCode(sub);
            }
        }
        return 0L;
    }

    @Override
    public String solveFirst(List<String> input) {
        Long result = runCode(input);

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }

    @Override
    public String solveSecond(List<String> input) {
        Long result = solveEndlessLoop(input);

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }
}
