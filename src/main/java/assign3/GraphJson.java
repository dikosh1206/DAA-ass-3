package assign3;

import java.util.List;

public class GraphJson {
    public List<GraphSpec> graphs;

    public static class GraphSpec {
        public int id;
        public List<String> nodes;
        public List<EdgeSpec> edges;
    }

    public static class EdgeSpec {
        public String from;
        public String to;
        public double weight;
    }
}
