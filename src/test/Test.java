package tubes.test;

import static tubes.Console.*;
import tubes.*;

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
    private static double roundDouble(double num, int decPlace) {
        num = num*Math.pow(10, decPlace);
        num = Math.round(num);
        return (num/Math.pow(10, decPlace));
    }
    // endregion
    private static void doTheTests() {
        test("Test awal", new Matrix(new double[][] {
            {1,1},
            {1,2}
        }).getEchelonForm(2).equals(new Matrix(new double[][] {
            {1,1},
            {0,1}
        })));

        Matrix m = new Matrix(new double[][] {
            {0.0000000000000003,59.14,59.17},
            {5.29, -6.13, 46.78}
        });
        out(m.getSistemPersamaanLinear(Method.GAUSS_JORDAN));
        out(new SPL(new double[] {
            0,
            Test.roundDouble(31292813d/3128506d, 10),
            Test.roundDouble(5917d/5914d, 10)
        }));

        test("Test scaled pivot", new Matrix(new double[][] {
            {0.0000000000000003,59.14,59.17},
            {5.29, -6.13, 46.78}
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN).equals(new SPL(new double[] {
            0,
            Test.roundDouble(31292813d/3128506d, 10),
            Test.roundDouble(5917d/5914d, 10)
        })));
    }
}
