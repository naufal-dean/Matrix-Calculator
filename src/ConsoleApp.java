package tubes;

import java.io.FileWriter;

import static tubes.Console.*;

//camcam
public class ConsoleApp {
    private static final String menu = "MENU:\n"
                + "1. Sistem Persamaaan Linier\n"
                + "2. Determinan\n"
                + "3. Matriks balikan\n"
                + "4. Matriks kofaktor\n"
                + "5. Adjoin\n"
                + "6. Interpolasi Polinom\n"
                + "7. Keluar";
    private static final String subMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss\n"
                + "2. Metode eliminasi Gauss-Jordan\n"
                + "3. Metode matriks balikan\n"
                + "4. Kaidah Cramer";

    public static void start() {
        try {
            printMenu();
            out("Pilih menu: ");
            int menu = num(), subMenu = -1;
            String fileName = "";
            if (menu >= 1 && menu <= 3) {
                outln();
                printSubMenu();
                out("Pilih metode: ");
                subMenu = num();
            }
            // Error
            out("Nama file input (kosongkan untuk menulis sendiri): ");
            fileName = next();
            selectMenu(menu, subMenu, fileName);
        } catch (Exception e) {
            error(e);
            enterToContinue();
            start();
        }
    }

    private static void selectMenu(int menuIndex, int subMenuIndex, String fileName) {
        try {
            boolean useFile = !fileName.isBlank();
            if (menuIndex >= 1 && menuIndex <= 3 && (subMenuIndex < 1 || subMenuIndex > 4))
                outln("Index \"" + subMenuIndex + "\" tidak termasuk pilihan index menu metode!");
            else {
                Matrix m = 1 <= menuIndex && menuIndex <= 5 ? useFile ? Matrix.readFile(fileName) : 
                    2 <= menuIndex && menuIndex <= 4 ? readSquareMatrix() : readMatrix() : null;
                Method method = subMenuIndex >= 1 && subMenuIndex <= 4 ? Method.values()[subMenuIndex-1] : null;
                Object res = null;
                switch (menuIndex) {
                    case 1:
                        outln("Solusi-nya:");
                        out(res = m.getSistemPersamaanLinear(method));
                        break;
                    case 2:
                        outln("Determinan-nya:");
                        outln(res = m.getDeterminan(method));
                        break;
                    case 3:
                        outln("Matrix inverse-nya:");
                        outln(res = m.getInverseMatrix(method));
                        break;
                    case 4:
                        outln("Matrix kofaktor-nya:");
                        outln(res = m.getCofactorMatrix());
                        break;
                    case 5:
                        outln("Matrix adjoin-nya:");
                        outln(res = m.getAdjointMatrix());
                        break;
                    case 6:
                        Point[] pts = useFile ? Point.readFile(fileName) : readPoints();
                        outln("Hasil interpolasi poin:");
                        outln(res = Point.interpolatePoint(pts).toPersamaanString());
                        break;
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        outln("Index \"" + menuIndex + "\" tidak termasuk pilihan index menu!");
                        break;
                }
                if (res != null) {
                    // Error
                    out("Nama file output (kosongkan jika tidak perlu): ");
                    fileName = next();
                    if (!fileName.isBlank())
                        writeFile(fileName, res);
                }
            }
        } catch (Exception e) {
            error(e);
        }
        outln();
        enterToContinue();
        start();
    }

    private static Matrix readMatrix() throws Exception {
        out("Ukuran baris(row): ");
        int r = num();
        out("Ukuran kolom(column): ");
        int c = num();
        return readMatrix(r, c);
    }

    private static Matrix readSquareMatrix() throws Exception {
        out("Ukuran matriks: ");
        int size = num();
        return readMatrix(size, size);
    }

    private static Matrix readMatrix(int r, int c) throws Exception {
        outln("Masukkan matrix:");
        Matrix m = new Matrix(r, c);
        for (int i = 1; i <= r; i++) {
            String[] line = line().split(" ");
            for (int j = 1; j <= c; j++)
                m.setElement(i, j, Float.parseFloat(line[j-1]));
        }
        outln("Matrix yang Anda masukkan:");
        outln(m);
        return m;
    }

    private static Point[] readPoints() {
        out("Banyak point: ");
        int n = num();
        outln("Masukkan point:");
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++)
            pts[i] = new Point(real(), real());
        return pts;
    }

    private static void enterToContinue() {
        outln("Tekan enter untuk kembali ke menu.");
        line();
    }

    private static void printMenu() {
        outln(menu);
    }

    private static void printSubMenu() {
        outln(subMenu);
    }

    private static void error(Exception e) {
        outln("Error: " + e.getMessage());
        //debug
        e.printStackTrace();
    }

    private static void writeFile(String fileName, Object o) throws Exception {
        FileWriter writer = new FileWriter(fileName);
        for (char c : o.toString().toCharArray())
            writer.write(c);
        writer.close();
    }
}
