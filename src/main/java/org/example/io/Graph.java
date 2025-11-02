package org.example.io;
import java.util.*;
public class Graph {
    private final int n;
    private final List<List<Edge>> adj;
    public Graph(int n) {
        this.n = n;
        this.adj = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Edge(u, v, weight));
    }

    public List<Edge> neighbors(int u) {
        return adj.get(u);
    }

    public int size() {
        return n;
    }

    public Graph reversed() {
        Graph rev = new Graph(n);
        for (int u = 0; u < n; u++) {
            for (Edge e : adj.get(u)) {
                rev.addEdge(e.to, e.from, e.weight);
            }
        }
        return rev;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int u = 0; u < n; u++) {
            sb.append(u).append(": ").append(adj.get(u)).append("\n");
        }
        return sb.toString();
    }
}
