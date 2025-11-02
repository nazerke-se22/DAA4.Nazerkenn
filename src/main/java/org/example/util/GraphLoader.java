package org.example.util;
import org.example.io.Graph;
import org.example.io.GraphIO;
public class GraphLoader {
    public static class Loaded {
        public final Graph graph;
        public final int source;
        public final String weightModel;
        public Loaded(Graph g, int s, String wm){ this.graph=g; this.source=s; this.weightModel=wm; }
    }

    public static Loaded load(String path) throws Exception {
        var lr = GraphIO.loadFromFile(path);
        return new Loaded(lr.graph, lr.source, lr.weightModel);
    }
}
