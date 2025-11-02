package org.example.dagsp;
import org.example.io.Edge;
import org.example.io.Graph;
import org.example.util.Metrics;
import java.util.Arrays;
import java.util.List;

public class DagLongestPaths {
    private final Graph dag;
    private final Metrics metrics;

    public static class Result {
        public final long[] dist;

        public Result(long[] d){ this.dist = d; }

        public long best(){
            long b = Long.MIN_VALUE;
            for(long x: dist) if (x>b) b=x;
            return b;
        }
    }

    public DagLongestPaths(Graph dag, Metrics m){
        this.dag = dag;
        this.metrics = m;
    }

    public Result longestFrom(int source, List<Integer> topo){
        long[] dist = new long[dag.size()];
        Arrays.fill(dist, Long.MIN_VALUE);
        dist[source] = 0;

        for(int u: topo){
            metrics.addOperation();
            if (dist[u] == Long.MIN_VALUE) continue;
            for(Edge e : dag.neighbors(u)){
                long nd = dist[u] + e.weight;
                metrics.addOperation();
                if(nd > dist[e.to]) dist[e.to] = nd;
            }
        }
        return new Result(dist);
    }
}