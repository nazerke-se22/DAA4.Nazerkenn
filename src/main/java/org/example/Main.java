package org.example;
import org.example.util.PerformanceAnalyzer;
import org.example.util.Metrics;
import org.example.io.GraphIO;
import org.example.io.Graph;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagLongestPaths;
import java.util.List;
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            PerformanceAnalyzer.runAll();
            return;
        }

        String path = args[0];
        System.out.println("Dataset: " + path);
        GraphIO.LoadResult lr = GraphIO.loadFromFile(path);
        Graph g = lr.graph;

        Metrics m1 = new Metrics(); m1.start();
        var sccs = new KosarajuSCC(g, m1).computeSCCs();
        m1.stop();
        System.out.println("SCC count = " + sccs.size() + "  " + m1);

        int[] compId = KosarajuSCC.buildComponentIds(sccs, g.size());
        var dag = new CondensationGraph(g, compId, sccs.size()).getCondensationGraph();

        Metrics m2 = new Metrics(); m2.start();
        List<Integer> topo = new TopologicalSort(dag).sort();
        m2.stop();
        System.out.println("Topo order = " + topo + "  " + m2);

        Metrics m3 = new Metrics(); m3.start();
        var longest = new DagLongestPaths(dag, m3).longestFrom(compId[lr.source], topo);
        m3.stop();

        long best = Long.MIN_VALUE;
        for (long d : longest.dist) best = Math.max(best, d);
        System.out.println("Critical (longest) path weight = " + (best==Long.MIN_VALUE? "N/A": best) + "  " + m3);
    }
}
