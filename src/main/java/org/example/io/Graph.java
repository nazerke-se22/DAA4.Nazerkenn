package org.example.io;
import java.util.*;

/** Directed weighted graph via adjacency lists. Vertices: 0..n-1. */
public class Graph {
    private final int n;
    private final List<List<Edge>> adj;

    public Graph(int n){
        this.n=n; this.adj=new ArrayList<>(n);
        for(int i=0;i<n;i++) adj.add(new ArrayList<>());
    }

    public void addEdge(int u,int v,int w){ adj.get(u).add(new Edge(u,v,w)); }
    public List<Edge> neighbors(int u){ return adj.get(u); }
    public int size(){ return n; }

    /** Reverse edges (used in Kosaraju). */
    public Graph reversed(){
        Graph r = new Graph(n);
        for(int u=0;u<n;u++) for(Edge e:adj.get(u)) r.addEdge(e.to, e.from, e.weight);
        return r;
    }

    @Override public String toString(){
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<n;i++) sb.append(i).append(": ").append(adj.get(i)).append("\n");
        return sb.toString();
    }
}