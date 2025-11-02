package org.example.util;
import java.io.FileWriter;
import java.io.IOException;
public class ResultsCSV {
    private static final String FILE = "results.csv";
    private static boolean headerWritten = false;

    public static void append(String dataset, int sccCount, int dagNodes,
                              double sccMs, double topoMs, double lpMs) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            if(!headerWritten){
                fw.write("dataset,sccCount,dagNodes,sccMs,topoMs,longestMs\n");
                headerWritten = true;
            }
            fw.write(dataset + "," + sccCount + "," + dagNodes + ","
                    + sccMs + "," + topoMs + "," + lpMs + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
