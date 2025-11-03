package org.example;
import org.example.io.Graph;
import org.example.io.GraphIO;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagShortestPaths;
import org.example.dagsp.DagLongestPaths;
import org.example.util.Metrics;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        String path = (args.length == 0)
                ? "data/small-1.json"
                : args[0];

        System.out.println("=== DAA4 MAIN RUN ===");
        System.out.println("file = " + path);

        GraphIO.LoadResult lr = GraphIO.loadFromFile(path);
        Graph g = lr.graph;

        // --- SCC ---
        Metrics m1 = new Metrics(); m1.start();
        var comps = new KosarajuSCC(g, m1).computeSCCs();
        m1.stop();

        int[] cid = KosarajuSCC.buildComponentIds(comps, g.size());

        // --- condensation ---
        Graph dag = new CondensationGraph(g, cid, comps.size()).getCondensationGraph();

        // --- topo ---
        Metrics m2 = new Metrics(); m2.start();
        List<Integer> topo = new TopologicalSort(dag).sort();
        m2.stop();

        // --- shortest ---
        Metrics m3 = new Metrics(); m3.start();
        var shortest = new DagShortestPaths(dag, m3).shortestFrom(cid[lr.source], topo);
        m3.stop();

        // --- longest ---
        Metrics m4 = new Metrics(); m4.start();
        var longest = new DagLongestPaths(dag, m4).longestFrom(cid[lr.source], topo);
        m4.stop();

        System.out.println("SCC = " + comps.size());
        System.out.println("Topo = " + topo);
        System.out.println("Shortest time ms = " + m3.getElapsedMs());
        System.out.println("Longest  time ms = " + m4.getElapsedMs());
        System.out.println("=== DONE ===");
    }
}
