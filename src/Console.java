package tubes;

import java.util.*;

//camcam
public class Console {
    public static String green = "", red = "", yellow = "", white = "";
    public static boolean usingColor = false;
    private static Scanner scan = new Scanner(System.in);
    public static void useColor() {
        usingColor = true;
        green = "\u001b[1;32m";
        red = "\u001b[1;31m";
        yellow = "\u001b[1;33m";
        white = "\u001b[0;37m";
    }
    public static void outln() {
        out("\n");
    }
    public static void outln(Object o) {
        out(white + o + white);
        outln();
    }
    public static void err(Object o) {
        outln(red + o);
    }
    public static void out(Object o) {
        System.out.print(o);
    }
    public static int num() {
        return scan.nextInt();
    }
    public static double real() {
        return scan.nextDouble();
    }
    public static String next() {
        return scan.next();
    }
    public static String line() {
        return scan.nextLine();
    }
    public static boolean hasNextLine() {
        return scan.hasNextLine();
    }
    public static void clear() {
        outln("\033[H\033[2J");
    }
}