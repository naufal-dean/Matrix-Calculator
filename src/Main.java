package tubes;

import static tubes.Console.*;

public class Main {
    public static void main(String[] args) {
        // ConsoleApp.start();
        main2();
        // System.out.printf("%.100f", 0d);
    }
    public static void main2() {
        outln("Hello, world!");
        outln("TEST");
        double[][] test = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
        double[][] test2 = { { 1 }, { 1 }, { 1 }, { 2 }};
        double[] buffer;
        Matrix M = new Matrix(test);
        Matrix Ma = new Matrix(test2);
        System.out.println("Normal: ");
        M.tulisMatrix(); System.out.println();
        System.out.printf("Determinan Cramer: %.20f %b\n", M.getDeterminan(Method.CRAMER), 0 == M.getDeterminan(Method.CRAMER));
        System.out.printf("Determinan Gauss: %.20f %b\n\n", M.getDeterminan(Method.GAUSS), 0 == M.getDeterminan(Method.GAUSS));
        System.out.println("Entry matrix: ");
        M.getEntryMatrix(2, 2).tulisMatrix(); System.out.println();
        System.out.println("Cofactor matrix: ");
        M.getCofactorMatrix().tulisMatrix(); System.out.println();
        System.out.println("Adjoint matrix: ");
        M.getAdjointMatrix().tulisMatrix(); System.out.println();
        System.out.println("Inverse Gauss-Jordan: ");
        M.getInverseMatrix(Method.GAUSS_JORDAN).tulisMatrix(); System.out.println();
        System.out.println("Inverse Cramer: ");
        M.getInverseMatrix(Method.CRAMER).tulisMatrix(); System.out.println();
        System.out.println("Echelon Form: ");
        M.getEchelonForm(M.getMaxColumn()).tulisMatrix(); System.out.println();
        System.out.println("Reduced Echelon Form: ");
        M.getReducedEchelonForm(M.getMaxColumn()).tulisMatrix(); System.out.println();

        double[][] test3 = { { 1, 2, 3 }, { 4, 5, 7 }};
        Matrix M3 = new Matrix(test3);
        System.out.println("Matriks SPL: ");
        M3.tulisMatrix();
        System.out.println("Get Solution Cramer: ");
        buffer = M3.getSistemPersamaanLinear(Method.CRAMER);
        for (int i = 1; i < buffer.length; i++) {
            System.out.printf("x%d = %.20f\n", i, buffer[i]);
        }
        System.out.println();
        System.out.println("Get Solution Inverse: ");
        buffer = M3.getSistemPersamaanLinear(Method.INVERSE);
        for (int i = 1; i < buffer.length; i++) {
            System.out.printf("x%d = %.20f\n", i, buffer[i]);
        }
        System.out.println();

        // outln(M);
        // outln(-0f == 0f);
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
