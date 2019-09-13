package tubes;

import static tubes.Console.*;

//camcam
public class ConsoleApp {
    String menu = "
   MENU
1. Sistem Persamaaan Linier
2. Determinan
3. Matriks balikan
4. Matriks kofaktor
5. Adjoin
6. Interpolasi Polinom
7. Keluar
";
    public void selectMenu(int menuIndex) {
        switch (menuIndex) {
            case 1:
                spl();
                break;
            case 2:
                det();
                break;
            case 3:
                bal();
                break;
            case 4:
                kof();
                break;
            case 5:
                adj();
                break;
            case 6:
                pol();
                break;
            case 7:
                exit();
                break;
            default:
                notFound();
                break;            
        }

    }
    public void printMenu() {

    }
    public void ntf() {
        outln("Menu index '" + menuIndex + "' tidak termasuk pilihan index menu!");
    }
    public void exit() {
        System.exit(0);
    }
}