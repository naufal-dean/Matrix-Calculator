package tubes;

import static tubes.Console.*;

public class Main {
    public static void main(String[] args) {
        outln("Hello, world!");
        outln("TEST");
        Matrix M = new Matrix();
        M.bacaMatrix();
        M.tulisMatrix(); System.out.println();
        M.transpose();
        M.tulisMatrix();
    }
}
