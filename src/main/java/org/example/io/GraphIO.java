package org.example.io;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import java.util.Map;

/** Loads graph JSON from /data/*.json produced by the generator. */
public class GraphIO {
    public static class GraphSpec {
        public boolean directed;
        public int n;
        public List<Map<String,Object>> edges;
        public Integer source;
        public String weight_model;
    }
    public static class LoadResult {
        public final Graph graph; public final int source; public final String weightModel;
        public LoadResult(Graph g,int s,String w){ graph=g; source=s; weightModel=w; }
    }
    public static LoadResult loadFromFile(String path) throws Exception{
        ObjectMapper om = new ObjectMapper();
        GraphSpec spec = om.readValue(new File(path), GraphSpec.class);
        Graph g = new Graph(spec.n);
        if (spec.edges != null) {
            for (Map<String,Object> e : spec.edges) {
                int u=((Number)e.get("u")).intValue();
                int v=((Number)e.get("v")).intValue();
                int w=((Number)e.get("w")).intValue();
                g.addEdge(u,v,w);
            }
        }
        int src = spec.source != null ? spec.source : 0;
        return new LoadResult(g, src, spec.weight_model);
    }
}
