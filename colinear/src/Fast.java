import java.util.ArrayList;
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

    //private static final double eps = 1e-10;

//    private static boolean equal(double a, double b) {
//        if (Math.abs(a - b) < eps
//                || (Double.isInfinite(a) && Double.isInfinite(b))) {
//            // StdOut.println(String.format("equal %f %f - TRUE", a, b));
//            return true;
//        }
//        // StdOut.println(String.format("equal %f %f - FALSE", a, b));
//        return false;
//    }

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
        ArrayList<String> found_seg = new ArrayList<String>();
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
            points[i].draw();
        }

        // reset the pen radius
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);

        // initial sort
        Arrays.sort(points);
        
        int found = 0;
        for (int i = 0; i < N; i++) {
            // make a copy and swap point i at first position
            Point pts[] = points.clone();
            Point p = pts[i];
            //StdOut.println(String.format("=== P %s i %d", p, i));
            pts[i] = pts[0];
            pts[0] = p;
            // sort points 1..N using slope comparator with p
            Arrays.sort(pts, 0, N, p.SLOPE_ORDER);
            for (int j = 1; j < N; j++) {
                Point q = pts[j];
                // if q < p - skip (order rule)
                if (q.compareTo(p) <= 0) {
                    continue;
                }
                double s = p.slopeTo(q);
                //StdOut.println(String.format("    Q %s %f j %d", q, s, j));
                // find runs of size 3+
                int rlen = 0;
                for (int k = j + 1; k < N; k++) {
                    Point r = pts[k];
                    if (p.slopeTo(r) != s) {
                        break;
                    }
                    // StdOut.println(String.format("   k %d %f", k,
                    // p.slopeTo(pts[k])));
                    // skip if p is not lexicographically first in the run
                    if (r.compareTo(p) <= 0) {
                        rlen = 0;
                        break;
                    }
                    rlen++;
                }
                if (rlen >= 2) {
                    rlen += 2;
                    Point coll[] = new Point[rlen];
                    // copy, sort and display found set
                    coll[0] = p;
                    coll[1] = q;
                    int m = j + 1;
                    for (int x = 2; x < rlen; x++) {
                        coll[x] = pts[m];
                        m++;
                    }
                    Arrays.sort(coll);
                    String seg = segment(coll);
                    // jump to point after the run (-1)
                    j = m - 1;
                    // check for sub-segment
                    for (int x=0; x<found_seg.size(); x++) {
                        if (found_seg.get(x).contains(seg)) {
                            rlen = 0;
                            break;
                        }
                    }
                    // subseq?
                    if (rlen == 0) {
                        //StdOut.println("SKIP " + seg);
                        continue;
                    }
                    // draw line first->last - will contain all points
                    coll[0].drawTo(coll[coll.length - 1]);
                    StdOut.println(seg);
                    found_seg.add(seg);
                    found++;
                    //display(coll);
                    //StdOut.println(String.format("  FOUND run %d with size %d, PQ %f, next j %d",
                    //        found, rlen, s, j));
                }
            }
        }
        StdDraw.show(0);
        //StdOut.println("found " + found);
    }

    private static String segment(Point[] coll) {
        StringBuffer seq = new StringBuffer();
        seq.append(coll[0]);
        for (int i = 1; i < coll.length; i++) {
            seq.append(" -> " + coll[i]);
        }
        return seq.toString();
    }
  
//    private static void display(String seg, Point[] coll) {
//        int l = coll.length;
//        coll[0].drawTo(coll[l - 1]);
//        StdOut.print(coll[0]);
//        for (int i = 1; i < l; i++) {
//            seq.append(" -> " + coll[i]);
//        }
//        StdOut.println(seq);
//        sfound.add(seq.toString());
//    }
}
