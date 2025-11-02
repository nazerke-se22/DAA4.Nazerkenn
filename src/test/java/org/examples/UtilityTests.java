package org.examples;
import org.example.util.Metrics;
import org.example.util.GraphLoader;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Utility tests for Metrics and GraphLoader functionality.
 */
public class UtilityTests {

    @Test
    void testMetricsIncrementAndReset() {
        Metrics m = new Metrics();
        m.addOperation();
        m.addOperation();
        assertTrue(m.getOperationCount() >= 2);

        m.start();
        m.addOperation();
        m.stop();
        assertEquals(1, m.getOperationCount());
        assertTrue(m.getElapsedMs() >= 0.0);
    }

    @Test
    void testGraphLoaderLoadsExistingDataset() throws Exception {
        var loaded = GraphLoader.load("data/small-1.json");
        assertNotNull(loaded.graph);
        assertTrue(loaded.source >= 0);
    }
}
