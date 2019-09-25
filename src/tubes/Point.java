package tubes;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class Point sebagai bentuk obyek titik pada koordinat kartesius.
 */
public class Point {
    /**
     * Komponen absis pada titik.
     */
    private BigDecimal x;

    /**
     * Komponen ordinat pada titik.
     */
    private BigDecimal y;

    //** Konstruktor **//
    /**
     * F.S Menghasilkan point dengan atribut x bernilai x dan atribut y.
     * @param x Komponen absis point.
     * @param y Komponen ordinat point.
     */
    public Point(BigDecimal x, BigDecimal y) {
        this.x = x;
        this.y = y;
    }

    //** Selektor **//
    //-- Selektor: Get --//
    /**
     * F.S Mendapatkan nilai x point.
     * @return Nilai x point.
     */
    public BigDecimal getX() {
        return this.x;
    }

    /**
     * F.S Mendapatkan nilai y point.
     * @return Nilai y point.
     */
    public BigDecimal getY() {
        return this.y;
    }

    //-- Selektor: Set --//
    /**
     * F.S Mengubah nilai x point dengan x.
     * @param x Nilai x point yang baru.
     */
    public void setX(BigDecimal x) {
        this.x = x;
    }

    /**
     * F.S Mengubah nilai y point dengan y.
     * @param y Nilai y point yang baru.
     */
    public void setY(BigDecimal y) {
        this.y = y;
    }

    // ** Interpolasi **//
    /**
     * F.S Mengubah array of point menjadi bermatriks berdimensi (panjang array x 2)
     * @param p Konten isi point yang akan diubah menjadi matriks.
     * @return Matriks hasil.
     */
    public static Matrix toMatrix(Point[] p) {
        BigDecimal[][] tempVal = new BigDecimal[p.length][p.length+1];

        for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p.length; j++)
                tempVal[i][j] = p[i].getX().pow(j);
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
        List<BigDecimal> xs = new ArrayList<>();
        while (scan.hasNextLine()) {
            String[] strs = scan.nextLine().split(" ");
            BigDecimal x = new BigDecimal(strs[0]);
            if (xs.stream().anyMatch(v -> BD.eq(v, x))) {
                scan.close();
                throw new MatrixException(MatrixErrorIdentifier.INTERPOLATION_ERROR);
            }
            xs.add(x);
            list.add(new Point(x, new BigDecimal(strs[1])));
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
        return BD.eqTest(x, p.x) && BD.eqTest(y, p.y);
    }

    /**
     * F.S Mengubah point ke dalam string.
     */
    @Override
    public String toString() {
        return String.format("<%.2f,%.2f>", x, y);
    }
}
