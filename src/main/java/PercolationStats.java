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
			percolation.open(randomCell);
		}

		return percolation.fractionOfOpenedCells();
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
}
