package tubes;

import java.io.File;
import java.util.Scanner;

import static tubes.Console.*;

public class Test {
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
            e.printStackTrace();
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
        boolean test = a.equals(b);
        if (a instanceof Double && b instanceof Double)
            test = Utils.doubleEquals((double)a, (double)b);
        if (!check(msg, test)) {
            out(yellow);
            outln(a);
            outln(red + "DOESN'T EQUAL");
            out(yellow);
            outln(b);
        }
    }
    private static void doTheTestFromFile() {
        File[] files = new File("test/").listFiles();
        Scanner scan = null;
        if (files != null)
            for (File f : files) {
                if (!f.getName().endsWith(".test"))
                    continue;
                try {
                    scan = new Scanner(f);
                    while (scan.hasNextLine()) {
                        String msg = scan.nextLine();
                        while (msg.isEmpty() && scan.hasNextLine())
                            msg = scan.nextLine();
                        if (msg.isEmpty())
                            break;
                        Object expected = null;
                        try {
                            int menuIndex = scan.nextInt(), subMenuIndex = -1;
                            if (menuIndex >= 1 && menuIndex <= 3)
                                subMenuIndex = scan.nextInt();
                            scan.nextLine();
                            Object res = null;
                            Method method = subMenuIndex > 0 ? Method.values()[subMenuIndex-1] : null;
                            Matrix m = null;
                            if (menuIndex >= 1 && menuIndex <= 5)
                                if (menuIndex == 1)
                                    m = readMatrix(scan);
                                else
                                    m = readSquareMatrix(scan);
                            switch (menuIndex) {
                                case 1:
                                    res = m.getSistemPersamaanLinear(method);
                                    expected = readSPL(scan);
                                    break;
                                case 2:
                                    res = m.getDeterminan(subMenuIndex == 4 ? Method.COFACTOR_EXPANSION : method);
                                    expected = scan.nextDouble();scan.nextLine();
                                    break;
                                case 3:
                                    res = m.getInverseMatrix(subMenuIndex == 1 ? Method.GAUSS_JORDAN : Method.ADJOIN);
                                    expected = readSquareMatrix(scan);
                                    break;
                                case 4:
                                    res = m.getCofactorMatrix();
                                    expected = readSquareMatrix(scan);
                                    break;
                                case 5:
                                    res = m.getAdjointMatrix();
                                    expected = readSquareMatrix(scan);
                                    break;
                                case 6:
                                    res = Point.interpolatePoint(readPoints(scan)).toPersamaanString();
                                    expected = scan.nextLine();
                                    break;
                                default:
                                    continue;
                            }
                            test(msg, expected, res);
                        } catch (Exception e) {
                            if (e instanceof MatrixException && expected == null)
                                test(msg, scan.nextLine(), ((MatrixException)e).errorType.identifier);
                            else
                                throw e;
                        }
                    }
                } catch (Exception e) {
                    outln(red + "Skipping file: " + yellow + f);
                    outln(red + "Error: " + e.getMessage());
                }
            }
    }
    private static SPL readSPL(Scanner scan) {
        int n = scan.nextInt();scan.nextLine();
        double[][] sol = new double[n+1][n+1];
        for (int i = 1; i <= n; i++)
            for (int j = 0; j <= n; j++)
                sol[i][j] = scan.nextDouble();
        scan.nextLine();
        return new SPL(sol);
    }
    private static Matrix readMatrix(Scanner scan) {
        int r = scan.nextInt(), c = scan.nextInt();scan.nextLine();
        return readMatrix(scan, r, c);
    }
    private static Matrix readSquareMatrix(Scanner scan) {
        int size = scan.nextInt();scan.nextLine();
        return readMatrix(scan, size, size);
    }
    private static Matrix readMatrix(Scanner scan, int r, int c) {
        Matrix m = new Matrix(r, c);
        for (int i = 1; i <= r; i++) {
            String[] line = scan.nextLine().split(" ");
            for (int j = 1; j <= c; j++)
                m.setElement(i, j, Float.parseFloat(line[j-1]));
        }
        return m;
    }
    private static Point[] readPoints(Scanner scan) {
        int n = scan.nextInt();scan.nextLine();
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++)
            pts[i] = new Point(scan.nextDouble(), scan.nextDouble());
        return pts;
    }
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
