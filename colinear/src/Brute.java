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

    private static final double eps = 0.0000001;

    private static boolean equal(double a, double b) {
        if (Math.abs(a - b) < eps)
            return true;
        return false;
    }

    private static void display(Point p, Point q, Point r, Point s) {
        Point a[] = {p, q, r, s};

//        StdOut.println(String.format("%s -> %s -> %s -> %s",
//                a[0], a[1], a[2], a[3]));

        Arrays.sort(a);
        StdOut.println(String.format("%s -> %s -> %s -> %s",
                a[0], a[1], a[2], a[3]));
        a[0].drawTo(a[1]);
        a[0].drawTo(a[2]);
        a[0].drawTo(a[3]);

    }
    public static void main(String[] args) {
        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        StdOut.println("File " + filename + ", " + N + " points");
        if (N < 4)
            return;
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        PointPlotter.plot(points);
        StdDraw.setPenColor(StdDraw.BLUE);

        for (int i = 0; i < N; i++) {
            Point p = points[i];
            for (int j = i + 1; j < N; j++) {
                Point q = points[j];
                double slope_pq = p.slopeTo(q);
                //StdOut.println("p " + p + ", q " + q + ", slope p, q " + slope_pq);
                for (int k = j + 1; k < N; k++) {
                    Point r = points[k];
                    double slope_pr = p.slopeTo(r);
                    if (!equal(slope_pq, slope_pr)) {
                        continue;
                    }
                    //StdOut.println("r " + r + ", slope p, r " + slope_pq);
                    for (int l = k + 1; l < N; l++) {
                        Point s = points[l];
                        double slope_ps = p.slopeTo(s);
                        if (!equal(slope_pq, slope_ps)) {
                            continue;
                        }
                        //StdOut.println("s " + s + ", slope p, s " + slope_ps);
//                        StdOut.println(String.format("p-q %f  p-r %s  p-s %f",
//                                slope_pq, slope_pr, slope_ps));
                        display(p, q, r, s);
                    }
                }
            }
        }
        StdDraw.show(0);
    }
}
