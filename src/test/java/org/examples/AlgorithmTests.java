package org.examples;
import org.example.io.Graph;
import org.example.scc.KosarajuSCC;
import org.example.topo.CondensationGraph;
import org.example.topo.TopologicalSort;
import org.example.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests for core graph algorithms:
 * - SCC detection (Kosaraju)
 * - Condensation graph
 * - Topological Sort (Kahn)
 */
public class AlgorithmTests {

    // --- Kosaraju tests ---
    @Test
    void testStronglyConnectedPair() {
        Graph g = new Graph(2);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);

        Metrics m = new Metrics();
        var comps = new KosarajuSCC(g, m).computeSCCs();

        assertEquals(1, comps.size());
        assertTrue(comps.get(0).containsAll(List.of(0, 1)));
    }

    @Test
    void testDisconnectedVertices() {
        Graph g = new Graph(3);
        Metrics m = new Metrics();
        var comps = new KosarajuSCC(g, m).computeSCCs();

        assertEquals(3, comps.size());
    }

    // --- Condensation Graph test ---
    @Test
    void testCondensationGraph() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 0, 1);
        g.addEdge(2, 3, 1);
        g.addEdge(3, 2, 1);

        Metrics m = new Metrics();
        var comps = new KosarajuSCC(g, m).computeSCCs();
        int[] compId = org.example.scc.KosarajuSCC.buildComponentIds(comps, g.size());
        Graph dag = new CondensationGraph(g, compId, comps.size()).getCondensationGraph();

        assertEquals(2, dag.size());
    }

    // --- Topological Sort tests ---
    @Test
    void testLinearTopoOrder() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 1);
        g.addEdge(2, 3, 1);

        var order = new TopologicalSort(g).sort();
        assertEquals(List.of(0, 1, 2, 3), order);
    }

    @Test
    void testBranchingTopoOrder() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);

        var order = new TopologicalSort(g).sort();
        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
    }
}
