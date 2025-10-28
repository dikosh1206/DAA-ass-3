package assign3;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        if (args.length < 1) {
            System.err.println("Usage: java -jar assignment3-mst.jar <json-file> [graphId]");
            return;
        }
        String jsonFile = args[0];
        Integer graphId = null;
        if (args.length >= 2) {
            try { graphId = Integer.parseInt(args[1]); } catch (Exception ignored) {}
        }

        Gson gson = new Gson();
        JsonReader jr = new JsonReader(new FileReader(jsonFile));
        GraphJson gj = gson.fromJson(jr, GraphJson.class);

        for (GraphJson.GraphSpec gs : gj.graphs) {
            if (graphId != null && gs.id != graphId) continue;
            runGraph(gs);
        }
    }

    private static void runGraph(GraphJson.GraphSpec gs) {
        System.out.println("=== Graph id = " + gs.id + " ===");
        // map node names to indices
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < gs.nodes.size(); i++) map.put(gs.nodes.get(i), i);

        EdgeWeightedGraph G = new EdgeWeightedGraph(gs.nodes.size());
        for (GraphJson.EdgeSpec es : gs.edges) {
            int u = map.get(es.from);
            int v = map.get(es.to);
            G.addEdge(new Edge(u, v, es.weight));
        }

        System.out.printf("Original graph: |V|=%d  |E|=%d\n", G.V(), G.E());

        // Run Prim
        long t0 = System.nanoTime();
        PrimMSTInstrumented prim = new PrimMSTInstrumented(G);
        long t1 = System.nanoTime();
        long primTimeMs = (t1 - t0) / 1_000_000;
        System.out.println("\n--- Prim's algorithm ---");
        printMSTEdges(prim.edges());
        System.out.printf("Total MST cost: %.3f\n", prim.weight());
        System.out.printf("Execution time: %d ms\n", primTimeMs);
        System.out.println("Counters:");
        System.out.printf(" edgeScans: %d\n pqInserts: %d\n pqDecreaseKeys: %d\n keyComparisons: %d\n delMinOps: %d\n",
                prim.edgeScans, prim.pqInserts, prim.pqDecreaseKeys, prim.keyComparisons, prim.delMinOps);

        // Run Kruskal
        long k0 = System.nanoTime();
        KruskalMSTInstrumented kruskal = new KruskalMSTInstrumented(G);
        long k1 = System.nanoTime();
        long kruskalTimeMs = (k1 - k0) / 1_000_000;
        System.out.println("\n--- Kruskal's algorithm ---");
        printMSTEdges(kruskal.edges());
        System.out.printf("Total MST cost: %.3f\n", kruskal.weight());
        System.out.printf("Execution time: %d ms\n", kruskalTimeMs);
        System.out.println("Counters:");
        System.out.printf(" edgeExamined: %d\n ufFinds: %d\n ufUnions: %d\n mstEdgesAdded: %d\n",
                kruskal.edgeExamined, kruskal.ufFinds, kruskal.ufUnions, kruskal.mstEdgesAdded);

        System.out.println("=== End Graph ===\n");
    }

    private static void printMSTEdges(Iterable<Edge> edges) {
        System.out.println("MST edges:");
        for (Edge e : edges) {
            System.out.println("  " + e);
        }
    }
}