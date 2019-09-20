package tubes;

public class MatrixException extends RuntimeException {
    public MatrixErrorIdentifier errorType;
    public MatrixException(MatrixErrorIdentifier errorType) {
        this.errorType = errorType;
    }
}