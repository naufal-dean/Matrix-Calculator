package tubes;

import static tubes.Console.*;

public class Main {
    public static void main(String[] args) {
        // ConsoleApp.start();
        main2();
    }
    public static void main2() {
        outln("Hello, world!");
        outln("TEST");
        double[][] test = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 8 }};
        double[][] test2 = { { 1 }, { 1 }, { 1 }, { 2 }};
        Matrix M = new Matrix(test);
        Matrix Ma = new Matrix(test2);
        System.out.println("Normal: ");
        M.tulisMatrix(); System.out.println();
        System.out.println("Entry matrix: ");
        M.getEntryMatrix(2, 2).tulisMatrix(); System.out.println();
        System.out.println("Cofactor matrix: ");
        M.getCofactorMatrix().tulisMatrix(); System.out.println();
        System.out.println("Adjoint matrix: ");
        M.getAdjointMatrix().tulisMatrix(); System.out.println();
        System.out.println("Inverse: ");
        M.getInverseMatrix(Method.GAUSS_JORDAN).tulisMatrix(); System.out.println();
        System.out.println("Echelon Form: ");
        M.getEchelonForm(M.getMaxColumn()).tulisMatrix(); System.out.println();
        System.out.println("Reduced Echelon Form: ");
        M.getReducedEchelonForm(M.getMaxColumn()).tulisMatrix(); System.out.println();
        outln(M);
        outln(-0f == 0f);
        // M.bacaMatrix();
        // M.appendMatrix(M.getIdentityMatrix()).tulisMatrix();;
        // Matrix M1 = M.getTransposeMatrix();
        // M.addOBE(1, 2, -1);
        // Matrix M1 = M.getReducedEchelonForm(M.getMaxColumn());
        // M1.tulisMatrix();
        // M1.tulisMatrix(); System.out.println();
        // Matrix M1 = new Matrix();
        // System.out.println(M.subMatrixContent(1,1)[0][2]);
        // M.getEchelonForm();
        // Matrix M1 = M.copyMatrix();
        // M1.tulisMatrix();
    }
}
