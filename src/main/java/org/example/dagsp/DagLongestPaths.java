package org.example.dagsp;
import org.example.io.Edge;
import org.example.io.Graph;
import org.example.util.Metrics;
import java.util.Arrays;
import java.util.List;
/**
 * Computes the longest (critical) path in a DAG using DP based on topological order.
 *
 * This is used after SCC condensation, because after compression the graph is always a DAG,
 * which allows linear DP without cycles.
 *
 *   complexity: O(V + E)
 *
 * metrics:
 *  - metrics.addOperation() counts DP relaxations and visits
 */
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

    /**
     * Runs max-DP relaxations along provided topological order.
     *
     * @param source the start vertex in DAG (usually original SCC id)
     * @param topo   list of vertices in topological order
     * @return Result object which contains dist[] and best() = maximum achievable distance
     */
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
