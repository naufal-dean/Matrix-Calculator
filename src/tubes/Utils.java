package tubes;

/**
 * Class Utils digunakan untuk membantu operasi-operasi dengan algoritma yang dibutuhkan oleh program.
 */
public class Utils {
    /**
     * @deprecated Tidak dibutuhkan dalam bentuk obyek.
     */
    @Deprecated
    private Utils() {}

    /**
     * Konstanta THRESHOLD untuk galat komparasi bilangan riil.
     */
    private static final double THRESHOLD = 1e-2;

    /**
     * Menghasilkan true apabila a dan b bernilai sama dengan galat THRESHOLD.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi bilangan riil a dan b.
     */
    public static boolean doubleEquals(double a, double b) {
        return Math.abs(a-b) < THRESHOLD;
    }
}
