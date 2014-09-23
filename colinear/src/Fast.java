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
        if (Math.abs(a - b) < eps
                || (Double.isInfinite(a) && Double.isInfinite(b))) {
            // StdOut.println(String.format("equal %f %f - TRUE", a, b));
            return true;
        }
        // StdOut.println(String.format("equal %f %f - FALSE", a, b));
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
        // StdOut.println("FAST File " + filename + ", " + N + " points");
        if (N < 4)
            return;

        initPlot();

        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            // StdOut.println(points[i] + " ");
            points[i].draw();
        }
        Arrays.sort(points);
        // Quick.sort(points);
        for (int i = 0; i < N; i++) {
            // StdOut.println(points[i] + " ");
            points[i].draw();
        }

        // reset the pen radius
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        int found = 0;
        for (int i = 0; i < N - 1; i++) {
            Point p = points[i];
            // make a copy of points to the right
            int n = N - i - 1;
//            if (n < 3) {
//                break;
//            }
            StdOut.println("--- i " + i + ", p " + p + ", n " + n);
            Point pts[] = new Point[n];
            for (int j = 0; j < n; j++) {
                pts[j] = points[i + j + 1];
                // StdOut.println(pts[j]);
            }
            // sort points i..N using slope comparator
            Arrays.sort(pts, 0, n, p.SLOPE_ORDER);
            for (int j = 0; j < n; j++) {
                Point q = pts[j];
                double slope_pq = p.slopeTo(q);
                StdOut.println(String.format("SORTED j %d, q %s, slope %f", j,
                        q, slope_pq));
            }
            for (int j = 0; j < n; j++) {
                Point q = pts[j];
                double slopePQ = p.slopeTo(q);
                //StdOut.println(String.format("i %d, j %d, q %s, slope %f", i, j, q,
                //        slopePQ));
                int count = 0;
                // max 10 collinear
                Point coll[] = new Point[10];
                coll[0] = p;
                coll[1] = q;
                for (int k = j + 1; k < n; k++) {
                    // StdOut.println(String.format(
                    // "k %d, r %s, slope %f, count %d", k, pts[k],
                    // p.slopeTo(pts[k]), count));
                    if (equal(p.slopeTo(pts[k]), slopePQ)) {
                        coll[count + 2] = pts[k];
                        count++; // count equal
                        StdOut.println(String.format(
                                "k %d, r %s, slope %f, count %d", k, pts[k],
                                p.slopeTo(pts[k]), count));
                    } else {
                        break;
                    }
                }
                if (count >= 2) {
                    StdOut.println(String.format("j %d count %d, PQ %f", j, count,
                           slopePQ));
                    /**
                     * Given a point p you can find a set of points {p1, p2 ...
                     * pn} which are collinear with p and which occur in the
                     * list after p. But is this set a subset of something
                     * you've already printed? It is if and only if there is a
                     * point p0, which is before p, and which is collinear with
                     * p1...pn.
                     * 
                     * In other words, you can check if there is a point before
                     * p, with the same slope with it as p1...pn. This can even
                     * be done with logarithmic complexity.
                     */
                    for (int s = 0; s < j - 1; s++) {
                        StdOut.println("check " + p.slopeTo(pts[s]));
                        if (equal(p.slopeTo(pts[s]), slopePQ)) {
                            StdOut.println("ALREADY PRINTED " + pts[s]);
                        }
                    }
                    count += 2;
                    found++;
                    display(coll, count);
                    //j += count - 3;
                    //i += count - 4;
                    StdOut.println(String.format("count %d, i %d, j %d", count,
                            i, j));
                }
            }
        }
        StdDraw.show(0);
        StdOut.println("found " + found);
    }

    private static void display(Point[] coll, int l) {
        Arrays.sort(coll, 0, l);
        coll[0].drawTo(coll[l - 1]);
        StdOut.print(coll[0]);
        for (int i = 1; i < l; i++) {
            StdOut.print(" -> " + coll[i]);
        }
        StdOut.println("");
    }
}
