package tubes;

public enum MatrixErrorIdentifier {
    INCONSISTENT_ERROR("inconsistent_error", "Matrix is inconsistent!"),
    MULTIPLY_ERROR("multiply_error", "Column count of the left matrix is not equal to row count of the right matrix!"),
    DIFFERENT_SIZE_ERROR("different_size_error", "Different matrix size!"),
    NOT_SQUARE_ERROR("not_square_error", "Max row and max column are not the same! No determinant."),
    DETERMINANT_ZERO_ERROR("determinant_zero_error", "Max row and max column are not the same! No determinant."),
    NOT_AUGMENTED_ERROR("not_augmented_error", "Matrix is not augmented matrix!"),
    INTERPOLATION_ERROR("interpolation_error", "Interpolation error!");
    public String identifier, msg;
    private MatrixErrorIdentifier(String identifier, String msg) {
        this.identifier = identifier;
        this.msg = msg;
    }
}