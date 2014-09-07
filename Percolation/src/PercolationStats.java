/*
 * Performs T independent computational experiments on an N-by-N grid
 * 
 */
public class PercolationStats {
	
	private double [] threshold;
	// mean
	private double mu;
	// stdev
	private double sigma;
	// number of experiments
	private int T;
	
	/**
	 * Initalizes stats object
	 * @param N
	 *            grid size
	 * @param T
	 *            number of experiments
	 */
	public PercolationStats(int N, int T) {
		if (N < 1)
			throw new IllegalArgumentException();
		if (T < 1)
			throw new IllegalArgumentException();
		this.T= T;
		int total = N*N;
		threshold = new double[T];
		// perform T experiments, exit if percolates, compute stats
		for (int i=0; i<T; i++) {
			Percolation grid = new Percolation(N);
			int opened = 0;
			do {
				// monte-carlo - fill randomly the grid until percolates
				int row = StdRandom.uniform(1, N+1);
				int col = StdRandom.uniform(1, N+1);
				//StdOut.println(String.format("open %d, %d", row, col));
				if (!grid.isOpen(row, col)) {
					grid.open(row, col);
					opened++;
				}
			} while (!grid.percolates());
			double pt = ((double) opened) / total;
			//StdOut.println(String.format("opened %d perc.thresh. %f", opened, pt));
			threshold[i] = pt;
		}
		mu = StdStats.mean(threshold);
		sigma = StdStats.stddev(threshold); 
	}

	/**
	 * sample mean of percolation threshold
	 * 
	 * @return
	 */
	public double mean() {
		return mu;
	}

	/**
	 * 
	 * @return sample standard deviation of percolation threshold
	 */
	public double stddev() {
		return sigma;
	}

	/**
	 * 
	 * @return returns lower bound of the 95% confidence interval
	 */
	public double confidenceLo() {
		return mu - 1.96 * sigma / Math.sqrt(T);
	}

	/**
	 * 
	 * @return upper bound of the 95% confidence interval
	 */
	public double confidenceHi() {
		return mu + 1.96 * sigma / Math.sqrt(T);
	}

	/**
	 * Test client takes two command-line arguments N and T, performs T
	 * independent computational experiments (discussed above) on an N-by-N
	 * grid, and prints out the mean, standard deviation, and the 95% confidence
	 * interval for the percolation threshold. Use standard random from our
	 * standard libraries to generate random numbers; use standard statistics to
	 * compute the sample mean and standard deviation.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			StdOut.println("Usage: Percolation grid-size num-experiments");
			return;
		}
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);
		StdOut.println(String.format("PercolationStats - %d experiments of size %d", n, t));
		Stopwatch timer = new Stopwatch();
		PercolationStats stats = new PercolationStats(n, t);
		StdOut.println(String.format("took: %.2f sec", timer.elapsedTime()) );
		StdOut.println(String.format("mean: %f", stats.mean()) );
		StdOut.println(String.format("stdev: %f", stats.stddev()) );
		StdOut.println(String.format("confidence interval: [%f, %f]", stats.confidenceLo(), stats.confidenceHi()) );
	}
}
