package aoc.solutions.Y2022;

import aoc.solutions.Solution;

import java.util.List;

public class Day2 extends Solution {
    @Override
    public String solveFirst(List<String> input) {
        long score = 0;
        for (String in : input) {
            String elf = in.substring(0, 1);
            String me = in.substring(2, 3);
            if (me.equals("X")) score+=1;
            if (me.equals("Y")) score+=2;
            if (me.equals("Z")) score+=3;
            //damn it it can be done as algoritm but i cant figure how  right now;
            if (elf.equals("A")) {
                if (me.equals("X")) score+=3;
                if (me.equals("Y")) score+=6;
                if (me.equals("Z")) score+=0;
            }
            if (elf.equals("B")) {
                if (me.equals("X")) score+=0;
                if (me.equals("Y")) score+=3;
                if (me.equals("Z")) score+=6;
            }
            if (elf.equals("C")) {
                if (me.equals("X")) score+=6;
                if (me.equals("Y")) score+=0;
                if (me.equals("Z")) score+=3;
            }
        }
        return String.valueOf(score);
    }

    @Override
    public String solveSecond(List<String> input) {
        long score = 0;
        for (String in : input) {
            String elf = in.substring(0, 1);
            String me = in.substring(2, 3);
            //damn it it can be done as algoritm but i cant figure how  right now;
            if (elf.equals("A")) {
                if (me.equals("X")) score+=0+3;
                if (me.equals("Y")) score+=3+1;
                if (me.equals("Z")) score+=6+2;
            }
            if (elf.equals("B")) {
                if (me.equals("X")) score+=0+1;
                if (me.equals("Y")) score+=3+2;
                if (me.equals("Z")) score+=6+3;
            }
            if (elf.equals("C")) {
                if (me.equals("X")) score+=0+2;
                if (me.equals("Y")) score+=3+3;
                if (me.equals("Z")) score+=6+1;
            }
        }
        return String.valueOf(score);
    }
}
