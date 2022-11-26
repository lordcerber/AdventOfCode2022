package aoc.solutions.Y2020;

import aoc.solutions.Solution;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day5 extends Solution {

    //wow a binary coding
    //FFBBFFFRLL
    // so i assume FFFFFF is 0 BBBBBB is 111111 and LLL is 0 RRR is 111


    public Seat decodeBinaryPlacement(String ticket) {

        String binaryRow = ticket.substring(0, ticket.length() - 3).replace("F", "0").replace("B", "1");
        String binaryColumn = ticket.substring(ticket.length() - 3).replace("R","1").replace("L","0");

        int row = Integer.parseInt(binaryRow, 2); //Supercow power
        int column = Integer.parseInt(binaryColumn, 2);

        return new Seat(row, column);
    }

    public int getHighestSeatID(List<Seat> seats) {
        seats.sort(Seat::compareTo);
        return seats.get(seats.size()-1).getID();
    }

    public int getEmptySeatID(List<Seat> seats) {
        seats.sort(Seat::compareTo);
        for (int i=0; i<seats.size()-1;i++) {
            if (seats.get(i+1).getID()-seats.get(i).getID()>1) return seats.get(i).getID()+1;
        }
        return 0;
    }

    @Override
    public String solveFirst(List<String> input) {
        List<Seat> plane = input.stream().map(s -> decodeBinaryPlacement(s)).collect(Collectors.toList());

        Logger.getGlobal().info("answer is "+ getHighestSeatID(plane));

        Assert.assertTrue(getHighestSeatID(plane) > 0);

        return String.valueOf(getHighestSeatID(plane));
    }

    @Override
    public String solveSecond(List<String> input) {
        List<Seat> plane = input.stream().map(s -> decodeBinaryPlacement(s)).collect(Collectors.toList());

        Logger.getGlobal().info("answer is "+ getEmptySeatID(plane));

        Assert.assertTrue(getEmptySeatID(plane) > 0);

        return String.valueOf(getEmptySeatID(plane));
    }

    //that can be actually solved without decoding and stuff, by strings sorting
    public class Seat implements Comparable<Seat> {
        private final int row;
        private final int column;

        public Seat(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getColumn() {
            return column;
        }

        public int getRow() {
            return row;
        }

        public int getID() {
            return row * 8 + column; //well this is actually a whole string decoded without need to split and something
        }

        @Override
        public int compareTo(@NotNull Seat o) {
            return Integer.compare(this.getID(), o.getID());
        }
    }
}
