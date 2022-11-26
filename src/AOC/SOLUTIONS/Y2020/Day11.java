package aoc.solutions.Y2020;

import aoc.solutions.Solution;
import aoc.solutions.Y2020.day11.Field;

import java.util.List;


public class Day11 extends Solution {
    //hey this task ihas a rules of "LIFE" game popular at DOS times among coders.
    //unfortunately java as awful to work with string
    //i have an idea how to cantain a object level of life
    private static volatile Day11 instance = null;
    public Field field;

    public static Day11 getInstance() {
        if (instance == null) {
            synchronized (Day11.class) {
                if (instance == null) {
                    instance = new Day11();
                }
            }
        }

        return instance;
    }


    @Override
    public String solveFirst(List<String> input) {
        field = new Field(input, "L");
        //Logger.getGlobal().info(field.toString());
        while (field.advanceTimeRule1()>0L) {
            //Logger.getGlobal().info(field.toString());
        }
        return String.valueOf(field.countActiveCells());
    }

    @Override
    public String solveSecond(List<String> input) {
        field = new Field(input, "L");
        //Logger.getGlobal().info(field.toString());
        while (field.advanceTimeRule2()>0L) {
            //Logger.getGlobal().info(field.toString());
        }
        return String.valueOf(field.countActiveCells());
    }
}
