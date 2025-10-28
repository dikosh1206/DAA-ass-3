package assign3;

public class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        if (v < 0) throw new IllegalArgumentException("vertex index must be nonnegative");
        if (w < 0) throw new IllegalArgumentException("vertex index must be nonnegative");
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() { return weight; }
    public int either() { return v; }
    public int other(int vertex) { return vertex == v ? w : v; }

    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.3f", v, w, weight);
    }
}
