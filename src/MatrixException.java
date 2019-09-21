package tubes;

public class MatrixException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public MatrixErrorIdentifier errorType;
    public MatrixException(MatrixErrorIdentifier errorType) {
        this.errorType = errorType;
    }
}