package aoc.solutions.Y2022;

import aoc.solutions.Solution;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 extends Solution {
    @Override
    public String solveFirst(List<String> input) {
        long score = 0;
        Pattern pattern = Pattern.compile("(?<min1>\\d+)(-)(?<max1>\\d+)(,)(?<min2>\\d+)(-)(?<max2>\\d+)");
        //find characters that are in both bags
        for (String in:input){
            Matcher matcher = pattern.matcher(in);
            matcher.matches();
            if (includes(
                    Long.valueOf(matcher.group("min1")),
                    Long.valueOf(matcher.group("max1")),
                    Long.valueOf(matcher.group("min2")),
                    Long.valueOf(matcher.group("max2")))) {
                score++;
            }
        }
        return String.valueOf(score);
    }

    private boolean overlap(Long min1, Long max1, Long min2, Long max2) {
        boolean result = min2>=min1 && min2<=max1 || max2>=min1 && max2<=max1 || min2<min1 && max2>max1;
        return result;
    }

    private boolean includes (Long min1, Long max1, Long min2, Long max2) {
        boolean result = min2>=min1 && max2<=max1 || min1>=min2 && max1<=max2;
        return result;
    }


    @Override
    public String solveSecond(List<String> input) {
        long score = 0;
        Pattern pattern = Pattern.compile("(?<min1>\\d+)(-)(?<max1>\\d+)(,)(?<min2>\\d+)(-)(?<max2>\\d+)");
        //find characters that are in both bags
        for (String in:input){
            Matcher matcher = pattern.matcher(in);
            matcher.matches();
            if (overlap(
                    Long.valueOf(matcher.group("min1")),
                    Long.valueOf(matcher.group("max1")),
                    Long.valueOf(matcher.group("min2")),
                    Long.valueOf(matcher.group("max2")))) {
                score++;
            }
        }
        return String.valueOf(score);
    }


}
