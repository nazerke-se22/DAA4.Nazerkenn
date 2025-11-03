# DAA Assignment 4 – Smart City Dependency Analysis
**Author: Abzhamal Nazerke — SE-2422**

---

## 1) Project Goal

The goal of this project is to analyse directed task-dependency graphs (Smart City / Smart Campus scenario) and measure performance of core graph algorithms:

| Stage | Algorithm used |
|-------|----------------|
| 1 | Strongly Connected Components → **Kosaraju** |
| 2 | SCC Condensation → DAG |
| 3 | Topological Sort |
| 4 | Shortest Path in DAG |
| 5 | Longest Path in DAG (Critical Path) |

All algorithms run in **O(V + E)**.

---

## 2) Algorithms Explanation (simple & clear)

| Component | Why it is needed |
|----------|------------------|
| **Kosaraju SCC** | finds cycles → which tasks depend on each other mutually |
| **Condensation DAG** | compresses every SCC into 1 node → after this graph has no cycles |
| **Topological Sort** | creates valid execution order (dependencies respected) |
| **Shortest Path in DAG** | finds minimum required time / cheapest execution chain |
| **Longest Path in DAG** | finds “critical path” → longest chain → defines total project duration |

This is a realistic model for Smart City scheduling (repairs, maintenance, logistics).

---

## 3) Performance Results (table #2 — final used)

unit = milliseconds (ms)

| dataset | sccCount | dagNodes | sccMs | topoMs | shortMs | longMs |
|--------:|---------:|---------:|------:|-------:|--------:|-------:|
| data/small-1.json | 2 | 2 | 0.038541 | 0.244125 | 0.989833 | 0.3555 |
| data/small-2.json | 1 | 1 | 0.017959 | 0.002084 | 0.00125 | 0.000667 |
| data/small-3.json | 6 | 6 | 0.0085 | 0.003625 | 0.001958 | 0.001292 |
| data/medium-1.json | 1 | 1 | 0.10475 | 0.004292 | 0.003 | 0.001917 |
| data/medium-2.json | 1 | 1 | 0.053 | 0.001667 | 0.001291 | 0.000625 |
| data/medium-3.json | 1 | 1 | 0.051208 | 0.005208 | 0.003708 | 0.002833 |
| data/large-1.json | 1 | 1 | 0.100541 | 0.002375 | 0.001833 | 0.001167 |
| data/large-2.json | 1 | 1 | 0.120875 | 0.0025 | 0.00225 | 0.00075 |
| data/large-3.json | 1 | 1 | 0.256166 | 0.002542 | 0.001292 | 0.000666 |

---

## 4) Analysis & Interpretation (main part)

* SCC time grows slightly because on larger graphs SCC needs to scan more edges → but still linear.
* Most medium & large datasets collapse to **1 SCC**, therefore condensation DAG = only 1 node.
* Because DAG is mostly 1 node → topo sort, shortest and longest path are almost instant.
* Shortest vs Longest path have almost the same time — because they both use DP relaxation over topo order.

Very important conclusion:

> The performance results are consistent with theoretical complexity **O(V + E)**.  
> There is no exponential growth — scaling remains linear.

This means algorithms are suitable for real Smart City systems because they are fast even on dense graphs.

---

## 5) How to run

compile:

```bash
mvn clean install
mvn exec:java -Dexec.mainClass=org.example.Main -Dexec.args="data/small-1.json"
mvn exec:java -Dexec.mainClass=org.example.PerformanceAnalyzer
mvn test
___ 

## 6) Final Conclusion

This project successfully implemented:

SCC detection

Condensation DAG

Topological sort

Shortest & Longest path on DAG

The empirical results match theory, confirm linear scalability and prove correctness.
This analysis can be directly applied to Smart Campus / Smart City scheduling to detect cycles, plan correct order and estimate total completion time.