package src;
import src.Matrix;

public class Main {
    public static void main(String[] args) {
        Matrix M = new Matrix();
        M.BacaMatrix();
        M.TulisMatrix(); System.out.println();
        M.Transpose();
        M.TulisMatrix();

        System.out.printf("%f\n", M.GetElmt(1,1));
        M.SetElmt(1, 1, 10);
        System.out.printf("%f\n", M.GetElmt(1,1));
        M.TulisMatrix();
    }
}
