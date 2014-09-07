/*
 * 
 */
public class PercolationStats {
	/**
	 * perform T independent computational experiments on an N-by-N grid
	 * 
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
		// perform T experiments, exit if percolates, compute stats
		for (int i=0; i<T; i++) {
			Percolation grid = new Percolation(N);
			//todo - monte-carlo
			if (grid.percolates()) {
				break;
			}
		}
	}

	/**
	 * sample mean of percolation threshold
	 * 
	 * @return
	 */
	public double mean() {
		return 0;
	}

	/**
	 * 
	 * @return sample standard deviation of percolation threshold
	 */
	public double stddev() {
		return 0;
	}

	/**
	 * 
	 * @return returns lower bound of the 95% confidence interval
	 */
	public double confidenceLo() {
		return 0;
	}

	/**
	 * 
	 * @return upper bound of the 95% confidence interval
	 */
	public double confidenceHi() {
		return 0;
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
		
		PercolationStats stats = new PercolationStats(n, t);
		StdOut.println(String.format("mean: %f", stats.mean()) );
		StdOut.println(String.format("stdev: %f", stats.stddev()) );
		StdOut.println(String.format("confidence interval: [%f, %f]", stats.confidenceLo(), stats.confidenceHi()) );
	}
}
