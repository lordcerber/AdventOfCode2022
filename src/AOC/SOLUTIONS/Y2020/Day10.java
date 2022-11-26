package aoc.solutions.Y2020;

import aoc.solutions.Solution;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Day10 extends Solution {
    //rather strange task
    private static volatile Day10 instance = null;

    public static Day10 getInstance() {
        if (instance == null) {
            synchronized (Day10.class) {
                if (instance == null) {
                    instance = new Day10();
                }
            }
        }

        return instance;
    }


    public static List<Long> convertInput(List<String>input) {
        return (input.stream().map(s -> Long.parseLong(s)).collect(Collectors.toList()));
    }

    public static long solveTask1(List<Long> report) {
        long answer = 0;

        report.sort(Long::compareTo);
        Long val1 = 0L;
        Long val2 = 1l; //the final value always a three;
        long tick = 0;
        loop:
        for (Long tock : report) {
            long delta = tock-tick;
            if (delta == 1 ) val1++;
            if (delta == 3 )val2++;
            tick = tock;
        }
        answer = val1*val2;
        return answer;
    }

//ok task 2 is a little more interesting;
//there should be an easy math way... cant thing of one now

    public long solveTask2(List<Long> report) {
        HashMap<Long, Connector> map = new HashMap<Long, Connector>();
        List<Integer> deltas = List.of(1,2,3);

        report.sort(Long::compareTo);

        map.put(0L,new Connector(0L));

        loop:
        for (Long tock : report) {
            Connector con = new Connector(tock);
            map.put(tock, con);
            for (int d:deltas) {
                if (map.containsKey(tock-d)) map.get(tock-d).addLink(con);
            }
        }
        //last one is always a single 3 connector so no counting;
        Long answer = map.get(0L).getTree();
        return answer;
    }

    public long solveTask2NoMemory(List<Long> report) {
        HashMap<Long, Connector> map = new HashMap<Long, Connector>();
        List<Integer> deltas = List.of(1,2,3);

        report.sort(Long::compareTo);

        map.put(0L,new Connector(0L));

        loop:
        for (Long tock : report) {
            Connector con = new Connector(tock);
            map.put(tock, con);
            for (int d:deltas) {
                if (map.containsKey(tock-d)) map.get(tock-d).addLink(con);
            }
        }
        //last one is always a single 3 connector so no counting;
        Long answer = map.get(0L).getTreeNoMemory();
        return answer;
    }

    @Test
    public void compareCalculationTime (List<String> i) {
        List<Long> input = Day10.convertInput(i);
        long time = System.currentTimeMillis();
        Long result = Day10.getInstance().solveTask2(input);
        Logger.getGlobal().info("answer is "+ result+ "took " + (System.currentTimeMillis()-time) + "ms");
        time = System.currentTimeMillis();
        Long result2 = Day10.getInstance().solveTask2NoMemory(input);
        Logger.getGlobal().info("answer is "+ result2+ "took " + (System.currentTimeMillis()-time) + "ms");

        Assert.assertTrue(result.equals(result2));
    }

    @Override
    public String solveFirst(List<String> input) {
        List<Long> input2 = Day10.convertInput(input);
        Long result = Day10.solveTask1(input2);

        Logger.getGlobal().info("answer is "+ result);

        return  result.toString();
    }

    @Override
    public String solveSecond(List<String> input) {
        List<Long> input2 = Day10.convertInput(input);
        Long result = solveTask2(input2);

        Logger.getGlobal().info("answer is "+ result);

        return  result.toString();
    }

    private class Connector {
        private final Long self;
        private HashMap <Connector, Long> links = new HashMap<>();

        public Connector(Long i) {
            self = i;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Connector connector = (Connector) o;
            return self.equals(connector.self);
        }

        @Override
        public int hashCode() {
            return Objects.hash(self);
        }

        public void addLink(Connector tock) {
            links.put(tock, tock.self - this.self);
        }

        Long tree; //this greatly reduses calculation time;
        public Long getTree() {
            if (tree == null) {
                AtomicLong summ = new AtomicLong(links.size()>0 ? links.size():1);
                links.forEach((link, b) -> {
                    summ.addAndGet(link.getTree() - 1);
                });
                tree = summ.get();
            }
            return tree;
        }

        public Long getTreeNoMemory() {
                AtomicLong summ = new AtomicLong(links.size()>0 ? links.size():1);
                links.forEach((link, b) -> {
                    summ.addAndGet(link.getTreeNoMemory() - 1);
                });
            return summ.get();
        }
    }
}
