package org.example.io;
public class Edge {
    public final int from;
    public final int to;
    public final int weight;
    public Edge(int from, int to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return String.format("(%d → %d, w=%d)", from, to, weight);
    }

    @Override
    public int hashCode() {
        return from * 31 + to;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge)) return false;
        Edge other = (Edge) obj;
        return this.from == other.from && this.to == other.to && this.weight == other.weight;
    }
}
