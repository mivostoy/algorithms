import java.util.Arrays;

/**
 * 
 * A faster, sorting-based solution. Remarkably, it is possible to solve the
 * problem much faster than the brute-force solution described above. Given a
 * point p, the following method determines whether p participates in a set of 4
 * or more colinear points.
 * 
 * Think of p as the origin.
 * 
 * For each other point q, determine the slope it makes with p.
 * 
 * Sort the points according to the slopes they makes with p.
 * 
 * Check if any 3 (or more) adjacent points in the sorted order have equal
 * slopes with respect to p. If so, these points, together with p, are colinear.
 * Applying this method for each of the N points in turn yields an efficient
 * algorithm to the problem.
 * 
 * The algorithm solves the problem because points that have equal slopes with
 * respect to p are colinear, and sorting brings such points together.
 * 
 * The algorithm is fast because the bottleneck operation is sorting.
 */

public class Fast {

    private static final double eps = 1e-10;

    private static boolean equal(double a, double b) {
        if (Math.abs(a - b) < eps || 
                (Double.isInfinite(a) && Double.isInfinite(b)) ) {
            //StdOut.println(String.format("equal %f %f - TRUE", a, b));
            return true;
        }
        //StdOut.println(String.format("equal %f %f - FALSE", a, b));
        return false;
    }

    private static void initPlot() {
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01); // make the points a bit larger
    }

    public static void main(String[] args) {
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        //StdOut.println("FAST File " + filename + ", " + N + " points");
        if (N < 4)
            return;

        initPlot();

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            //StdOut.println(points[i] + " ");
            points[i].draw();
        }

        Quick.sort(points);
//        for (int i = 0; i < N; i++) {
//            StdOut.println(points[i] + " ");
//            points[i].draw();
//        }

        // reset the pen radius
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);

        for (int i = 0; i < N; i++) {
            Point p = points[i];
            StdOut.println("--- " + i + ", p " + p);
            StdOut.println("p " + p);
            // make a copy of points to the right
            int n = N - i - 1;
            if (n < 3) {
                break;
            }
            //StdOut.println("=== " + n);
            Point pts [] = new Point[n];
            for (int j = 0; j < n; j++) {
                pts[j] = points[i+j+1];
                //StdOut.println(pts[j]);
            }
 
            // sort points i+1..N using slope comparator
            Arrays.sort(pts, 0, n, p.SLOPE_ORDER);

            for (int j = 0; j < n; j++ ) {
                Point q = pts[j];
                double slope_pq = p.slopeTo(q);
                StdOut.println(String.format("SORTED j %d, q %s, slope %f", j, q, slope_pq));
            }
            
            for (int j = 0; j < n - 1; j++ ) {
                Point q = pts[j];
                double slope_pq = p.slopeTo(q);
                StdOut.println(String.format("j %d, q %s, slope %f", j, q, slope_pq));
                int count = 0;
                Point last = null;
                String outp = p + " -> " + q;
                for (int k = j + 1; k < n; k++ ) {
                    StdOut.println(String.format("k %d, r %s, slope %f, count %d", 
                            k, pts[k], p.slopeTo(pts[k]), count));
                    if (equal(p.slopeTo(pts[k]), slope_pq)) {
                        count++; // count equal
                        last = pts[k];
                        outp += " -> " + last;
                    } else {
                        break;
                    }
                }
                if (count >= 2) {
                    p.drawTo(last);
                    StdOut.println(outp);
                    j += count - 1;
                    i += count - 1;
                }
            }
            
        }
        StdDraw.show(0);
    }
}
