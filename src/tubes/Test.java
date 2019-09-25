package tubes;

import java.io.File;
import java.math.BigDecimal;
import java.util.Scanner;

import static tubes.Console.*;

/**
 * Class Test digunakan untuk melakukan testing test-case untuk mengecek kevalidan program ini.
 */
public class Test {
    /**
     * @deprecated Tidak dibutuhkan dalam bentuk obyek.
     */
    @Deprecated
    private Test() {}

    /**
     * Jumlah test-case yang sukses.
     */
    private static int succeedCount;

    /**
     * Jumlah test-case yang gagal.
     */
    private static int failedCount;

    /**
     * Indeks test-case.
     */
    private static int index;

    /**
     * Prosedur pertama untuk menjalankan program test.
     * @param args Argumen-argumen dari command line.
     */
    public static void main(String[] args) {
        useColor();
        outln(yellow + "=====================================");
        doTheTestFromFile();
        outln(yellow + "=====[" + green + "SUCCEED: " + succeedCount + yellow + "]====[" + red + "FAILED: " + failedCount + yellow + "]=====");
    }

    /**
     * Menambah index dan jika benar menambah nilai succeed, jika salah menambah nilai failed.
     * @param msg Pesan test.
     * @param value Nilai boolean hasil test.
     * @return Nilai boolean value.
     */
    private static boolean check(String msg, boolean value) {
        outln((value ? green : red) + ++index + ". " + (usingColor ? "" : value ? "SUCCEED" : "FAILED ") + (usingColor ? "" : " - ") + msg);
        if (value)
            ++succeedCount;
        else
            ++failedCount;
        return value;
    }

    /**
     * F.S Mencetak kebenaran suatu test.
     * Jika tidak setara kedua obyek itu dicetak
     * @param msg Pesan test.
     * @param a Obyek komparasi.
     * @param b Obyek komparasi.
     */
    private static void test(String msg, Object a, Object b) {
        boolean test = a.equals(b);
        if (a instanceof BigDecimal && b instanceof BigDecimal)
            test = BD.eqTest((BigDecimal)a, (BigDecimal)b);
        if (!check(msg, test)) {
            out(yellow);
            outln(a);
            outln(red + "EXPECTED:");
            out(yellow);
            outln(b);
        }
    }

    /**
     * F.S mengimplementasikan fungsi fungsi test untuk testing program dengan membandingkan object-object yang seharusnya benar dari pembacaan file-file test.
     */
    private static void doTheTestFromFile() {
        File[] files = new File("test/").listFiles();
        Scanner scan = null;
        if (files != null)
            for (File f : files) {
                if (!f.getName().endsWith(".test"))
                    continue;
                try {
                    outln(yellow + "FILE " + f.getName() + ":");
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
                                if (menuIndex == 1) {
                                    int a = scan.nextInt(), b = scan.nextInt();scan.nextLine();
                                    m = readMatrix(scan, a, b);
                                } else {
                                    int n = scan.nextInt();scan.nextLine();
                                    m = readMatrix(scan, n, n);
                                }
                            else if (menuIndex == 6)
                                res = Point.interpolatePoint(readPoints(scan));
                            expected = scan.nextLine();
                            int a = -1, b = -1;
                            if (menuIndex != 2 && !((String)expected).matches("^[A-Za-z_]+$")) {
                                String[] str = ((String)expected).split(" ");
                                if (str.length > 0)
                                    a = Integer.parseInt(str[0]);
                                if (str.length > 1)
                                    b = Integer.parseInt(str[1]);
                                expected = null;
                            }
                            switch (menuIndex) {
                                case 1:
                                    if (expected == null)
                                        expected = readSPL(scan, a);
                                    res = m.getSistemPersamaanLinear(method);
                                    break;
                                case 2:
                                    expected = new BigDecimal((String)expected);
                                    res = m.getDeterminan(subMenuIndex == 4 ? Method.COFACTOR_EXPANSION : method);
                                    break;
                                case 3:
                                    if (expected == null)
                                        expected = readMatrix(scan, a, b);
                                    res = m.getInverseMatrix(subMenuIndex == 1 ? Method.GAUSS_JORDAN : Method.ADJOIN);
                                    break;
                                case 4:
                                    if (expected == null)
                                        expected = readMatrix(scan, a, b);
                                    res = m.getCofactorMatrix();
                                    break;
                                case 5:
                                    if (expected == null)
                                        expected = readMatrix(scan, a, b);
                                    res = m.getAdjointMatrix();
                                    break;
                                case 6:
                                    if (expected == null)
                                        expected = readSPL(scan, a);
                                    break;
                                default:
                                    continue;
                            }
                            test(msg, res, expected);
                        } catch (Exception e) {
                            if (e instanceof MatrixException)
                                test(msg, ((MatrixException)e).errorType.identifier, expected);
                            else
                                throw e;
                        }
                    }
                } catch (Exception e) {
                    outln(red + ++index + ". Skipping file: " + yellow + f);
                    outln(red + "Error: " + e.getMessage());
                    ++failedCount;
                    e.printStackTrace();
                }
            }
    }

    /**
     * Membaca input dari Scanner scan ke bentuk SPL.
     * @param scan Scanner untuk dibaca inputnya.
     * @param n Jumlah persamaan.
     * @return Sebuah obyek SPL hasil membaca scan.
     */
    private static SPL readSPL(Scanner scan, int n) {
        BigDecimal[][] sol = new BigDecimal[n][n+1];
        for (int i = 0; i < n; i++)
            for (int j = 0; j <= n; j++)
                sol[i][j] = new BigDecimal(scan.next());
        return new SPL(sol);
    }

    /**
     * Membaca input dari Scanner scan ke bentuk matriks berukuran r x c.
     * @param scan Scanner untuk dibaca inputnya.
     * @param r Ukuran baris matriks.
     * @param c Ukuran kolom matriks.
     * @return Sebuah obyek matriks hasil membaca scan.
     */
    private static Matrix readMatrix(Scanner scan, int r, int c) {
        Matrix m = new Matrix(r, c);
        for (int i = 1; i <= r; i++) {
            String[] line = scan.nextLine().split(" ");
            for (int j = 1; j <= c; j++)
                m.setElement(i, j, new BigDecimal(line[j-1]));
        }
        return m;
    }

    /**
     * Membaca input dari Scanner scan ke bentuk array of point.
     * @param scan Scanner untuk dibaca inputnya.
     * @return Sebuah array of point hasil membaca scan.
     */
    private static Point[] readPoints(Scanner scan) {
        int n = scan.nextInt();scan.nextLine();
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++)
            pts[i] = new Point(new BigDecimal(scan.next()), new BigDecimal(scan.next()));
        scan.nextLine();
        return pts;
    }
}
