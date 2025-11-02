package org.example.topo;
import org.example.io.Edge;
import org.example.io.Graph;
import java.util.HashSet;
import java.util.Set;

/** Builds condensation DAG from SCC labels. */
public class CondensationGraph {
    private final Graph g; private final int[] compId; private final int compCount;
    private Graph dag;

    public CondensationGraph(Graph g, int[] compId, int compCount){
        this.g=g; this.compId=compId; this.compCount=compCount;
    }

    public Graph buildCondensation(){
        Graph d = new Graph(compCount);
        Set<Long> seen = new HashSet<>();
        for (int u=0; u<g.size(); u++){
            for (Edge e: g.neighbors(u)){
                int cu=compId[u], cv=compId[e.to];
                if (cu!=cv){
                    long key = (((long)cu)<<32) | (cv & 0xffffffffL);
                    if (seen.add(key)) d.addEdge(cu, cv, e.weight);
                }
            }
        }
        this.dag = d; return d;
    }

    public Graph getCondensationGraph(){
        if (dag==null) return buildCondensation(); return dag;
    }
}
