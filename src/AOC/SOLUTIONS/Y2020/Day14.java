package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 extends Solution  {
    //bitmasks. i have no idea how to work with core bitmasks
    private static volatile Day14 instance = null;

    public static Day14 getInstance() {
        if (instance == null) {
            synchronized (Day14.class) {
                if (instance == null) {
                    instance = new Day14();
                }
            }
        }

        return instance;
    }


    public static long solveTask1(List<String> report) {
        Bitmask bitmask = new Bitmask();
        HashMap<Integer, BitInteger> memory = new HashMap<Integer, BitInteger>();
        for (String line : report) {
            Pattern pattern = Pattern.compile("(?<op>\\w+)(?<suf>(\\[\\d+\\])|())(\\s=\\s)(?<value>.+)");
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            String operation = matcher.group("op");
            String suffix = matcher.group("suf");
            String value = matcher.group("value");

            if (operation.equals("mask")) {
                bitmask.setMask(value);
            }
            if (operation.equals("mem")) {
                int index = Integer.parseInt(suffix.replace("[", "").replace("]", ""));
                BitInteger val = new BitInteger(Integer.parseInt(value));
                val.applyBitmask(bitmask);
                memory.put(index, val);
            }
        }
        AtomicLong answer = new AtomicLong();
        memory.values().stream().forEach(s -> answer.addAndGet(s.longValue()));
        return answer.get();
    }

    public static long solveTask2(List<String> report) {
        Bitmask bitmask = new Bitmask();
        HashMap<Long, BitInteger> memory = new HashMap<Long, BitInteger>();
        int count = 0;
        for (String line : report) {
            count++;
            System.out.println("parsing line "+count + " " + line);
            Pattern pattern = Pattern.compile("(?<op>\\w+)(?<suf>(\\[\\d+\\])|())(\\s=\\s)(?<value>.+)");
            Matcher matcher = pattern.matcher(line);
            matcher.matches();
            String operation = matcher.group("op");
            String suffix = matcher.group("suf");
            String value = matcher.group("value");

            if (operation.equals("mask")) {
                bitmask.setMask(value);
            }
            if (operation.equals("mem")) {
                long index = Integer.parseInt(suffix.replace("[", "").replace("]", ""));
                BitInteger val = new BitInteger(Integer.parseInt(value));
                Bitmask bitAdress = new Bitmask(bitmask, index);
                System.out.println("generated bitmask " + bitAdress.mask);
                val.writeSelfToMaskedMemory(memory, bitAdress,0);
            }
        }
        AtomicLong answer = new AtomicLong();
        memory.values().stream().forEach(s -> answer.addAndGet(s.longValue()));
        return answer.get();
    }

    @Override
    public String solveFirst(List<String> input) {

        Long result = Day14.solveTask1(input);

        Logger.getGlobal().info("answer is "+ result);

        return String.valueOf(result);
    }

    @Override
    public String solveSecond(List<String> input) {
        Long result = Day14.solveTask2(input);

        Logger.getGlobal().info("answer is "+ result);

        return String.valueOf(result);
    }

    private static class Bitmask {
        private String mask;

        public Bitmask(Bitmask bitmask, Long val) {
            mask = bitmask.mask;
            or(val);
        }

        public Bitmask(String x) {
            setMask(x);
        }

        public Bitmask() {

        }

        private void or(Long val) {
            or(new BitInteger(val));
        }

        private void or(BitInteger bitInteger) {
            System.out.println("converted value   " + bitInteger.bValue);
            String newval = "";
            for (int i = 0; i < mask.length(); i++) {
                String bit = mask.substring(i, i + 1);
                String or = bitInteger.bValue.substring(i, i + 1);
                if (bit.equals("0")) newval = newval + or;
                else newval = newval + bit; //cas of X and 1 leave value unchanged
            }
            mask = newval;
        }

        public void setMask(String value) {
            mask = value;
        }

        public Long longValue() {
            return Long.parseLong(mask, 2);
        }
    }

    private static class BitInteger {
        private String bValue;

        public BitInteger(long val) {
            bValue = Long.toBinaryString(val);
            while (bValue.length() < 36) bValue = "0" + bValue; //normalise string
        }

        public void applyBitmask(Bitmask bitmask) {
            String newval = "";
            for (int i = 0; i < bValue.length(); i++) {
                String bit = bValue.substring(i, i + 1);
                String mask = bitmask.mask.substring(i, i + 1);
                if (mask.equals("X")) newval = newval + bit;
                else newval = newval + mask;
            }
            bValue = newval;
        }

        public long longValue() {
            return Long.parseLong(bValue, 2);
        }

        public void writeSelfToMaskedMemory(HashMap<Long, BitInteger> memory, Bitmask bitAdress, int start) {
            //fork tree
            if (bitAdress.mask.contains("X")) {
                for (int i = start; i < bitAdress.mask.length(); i++) {
                    String bit = bitAdress.mask.substring(i, i + 1);
                    if (bit.equals("X")) {
                        writeSelfToMaskedMemory(memory, new Bitmask(bitAdress.mask.replaceFirst("X", "1")),i);
                        writeSelfToMaskedMemory(memory, new Bitmask(bitAdress.mask.replaceFirst("X", "0")),i);
                    }
                }
            } else {
                memory.put(bitAdress.longValue(), new BitInteger(this.longValue()));
            }
        }

    }
}