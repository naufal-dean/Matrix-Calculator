package tubes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Class Point sebagai bentuk obyek titik pada koordinat kartesius.
 */
public class Point {
    /**
     * Komponen absis pada titik.
     */
    private double x;

    /**
     * Komponen ordinat pada titik.
     */
    private double y;

    //** Konstruktor **//
    /**
     * F.S Menghasilkan point dengan atribut x bernilai x dan atribut y.
     * @param x Komponen absis point.
     * @param y Komponen ordinat point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //** Selektor **//
    //-- Selektor: Get --//
    /**
     * F.S Mendapatkan nilai x point.
     * @return Nilai x point.
     */
    public double getX() {
        return this.x;
    }

    /**
     * F.S Mendapatkan nilai y point.
     * @return Nilai y point.
     */
    public double getY() {
        return this.y;
    }

    //-- Selektor: Set --//
    /**
     * F.S Mengubah nilai x point dengan x.
     * @param x Nilai x point yang baru.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * F.S Mengubah nilai y point dengan y.
     * @param y Nilai y point yang baru.
     */
    public void setY(double y) {
        this.y = y;
    }

    // ** Interpolasi **//
    /**
     * F.S Mengubah array of point menjadi bermatriks berdimensi (panjang array x 2)
     * @param p Konten isi point yang akan diubah menjadi matriks.
     * @return Matriks hasil.
     */
    public static Matrix toMatrix(Point[] p) {
        double[][] tempVal = new double[p.length][p.length+1];

        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p.length; j++) {
                tempVal[i][j] = Math.pow(p[i].getX(), j);
            }
            tempVal[i][p.length] = p[i].getY();
        }
        return new Matrix(tempVal);
    }

    /**
     * F.S Mengubah array of point menjadi SPL.
     * @param p Konten isi point yang akan diubah menjadi SPL.
     * @return Sistem persamaan linear hasil.
     */
    public static SPL interpolatePoint(Point[] p) {
        return Point.toMatrix(p).getSistemPersamaanLinear(Method.GAUSS);
    }
    
    /**
     * F.S Membaca Point melalui sebuah file fileName.
     * @param fileName Nama file.
     * @return Array of point dari hasil membaca file.
     * @throws Exception Jika akses file gagal atau input tidak valid.
     */
    public static Point[] readFile(String fileName) throws Exception {
        Scanner scan = new Scanner(new File(fileName));
        List<Point> list = new ArrayList<>();
        Set<Double> xs = new HashSet<>();
        while (scan.hasNextLine()) {
            String[] strs = scan.nextLine().split(" ");
            double x = Double.parseDouble(strs[0]);
            if (xs.contains(x)) {
                scan.close();
                throw new MatrixException(MatrixErrorIdentifier.INTERPOLATION_ERROR);
            }
            xs.add(x);
            list.add(new Point(x, Double.parseDouble(strs[1])));
        }
        scan.close();
        return list.toArray(new Point[list.size()]);
    }

    //** Utility **//
    /**
     * F.S Menghasilkan true jika obyek o sama dengan Point ini.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return Utils.doubleEquals(x, p.x) && Utils.doubleEquals(y, p.y);
    }

    /**
     * F.S Mengubah point ke dalam string.
     */
    @Override
    public String toString() {
        return String.format("<%.2f,%.2f>", x, y);
    }
}
