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
                + "7. Keluar\n";
    private static final String subMenu = "Pilihan metode:\n"
                + "1. Metode eliminasi Gauss\n"
                + "2. Metode eliminasi Gauss-Jordan\n"
                + "3. Metode matriks balikan\n"
                + "4. Kaidah Cramer\n";

    public static void start() {
        printMenu();
        out("Pilih menu: ");
        selectMenu(num());
    }
    
    private static void selectMenu(int menuIndex) {
        switch (menuIndex) {
            case 1:
                // spl();
                break;
            case 2:
                // det();
                break;
            case 3:
                // bal();
                break;
            case 4:
                // kof();
                break;
            case 5:
                // adj();
                break;
            case 6:
                // pol();
                break;
            case 7:
                System.exit(0);
                break;
            default:
                outln("Menu index '" + menuIndex + "' tidak termasuk pilihan index menu!");
                break;
        }
        start();
    }

    private static void printMenu() {
        outln(menu);
    }
}
