import java.util.Arrays;

/**
 * We model a percolation system using an N-by-N grid of sites. Each site is
 * either open or blocked. A full site is an open site that can be connected to
 * an open site in the top row via a chain of neighboring (left, right, up,
 * down) open sites. We say the system percolates if there is a full site in the
 * bottom row. In other words, a system percolates if we fill all open sites
 * connected to the top row and that process fills some open site on the bottom
 * row.
 * 
 * 
 * 
 * @author ivostoy
 * 
 */
public class Percolation {
    private static final int OPEN = 1;
    private static final int CLOSED = 0;

    // union find object
    private WeightedQuickUnionUF grid;
    // size of the grid
    private int size;
    // 2-dimensional array with sites - includes extra top/bot rows and
    // left/right cols
    private int[][] site;
    // index of virtual top
    private int vtop;
    private boolean isPercolating;
    // true if a comp. is connected to bottom
    private byte[] compInfo;

    // index of virtual bottom
    // private int vbottom;

    /**
     * Initialize our structure by creating N-by-N grid, with all sites blocked
     * Creates virtual top and bottom rows Uses extra rows/cols for faster
     * filling
     * 
     * @param N
     *            size
     */
    public Percolation(int N) {
        if (N < 1)
            throw new IllegalArgumentException();
        size = N;
        // wil be init to 0 / false
        site = new int[N][N];
        compInfo = new byte[N * N];
        // Arrays.fill(site, CLOSED);
        // Arrays.fill(compInfo, 0);
        //
        // for (int i = 0; i < N; i++) {
        // for (int j = 0; j < N; j++) {
        // // all closed initially
        // site[i][j] = CLOSED;
        // //botconn[i][j] = false;
        // }
        // }
        // virtual top/bottom - middle col
        int size2 = size * size;
        grid = new WeightedQuickUnionUF(size2 + 2);
        vtop = size2;
        // vbot = size2 + 1;
    }

    private void checkBounds(int row, int col) {
        if (row < 1 || row > size)
            throw new IndexOutOfBoundsException();
        if (col < 1 || col > size)
            throw new IndexOutOfBoundsException();
    }

    /**
     * Opens site (row i, column j) if it is not already open
     * 
     * @param i
     *            row (1..N)
     * @param j
     *            column (1..N)
     */
    public void open(int row, int col) {
        checkBounds(row, col);
        if (site[row - 1][col - 1] == OPEN) {
            // if (isOpen(row, col)) {
            return; // already open
        }
        // open this site
        site[row - 1][col - 1] = OPEN;
        int idx = index(row, col);
        StdOut.println("*** open " + row + " " + col);
        // check all 4 sides
        // top row?
        int flag = 0;
        if (row == 1) {
            grid.union(vtop, idx);
            flag |= compCheck(row, col);
        } else {
            if (isOpen(row - 1, col)) {
                connect(row, col, row - 1, col);
                flag |= compCheck(row - 1, col);
            }
        }
        if (row < size) {
            if (isOpen(row + 1, col)) {
                connect(row, col, row + 1, col);
                flag |= compCheck(row + 1, col);
            }
        }
        if (col > 1) {
            if (isOpen(row, col - 1)) {
                connect(row, col, row, col - 1);
                flag |= compCheck(row, col - 1);
            }
        }
        if (col < size) {
            if (isOpen(row, col + 1)) {
                connect(row, col, row, col + 1);
                flag |= compCheck(row, col + 1);
            }
        }
        flag |= compCheck(row, col);
        StdOut.println("flag " + flag);
        if (!isPercolating && flag == 3) {
            isPercolating = true;
            StdOut.println("*** PERCOLATION, r " + row + ", c " + col);
        }

    }

    private int compCheck(int row, int col) {
        int ret = 0;
        int idx1 = index(row, col);
        int comp1 = grid.find(idx1);
        if (comp1 == vtop) {
            compInfo[idx1] = 1; // full
            ret = 1;
        }

        StdOut.println("compCheck " + row + ", " + col + " idx1 " + idx1
                + " comp1 " + comp1 + ", info " + compInfo[idx1]);

        if (comp1 < vtop) {
            StdOut.println("info1 " + compInfo[idx1]);
        }

        int idx2 = index(size, col);
        int comp2 = grid.find(idx2);

//        StdOut.println("idx2 " + idx2 + ", comp2 " + comp2 + ", info "
//                + compInfo[idx2]);

        // if (grid.connected(comp2, idx1)) {
        if (comp1 == comp2) {
            compInfo[idx2] |= 2;
            ret = ret + 2;
            StdOut.println("== Connected to bottom idx1 " + idx1 + ", comp2 "
                    + comp2 + ", info " + compInfo[idx1]);
            //StdOut.println("Connected to top " + grid.connected(idx1, vtop));
        }

        // if (!isPercolating && compInfo[idx1] == 3) {
        // isPercolating = true;
        // StdOut.println("*** PERCOLATION, r " + row + ", c " + col +
        // ", comp1 " + comp1 + " ***");
        // }
        return ret;
    }

    private void connect(int row1, int col1, int row2, int col2) {
        int idx1 = index(row1, col1);
        int idx2 = index(row2, col2);
        // StdOut.println("connect " + row1 + ", " + col1 + " idx " + idx1 +
        // " with " + row2 + ", " + col2 + " idx " + idx2);
        grid.union(idx1, idx2);
    }

    /**
     * is site at (row i, column j) open?
     * 
     * @param i
     *            row (1..N)
     * @param j
     *            column (1..N)
     * @return true if site is open
     */
    public boolean isOpen(int i, int j) {
        checkBounds(i, j);
        return site[i - 1][j - 1] == OPEN;
    }

    /**
     * is site at (row i, column j) full? A full site is an open site that can
     * be connected to an open site in the top row via a chain of neighboring
     * (left, right, up, down) open sites
     * 
     * @param row
     *            row (1..N)
     * @param col
     *            column (1..N)
     * @return true if site is full
     */
    public boolean isFull(int row, int col) {
        checkBounds(row, col);
        // check for open and connected to virtual top
        if (!isOpen(row, col)) {
            return false;
        }
        int idx = index(row, col);
        // int comp = grid.find(idx);
        // StdOut.println("isFull? " + row + ", " + col +
        // ", idx " + idx + ", comp " + comp + ", vtop " + vtop);
        // return comp == vtop;
        // return false;
        return grid.connected(vtop, idx);
        // return vtop == grid.find(idx);
    }

    /*
     * does the system percolate?
     */
    public boolean percolates() {
        return isPercolating;
    }

    /*
     * map row, col to union index 1,1 -> 0 1,2 -> 1 size, size -> size*size-1
     */
    private int index(int row, int col) {
        return (row - 1) * size + col - 1;
    }

    /**
     * public static void myassert(boolean condition, String msg) { if
     * (!condition) { StdOut.println(msg + " failed"); } } // self test public
     * static void main(String[] args) { StdOut.println("start test 2");
     * Percolation p1 = new Percolation(1); myassert(p1.percolates() == false,
     * "empty"); p1.open(1, 1); myassert(p1.percolates() == true, "one");
     * 
     * Percolation p3 = new Percolation(3); myassert(p3.percolates() == false,
     * "3.1"); p3.open(1, 1); myassert(p3.isOpen(1, 1), "1,1");
     * myassert(p3.isFull(1, 2), "1,2"); myassert(p3.isFull(2, 1), "2,1");
     * myassert(p3.percolates() == false, "3.2"); p3.open(2, 1);
     * myassert(p3.isOpen(2, 1), "2,1"); myassert(p3.percolates() == false,
     * "3.3"); p3.open(3, 1); myassert(p3.percolates() == true, "3.4"); //
     * PercolationVisualizer.display(p3, 3); }
     **/
}
