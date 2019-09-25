package tubes;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Class BD digunakan untuk membantu operasi-operasi BigDecimal yang dibutuhkan oleh program.
 */
public class BD {
    /**
     * @deprecated Tidak dibutuhkan dalam bentuk obyek.
     */
    @Deprecated
    private BD() {}

    /**
     * Konstanta untuk galat komparasi bilangan riil.
     */
    public static final int SCALE = 100;

    /**
     * Konstanta untuk ketelitian komparasi.
     */
    public static final int PRECISION = 16;

    /**
     * Konstanta untuk ketelitian komparasi saat test.
     */
    public static final int PRECISION_TEST = 16;

    /**
     * Menghasilkan true apabila a dan b bernilai sama dengan galat PRECISION.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi bilangan riil a dan b.
     */
    public static boolean eq(BigDecimal a, BigDecimal b) {
        return a.setScale(PRECISION, RoundingMode.HALF_UP).compareTo(b.setScale(PRECISION, RoundingMode.HALF_UP)) == 0;
    }

    /**
     * Menghasilkan true apabila a dan b bernilai sama dengan galat PRECISION_TEST.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi bilangan riil a dan b.
     */
    public static boolean eqTest(BigDecimal a, BigDecimal b) {
        return a.setScale(PRECISION_TEST, RoundingMode.HALF_UP).compareTo(b.setScale(PRECISION_TEST, RoundingMode.HALF_UP)) == 0;
    }

    /**
     * Menghasilkan true apabila a bernilai 0 dengan galat PRECISION.
     * @param a Bilangan riil.
     * @return Nilai boolean hasil komparasi bilangan riil a dengan 0.
     */
    public static boolean eq0(BigDecimal a) {
        return eq(a, BigDecimal.ZERO);
    }

    /**
     * Menghasilkan true apabila a bernilai 1 dengan galat PRECISION.
     * @param a Bilangan riil.
     * @return Nilai boolean hasil komparasi bilangan riil a dengan 1.
     */
    public static boolean eq1(BigDecimal a) {
        return eq(a, BigDecimal.ONE);
    }

    /**
     * Menghasilkan true apabila a bernilai lebih kecil daripada b.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi.
     */
    public static boolean lt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }

    /**
     * Menghasilkan true apabila a bernilai lebih besar daripada b.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi.
     */
    public static boolean gt(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) > 0;
    }

    /**
     * Menghasilkan true apabila a bernilai lebih kecil sama dengan b.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi.
     */
    public static boolean lte(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }

    /**
     * Menghasilkan true apabila a bernilai lebih besar sama dengan b.
     * @param a Bilangan riil.
     * @param b Bilangan riil.
     * @return Nilai boolean hasil komparasi.
     */
    public static boolean gte(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) >= 0;
    }

    /**
     * Menghasilkan string format bilangan riil dengan 2 angka di belakang koma.
     * @param a Bilangan riil.
     * @return Sebuah string hasil format.
     */
    public static String format(BigDecimal a) {
        return a.setScale(2, RoundingMode.HALF_UP).toString();
    }

    /**
     * Menghasilkan string format bilangan riil dengan 8 angka di belakang koma.
     * @param a Bilangan riil.
     * @return Sebuah string hasil format.
     */
    public static String formatLong(BigDecimal a) {
        return a.setScale(8, RoundingMode.HALF_UP).toString();
    }
}
