package org.example.util;
import org.example.io.GraphIO;
import org.example.io.Graph;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagShortestPaths;
import org.example.dagsp.DagLongestPaths;
import java.util.List;

/** runs all datasets and writes timing to results.csv */
public class PerformanceAnalyzer {

    public static void main(String[] args) throws Exception {

        List<String> files = List.of(
                "data/small-1.json",
                "data/small-2.json",
                "data/small-3.json",
                "data/medium-1.json",
                "data/medium-2.json",
                "data/medium-3.json",
                "data/large-1.json",
                "data/large-2.json",
                "data/large-3.json"
        );

        ResultsCSV.reset();

        for (String path : files) {
            runOne(path);
        }

        System.out.println("=== DONE ===");
    }

    public static void runOne(String path) throws Exception {

        var lr = GraphIO.loadFromFile(path);
        Graph g = lr.graph;

        // ---- SCC ----
        Metrics m1 = new Metrics(); m1.start();
        var scc = new KosarajuSCC(g, m1).computeSCCs();
        m1.stop();

        int compCount = scc.size();
        int[] cid = KosarajuSCC.buildComponentIds(scc, g.size());

        // ---- condensation ----
        Graph dag = new CondensationGraph(g, cid, compCount).getCondensationGraph();

        // ---- topo sort ----
        Metrics m2 = new Metrics(); m2.start();
        var topo = new TopologicalSort(dag).sort();
        m2.stop();

        // ---- shortest ----
        Metrics m3 = new Metrics(); m3.start();
        var shortest = new DagShortestPaths(dag, m3).shortestFrom(cid[lr.source], topo);
        m3.stop();

        // ---- longest ----
        Metrics m4 = new Metrics(); m4.start();
        var longest = new DagLongestPaths(dag, m4).longestFrom(cid[lr.source], topo);
        m4.stop();

        // CSV write
        ResultsCSV.append(
                path,
                compCount,
                dag.size(),
                m1.getElapsedMs(),
                m2.getElapsedMs(),
                m3.getElapsedMs(),
                m4.getElapsedMs()
        );

        System.out.println(path + " done.");
    }
}
