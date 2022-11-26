package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day9 extends Solution {
    //the task have some reseblense to day 1 and maybe some code can be reused

    public static List<Long> convertInput(List<String>input) {
        return (input.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
    }

    public static long day1ReportCalculationOneIteration(List<Long> report, Long expectation) {
        long answer = 0;

        int size = report.size();
        Long val1 = 0l;
        Long val2 = 0l;

        HashMap<Long, Integer> rMap = new HashMap<Long, Integer>();
        loop:
        for (int i = 0; i < size; i++) {
            val1 = report.get(i);
            if (rMap.containsKey(expectation - val1)) {
                val2 = expectation - val1;
                break loop;
            }
            else {
                rMap.put(val1,i);
            }
        }
        answer = val1*val2;
        return answer;
    }

    public static Long solveTask1 (List<Long> input, Integer preambula) {
        Deque<Long> previous = new ArrayDeque<Long>();

        for (Long val : input) {
            if (previous.size()< preambula) {
                previous.add(val);
            }
            else if (day1ReportCalculationOneIteration(List.copyOf(previous), val) == 0 ) {
                return val; //it is out answer
            }
            else {
                //shifting preambula
                previous.pollFirst();
                previous.add(val);
            }
        }
        return 0l; //ouch
    }

    public static Long solveTask2 (List<Long> input, Long target) {
        //oh idea! ill keep going with dynamic deque as sliding summ!
        Deque<Long> buffer = new ArrayDeque<Long>();

        Long bufferedSumm = 0l; // i dont want to iterate deque each time
        for (Long val : input) {
            //expand
            buffer.add(val);
            bufferedSumm+=val;
            //shrink
            while (bufferedSumm > target) {
                bufferedSumm -= buffer.peekFirst();
                buffer.pollFirst();
            }
            //check
            if (target.equals(bufferedSumm) && buffer.size()>1) {
                return buffer.stream().max(Long::compareTo).get() + buffer.stream().min(Long::compareTo).get();
            }
            //at this point buffer is smaller then target so loop;
        }
        return 0l; //ouch
    }


    @Override
    public String solveFirst(List<String> input) {
        List<Long> input2 = Day9.convertInput(input);
        Long result = Day9.solveTask1(input2,5);

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }

    @Override
    public String solveSecond(List<String> input) {
        List<Long> input2 = Day9.convertInput(input);
        Long preresult = Day9.solveTask1(input2,5);
        Long result = Day9.solveTask2(input2,preresult);

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }
}
