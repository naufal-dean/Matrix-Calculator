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
        doTheTests();
        outln(yellow + "=====[" + green + "SUCCEED: " + succeed + yellow + "]====[" + red + "FAILED: " + failed + yellow + "]=====");
    }
    private static void useColor() {
        usingColor = true;
        green = "\u001b[1;32m";
        red = "\u001b[1;31m";
        yellow = "\u001b[1;33m";
    }
    private static boolean test(String msg, boolean value) {
        outln((value ? green : red) + ++index + ". " + (usingColor ? "" : value ? "SUCCEED" : "FAILED ") + (usingColor ? "" : " - ") + msg);
        if (value) ++succeed; else ++failed;
        return value;
    }
    private static void test(String msg, Object a, Object b) {
        if (!test(msg, a.equals(b))) {
            outln(a);
            outln("IS NOT EQUAL");
            outln(b);
        }
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

        test("Test presisi scaled pivot 1", new Matrix(new double[][] {
            {0.0000000000000003,59.14,59.17},
            {5.29, -6.13, 46.78}
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN).equals(new SPL(new double[] {
            0,
            Test.roundDouble(31292813d/3128506d, 16),
            Test.roundDouble(5917d/5914d, 16)
        })));

        test("Test presisi scaled pivot 2", new Matrix(new double[][] {
            {3,59140,59170},
            {5.29, -6.13, 46.78}
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN).equals(new SPL(new double[] {
            0,
            Test.roundDouble(312928130d/31286899d, 16),
            Test.roundDouble(31286896d/31286899d, 16)
        })));

        test("Cek nilai SPL yang ada 1 free variable di x4", new Matrix(new double[][] {
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 0, 2 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 0, 0 }
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN).equals(new SPL(new double[][] {
            { 1, 0, 0, 0, 0 },
            { 2, 0, 0, 0, 0 },
            { 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1 }
        })));

        test("Cek nilai SPL rownya lebih banyak", new Matrix(new double[][] {
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 0, 2 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN).equals(new SPL(new double[][] {
            { 1, 0, 0, 0, 0 },
            { 2, 0, 0, 0, 0 },
            { 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1 }
        })));

        test("Cek nilai SPL tanpa free variabel dengan metode CRAMER", new Matrix(new double[][] {
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 0, 2 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 1, 0 }
        }).getSistemPersamaanLinear(Method.CRAMER).equals(new SPL(new double[][] {
            { 1, 0, 0, 0, 0 },
            { 2, 0, 0, 0, 0 },
            { 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
        })));

        test("Cek REF", new Matrix(new double[][] {
            {1, 2, -1, -4},
            {2, 3, -1, -11},
            {-2, 0, -3, 22}
        }).getReducedEchelonForm(), (new Matrix(new double[][] {
            {1, 0, 0, -8},
            {0, 1, 0, 1},
            {0, 0, 1, -3}
        })));
        out(new Matrix(new double[][] {
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 0, 2 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 1, 0 }
        }).getSistemPersamaanLinear(Method.CRAMER).content[1].length);
        outln();
        outln(new SPL(new double[][] {
            { 1, 0, 0, 0, 0 },
            { 2, 0, 0, 0, 0 },
            { 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
        }).content[1].length);
    }
}
