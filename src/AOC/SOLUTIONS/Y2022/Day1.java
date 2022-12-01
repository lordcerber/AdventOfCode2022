package aoc.solutions.Y2022;

import aoc.solutions.Solution;

import java.util.ArrayList;
import java.util.List;

public class Day1 extends Solution {
    @Override
    public String solveFirst(List<String> input) {
        long max = 0;
        long elfBackpack=0;
        for (String in : input) {
           if (in.equals("")) {
               if (elfBackpack>max) {
                   max = elfBackpack;
               }
               elfBackpack=0;
           }
           else {
               elfBackpack += Long.parseLong(in);
           }
        }
        return String.valueOf(max);
    }

    @Override
    public String solveSecond(List<String> input) {
        ArrayList<Long> list = new ArrayList<Long>();
        int counter = 3;
        long elfBackpack=0;
        for (String in : input) {
            if (in.equals("")) {
                if (counter>0) {
                    list.add(elfBackpack);
                    counter--;
                }
                else {
                    if (elfBackpack > list.stream().min(Long::compare).get()) {
                        list.remove(list.stream().min(Long::compare).get());
                        list.add(elfBackpack);
                    }
                }
                elfBackpack=0;
            }
            else {
                elfBackpack += Long.parseLong(in);
            }
        }
        return String.valueOf(list.stream().reduce(0L, Long::sum));
    }

}
