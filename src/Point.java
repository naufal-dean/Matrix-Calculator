package tubes;

import java.util.*;

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
        Matrix m = Point.toMatrix(p);

        return m.getSistemPersamaanLinear(Method.GAUSS);
    }

    //** Utility **//
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return x == p.x && y == p.y;
    }
}
