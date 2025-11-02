package org.example.scc;
import org.example.io.Edge;
import org.example.io.Graph;
import org.example.util.Metrics;

import java.util.*;

/** Kosaraju SCC in O(V+E). */
public class KosarajuSCC {
    private final Graph g; private final Metrics metrics;
    public KosarajuSCC(Graph g, Metrics m){ this.g=g; this.metrics=m; }

    public List<List<Integer>> computeSCCs(){
        metrics.start();
        int n=g.size(); boolean[] used=new boolean[n]; Deque<Integer> order=new ArrayDeque<>();
        for(int v=0;v<n;v++) if(!used[v]) dfs1(v,used,order);
        Graph rev=g.reversed(); Arrays.fill(used,false); List<List<Integer>> comps=new ArrayList<>();
        while(!order.isEmpty()){
            int v=order.removeLast();
            if(!used[v]){ List<Integer> comp=new ArrayList<>(); dfs2(v,used,rev,comp); comps.add(comp); }
        }
        metrics.stop();
        return comps;
    }
    private void dfs1(int u, boolean[] used, Deque<Integer> order){
        used[u]=true; metrics.addOperation();
        for(Edge e:g.neighbors(u)) if(!used[e.to]) dfs1(e.to, used, order);
        order.addLast(u);
    }
    private void dfs2(int u, boolean[] used, Graph rev, List<Integer> comp){
        used[u]=true; metrics.addOperation(); comp.add(u);
        for(Edge e:rev.neighbors(u)) if(!used[e.to]) dfs2(e.to, used, rev, comp);
    }

    /** compId[v] = index of component containing v */
    public static int[] buildComponentIds(List<List<Integer>> comps, int n){
        int[] id=new int[n]; Arrays.fill(id,-1);
        for(int i=0;i<comps.size();i++) for(int v:comps.get(i)) id[v]=i;
        return id;
    }
}
