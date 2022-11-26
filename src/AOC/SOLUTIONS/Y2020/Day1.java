package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day1 extends Solution {


    @Override
    public String solveFirst(List<String> inputTest) {
        return String.valueOf(ExpenseReportCalculation(inputTest.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()),2020));
    }

    @Override
    public String solveSecond(List<String> inputTest) {
        return String.valueOf(ExpenseReportCalculationTriplet(inputTest.stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList()),2020));
    }

    public static long ExpenseReportCalculation(List<Integer> report, int expectation) {
        long answer = 0;


        int size = report.size();
        Integer val1 = 0;
        Integer val2 = 0;
        //simple double iteration. possibly there is a more euristic way, like sorting and then summ biggest with smallest in approaching loops

        loop:
        for (int i = 0; i < size; i++) {
            val1 = report.get(i);
            for (int j = i + 1; j < size; j++) {
                val2 = report.get(j);
                if (val1 + val2 == expectation) break loop;
            }
        }

        //a lot of unhandled error cases here... maybe fix that
        Logger.getGlobal().info("answer is " + val1 + " * " + val2 + " = " + val1*val2);
        answer = val1*val2;

        return answer;
    }

    public static long ExpenseReportCalculationTriplet(List<Integer> report, int expectation) {
        long answer = 0;


        int size = report.size();
        Integer val1 = 0;
        Integer val2 = 0;
        Integer val3 = 0;
        //simple double iteration. possibly there is a more euristic way, like sorting and then summ biggest with smallest in approaching loops

        loop:
        for (int i = 0; i < size; i++) {
            val1 = report.get(i);
            for (int j = i + 1; j < size; j++) {
                val2 = report.get(j);
                for (int k = j + 1; k < size; k++) {
                    val3 = report.get(k);
                    if (val1 + val2 + val3 == expectation) break loop;
                }
            }
        }

        //a lot of unhandled error cases here... maybe fix that
        Logger.getGlobal().info("answer is " + val1 + " * " + val2 +" * "+val3+ " = " + val1*val2*val3);
        answer = val1*val2*val3;

        return answer;
    }

}
