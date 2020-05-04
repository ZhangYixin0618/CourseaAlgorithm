public class Percolation {
    private boolean[] stats; // true = open, false = closed
    private int[] id;
    private int[] weight;
    private final int N;
    private int count;
    private int countOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        N = n;
        countOpen = 0;
        id = new int[N * N + 2];
        weight = new int[N * N + 2];
        stats = new boolean[N * N + 2];
        count = N * N + 2;
        for (int i = 0; i < N * N + 2; i++) {
            id[i] = i;
            weight[i] = 1;
            stats[i] = false;
        }
        id[0] = 0;
        stats[0] = true;
        weight[0] = 0;
        id[N * N + 1] = N * N + 1;
        stats[N * N + 1] = true;
        weight[N * N + 1] = 0;
    }

    private void valid(int row, int col) {
        if (row * col <= 0 || row > N || col > N) {
            throw new java.lang.IllegalArgumentException();
        }
    }

    private int pos2Index(int row, int col) {
        return (row - 1) * N + col;
    }

    private int findRoot(int index) {
        while (id[index] != index) {
            id[index] = id[id[index]];
            index = id[index];
        }
        return index;
    }

    private void union(int index1, int index2) {
        int root1 = findRoot(index1);
        int root2 = findRoot(index2);
        if (weight[root1] > weight[root2]) {
            id[root2] = id[root1];
            weight[root1] += weight[root2];
        }
        else {
            id[root1] = id[root2];
            weight[root2] += weight[root1];
        }
        count--;
    }

    private boolean isConnected(int index1, int index2) {
        int root1 = findRoot(index1);
        int root2 = findRoot(index2);
        if (root1 == root2) {
            return true;
        }
        else {
            return false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        valid(row, col);
        int siteID = pos2Index(row, col);
        if (!stats[siteID]) {
            stats[siteID] = true;
            countOpen++;
            if (row > 1 && isOpen(row - 1, col)) {
                union(pos2Index(row, col), pos2Index(row - 1, col));
            }
            if (row < N && isOpen(row + 1, col)) {
                union(pos2Index(row, col), pos2Index(row + 1, col));
            }
            if (col > 1 && isOpen(row, col - 1)) {
                union(pos2Index(row, col), pos2Index(row, col - 1));
            }
            if (col < N && isOpen(row, col + 1)) {
                union(pos2Index(row, col), pos2Index(row, col + 1));
            }
            if (row == 1) {
                union(pos2Index(row, col), 0);
            }
            if (row == N) {
                union(pos2Index(row, col), N * N + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        valid(row, col);
        return stats[pos2Index(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        valid(row, col);
        return isConnected(pos2Index(row, col), 0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (N == 1) {
            return isOpen(1, 1);
        }
        else {
            return isConnected(0, N * N + 1);
        }
    }

    // test client (optional)
    public static void main(String[] args) {


    }
}
