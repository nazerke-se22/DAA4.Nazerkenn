package org.example.topo;
import org.example.io.Edge;
import org.example.io.Graph;
import java.util.*;

/** Kahn's algorithm for topological order on DAG. */
public class TopologicalSort {
    private final Graph dag;
    public TopologicalSort(Graph dag){ this.dag=dag; }

    public List<Integer> sort(){
        int n=dag.size(); int[] indeg=new int[n];
        for(int u=0;u<n;u++) for(Edge e:dag.neighbors(u)) indeg[e.to]++;
        Deque<Integer> q=new ArrayDeque<>(); for(int i=0;i<n;i++) if(indeg[i]==0) q.add(i);
        List<Integer> order=new ArrayList<>();
        while(!q.isEmpty()){
            int u=q.remove(); order.add(u);
            for(Edge e:dag.neighbors(u)){ if(--indeg[e.to]==0) q.add(e.to); }
        }
        if(order.size()!=n) throw new IllegalStateException("Graph has cycle");
        return order;
    }
}
