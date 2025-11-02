package org.example.util;

import org.example.dagsp.DagLongestPaths;
import org.example.io.Graph;
import org.example.io.GraphIO;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;

/** runs one dataset and writes timing to CSV */
public class PerformanceAnalyzer {

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

        // ---- longest path ----
        Metrics m3 = new Metrics(); m3.start();
        var longest = new DagLongestPaths(dag, m3).longestFrom(cid[lr.source], topo);
        m3.stop();

        // write CSV
        ResultsCSV.append(
                path,
                compCount,
                dag.size(),
                m1.getElapsedMs(),
                m2.getElapsedMs(),
                m3.getElapsedMs()
        );

        System.out.println(path);
        System.out.println("  SCC=" + compCount +
                "  DAG nodes=" + dag.size());
    }
}
