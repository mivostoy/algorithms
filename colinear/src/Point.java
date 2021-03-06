/**
 * 
 * https://class.coursera.org/algs4partI-006/assignment/view?assignment_id=4
 * 
 */
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeComparator();
    private static final double eps = 1e-10;
    private static final double POSITIVE_ZERO = (1.0 - 1.0) / 1.0;

    private class SlopeComparator implements Comparator<Point> {

        @Override
        // the point (x1, y1) is less than the point (x2, y2) if and only if the
        // slope (y1 − y0) / (x1 − x0) is less than the slope (y2 − y0) / (x2 −
        // x0).
        // Treat horizontal, vertical, and degenerate line segments as in the
        // slopeTo() method
        // Returns a negative integer, zero, or a positive integer as this
        // object is
        // less than, equal to, or greater than the specified object.
        public int compare(Point p1, Point p2) {
            if (p1 == null || p2 == null) 
                throw new NullPointerException();
            
            double s1 = p1.slopeTo(Point.this);
            double s2 = p2.slopeTo(Point.this);
            if (Double.isInfinite(s1) && Double.isInfinite(s2)) {
                //StdOut.println("s1 " + s1 + " s2 " + s2);
                if (s1 < s2)
                    return -1;
                if (s1 > s2)
                    return 1;
                return 0;
            }
            
            double d = s1-s2;
            //StdOut.println("d1 " + s1 + " d2 " + s2 + " delta " + d);
            if (Math.abs(d) < eps)
                return 0;
            return d < 0 ? -1 : 1;
        }
    }

    private final int x; // x coordinate
    private final int y; // y coordinate

    // private final Point p0;

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    /**
     * What does it mean for slopeTo() to return positive zero? Java (and the
     * IEEE 754 floating-point standard) define two representations of zero:
     * negative zero and positive zero.
     * 
     * double a = 1.0; double x = (a - a) / a; // positive zero ( 0.0) double y
     * = (a - a) / -a; // negative zero (-0.0)
     */
    public double slopeTo(Point that) {
        int dx = that.x - x;
        int dy = that.y - y;
        if (dx == 0) {
            if (dy == 0) {
                // same point
                return Double.NEGATIVE_INFINITY;
            }
            // vertical
            return Double.POSITIVE_INFINITY;
        }
        if (dy == 0)
            return POSITIVE_ZERO;
        return (double) dy / (double) dx;

    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    // the invoking point (x0, y0) is less than the argument point (x1, y1) if
    // and only if either y0 < y1 or if y0 = y1 and x0 < x1
    // Returns a negative integer, zero, or a positive integer as this object is
    // less than, equal to, or greater than the specified object.
    public int compareTo(Point that) {
        int dy = this.y - that.y;
        if (dy != 0)
            return dy;
        return this.x - that.x;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}