import static org.junit.Assert.*;

import java.util.Comparator;

import org.junit.Test;

public class PointTest {
    private static final double eps = 0.0001;

    void assertApproxEq(double a, double b) {
        assertTrue(Math.abs(a - b) < eps);
    }
/*
    @Test
    public void testCompareTo1() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 1);
        int c = p1.compareTo(p2);
        StdOut.println(p1.toString() + " compareTo " + p2.toString() + ": " + c);
        assertTrue(c < 0);
        c = p2.compareTo(p1);
        assertTrue(c > 0);
    }

    @Test
    public void testCompareTo2() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 2);
        int c = p1.compareTo(p2);
        StdOut.println(p1.toString() + " compareTo " + p2.toString() + ": " + c);
        assertTrue(c < 0);
    }

    @Test
    public void testSlopeTo() {
        double s1;
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        s1 = p1.slopeTo(p2);
        StdOut.println("slope " + p1.toString() + " " + p2.toString() + ": "
                + s1);
        assertApproxEq(1.0, s1);
    }
 */   
/*
 * 
 * 
 sign of compare(), where p, q, and r have coordinates in [0, 500)
     Failed on trial 70190 of 100000
     p                         = (470, 120)
     q                         = (107, 7)
     r                         = (197, 35)
     student   p.compare(q, r) = 0
     reference p.compare(q, r) = -1
     reference p.slopeTo(q)    = 0.31129476584022037
     reference p.slopeTo(r)    = 0.31135531135531136
     
  *  sign of compare(), where p, q, and r have coordinates in [0, 32768)
     Failed on trial 66269 of 100000
     p                         = (16451, 14041)
     q                         = (7188, 5376)
     r                         = (28622, 25426)
     student   p.compare(q, r) = 0
     reference p.compare(q, r) = 1
     reference p.slopeTo(q)    = 0.9354420813991148
     reference p.slopeTo(r)    = 0.9354202612768056
     
     p                         = (7, 0)
     q                         = (7, 6)
     r                         = (7, 0)
     student   p.compare(q, r) = 0
     reference p.compare(q, r) = 1
     reference p.slopeTo(q)    = Infinity
     reference p.slopeTo(r)    = -Infinity
  *  throw java.lang.NullPointerException if either argument is null
 */

    @Test
    public void testSlopeTo1() {
        double s1, s2;
//        Point p = new Point(16451, 14041);
//        Point q = new Point(7188, 5376);
//        Point r = new Point(28622, 25426);

        Point p = new Point(7, 0);
        Point q = new Point(7, 0);
        Point r = new Point(7, 6);

        s1 = p.slopeTo(q);
        s2 = p.slopeTo(r);
        StdOut.println("slope1 " + p.toString() + " " + q.toString() + ": "
                + s1);
        //assertApproxEq(1.0, s1);
        StdOut.println("slope2 " + p.toString() + " " + r.toString() + ": "
                + s2);
        
        Comparator<Point> c = p.SLOPE_ORDER;
        int x = c.compare(q, r);
        StdOut.println("compare result: " + x);

    }


}
