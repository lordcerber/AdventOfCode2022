package adventure;

import aoc.Adventure;
import org.testng.Assert;

import java.util.logging.Logger;

public class AdventureTest {

    public static void test(String date) {
        Logger.getGlobal().info("Run first trial with test data");
        Assert.assertTrue(Adventure.firstTest(date));
        Logger.getGlobal().info("Solve first riddle");
        Assert.assertNotNull(Adventure.firstTrial(date));
        Logger.getGlobal().info("Run second trial with test data");
        Assert.assertTrue(Adventure.secondTest(date));
        Logger.getGlobal().info("Solve second riddle");
        Assert.assertNotNull(Adventure.secondTrial(date));
    }
}
