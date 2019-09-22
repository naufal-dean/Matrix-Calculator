package tubes;

/**
 * Enum MatrixErrorIdentifier untuk mengidentifikasi tipe jeni error dalam pengoperasian ataupun menjalankan implementasi matriks atau point.
 */
public enum MatrixErrorIdentifier {
    /**
     * Matriks tidak konsisten.
     */
    INCONSISTENT_ERROR("inconsistent_error", "Matrix is inconsistent!"),

    /**
     * Tidak bisa melakukan perkalian matriks.
     */
    MULTIPLY_ERROR("multiply_error", "Column count of the left matrix is not equal to row count of the right matrix!"),

    /**
     * Ukuran matriks tidak sama.
     */
    DIFFERENT_SIZE_ERROR("different_size_error", "Different matrix size!"),

    /**
     * Matriks tidak bujur sangkar.
     */
    NOT_SQUARE_ERROR("not_square_error", "Max row and max column are not the same! No determinant."),

    /**
     * Matriks menghasilkan determinan nol.
     */
    DETERMINANT_ZERO_ERROR("determinant_zero_error", "Determinant is zero."),

    /**
     * Matriks tidak termasuk augmented matriks.
     */
    NOT_AUGMENTED_ERROR("not_augmented_error", "Matrix is not augmented matrix!"),

    /**
     * Tidak bisa melakukan interpolasi titik.
     */
    INTERPOLATION_ERROR("interpolation_error", "Interpolation error!");

    /**
     * Tipe error dalam bentuk string.
     */
    public String identifier;

    /**
     * Pesan error dalam bentuk string.
     */
    public String msg;

    /**
     * Konstruktor Matrix Error Identifier.
     * @param identifier Tipe error.
     * @param msg Pesan error.
     */
    private MatrixErrorIdentifier(String identifier, String msg) {
        this.identifier = identifier;
        this.msg = msg;
    }
}