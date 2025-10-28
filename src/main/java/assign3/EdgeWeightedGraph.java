package assign3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EdgeWeightedGraph {
    private final int V;
    private int E;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;
        adj = (List<Edge>[]) new List[V];
        for (int v = 0; v < V; v++) adj[v] = new LinkedList<>();
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        List<Edge> list = new ArrayList<>();
        boolean[][] seen = new boolean[V][V];
        for (int v = 0; v < V; v++) {
            for (Edge e : adj[v]) {
                int w = e.other(v);
                if (!seen[v][w]) {
                    list.add(e);
                    seen[v][w] = seen[w][v] = true;
                }
            }
        }
        return list;
    }

    public int V() { return V; }
    public int E() { return E; }
}