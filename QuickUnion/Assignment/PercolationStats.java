import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] result;
    private final int T;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        T = trials;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        result = new double[trials];
        Percolation perc;
        for (int q = 0; q < trials; q++) {
            perc = new Percolation(n);
            double openNum = 0;
            int i;
            int j;
            while (!perc.percolates()) {
                i = StdRandom.uniform(1, n + 1);
                j = StdRandom.uniform(1, n + 1);
                if (perc.isOpen(i, j)) {
                    continue;
                }
                perc.open(i, j);
                openNum++;
            }
            result[q] = openNum / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(result);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(result);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return (mean() - 1.96 * stddev() / Math.sqrt(T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (mean() + 1.96 * stddev() / Math.sqrt(T));
    }

    // test client (see below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);
        StdOut.printf("mean                    = %f%n", ps.mean());
        StdOut.printf("stddev                  = %f%n", ps.stddev());
        StdOut.printf("95%% confidence interval = %f, %f%n", ps.confidenceLo(), ps.confidenceHi());
    }

}
