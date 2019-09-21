package tubes;

import java.io.File;
import java.util.Scanner;

import static tubes.Console.*;

//camcam
public class Test {
    // region Test
    private static int succeed, failed, index;
    public static void main(String[] args) {
        useColor();
        outln(yellow + "=====================================");
        outln(yellow + "Testing from test files:");
        doTheTestFromFile();
        outln(yellow + "Testing from hard-coded tests:");
        try {
            doTheTests();
        } catch (Exception e) {
            err("~ Uncaught exception! Error while doing the hard-coded tests.");
        }
        outln(yellow + "=====[" + green + "SUCCEED: " + succeed + yellow + "]====[" + red + "FAILED: " + failed + yellow + "]=====");
    }
    private static boolean check(String msg, boolean value) {
        outln((value ? green : red) + ++index + ". " + (usingColor ? "" : value ? "SUCCEED" : "FAILED ") + (usingColor ? "" : " - ") + msg);
        if (value)
            ++succeed;
        else
            ++failed;
        return value;
    }
    private static void test(String msg, Object a, Object b) {
        if (!check(msg, a.equals(b))) {
            out(yellow);
            outln(a);
            outln(red + "DOESN'T EQUAL");
            out(yellow);
            outln(b);
        }
    }
    private static void doTheTestFromFile() {
        File[] files = new File("./test/").listFiles();
        Scanner scan = null;
        if (files != null)
            for (File f : files) {
                try {
                    scan = new Scanner(f);
                    
                } catch (Exception e) {
                    outln(red + "Skipping file: " + yellow + f);
                    outln(red + "Error: " + e.getMessage());
                }
                if (scan != null)
                    scan.close();
            }
    }
    // endregion
    private static void doTheTests() {
        test("Test awal", new Matrix(new double[][] {
            {1,1},
            {1,2}
        }).getEchelonForm(2), (new Matrix(new double[][] {
            {1,1},
            {0,1}
        })));

        test("Test presisi scaled pivot 1", new Matrix(new double[][] {
            {0.0000000000000003,59.14,59.17},
            {5.29, -6.13, 46.78}
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN), (new SPL(new double[] {
            0,
            31292813d/3128506d,
            5917d/5914d
        })));

        test("Test presisi scaled pivot 2", new Matrix(new double[][] {
            {3,59140,59170},
            {5.29, -6.13, 46.78}
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN), (new SPL(new double[] {
            0,
            312928130d/31286899d,
            31286896d/31286899d
        })));

        test("Cek nilai SPL yang ada 1 free variable di x4", new Matrix(new double[][] {
            { 1, 0, 0, 0, 1 },
            { 0, 1, 0, 0, 2 },
            { 0, 0, 1, 0, 3 },
            { 0, 0, 0, 0, 0 }
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN), (new SPL(new double[][] {
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
        }).getSistemPersamaanLinear(Method.GAUSS_JORDAN), (new SPL(new double[][] {
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
        }).getSistemPersamaanLinear(Method.GAUSS), (new SPL(new double[][] {
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
        }).getSistemPersamaanLinear(Method.CRAMER), (new SPL(new double[][] {
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
        }).getSistemPersamaanLinear(Method.GAUSS), (new SPL(new double[][] {
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
