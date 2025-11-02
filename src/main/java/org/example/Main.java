package org.example;
import org.example.io.Graph;
import org.example.io.GraphIO;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagLongestPaths;
import org.example.util.Metrics;
import java.util.List;
import java.util.Arrays;
/**
 * Single-run pipeline:
 * load → SCC → DAG → Topo → Critical path
 */
public class Main {

    public static void main(String[] args) throws Exception {

        String path = (args.length == 0)
                ? "data/small-1.json"
                : args[0];

        System.out.println("=== DAA4 MAIN RUN ===");
        System.out.println("file = " + path);

        GraphIO.LoadResult lr = GraphIO.loadFromFile(path);
        Graph g = lr.graph;

        System.out.println("n = " + g.size());
        System.out.println("source = " + lr.source);

        // --- SCC ---
        Metrics m1 = new Metrics(); m1.start();
        var comps = new KosarajuSCC(g, m1).computeSCCs();
        m1.stop();
        System.out.println("SCC count = " + comps.size() + "   " + m1);

        // ids for condensation
        int[] compId = KosarajuSCC.buildComponentIds(comps, g.size());

        // --- condensation DAG ---
        var dag = new CondensationGraph(g, compId, comps.size()).getCondensationGraph();

        // --- topo sort ---
        Metrics m2 = new Metrics(); m2.start();
        List<Integer> topo = new TopologicalSort(dag).sort();
        m2.stop();
        System.out.println("Topo = " + topo + "   " + m2);

        // --- longest path ---
        Metrics m3 = new Metrics(); m3.start();
        var longest = new DagLongestPaths(dag, m3).longestFrom(compId[lr.source], topo);
        m3.stop();

        long best = Long.MIN_VALUE;
        for (long d : longest.dist) best = Math.max(best, d);

        System.out.println("Critical path weight = " + (best == Long.MIN_VALUE ? "none" : best) + "   " + m3);
        System.out.println("=== DONE ===");
    }
}
