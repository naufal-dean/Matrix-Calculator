package tubes;

/**
 * Class MatrixException untuk tipe error pada pengoperasian ataupun jalannya implementasi matriks.
 */
public class MatrixException extends RuntimeException {
    /**
     * Sebuah unique serial version identifier
     */
    private static final long serialVersionUID = 1L;

    /**
     * Tipe error exception ini.
     */
    public MatrixErrorIdentifier errorType;

    /**
     * Konstruktor MatrixException.
     * @param errorType Tipe error.
     */
    public MatrixException(MatrixErrorIdentifier errorType) {
        this.errorType = errorType;
    }
}