package tubes;

import java.io.FileWriter;

import static tubes.Console.*;

/**
 * Class ConsoleApp untuk menjalankan program utama di konsol.
 */
public class ConsoleApp {
    /**
     * @deprecated Tidak dibutuhkan dalam bentuk obyek.
     */
    @Deprecated
    private ConsoleApp() {}

    /**
     * String list menu.
     */
    private static final String menu = "MENU:\n"
                + "1. Sistem Persamaaan Linier.\n"
                + "2. Determinan.\n"
                + "3. Matriks balikan.\n"
                + "4. Matriks kofaktor.\n"
                + "5. Adjoin.\n"
                + "6. Interpolasi Polinom.\n"
                + "7. Keluar.";

    /**
     * String list submenu dari menu sistem persamaan linier.
     */
    private static final String subSPLMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss.\n"
                + "2. Metode eliminasi Gauss-Jordan.\n"
                + "3. Metode matriks balikan.\n"
                + "4. Kaidah Cramer.";

    /**
     * String list submenu dari menu determinan.
     */
    private static final String subDeterminanMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss.\n"
                + "2. Metode eliminasi Gauss-Jordan.\n"
                + "3. Metode matriks balikan.\n"
                + "4. Metode ekspansi kofaktor.";

    /**
     * String list submenu dari menu inverse.
     */
    private static final String subInverseMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss-Jordan.\n"
                + "2. Metode adjoin.";

    /**
     * Prosedur menjalankan program.<br>
     * Memanggil prosedur printMenu, selectMenu, error, enterToContinue dan memanggil fungsi ini kembali
     */
    public static void start() {
        try {
            printMenu();
            out("Pilih menu: ");
            int menuIndex = num(), subMenuIndex = -1;line();
            if (menuIndex < 1 || menuIndex > 7)
                throw new RuntimeException("Menu " + menuIndex + " tidak ada!");
            String fileName = "";
            if (menuIndex >= 1 && menuIndex <= 3) {
                outln();
                printSubMenu(menuIndex);
                out("Pilih metode: ");
                subMenuIndex = num();line();
                if (menuIndex >= 1 && menuIndex <= 2 && subMenuIndex > 4 || menuIndex == 3 && subMenuIndex > 2 || subMenuIndex < 1)
                    throw new RuntimeException("Index \"" + subMenuIndex + "\" tidak termasuk pilihan index menu metode!");
            }
            if (menuIndex != 7) {
                out("Nama file input (kosongkan untuk menulis sendiri): ");
                fileName = line();
            }
            selectMenu(menuIndex, subMenuIndex, fileName);
        } catch (Exception e) {
            error(e);
            enterToContinue();
            start();
        }
    }

    /**
     * Prosedur memilih pilihan menu.
     * Memanggil fungsi-fungsi sesuai menu yang dipilih.
     * @param menuIndex Pilihan indeks menu utama.
     * @param subMenuIndex Pilihan indeks menu metode.
     * @param fileName Nama file input.
     */
    private static void selectMenu(int menuIndex, int subMenuIndex, String fileName) {
        try {
            boolean useFile = !fileName.isBlank();
            Matrix m = null;
            if (menuIndex >= 1 && menuIndex <= 5) {
                if (useFile)
                    m = Matrix.readFile(fileName);
                else if (menuIndex == 1)
                    m = readMatrix();
                else
                    m = readSquareMatrix();
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

    /**
     * Fungsi membaca matriks,
     * dimulai dari membaca jumlah kolom dan baris.
     * @return Sebuah matriks.
     * @throws Exception Saat input tidak valid.
     */
    private static Matrix readMatrix() throws Exception {
        out("Ukuran baris(row): ");
        int r = num();line();
        out("Ukuran kolom(column): ");
        int c = num();line();
        return readMatrix(r, c);
    }

    /**
     * Fungsi membaca matriks bujur sangkar,
     * dimulai dari membaca sisi matrix.
     * @return Sebuah matriks bujur sangkar.
     * @throws Exception Saat input tidak valid.
     */
    private static Matrix readSquareMatrix() throws Exception {
        out("Ukuran matriks: ");
        int size = num();line();
        return readMatrix(size, size);
    }

    /**
     * Fungsi membaca elemen matriks dengan dimensi r x c.
     * @param r Panjang baris.
     * @param c Panjang kolom.
     * @return Sebuah matriks.
     * @throws Exception Saat input tidak valid.
     */
    private static Matrix readMatrix(int r, int c) throws Exception {
        outln("Masukkan matriks:");
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

    /**
     * Fungsi membaca point.
     * @return Sebuah array point.
     */
    private static Point[] readPoints() {
        out("Banyak point: ");
        int n = num();line();
        outln("Masukkan point:");
        Point[] pts = new Point[n];
        for (int i = 0; i < n; i++)
            pts[i] = new Point(real(), real());
        return pts;
    }

    /**
     * Prosedur untuk memberi pilihan enter untuk melanjutkan proses program.
     */
    private static void enterToContinue() {
        out("Tekan enter untuk kembali ke menu.");
        line();
        clear();
    }

    /**
     * Prosedur mencetak daftar pilihan menu.
     */
    private static void printMenu() {
        outln(menu);
    }

    /**
     * Prosedur mencetak daftar pilihan submenu.
     * @param menu Indeks submenu.
     */
    private static void printSubMenu(int menu) {
        if (menu == 1)
            outln(subSPLMenu);
        else if (menu == 2)
            outln(subDeterminanMenu);
        else if (menu == 3)
            outln(subInverseMenu);
    }

    /**
     * Prosedur untuk menampilkan jenis error jika terjadi.
     * @param e Obyek error dengan tipe data Exception.
     */
    private static void error(Exception e) {
        out(red + "Error: ");
        if (e instanceof MatrixException) {
            err(((MatrixException)e).errorType.msg);
        } else {
            err(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Prosedur penulisan suatu obyek pada file.
     * @param fileName Nama file.
     * @param o Obyek yang akan ditulis.
     * @throws Exception Jika akses file gagal.
     */
    private static void writeFile(String fileName, Object o) throws Exception {
        FileWriter writer = new FileWriter(fileName);
        for (char c : o.toString().toCharArray())
            writer.write(c);
        writer.close();
    }
}
