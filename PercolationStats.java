package algs.inclassPracticeProblems.project1.src;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    private int m;
    private double[] x;

    // Performs m independent experiments on an n x n percolation system.
    public PercolationStats(int n, int m) {
        if(m <= 0 || n <= 0) throw new IllegalArgumentException("Illegal m or n");
        this.m = m;
        x = new double[m];
        for(int i = 0; i < m; i++){
            UFPercolation ufperc = new UFPercolation(n);
            int a,b;
            while(!ufperc.percolates()) {
                a = StdRandom.uniform(0, n);
                b = StdRandom.uniform(0, n);
                ufperc.open(a,b);
            }
            x[i] = (double) ufperc.numberOfOpenSites() / (n*n);
        }
    }

    // Returns sample mean of percolation threshold.
    public double mean() {
        double mean = 0.0;
        for(int i = 0; i < m; i++) mean += x[i];
        return mean/m;
    }

    // Returns sample standard deviation of percolation threshold.
    public double stddev() {
        double mean = mean();
        double dev = 0.0;
        for(int i = 0; i < m; i++) dev += (x[i] - mean) * (x[i] - mean);
        return Math.sqrt(dev/(m-1));
    }

    // Returns low endpoint of the 95% confidence interval.
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(m);
    }

    // Returns high endpoint of the 95% confidence interval.
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(m);
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, m);
        StdOut.printf("Percolation threshold for a %d x %d system:\n", n, n);
        StdOut.printf("  Mean                = %.3f\n", stats.mean());
        StdOut.printf("  Standard deviation  = %.3f\n", stats.stddev());
        StdOut.printf("  Confidence interval = [%.3f, %.3f]\n", stats.confidenceLow(),
                stats.confidenceHigh());
    }
}