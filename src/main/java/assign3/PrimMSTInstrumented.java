package assign3;

import java.util.ArrayList;
import java.util.List;

/**
 * Prim's algorithm instrumentation.
 */
public class PrimMSTInstrumented {
    private Edge[] edgeTo;
    private double[] distTo;
    private boolean[] marked;
    private IndexMinPQ<Double> pq;
    // counters:
    public long edgeScans = 0;
    public long pqInserts = 0;
    public long pqDecreaseKeys = 0;
    public long keyComparisons = 0;
    public long delMinOps = 0;

    private List<Edge> mstEdges = new ArrayList<>();

    public PrimMSTInstrumented(EdgeWeightedGraph G) {
        int V = G.V();
        edgeTo = new Edge[V];
        distTo = new double[V];
        marked = new boolean[V];
        pq = new IndexMinPQ<>(V);
        for (int v = 0; v < V; v++) distTo[v] = Double.POSITIVE_INFINITY;
        for (int v = 0; v < V; v++) if (!marked[v]) prim(G, v);
        // collect edges
        for (Edge e : edgeTo) if (e != null) mstEdges.add(e);
    }

    private void prim(EdgeWeightedGraph G, int s) {
        distTo[s] = 0.0;
        pq.insert(s, distTo[s]);
        pqInserts++;
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            delMinOps++;
            scan(G, v);
        }
    }

    private void scan(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            edgeScans++;
            int w = e.other(v);
            if (marked[w]) continue;
            keyComparisons++;
            if (e.weight() < distTo[w]) {
                distTo[w] = e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.decreaseKey(w, distTo[w]);
                    pqDecreaseKeys++;
                } else {
                    pq.insert(w, distTo[w]);
                    pqInserts++;
                }
            }
        }
    }

    public Iterable<Edge> edges() { return mstEdges; }

    public double weight() {
        double sum = 0;
        for (Edge e : mstEdges) sum += e.weight();
        return sum;
    }
}