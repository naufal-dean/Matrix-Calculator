package tubes;

public class Utils {
    private static final double THRESHOLD = 1e-3;
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a-b) < THRESHOLD;
    }
}