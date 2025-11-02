package org.example.util;
import org.example.io.Graph;
import org.example.io.GraphIO;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagLongestPaths;
import java.util.List;
/** Runs SCC → Condensation → Topo → LongestPath on all datasets and prints metrics. */
public class PerformanceAnalyzer {
    public static void runAll() throws Exception {
        List<String> files = List.of(
                "data/small-1.json","data/small-2.json","data/small-3.json",
                "data/medium-1.json","data/medium-2.json","data/medium-3.json",
                "data/large-1.json","data/large-2.json","data/large-3.json"
        );
        System.out.println("=== DAA4 Performance ===\n");
        for(String path: files){
            System.out.println("▶ " + path);
            GraphIO.LoadResult lr = GraphIO.loadFromFile(path);
            Graph g = lr.graph;

            Metrics m1=new Metrics(); m1.start();
            var comps = new KosarajuSCC(g, m1).computeSCCs();
            m1.stop();
            System.out.println("  SCC count = " + comps.size() + "  " + m1);

            int[] compId = KosarajuSCC.buildComponentIds(comps, g.size());
            var dag = new CondensationGraph(g, compId, comps.size()).getCondensationGraph();

            Metrics m2=new Metrics(); m2.start();
            var topo = new TopologicalSort(dag).sort();
            m2.stop();
            System.out.println("  Condensation size = " + dag.size() + "  " + m2);
            System.out.println("  Topo order = " + topo);

            Metrics m3=new Metrics(); m3.start();
            var longest = new DagLongestPaths(dag, m3).longestFrom(compId[lr.source], topo);
            m3.stop();
            long best=Long.MIN_VALUE; for(long d: longest.dist) best=Math.max(best,d);
            System.out.println("  Critical path weight = " + (best==Long.MIN_VALUE?"N/A":best) + "  " + m3 + "\n");
        }
        System.out.println("=== Done ===");
    }
}
