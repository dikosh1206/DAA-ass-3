package assign3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Kruskal's algorithm instrumentation.
 */
public class KruskalMSTInstrumented {
    public long edgeExamined = 0;
    public long ufFinds = 0; // counted as calls to UF.find aggregated
    public long ufUnions = 0;
    public long mstEdgesAdded = 0;

    private double weight = 0.0;
    private List<Edge> mst = new ArrayList<>();

    public KruskalMSTInstrumented(EdgeWeightedGraph G) {
        // copy edges
        List<Edge> list = new ArrayList<>();
        for (Edge e : G.edges()) list.add(e);
        Edge[] edges = list.toArray(new Edge[0]);
        Arrays.sort(edges);

        UF uf = new UF(G.V());
        for (int i = 0; i < edges.length && mst.size() < G.V() - 1; i++) {
            Edge e = edges[i];
            edgeExamined++;
            int v = e.either();
            int w = e.other(v);

            int r1 = uf.find(v); ufFinds++;
            int r2 = uf.find(w); ufFinds++;
            if (r1 != r2) {
                uf.union(r1, r2); ufUnions++;
                mst.add(e);
                weight += e.weight();
                mstEdgesAdded++;
            }
        }
    }

    public Iterable<Edge> edges() { return mst; }
    public double weight() { return weight; }
}
