package tubes;

import java.util.*;

//camcam
public class Console {
    private static Scanner scan = new Scanner(System.in);
    public static void outln() {
        out("\n");
    }
    public static void outln(Object o) {
        out(o);
        outln();
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
}