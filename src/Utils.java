package tubes;

public class Utils {
    private static double THRESHOLD = .00000000001;
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a-b) < THRESHOLD;
    }
}