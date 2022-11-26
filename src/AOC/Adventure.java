package AOC;

import AOC.SOLUTIONS.Solution;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Adventure {
    private static Adventure advent;
    private final List<String> input;
    private final String textExpectation2;
    private final String textExpectation1;
    private final List<String> inputTest;
    private String date;

    private static final int FIRST = 1;
    private static final int SECOND = 2;

    public Adventure (String d) {
        date = d;
        input = getFileByLines(date+"/Input");
        List<String> testData = getFileByLines(date + "/Test");
        textExpectation2= testData.remove(testData.size()-1).substring(3);
        textExpectation1= testData.remove(testData.size()-1).substring(3);
        inputTest = testData;
    }

    public static Adventure load (String date) {
        if (advent == null) advent = new Adventure(date);
        if (advent.date != date) advent = new Adventure(date);
        return advent;
    }

    public static List<String> getFileByLines(String filename) {
        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource(filename).toURI()))) {
            result = lines.collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return result;
    }

    private boolean test(int var) {
        if (var == FIRST) return getSolution(date).solveFirst(inputTest).equals(textExpectation1);
        if (var == SECOND) return getSolution(date).solveSecond(inputTest).equals(textExpectation2);
        return false;
    }

    private String solution(int var) {
        if (var == FIRST) return getSolution(date).solveFirst(input);
        if (var == SECOND) return getSolution(date).solveSecond(input);
        return null;
    }
    private Solution getSolution(String date) {
        try {
            Class<? extends Solution> solutionClass = Class.forName("AOC.SOLUTIONS.Y"+date.replace("/","."))
                    .asSubclass(Solution.class);
            return (Solution) solutionClass.getConstructors()[0].newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static String firstTrial(String date) {
        return Adventure.load(date).solution(FIRST);
    }

    public static String secondTrial(String date) {
        return Adventure.load(date).solution(SECOND);
    }

    public static boolean firstTest(String date) {
        return Adventure.load(date).test(FIRST);
    }

    public static boolean secondTest(String date) {
        return Adventure.load(date).test(SECOND);
    }
}
