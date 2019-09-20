package tubes;

public class Utils {
    private static double THRESHOLD = 1e-10;
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a-b) < THRESHOLD;
    }
}