package tubes;

import static tubes.Console.*;

//camcam
public class ConsoleApp {
    String menu = "MENU\n"
                + "1. Sistem Persamaaan Linier\n"
                + "2. Determinan\n"
                + "3. Matriks balikan\n"
                + "4. Matriks kofaktor\n"
                + "5. Adjoin\n"
                + "6. Interpolasi Polinom\n"
                + "7. Keluar\n"
                + "\n";

    public void selectMenu(int menuIndex) {
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
                // exit();
                break;
            default:
                // notFound();
                break;
        }

    }
    public void printMenu() {

    }
    public void ntf() {
        // outln("Menu index '" + menuIndex + "' tidak termasuk pilihan index menu!");
    }
    public void exit() {
        System.exit(0);
    }
}
