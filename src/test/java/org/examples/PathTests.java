package org.examples;
import org.example.io.Graph;
import org.example.topo.TopologicalSort;
import org.example.dagsp.DagLongestPaths;
import org.example.util.Metrics;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests for DAG longest path algorithm (critical path).
 */
public class PathTests {

    @Test
    void testLongestPathSimple() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(0, 2, 10);
        g.addEdge(2, 3, 1);

        var topo = new TopologicalSort(g).sort();
        Metrics m = new Metrics();
        var res = new DagLongestPaths(g, m).longestFrom(0, topo);

        // Longest: 0 - 2 - 3 = 11
        assertEquals(11, res.dist[3]);
    }

    @Test
    void testUnreachableVertex() {
        Graph g = new Graph(3);
        g.addEdge(0, 1, 5);

        var topo = new TopologicalSort(g).sort();
        Metrics m = new Metrics();
        var res = new DagLongestPaths(g, m).longestFrom(0, topo);
        assertEquals(Long.MIN_VALUE, res.dist[2]); // not reachable
    }
}
