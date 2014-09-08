/**
 * We model a percolation system using an N-by-N grid of sites. Each site is
 * either open or blocked. A full site is an open site that can be connected to
 * an open site in the top row via a chain of neighboring (left, right, up,
 * down) open sites. We say the system percolates if there is a full site in the
 * bottom row. In other words, a system percolates if we fill all open sites
 * connected to the top row and that process fills some open site on the bottom
 * row.
 * 
 * @author ivostoy
 * 
 */
public class Percolation {
    private static final int OPEN = 0;
    private static final int CLOSED = 1;

    // union find object
    private WeightedQuickUnionUF grid;
    // size of the grid
    private int size;
    // 2-dimensional array with sites - includes extra top/bot rows and
    // left/right cols
    private int[][] site;
    // index of virtual top
    private int vtop;
    // index of virtual bottom
    private int vbottom;

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
        // add 1 row/col
        site = new int[N + 1][N + 1];
        for (int i = 0; i < N + 1; i++) {
            for (int j = 0; j < N + 1; j++) {
                // all closed initially
                site[i][j] = CLOSED;
            }
        }
        // virtual top/bottom - middle col
        vtop = 0;
        vbottom = 1; // (size + 2) * (size + 1) + size/2;
        grid = new WeightedQuickUnionUF((size + 2) * (size + 2));
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
        if (row < 1 || row > size)
            throw new IndexOutOfBoundsException();
        if (col < 1 || col > size)
            throw new IndexOutOfBoundsException();

        if (site[row][col] == OPEN) {
            return; // already open
        }
        // open this site
        site[row][col] = OPEN;
        // StdOut.println("open " + row + " " + col);
        // check all 4 sides (including extras)
        // int idx = index(row, col);
        // StdOut.println("open " + row + " " + col + " idx " + idx);

        if (row == 1) {
            grid.union(vtop, index(row, col));
        } else {
            if (site[row - 1][col] == OPEN) {
                connect(row, col, row - 1, col);
                // grid.union(idx, index(row - 1, col));
            }
        }

        if (row == size) {
            grid.union(vbottom, index(row, col));
        } else {
            if (site[row + 1][col] == OPEN) {
                connect(row, col, row + 1, col);
                // grid.union(idx, index(row + 1, col));
            }
        }
        if (col > 1) {
            if (site[row][col - 1] == OPEN) {
                connect(row, col, row, col - 1);
                // grid.union(idx, idx - 1);
            }
        }
        if (col < size) {
            if (site[row][col + 1] == OPEN) {
                connect(row, col, row, col + 1);
                // grid.union(idx, idx + 1);
            }
        }
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
        if (i < 1 || i > size)
            throw new IndexOutOfBoundsException("invalid row");
        if (j < 1 || j > size)
            throw new IndexOutOfBoundsException("invalid col");
        return site[i][j] == OPEN;
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
        if (row < 1 || row > size)
            throw new IndexOutOfBoundsException("invalid row");
        if (col < 1 || col > size)
            throw new IndexOutOfBoundsException("invalid col");
        // check for open and connected to virtual top
        if (!isOpen(row, col)) {
            return false;
        }
        //StdOut.println("isFull? " + row + ", " + col);
        // avoid backwash
        if (row == 1) {
            //StdOut.println("TOP ROW");
            return true; // top row
        }
        if (grid.connected(vtop, index(row-1, col))) {
            //StdOut.println("TOP " + (row-1) + ", " + col + " full");
            return true;
        }
        if (col > 1 && grid.connected(vtop, index(row, col-1))) {
            //StdOut.println("LEFT full");
            return true;
        }
        if (col < size && grid.connected(vtop, index(row, col+1))) {
            //StdOut.println("RIGHT full");
            return true;
        }
        if (row < size && grid.connected(vtop, index(row+1, col))) {
            return true;
        }
        return false;
    }

    /*
     * does the system percolate?
     */
    public boolean percolates() {
        return grid.connected(vtop, vbottom);
    }

    private int index(int row, int col) {
        return row * (size + 1) + col;
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