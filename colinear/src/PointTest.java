import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {
    private static final double eps = 0.0001;

    void assertApproxEq(double a, double b) {
        assertTrue(Math.abs(a - b) < eps);
    }

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
}
