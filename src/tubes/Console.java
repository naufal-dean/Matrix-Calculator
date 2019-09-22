package tubes;

import java.util.*;

/**
 * Class Console untuk menghasilkan output di konsol dengan bantuan kustomisasi warna dan juga membaca input dari pengguna.
 */
public class Console {
    private Console() {}
    /**
     * String warna untuk mewarnai output.
     */
    public static String green = "", red = "", yellow = "", white = "";
    /**
     * Status penggunaan warna.
     */
    public static boolean usingColor = false;
    /**
     * Scanner untuk membaca input dari pengguna.
     */
    private static Scanner scan = new Scanner(System.in);
    /**
     * F.S Mengaktifkan penggunaan warna.
     */
    public static void useColor() {
        usingColor = true;
        green = "\u001b[1;32m";
        red = "\u001b[1;31m";
        yellow = "\u001b[1;33m";
        white = "\u001b[0;37m";
    }
    /**
     * F.S Mencetak baris baru.
     */
    public static void outln() {
        out("\n");
    }
    /**
     * F.S Mencetak sebuah obyek dilanjutkan dengan mencetak baris baru.
     * @param o Obyek yang akan dikeluarkan pada konsol.
     */
    public static void outln(Object o) {
        out(white + o + white);
        outln();
    }
    /**
     * F.S Mengeluarkan pesan error pada konsol dengan tambahan warna merah.
     * @param o Obyek yang akan dikeluarkan pada konsol.
     */
    public static void err(Object o) {
        outln(red + o);
    }
    /**
     * F.S Mencetak sebuah obyek tanpa mencetak baris baru.
     * @param o Obyek yang akan dikeluarkan pada konsol.
     */
    public static void out(Object o) {
        System.out.print(o);
    }
    /**
     * F.S Membaca sebuah integer.
     * @return Sebuah bilangan bulat dengan tipe data integer.
     */
    public static int num() {
        return scan.nextInt();
    }
    /**
     * F.S Membaca sebuah double/real.
     * @return Sebuah bilangan riil dengan tipe data double.
     */
    public static double real() {
        return scan.nextDouble();
    }
    /**
     * F.S Membaca string yang terpisah spasi.
     * @return Sebuah kata dengan tipe data string.
     */
    public static String next() {
        return scan.next();
    }
    /**
     * F.S Membaca string dalam sebaris utuh.
     * @return Sebuah string dalam sebaris utuh.
     */
    public static String line() {
        return scan.nextLine();
    }
    /**
     * F.S Mengosongkan konsol.
     */
    public static void clear() {
        outln("\033[H\033[2J");
    }
}