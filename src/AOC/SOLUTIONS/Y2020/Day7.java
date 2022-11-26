package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 extends Solution {

//    light red bags contain 1 bright white bag, 2 muted yellow bags.
//    dark orange bags contain 3 bright white bags, 4 muted yellow bags.
//    bright white bags contain 1 shiny gold bag.
//    muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
//    shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
//    dark olive bags contain 3 faded blue bags, 4 dotted black bags.
//    vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
//    faded blue bags contain no other bags.
//    dotted black bags contain no other bags.

    //so how does that look like
    //a tree no less. unique key is 2-word color. then "bags contain" than multiple choises.
    //the outer key may contain multiple inner keys.

    //i need an object @bag
    //and class will be non-static. let it be singletone

       private HashMap<String, Bag> Bags = new HashMap<>();

    private static volatile Day7 instance = null;

    private Day7() {
    }

    public void parseInput(String input) {

        Pattern pattern = Pattern.compile("(?<color>\\w+ \\w+)( bags contain )(?<tail>.+)");
        Matcher matcher = pattern.matcher(input);
        matcher.matches();
        String color = matcher.group("color");
        String tail = matcher.group("tail");

        Bag bag = getBag(color);

        //skip ending bags
        if (!tail.contains("no other bags")) {
            for (String Tails : tail.split(", ")) {
                Pattern tailPattern = Pattern.compile("(?<count>\\d+)(\\s)(?<color>\\w+ \\w+)(.+)");
                Matcher tailMatcher = tailPattern.matcher(Tails);
                tailMatcher.matches();
                int count = Integer.parseInt(tailMatcher.group("count"));
                String tailColor = tailMatcher.group("color");
                bag.add(tailColor, count);
            }
        }

    }

    public Bag getBag(String color) {
        if (Bags.containsKey(color)) return Bags.get(color);
        else {
            Bags.put(color, new Bag(color));
            return getBag(color);
        }
    }

    //если я не накосячил то все готово, теперь решать задачку

    public int taskOneFindContainers(String color) {
        int count = 0;
        for (Bag bag : Bags.values()) {
            if (!bag.isColor(color) && bag.containsAnyDepth(color)) count++;
        }
        return count;
    }

    public int taskTwoCountTreeSize(String color) {
        return getBag(color).countInnerTree() - 1;

    }

    @Override
    public String solveFirst(List<String> input) {
        for (String line : input) {
            parseInput(line);
        }

        Integer result = taskOneFindContainers("shiny gold");

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }

    @Override
    public String solveSecond(List<String> input) {
        for (String line : input) {
            parseInput(line);
        }

        Integer result = taskOneFindContainers("shiny gold");

        Logger.getGlobal().info("answer is "+ result);

        return result.toString();
    }

    public class Bag {
        private String ID;
        private HashMap<Bag, Integer> container;


        public Bag(String ID) {
            this.ID = ID;
            container = new HashMap();
        }


        public void add(String id, int count) {
            //assume that in data id != ID and no loops
            container.put(Day7.this.getBag(id), count);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Bag day7Bag = (Bag) o;
            return ID.equals(day7Bag.ID);
        }

        @Override
        public int hashCode() {
            return Objects.hash(ID);
        }

        public boolean isColor(String color) {
            return ID.equals(color);
        }

        public boolean containsAnyDepth(String color) {
            if (ID.equals(color)) return true;
                //and here comes crazy recursion
            else if (container.keySet().stream().anyMatch(key -> key.containsAnyDepth(color))) return true;
            return false;

        }

        public int countInnerTree() {
            AtomicInteger summ = new AtomicInteger(1);
            //even more crazy recursion
            container.forEach((bag, val) -> summ.addAndGet(val * bag.countInnerTree()));
            return summ.get();
        }
    }
}
