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

	private int size;
	private int site[][];
	private int vtop;
	private int vbottom;
	private final int OPEN = 0;
	private final int CLOSED = 1;
	private WeightedQuickUnionUF grid;

	/**
	 * Initialize our structure by
	 * creating N-by-N grid, with all sites blocked
	 * Creates virtual top and bottom rows
	 * Uses extra rows/cols for faster filling
	 * @param N size
	 */
	public Percolation(int N) {
		if (N < 1)
			throw new IllegalArgumentException();
		size = N;
		// add 1 row/col on each side - top/bot, rt/left
		site = new int[N + 2][N + 2];
		for (int i = 0; i < N + 2; i++) {
			for (int j = 0; j < N + 2; j++) {
				// all closed initially
				site[i][j] = CLOSED;
			}
		}
		// virtual top/bottom - 2nd col of 0 and size+1 extra rows
		vtop = 1;
		// virtual top - connect (0,1) with all row 1 1-size sites
		site[0][1] = OPEN;
		site[size + 1][1] = OPEN;
		grid = new WeightedQuickUnionUF((size + 2) * (size + 2));
		// init virtual top - connect (0,1) with all row 1 1-size sites
		for (int col = index(0, 1), cnt = 0; cnt < size; col++, cnt++) {
			site[0][cnt] = OPEN;
			grid.union(vtop, col);
		}
		// init virtual bottom - connect (size+1,1) with all row size 1-size sites
		vbottom = (size + 2) * (size + 1) + 1;
		for (int col = index(size, 1), cnt = 0; cnt < size; col++, cnt++) {
			site[size+1][cnt] = OPEN;
			grid.union(vbottom, col);
		}
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
			return;		// already open
		}
		
		site[row][col] = OPEN;
		// site has extra row [0] on top, extra col 0 on left
		// check all 4 sides (including extras)
		int idx = index(row, col);
		if (site[row - 1][col] == OPEN) {
			grid.union(idx, index(row - 1, col));
		}
		if (site[row + 1][col] == OPEN) {
			grid.union(idx, index(row + 1, col));
		}
		if (site[row][col - 1] == OPEN) {
			grid.union(idx, idx - 1);
		}
		if (site[row][col + 1] == OPEN) {
			grid.union(idx, idx + 1);
		}
	}

	/**
	 * is site (row i, column j) open?
	 * 
	 * @param i
	 *            row (1..N)
	 * @param j
	 *            column (1..N)
	 * @return
	 */
	public boolean isOpen(int i, int j) {
		if (i < 1 || i > size)
			throw new IndexOutOfBoundsException();
		if (j < 1 || j > size)
			throw new IndexOutOfBoundsException();
		return site[i][j] == OPEN;
	}

	/**
	 * is site (row i, column j) full? 
	 * A full site is an open site that can be
	 * connected to an open site in the top row via a chain of neighboring
	 * (left, right, up, down) open sites
	 * 
	 * @param i
	 *            row (1..N)
	 * @param j
	 *            column (1..N)
	 * @return true if site (row i, column j) is full
	 */
	public boolean isFull(int i, int j) {
		if (i < 1 || i > size)
			throw new IndexOutOfBoundsException();
		if (j < 1 || j > size)
			throw new IndexOutOfBoundsException();
		// check for open and connected to virtual top
		return isOpen(i, j) && grid.connected(vtop, index(i,j));
	}

	/*
	 * does the system percolate?
	 */
	public boolean percolates() {
		return grid.connected(vtop, vbottom);
	}

	private int index(int row, int col) {
		return row * (size + 2) + col;
	}

/**
	public static void myassert(boolean condition, String msg) {
		if (!condition) {
			StdOut.println(msg + " failed");
		}
	}

	public static void main(String[] args) {
		StdOut.println("start test 2");
		Percolation p1 = new Percolation(1);
		myassert(p1.percolates() == false, "empty");
		p1.open(1, 1);
		myassert(p1.percolates() == true, "one");

		Percolation p3 = new Percolation(3);
		myassert(p3.percolates() == false, "3.1");
		p3.open(1, 1);
		myassert(p3.isOpen(1, 1), "1,1");
		myassert(p3.isFull(1, 2), "1,2");
		myassert(p3.isFull(2, 1), "2,1");
		myassert(p3.percolates() == false, "3.2");
		p3.open(2, 1);
		myassert(p3.isOpen(2, 1), "2,1");
		myassert(p3.percolates() == false, "3.3");
		p3.open(3, 1);
		myassert(p3.percolates() == true, "3.4");
		// PercolationVisualizer.display(p3, 3);
	}
**/	
}
