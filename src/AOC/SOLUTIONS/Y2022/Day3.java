package aoc.solutions.Y2022;

import aoc.solutions.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Day3 extends Solution {
    @Override
    public String solveFirst(List<String> input) {
        long score = 0;
        ArrayList<Character> mixedOnes= new ArrayList<>();
        //find characters that are in both bags
        for (String in:input){
            String a = in.substring(0, in.length() / 2);
            String b = in.substring(in.length() / 2);
            // System.out.println(in+" "+a+" "+b+ " contains"+common);
            mixedOnes.addAll(CommonLetter(a,b));
        }
        //calc the score
        String mapper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (Character c : mixedOnes) {
            score += (mapper.toLowerCase(Locale.ROOT)+mapper.toUpperCase(Locale.ROOT)).indexOf(c.toString())+1;
        }

        return String.valueOf(score);
    }


    @Override
    public String solveSecond(List<String> input) {
        long score = 0;
        ArrayList<Character> mixedOnes= new ArrayList<>();
        //find characters that are in both bags
        int index=0;
        ArrayList<Character> b = new ArrayList<>();
        String a="";
        for (String in:input){
            if (index == 0) {
                a = in;
                //System.out.println(a);
            }
            if (index == 1) {
                //System.out.println(in);
                b=CommonLetter(a,in);
                //System.out.println(b);
            }
            if (index == 2) {
                //System.out.println(in);
                ArrayList<Character> inl = new ArrayList<>();
                in.chars().forEach(y -> inl.add(Character.valueOf((char) y)));
                //System.out.println(inl);
                //System.out.println(CommonLetter(b,inl));
                mixedOnes.addAll(CommonLetter(b,inl));
            }
            index++;
            if (index==3) index=0;
        }
        //calc the score
        String mapper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (Character c : mixedOnes) {
            score += (mapper.toLowerCase(Locale.ROOT)+mapper.toUpperCase(Locale.ROOT)).indexOf(c.toString())+1;
        }

        return String.valueOf(score);
    }

    private ArrayList<Character> CommonLetter(String a,String b) {
        ArrayList<Character> al = new ArrayList<>();
        a.chars().forEach(y -> al.add(Character.valueOf((char) y)));
        ArrayList<Character> bl = new ArrayList<>();
        b.chars().forEach(y -> bl.add(Character.valueOf((char) y)));
        return CommonLetter(al,bl);
    }

    private ArrayList<Character> CommonLetter(ArrayList<Character> al,ArrayList<Character> bl) {
        ArrayList<Character> common= new ArrayList<>();

        for (Character c : al) {

            if (bl.contains(c)) {
                common.remove(c);
                common.add(c);
            }
        }
        return common;
    }
}
