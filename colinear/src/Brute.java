import java.util.Arrays;

/**
 * 
 * examines 4 points at a time and checks whether they all lie on the same line
 * segment, printing out any such line segments to standard output and drawing
 * them using standard drawing.
 * 
 * To check whether the 4 points p, q, r, and s are collinear, check whether the
 * slopes between p and q, between p and r, and between p and s are all equal.
 * 
 * The order of growth of the running time of your program should be N4 in the
 * worst case and it should use space proportional to N.
 */

public class Brute {

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

    private static void display(Point p, Point q, Point r, Point s) {
        Point a[] = { p, q, r, s };
        Arrays.sort(a);
        StdOut.println(String.format("%s -> %s -> %s -> %s", a[0], a[1], a[2],
                a[3]));
        a[0].drawTo(a[3]);
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
        // StdOut.println("BRUTE File " + filename + ", " + N + " points");
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

        Arrays.sort(points);

        // reset the pen radius
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        int found = 0;

        for (int i = 0; i < N; i++) {
            Point p = points[i];
            for (int j = i + 1; j < N; j++) {
                Point q = points[j];
                double slope_pq = p.slopeTo(q);
                for (int k = j + 1; k < N; k++) {
                    Point r = points[k];
                    double slope_pr = p.slopeTo(r);
                    if (!equal(slope_pq, slope_pr)) {
                        continue;
                    }
                    for (int l = k + 1; l < N; l++) {
                        Point s = points[l];
                        double slope_ps = p.slopeTo(s);
                        if (!equal(slope_pq, slope_ps)) {
                            continue;
                        }
                        display(p, q, r, s);
                        found++;
                    }
                }
            }
        }
        StdDraw.show(0);
        //StdOut.println("found " + found);
    }
}
