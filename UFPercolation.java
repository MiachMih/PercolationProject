package algs.inclassPracticeProblems.project1.src;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

// An implementation of the Percolation API using the UF data structure.
public class UFPercolation implements Percolation {
    private int n;
    private boolean[][] open;
    private int openSites;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF fullUf;
    private final int SOURCE;
    private final int SINK;

    // Constructs an n x n percolation system, with all sites blocked.
    public UFPercolation(int n) {
        if(n <= 0) throw new IllegalArgumentException("Illegal n");
        open = new boolean[n][n];
        this.n = n;
        openSites = 0;
        uf = new WeightedQuickUnionUF(n*n + 2);
        fullUf = new WeightedQuickUnionUF(n*n + 2);
        SOURCE = 0;
        SINK = n*n+1;
    }

    // Opens site (i, j) if it is not already open.
    public void open(int i, int j) {
        if(i < 0 || i >=n || j < 0 || j >= n) throw new IllegalArgumentException("Illegal i or j");
        if(isOpen(i,j)) return;
        open[i][j] = true;
        openSites++;
        if(i == 0) {
            uf.union(SOURCE,encode(i,j));
            fullUf.union(SOURCE,encode(i,j));
        }
        else if(i == n-1) uf.union(SINK,encode(i,j));

        if(i-1 >= 0 && isOpen(i-1, j)) {
            uf.union(encode(i-1, j),encode(i,j));
            fullUf.union(encode(i-1, j),encode(i,j));
        }
        if(j+1 < n && isOpen(i, j+1)) {
            uf.union(encode(i, j+1),encode(i,j));
            fullUf.union(encode(i, j+1),encode(i,j));
        }
        if(j-1 >= 0 && isOpen(i, j-1)) {
            uf.union(encode(i, j-1),encode(i,j));
            fullUf.union(encode(i, j-1),encode(i,j));
            }
        if(i+1 < n && isOpen(i+1, j)) {
            uf.union(encode(i+1, j),encode(i,j));
            fullUf.union(encode(i+1, j),encode(i,j));
        }
    }
    
    // Returns true if site (i, j) is open, and false otherwise.
    public boolean isOpen(int i, int j) {
        if(i < 0 || i >=n || j < 0 || j >= n) throw new IllegalArgumentException("Illegal i or j");
        return open[i][j];
    }
    
    // Returns true if site (i, j) is full, and false otherwise.
    public boolean isFull(int i, int j) {
        if(i < 0 || i >=n || j < 0 || j >= n) throw new IllegalArgumentException("Illegal i or j");
        return isOpen(i,j) && (fullUf.find(encode(i,j)) == fullUf.find(SOURCE));
    }
    
    // Returns the number of open sites.
    public int numberOfOpenSites() {
        return openSites;
    }
    
    // Returns true if this system percolates, and false otherwise.
    public boolean percolates() {
        return uf.find(SOURCE) == uf.find(SINK);
    }
    
    // Returns an integer ID (1...n^2) for site (i, j).
    private int encode(int i, int j) {
        return i*n + j + 1;
    }
    
    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        UFPercolation perc = new UFPercolation(n);
        while (!in.isEmpty()) {
            int i = in.readInt();
            int j = in.readInt();
            perc.open(i, j);
        }
        StdOut.printf("%d x %d system:\n", n, n);
        StdOut.printf("  Open sites = %d\n", perc.numberOfOpenSites());
        StdOut.printf("  Percolates = %b\n", perc.percolates());
        if (args.length == 3) {
            int i = Integer.parseInt(args[1]);
            int j = Integer.parseInt(args[2]);
            StdOut.printf("  isFull(%d, %d) = %b\n", i, j, perc.isFull(i, j));
        }
    }
}