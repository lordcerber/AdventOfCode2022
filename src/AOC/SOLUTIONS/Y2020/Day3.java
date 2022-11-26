package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class Day3 extends Solution {

    private static List<String> slope;

    @Override
    public String solveFirst(List<String> input) {
        slope = input;
        Integer result = Day3.hereWeRide(3,1);
        Logger.getGlobal().info("answer is "+ result);
        return result.toString();
    }

    @Override
    public String solveSecond(List<String> input) {
        slope = input;
        Integer result1 = Day3.hereWeRide(1,1);
        Integer result2 = Day3.hereWeRide(3,1);
        Integer result3 = Day3.hereWeRide(5,1);
        Integer result4 = Day3.hereWeRide(7,1);
        Integer result5 = Day3.hereWeRide(1,2);
        Logger.getGlobal().info("answer is "+ result1 + " " + result2 + " " + result3 + " " + result4 + " " + result5);
        BigDecimal total = BigDecimal.valueOf(result1).multiply(BigDecimal.valueOf(result2)).multiply(BigDecimal.valueOf(result3)).multiply(BigDecimal.valueOf(result4)).multiply(BigDecimal.valueOf(result5)) ;
        Logger.getGlobal().info("answer is "+ total.toPlainString());
        return total.toPlainString();
    }

    public static Integer hereWeRide (int right, int down) {
        int slide = 0;
        int trees = 0;
        for (int i = 0; i< slope.size(); i+= down) {
            String line = slope.get(i);
            while (slide >= line.length()) {
                slide = slide - line.length(); //looping world
            }
            if (line.substring(slide, slide+1).contentEquals("#")) {
                trees++;
            }
            slide += right;
        }
        return trees;

    }



}
