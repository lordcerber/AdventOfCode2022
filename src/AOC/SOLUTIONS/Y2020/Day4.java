package aoc.solutions.Y2020;

import aoc.solutions.Solution;

import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4 extends Solution {

    @Override
    public String solveFirst(List<String> input) {
        List<Map> passports = Day4.decodeInputData(input);
        int count = 0;
        for (Map passport:passports) {
            if (Day4.validatePassportFieldsPresent(passport)) count++;
        }
        Logger.getGlobal().info("answer is "+ count);
        return String.valueOf(count);
    }

    @Override
    public String solveSecond(List<String> input) {
        List<Map> passports = Day4.decodeInputData(input);
        int count = 0;
        for (Map passport:passports) {
            if (
                    Day4.validatePassportFieldsPresent(passport) &&
                            Day4.validatePassportFieldsValid(passport)) count++;
        }
        Logger.getGlobal().info("answer is "+ count);
        return String.valueOf(count);
    }


    public static List decodeInputData(List<String> input) {

        //ok we are expecting multiline or single line inputs
        //so like... store in map, read line by line, split with pattern
        List<Map> passports = new ArrayList();

        Map data = new HashMap<String, String>();
        for (String line : input) {

            if (line.length() == 0) {
                passports.add(Map.copyOf(data));
                data.clear();
            } else {
                for (String entry : Arrays.asList(line.split(" "))) {
                    Pattern pattern = Pattern.compile("(?<code>\\w{3})(:)(?<value>.+)");
                    Matcher matcher = pattern.matcher(entry);
                    matcher.matches();
                    data.put(matcher.group("code"), matcher.group("value"));
                }
            }
        }
        passports.add(Map.copyOf(data));
        return passports;
    }

    public static Boolean validatePassportFieldsPresent(Map passport) {
        List<String> mandatoryFields = Arrays.asList("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");
        List<String> optionalFields = Arrays.asList("cid");
        return validatePassportFieldsPresent(passport, mandatoryFields, optionalFields);
    }

    public static Boolean validatePassportFieldsPresent(Map passport, List mandatoryFields, List optionalFields) {

        //first check mandatory fields
        if (!passport.keySet().containsAll(mandatoryFields)) return false;
        //then check that only mandatory and optional keys are present
        if (passport.keySet().stream().allMatch(key -> mandatoryFields.contains(key) || optionalFields.contains(key)))
            return true;
        //probably i have lost a case of key being present twice
        return false;

    }

    public static Boolean validatePassportFieldsValid(Map<String, String> passport) {
        try {
            Logger.getGlobal().info("validating "+ passport.toString());
            int byr = Integer.parseInt(passport.get("byr"));
            if (byr < 1920 || byr > 2002) {
                return false;
            }

            int iyr = Integer.parseInt(passport.get("iyr"));
            if (iyr < 2010 || iyr > 2020) {
                return false;
            }

            int eyr = Integer.parseInt(passport.get("eyr"));
            if (eyr < 2020 || eyr > 2030) {
                return false;
            }

            String hgt = passport.get("hgt");
            Pattern pattern = Pattern.compile("(?<value>\\d+)(?<measure>cm|in)");
            Matcher matcher = pattern.matcher(hgt);
            if (!matcher.matches()) {
                return false;
            }
            int height = Integer.parseInt(matcher.group("value"));
            String measure = matcher.group("measure");
            if (measure.equals("cm") && (height < 150 || height>193)) {
                return false;
            }
            if (measure.equals("in") && (height < 59 || height>76)) {
                return false;
            }
            // i dont know how it passes regexp check
           // if (!measure.equals("cm") && !measure.equals("in")) return false;

            String hcl = passport.get("hcl");
            Pattern patternH = Pattern.compile("(?:#)(?:[0-9a-f]{6})");
            Matcher matcherH = patternH.matcher(hcl);
            if (!matcherH.matches()) {
                return false;
            }

            String ecl = passport.get("ecl");
            List<String> validEcl = Arrays.asList("amb", "blu", "brn", "gry", "grn", "hzl", "oth");
            if (!validEcl.contains(ecl)) {
                return false;
            }

            String pid = passport.get("pid");
            Pattern patternP = Pattern.compile("(?:[0-9]{9})");
            Matcher matcherP = patternP.matcher(pid);
            if (!matcherP.matches())
            {
                return false;
            }

        } catch (Exception E) {
            //ugly way to handle number format exceptions
            //there were none actually

            E.printStackTrace();
            return false;
        }

        return true;
    }


}
