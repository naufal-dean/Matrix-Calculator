package tubes;

import java.io.FileWriter;

import static tubes.Console.*;

//camcam
public class ConsoleApp {
    private static final String menu = "MENU:\n"
                + "1. Sistem Persamaaan Linier.\n"
                + "2. Determinan.\n"
                + "3. Matriks balikan.\n"
                + "4. Matriks kofaktor.\n"
                + "5. Adjoin.\n"
                + "6. Interpolasi Polinom.\n"
                + "7. Keluar.";
    private static final String subSPLMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss.\n"
                + "2. Metode eliminasi Gauss-Jordan.\n"
                + "3. Metode matriks balikan.\n"
                + "4. Kaidah Cramer.";
    private static final String subDeterminanMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss.\n"
                + "2. Metode eliminasi Gauss-Jordan.\n"
                + "3. Metode matriks balikan.\n"
                + "4. Metode ekspansi kofaktor.";
    private static final String subInverseMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss-Jordan.\n"
                + "2. Metode adjoin.";

    public static void start() {
        try {
            printMenu();
            out("Pilih menu: ");
            int menu = num(), subMenu = -1;line();
            if (menu < 1 || menu > 7)
                throw new RuntimeException("Menu " + menu + " tidak ada!");
            String fileName = "";
            if (menu >= 1 && menu <= 3) {
                outln();
                printSubMenu(menu);
                out("Pilih metode: ");
                subMenu = num();line();
                if (menu >= 1 && menu <= 2 && subMenu > 4 || menu == 3 && subMenu > 2 || subMenu < 1)
                    throw new RuntimeException("Index \"" + subMenu + "\" tidak termasuk pilihan index menu metode!");
            }
            if (menu != 7) {
                out("Nama file input (kosongkan untuk menulis sendiri): ");
                fileName = line();
            }
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
            Matrix m = null;
            if (menuIndex >= 1 && menuIndex <= 5) {
                if (useFile)
                    m = Matrix.readFile(fileName);
                else if (menuIndex >= 2 && menuIndex <= 4)
                    m = readSquareMatrix();
                else
                    m = readMatrix();
            }
            Object res = null;
            Method method = subMenuIndex > 0 ? Method.values()[subMenuIndex-1] : null;
            switch (menuIndex) {
                case 1:
                    res = m.getSistemPersamaanLinear(method);
                    outln("Solusi-nya:");
                    outln(res);
                    break;
                case 2:
                    res = m.getDeterminan(subMenuIndex == 4 ? Method.COFACTOR_EXPANSION : method);
                    outln("Determinan-nya:");
                    outln(res);
                    break;
                case 3:
                    res = m.getInverseMatrix(subMenuIndex == 1 ? Method.GAUSS_JORDAN : Method.ADJOIN);
                    outln("Matrix inverse-nya:");
                    outln(res);
                    break;
                case 4:
                    res = m.getCofactorMatrix();
                    outln("Matrix kofaktor-nya:");
                    outln(res);
                    break;
                case 5:
                    res = m.getAdjointMatrix();
                    outln("Matrix adjoin-nya:");
                    outln(res);
                    break;
                case 6:
                    res = Point.interpolatePoint(useFile ? Point.readFile(fileName) : readPoints()).toPersamaanString();
                    outln("Hasil interpolasi poin:");
                    outln(res);
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    outln("Index \"" + menuIndex + "\" tidak termasuk pilihan index menu!");
                    break;
            }
            if (res != null) {
                out("Nama file output (kosongkan jika tidak perlu): ");
                fileName = line();
                if (!fileName.isBlank())
                    writeFile(fileName, res);
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
        int r = num();line();
        out("Ukuran kolom(column): ");
        int c = num();line();
        return readMatrix(r, c);
    }

    private static Matrix readSquareMatrix() throws Exception {
        out("Ukuran matriks: ");
        int size = num();line();
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
        int n = num();line();
        outln("Masukkan point:");
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++)
            pts[i] = new Point(real(), real());
        return pts;
    }

    private static void enterToContinue() {
        out("Tekan enter untuk kembali ke menu.");
        line();
        clear();
    }

    private static void printMenu() {
        outln(menu);
    }

    private static void printSubMenu(int menu) {
        if (menu == 1)
            outln(subSPLMenu);
        else if (menu == 2)
            outln(subDeterminanMenu);
        else if (menu == 3)
            outln(subInverseMenu);
    }

    private static void error(Exception e) {
        out(red + "Error: ");
        if (e instanceof MatrixException) {
            err(((MatrixException)e).errorType.msg);
        } else {
            err(e.getMessage());
        }
    }

    private static void writeFile(String fileName, Object o) throws Exception {
        FileWriter writer = new FileWriter(fileName);
        for (char c : o.toString().toCharArray())
            writer.write(c);
        writer.close();
    }
}
