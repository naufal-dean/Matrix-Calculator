package tubes;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Point {
    private double x, y;

    //** Konstruktor **//
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    //** Selektor **//
    //-- Selektor: Get --//
    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    //-- Selektor: Set --//
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // ** Interpolasi **//
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

    public static SPL interpolatePoint(Point[] p) {
        return Point.toMatrix(p).getSistemPersamaanLinear(Method.GAUSS);
    }
    
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
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return Utils.doubleEquals(x, p.x) && Utils.doubleEquals(y, p.y);
    }

    @Override
    public String toString() {
        return String.format("<%.2f,%.2f>", x, y);
    }
}