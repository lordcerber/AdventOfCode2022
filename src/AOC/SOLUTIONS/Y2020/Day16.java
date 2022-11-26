package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16 extends Solution {
    //bitmasks. i have no idea how to work with core bitmasks
    private static volatile Day16 instance = null;

    private Day16() {
    }

    public static Day16 getInstance() {
        if (instance == null) {
            synchronized (Day16.class) {
                if (instance == null) {
                    instance = new Day16();
                }
            }
        }
        return instance;
    }

    public List<Rule> rules = new ArrayList<>();
    public List<Ticket> tickets = new ArrayList<>();


    public void convertInput(List<String> input) {
        int index = 0;
        String zone = "rules";
        for (String line : input) {
            if (line.equals("")) {
            }//do nothing
            else if (line.equals("your ticket:")) {
                zone = "ticket";
            } else if (line.equals("nearby tickets:")) {
                zone = "ticket";
            } else if (zone.equals("rules")) {
                Pattern pattern = Pattern.compile("(?<name>.+:)(\\s)(?<v11>\\d+)(-)(?<v12>\\d+)(\\s)(or)(\\s)(?<v21>\\d+)(-)(?<v22>\\d+)");
                Matcher matcher = pattern.matcher(line);
                matcher.matches();
                Rule rule = new Rule(matcher.group("name"));
                rule.addRange(matcher.group("v11"), matcher.group("v12"));
                rule.addRange(matcher.group("v21"), matcher.group("v22"));
                rules.add(rule);
            } else if (zone.equals("ticket")) {
                List<Integer> values = Arrays.stream(line.split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList());
                Ticket ticket = new Ticket(values);
                tickets.add(ticket);
            }
        }
    }

    public long solveTask1() {
        long a = 0;
        for (Ticket ticket : tickets) {
            for (int value : ticket.values) {
                if (!rules.stream().anyMatch(r -> r.isInRange(value))) a += value;
            }
        }
        return a;
    }

    public long solveTask2() {
        //remove false tickets;
        List<Ticket> falseTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            for (int value : ticket.values) {
                if (!rules.stream().anyMatch(r -> r.isInRange(value))) falseTickets.add(ticket);
            }
        }
        tickets.removeAll(falseTickets);

        //the question is how to map. let it be Rule->positions.

        HashMap<Rule, List<Integer>> epstainMartix = new HashMap<Rule, List<Integer>>();
        int range = tickets.get(0).values.size();
        for (Rule rule : rules) {
            epstainMartix.put(rule, IntStream.range(0, range).boxed().collect(Collectors.toList()));
        }

        printMatrix(epstainMartix,"Original Matrix");

        for (Ticket ticket : tickets) {
            for (int value : ticket.values) {
                for (Rule rule : rules) {
                    if (!rule.isInRange(value)) {
                        Integer id = ticket.values.indexOf(value);
                        epstainMartix.get(rule).remove(id);
                    }
                }
            }
        }
        printMatrix(epstainMartix,"After adding exceptions:");
        //aha so it is an real epstain matrix... need to solve it up;
        solveMatrix(epstainMartix);

        printMatrix(epstainMartix, "After solving:");

        tickets.get(0).applyMatrix(epstainMartix);

        AtomicReference<Long> val = new AtomicReference<>(1L);
        tickets.get(0).t.forEach((key, value) -> {
            if (key.name.contains("departure")) val.updateAndGet(v -> v * value);
        });

        return val.get();

    }

    private void solveMatrix(HashMap<Rule, List<Integer>> matrix) {
        List<Integer> solved = new ArrayList<>();

        boolean completed = false;
        while (!completed) {
            completed = true;
            for (List<Integer> list : matrix.values()) {
                if (list.size() > 1) {
                    completed = false;
                    list.removeAll(solved);
                } else if (list.size() == 1 && !solved.contains(list.get(0))) {
                    solved.add(list.get(0));
                }
            }
        }

    }

    private void printMatrix(Map<Rule, List<Integer>> matrix, String s) {
        System.out.println("");
        System.out.println(s);
        System.out.println("");
        for (Rule rule : matrix.keySet()) {
            System.out.print(rule.name + ":\t");
            for (Integer val : matrix.get(rule)) {
                System.out.print(val + ",\t");
            }
            System.out.println();
        }
    }

    @Override
    public String solveFirst(List<String> input) {
        Long result = Day16.getInstance().solveTask1();
        Logger.getGlobal().info("answer is "+ result);
        return String.valueOf(result);
    }

    @Override
    public String solveSecond(List<String> input) {
        Long result = Day16.getInstance().solveTask2();
        Logger.getGlobal().info("answer is "+ result);
        return String.valueOf(result);
    }

    private class Rule {
        public final String name;
        public HashMap<Integer, Integer> ranges = new HashMap<Integer, Integer>();

        public Rule(String name) {
            this.name = name;
        }

        public void addRange(String v1, String v2) {
            ranges.put(Integer.valueOf(v1), Integer.valueOf(v2));
        }

        public boolean isInRange(Integer value) {
            return ranges.entrySet().stream().anyMatch(i -> i.getKey() <= value && value <= i.getValue());
        }
    }

    private class Ticket {
        public List<Integer> values = new ArrayList<>();
        public HashMap<Rule, Integer> t = new HashMap<>();

        public Ticket(List<Integer> v) {
            values.addAll(v);
        }

        public void applyMatrix(HashMap<Rule, List<Integer>> matrix) {
            t.putAll(matrix.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> values.get(e.getValue().get(0)))));
        }
    }
}