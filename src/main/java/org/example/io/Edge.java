package org.example.io;
/** immutable directed weighted edge */
public class Edge {
    public final int from, to, weight;
    public Edge(int from, int to, int weight){ this.from=from; this.to=to; this.weight=weight; }
    @Override public String toString(){ return String.format("(%d→%d,w=%d)", from, to, weight); }
    @Override public int hashCode(){ return from*31 + to*17 + weight; }
    @Override public boolean equals(Object o){
        if (this==o) return true;
        if (!(o instanceof Edge)) return false;
        Edge e=(Edge)o; return from==e.from && to==e.to && weight==e.weight;
    }
}
