package org.example.util;
import java.io.FileWriter;
import java.io.IOException;
/** collects all timings into results.csv */
public class ResultsCSV {

    private static final String FILE = "results.csv";
    private static boolean headerWritten = false;

    public static void reset() {
        headerWritten = false;
        try (FileWriter fw = new FileWriter(FILE,false)) {
            // wipe file
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void append(String dataset, int sccCount, int dagNodes,
                              double sccMs, double topoMs, double shortMs, double longMs) {
        try (FileWriter fw = new FileWriter(FILE, true)) {
            if (!headerWritten) {
                fw.write("dataset,sccCount,dagNodes,sccMs,topoMs,shortMs,longMs\n");
                headerWritten = true;
            }
            fw.write(dataset + "," + sccCount + "," + dagNodes + ","
                    + sccMs + "," + topoMs + "," + shortMs + "," + longMs + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

