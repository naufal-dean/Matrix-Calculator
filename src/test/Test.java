package tubes.test;

import static tubes.Console.*;

//camcam
public class Test {
    // region Test
    private static String green = "", red = "", yellow = "";
    private static boolean usingColor = false;
    private static int succeed, failed, index;
    public static void main(String[] args) {
        useColor();
        outln(yellow + "=====================================");
        doFileTests();
        doTheTests();
        outln(yellow + "=====[" + green + "SUCCEED: " + succeed + yellow + "]====[" + red + "FAILED: " + failed + yellow + "]=====");
    }
    private static void useColor() {
        usingColor = true;
        green = "\u001b[1;32m";
        red = "\u001b[1;31m";
        yellow = "\u001b[1;33m";
    }
    private static void test(String msg, boolean value) {
        outln((value ? green : red) + ++index + ". " + (usingColor ? "" : value ? "SUCCEED" : "FAILED ") + (usingColor ? "" : " - ") + msg);
        if (value) ++succeed; else ++failed;
    }
    private static void doFileTests() {
        
    }
    // endregion
    private static void doTheTests() {
        test("lmao", false);
    }
}