
public class Point {
    public float x, y;

    //** Konstruktor **//
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // ** Interpolasi **//
    // public static Matrix toMatrix(Point[] p) {
    //     double[][] tempVal = new double[p.length][p.length+1];
    //
    //     for (int i = 0; i < p.length; i++) {
    //         for (int j = 0; j < p.length; j++) {
    //
    //         }
    //     }
    // }
    //
    // public static SPL interpolatePoint(Point[] p) {
    //
    // }

    //** Utility **//
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point)o;
        return x == p.x && y == p.y;
    }
}
