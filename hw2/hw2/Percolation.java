package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] nGrids;
    private int N;
    private int openSize;
    private WeightedQuickUnionUF UF;

    //no bottom
    private WeightedQuickUnionUF UF1;
    private int topGrid;
    private int bottomGrid;


    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        nGrids = new int[N*N];
        UF = new WeightedQuickUnionUF(N*N + 2);
        UF1 = new WeightedQuickUnionUF(N*N + 1);
        this.N = N;
        topGrid = N*N;
        bottomGrid = N*N+1;
        openSize = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        int n = findGrid(row, col);
        if (nGrids[n] == 0) {
            nGrids[n] = 1;
            openSize += 1;
            int[] surGrids = findSurroundingGrid(row, col);
            if (row == 0) {
                UF.union(topGrid, n);
                UF1.union(topGrid, n);
            }
            if (row == N-1) {
                UF.union(bottomGrid, n);
            }
            for (int i = 0; i < surGrids.length; i++) {
                int k = surGrids[i];
                if (k<0 || k>=N*N) {
                    continue;
                }
                if (nGrids[k] == 1){
                    UF.union(n, k);
                    UF1.union(n, k);
                }
            }
        }
    }

    private int findGrid(int row, int col) {
        return row*N + col;
    }

    private int[] findSurroundingGrid (int row, int col) {
        int[] surGrid = new int[4];
        surGrid[0] = findGrid(row-1, col);
        surGrid[1] = findGrid(row+1, col);
        surGrid[2] = findGrid(row, col-1);
        surGrid[3] = findGrid(row, col+1);
        return surGrid;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        int n = findGrid(row, col);
        return nGrids[n] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int n = findGrid(row, col);
        return UF1.connected(topGrid, n);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSize;
    }

    // does the system percolate?
    public boolean percolates() {
        if (UF.connected(topGrid,bottomGrid)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation p = new Percolation(10);
    }
}
