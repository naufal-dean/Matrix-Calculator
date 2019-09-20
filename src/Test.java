package tubes;

import static tubes.Console.*;

//camcam
public class Test {
    // region Test
    public static String green = "", red = "", yellow = "", a = "\u001b[1;34m", b = "\u001b[1;35m", c = "\u001b[1;36m", d = "\u001b[1;37m";
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
            out(yellow);
            outln(a);
            outln(red + "DOESN'T EQUAL");
            out(yellow);
            outln(b);
        }
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

        test("Cek nilai SPL(gauss_jordan) rownya lebih banyak", new Matrix(new double[][] {
            { 1, 1, 3, 0, 12 },
            { 0, 1, 1, 0, 5 },
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

        test("Cek nilai SPL(gauss) rownya lebih banyak", new Matrix(new double[][] {
            { 1, 1, 3, 0, 12 },
            { 0, 1, 1, 0, 5 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 },
        }).getSistemPersamaanLinear(Method.GAUSS).equals(new SPL(new double[][] {
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

        test("Cek nilai SPL tanpa free variabel dengan metode GAUSS", new Matrix(new double[][] {
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 0, 2 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 1, 0 }
        }).getSistemPersamaanLinear(Method.GAUSS).equals(new SPL(new double[][] {
            { 1, 0, 0, 0, 0 },
            { 2, 0, 0, 0, 0 },
            { 3, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 0 }
        })));

        test("Cek nilai SPL tanpa free variabel dengan metode GAUSS no telp", new Matrix(new double[][] {
            { 0, 8, 3, 2, 5 },
            { 4, 0, 9, 3, 2 },
            { 9, 1, 0, 8, 5 },
            { 8, 1, 5, 8, 5 }
        }).getSistemPersamaanLinear(Method.GAUSS), new SPL(new double[][] {
            { 95d/727, 0, 0, 0, 0 },
            { 372d/727, 0, 0, 0, 0 },
            { 19d/727, 0, 0, 0, 0 },
            { 301d/727, 0, 0, 0, 0 }
        }));

        test("Cek REF", new Matrix(new double[][] {
            {1, 2, -1, -4},
            {2, 3, -1, -11},
            {-2, 0, -3, 22}
        }).getReducedEchelonForm(), new Matrix(new double[][] {
            {1, 0, 0, -8},
            {0, 1, 0, 1},
            {0, 0, 1, -2}
        }));
    }
}
