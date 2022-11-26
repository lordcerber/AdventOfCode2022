package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 extends Solution {
    //a tortoise emulation.
    //i have played that as a kid

    private static volatile Day12 instance = null;

    public static Day12 getInstance() {
        if (instance == null) {
            synchronized (Day12.class) {
                if (instance == null) {
                    instance = new Day12();
                }
            }
        }

        return instance;
    }

    public long solveTask1 (List<String> input) {
        Cell ship = new Cell(0,0);

        for (String comand: input) {
            Pattern pattern = Pattern.compile("(?<operation>\\w)(?<value>\\d+)");
            Matcher matcher = pattern.matcher(comand);
            matcher.matches();
            String operation = matcher.group("operation");
            int value = Integer.parseInt(matcher.group("value"));
            ship.move(operation, value);
        }

        return Math.abs(ship.getCol())+Math.abs(ship.getRow());

    }

    public long solveTask2 (List<String> input) {
        Cell ship = new Cell(0,0);
        Cell vector = new Cell(0,0);
        vector.move("E",10);
        vector.move("N",1);

        for (String comand: input) {
            Pattern pattern = Pattern.compile("(?<operation>\\w)(?<value>\\d+)");
            Matcher matcher = pattern.matcher(comand);
            matcher.matches();
            String operation = matcher.group("operation");
            int value = Integer.parseInt(matcher.group("value"));
            if("SNEW".contains(operation))
                vector.move(operation, value);
            else if("LR".contains(operation))
                vector.vectorRotate(operation,value);
            else if(operation.equals("F"))
                ship.move(vector,value);
        }
        return Math.abs(ship.getCol())+Math.abs(ship.getRow());
    }


    @Override
    public String solveFirst(List<String> input) {
        Long result = solveTask1(input);

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }

    @Override
    public String solveSecond(List<String> input) {
        Long result = solveTask2(input);

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }

    public class Cell {

        private long row;
        private long col;
        private String direction = "E";

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            aoc.solutions.Y2020.Day12.Cell cell = (aoc.solutions.Y2020.Day12.Cell) o;
            return row == cell.row &&
                    col == cell.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, col);
        }

        public Cell(long r, long c) {
            row = r;
            col = c;
        }

        public long getRow() {
            return row;
        }

        public long getCol() {
            return col;
        }

        public void move(String operation, int value) {
            if (value == 0) return;
            else if (operation.equals("N")) {row++; move(operation,value-1);}
            else if (operation.equals("S")) {row--; move(operation,value-1);}
            else if (operation.equals("E")) {col++; move(operation,value-1);}
            else if (operation.equals("W")) {col--; move(operation,value-1);}

            else if (operation.equals("F")) {move(direction,value);}

            else if (operation.equals("L")) {turn(1); move(operation,value-90);}
            // 90 right is 270 left
            else if (operation.equals("R")) {turn(3); move(operation,value-90);}
        }

        public void move(aoc.solutions.Y2020.Day12.Cell vector, int value) {
            if (value == 0) return;
            row+= vector.row;
            col+=vector.col;
            move(vector, value -1);
        }

        private void turn(int i) {
            if (i==0) return;
                //counter clockwise is positive as in math
            else if (direction.equals("N")) {direction="W"; turn(i-1);}
            else if (direction.equals("E")) {direction="N"; turn(i-1);}
            else if (direction.equals("S")) {direction="E"; turn(i-1);}
            else if (direction.equals("W")) {direction="S"; turn(i-1);}
        }

        public void vectorRotate(String operation, int value) {
            if (value==0) return;
            else {
                long dc = col;
                long dr = row;
                if (operation.equals("R")) dc = -1 * dc;
                if (operation.equals("L")) dr = -1 * dr;
                row = dc;
                col = dr;
                vectorRotate(operation,value - 90);
            }
        }
    }
}
