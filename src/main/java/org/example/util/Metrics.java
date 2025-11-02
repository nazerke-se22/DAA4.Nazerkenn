package org.example.util;

/** Tracks runtime and counted operations. */
public class Metrics {
    private long startNs, endNs, ops;
    public void start(){ startNs=System.nanoTime(); ops=0; }
    public void stop(){ endNs=System.nanoTime(); }
    public void addOperation(){ ops++; }
    public void addOperations(long n){ ops += n; }
    public double getElapsedMs(){ return (endNs - startNs) / 1_000_000.0; }
    public long getOperationCount(){ return ops; }
    @Override public String toString(){ return String.format("[time=%.3f ms, ops=%d]", getElapsedMs(), ops); }
}
