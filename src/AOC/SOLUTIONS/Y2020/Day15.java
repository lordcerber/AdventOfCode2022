package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day15 extends Solution {
    //bitmasks. i have no idea how to work with core bitmasks
    private static volatile Day15 instance = null;

    private Day15() {
    }

    public static Day15 getInstance() {
        if (instance == null) {
            synchronized (Day15.class) {
                if (instance == null) {
                    instance = new Day15();
                }
            }
        }
        return instance;
    }

    public static long solveTask1(List<Long> report, Long limit) {
        HashMap<Long,Long> gameLog = new HashMap();
        Long turn = 0L;
        Long lastNumber=0L;
        Long nextNumber=0L;
        for (;turn < report.size();turn++) {
            nextNumber = report.get(turn.intValue());
            if (turn>0) gameLog.put(lastNumber, turn);
            lastNumber=nextNumber;
        }

        while (turn < limit) {
            if (gameLog.containsKey(lastNumber)) nextNumber=turn-gameLog.get(lastNumber);
            else nextNumber=0L;
            gameLog.put(lastNumber, turn);
            //System.out.println("turn:"+turn + " we say:"+nextNumber);
            lastNumber=nextNumber;
            turn++;
        }
        return nextNumber;
    }

    public static List<Long> convertInput(List<String>input) {
        return (input.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
    }

    @Override
    public String solveFirst(List<String> input) {
        Long result = Day15.solveTask1(convertInput(input),2020L);
        Logger.getGlobal().info("answer is "+ result);
        return String.valueOf(result);
    }

    @Override
    public String solveSecond(List<String> input) {
        long time = System.currentTimeMillis();
        Long result = Day15.solveTask1(convertInput(input),30000000L);
        Logger.getGlobal().info("in "+(System.currentTimeMillis()-time)+"ms answer is "+ result);
        return String.valueOf(result);
    }
}