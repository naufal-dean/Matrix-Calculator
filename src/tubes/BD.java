package tubes;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class Utils digunakan untuk membantu operasi-operasi dengan algoritma yang dibutuhkan oleh program.
 */
public class BD {
    /**
     * @deprecated Tidak dibutuhkan dalam bentuk obyek.
     */
    @Deprecated
    private BD() {}

    /**
     * Konstanta THRESHOLD untuk galat komparasi bilangan riil.
     */
    public static final int PRECISION = 16;

    public static final int SCALE = 100;

    /**
     * Menghasilkan true apabila a dan b bernilai sama dengan galat THRESHOLD.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi bilangan riil a dan b.
     */
    public static boolean eq(BigDecimal a, BigDecimal b) {
        return a.setScale(PRECISION, RoundingMode.HALF_UP).compareTo(b.setScale(PRECISION, RoundingMode.HALF_UP)) == 0;
    }

    public static boolean eqTest(BigDecimal a, BigDecimal b) {
        return a.setScale(2, RoundingMode.HALF_UP).compareTo(b.setScale(2, RoundingMode.HALF_UP)) == 0;
    }

    public static boolean eq0(BigDecimal a) {
        return eq(a, BigDecimal.ZERO);
    }

    public static boolean eq1(BigDecimal a) {
        return eq(a, BigDecimal.ONE);
    }

    public static boolean lt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    public static boolean gt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    public static boolean lte(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }

    public static boolean gte(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0;
    }

    public static String format(BigDecimal a) {
        return a.setScale(2, RoundingMode.HALF_UP).toString();
    }

    public static String formatLong(BigDecimal a) {
        return a.setScale(8, RoundingMode.HALF_UP).toString();
    }
}
