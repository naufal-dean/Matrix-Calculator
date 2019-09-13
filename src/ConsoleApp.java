package tubes;

import static tubes.Console.*;

public class ConsoleApp {
//    MENU
// 1. Sistem Persamaaan Linier
// 2. Determinan
// 3. Matriks balikan
// 4. Matriks kofaktor
// 5. Adjoin
// 6. Interpolasi Polinom
// 7. Keluar
    private Matrix matrix;
    public ConsoleApp() {
        this.matrix = new Matrix(5, 5);
        init();
    }
    public ConsoleApp(Matrix matrix) {
        this.matrix = matrix;
        init();
    }
    public void init() {
        Console.init();
    }
    public void selectMenu(int menuIndex) {
        switch (menuIndex) {
            case 1:

                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            case 7:

                break;
            default:
                outln("Menu index '" + menuIndex + "' tidak termasuk pilihan index menu!");
                break;            
        }
    }
    public void exit() {
        Console.close();
        System.exit(0);
    }
}