import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private final int n;
	private final int trials;
	private final double[] trialsResults;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		requireAtLeastOne(n);
		requireAtLeastOne(trials);

		this.n = n;
		this.trials = trials;
		this.trialsResults = new double[trials];
		performTrials();
	}

	private void performTrials() {
		for (int i = 0; i < trials; i++) {
			trialsResults[i] = performTrial();
		}
	}

	private double performTrial() {
		Percolation percolation = new Percolation(n);

		while(!percolation.percolates()) {
			int randomCell = StdRandom.uniform(n*n)+1;
			percolation.open(randomCell, Integer.MAX_VALUE);
		}

		return 1.0d * percolation.numberOfOpenSites() / (n*n);
	}

	private void requireAtLeastOne(int n) {
		if (n < 1) {
			throw new IllegalArgumentException();
		}
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(trialsResults);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(trialsResults);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return (mean() - 1.96d * stddev()) / Math.sqrt(trials);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return (mean() + 1.96d * stddev()) / Math.sqrt(trials);
	}

	//Also, include a main() method that takes two command-line arguments n and T,
	// performs T independent computational experiments (discussed above) on an n-by-n grid,
	// and prints the sample mean, sample standard deviation, and the 95% confidence interval for the percolation threshold.
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int t = Integer.parseInt(args[1]);

		PercolationStats percolationStats = new PercolationStats(n, t);

		System.out.println("Mean: " + percolationStats.mean());
		System.out.println("Dev: " + percolationStats.stddev());
		System.out.println("Confidence: [" + percolationStats.confidenceLo() + " - " + percolationStats.confidenceHi() + "]");
	}

}
