package assign3;

/**
 * Union-find with path compression and union-by-rank.
 * Instrumentation counters are exposed as public fields to be incremented externally.
 */
public class UF {
    private int[] parent;
    private int[] rank;
    public int count;
    // instrumentation
    public long findCalls = 0;
    public long unionCalls = 0;

    public UF(int n) {
        parent = new int[n];
        rank = new int[n];
        count = n;
        for (int i = 0; i < n; i++) parent[i] = i;
    }

    public int find(int x) {
        findCalls++;
        while (x != parent[x]) {
            parent[x] = parent[parent[x]]; // path compression
            x = parent[x];
        }
        return x;
    }

    public void union(int x, int y) {
        unionCalls++;
        int rx = find(x);
        int ry = find(y);
        if (rx == ry) return;
        if (rank[rx] < rank[ry]) parent[rx] = ry;
        else if (rank[ry] < rank[rx]) parent[ry] = rx;
        else {
            parent[ry] = rx;
            rank[rx]++;
        }
        count--;
    }
}