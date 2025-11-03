package org.example.dagsp;
import org.example.io.Edge;
import org.example.io.Graph;
import org.example.util.Metrics;
import java.util.Arrays;
import java.util.List;

/**
 * DAG shortest path using DP over topological order.
 * dist[v] = min(dist[v], dist[u] + w)
 * works only because DAG has no cycles.
 */
public class DagShortestPaths {

    private final Graph dag;
    private final Metrics metrics;

    public static final long INF = Long.MAX_VALUE / 4;

    public static class Result {
        public final long[] dist;
        public Result(long[] d) { this.dist = d; }
    }

    public DagShortestPaths(Graph dag, Metrics m){
        this.dag = dag;
        this.metrics = m;
    }

    public Result shortestFrom(int source, List<Integer> topo) {

        long[] dist = new long[dag.size()];
        Arrays.fill(dist, INF);
        dist[source] = 0;

        for (int u : topo) {
            metrics.addOperation();

            if (dist[u] == INF) continue;

            // shortest path DP:
            // again we use topological order because in DAG parents always come first.
            //
            // dist[u] holds the minimal cost to reach u
            // so for each outgoing edge u -> v:
            //      dist[v] = min(dist[v], dist[u] + weight)
            //
            // this works ONLY because graph is acyclic
            // (if cycles existed — this could loop forever / Dijkstra or Bellman-Ford needed)

            for (Edge e : dag.neighbors(u)) {
                long nd = dist[u] + e.weight;
                metrics.addOperation();
                if (nd < dist[e.to]) dist[e.to] = nd;
            }
        }
        return new Result(dist);
    }
}
