package tubes;


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
            if (menu >= 1 && menu <= 3) {
                outln();
                printSubMenu();
                out("Pilih metode: ");
                subMenu = num();
            }
            selectMenu(menu, subMenu);
        } catch (Exception _e) {
            outln("Error: Input tidak valid!");
            enterToContinue();
            start();
        }
    }

    private static void selectMenu(int menuIndex, int subMenuIndex) {
        if (menuIndex >= 1 && menuIndex <= 3 && (subMenuIndex < 1 || subMenuIndex > 4))
            outln("Index \"" + subMenuIndex + "\" tidak termasuk pilihan index menu metode!");
        else
            switch (menuIndex) {
                case 1:
                    // spl(subMenuIndex);
                    break;
                case 2:
                    // det(subMenuIndex);
                    break;
                case 3:
                    // inv(subMenuIndex);
                    break;
                case 4:
                    // kof();
                    break;
                case 5:
                    // adj();
                    break;
                case 6:
                    // intPol();
                    break;
                case 7:
                    System.exit(0);
                    break;
                default:
                    outln("Index \"" + menuIndex + "\" tidak termasuk pilihan index menu!");
                    break;
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
        Matrix m = new Matrix(r, c);
        for (int i = 1; i <= r; i++) {
            String[] line = line().split(" ");
            for (int j = 1; j <= c; j++)
                m.setElement(i, j, Float.parseFloat(line[j-1]));
        }
        return m;
    }

    private static void enterToContinue() {
        outln("Tekan enter untuk kembali ke menu.");
        line();line();
    }

    private static void printMenu() {
        outln(menu);
    }

    private static void printSubMenu() {
        outln(subMenu);
    }
}
