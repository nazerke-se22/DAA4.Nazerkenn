# Smart City Task Dependency — Graph Analysis Report

## 1. Overview

This project implements a full dependency–analysis pipeline for Smart City / Smart Campus tasks.

We take graph datasets (small / medium / large JSON files), detect cycles, compress them into a DAG, order execution, and find the project-critical chain.

| Step | Algorithm |
|-----:|-----------|
| 1 | SCC (Kosaraju) |
| 2 | Condensation Graph |
| 3 | Topological Sort |
| 4 | DAG Shortest Path |
| 5 | DAG Longest Path (Critical Path) |

All algorithms are O(V + E).

---

## 2. Algorithms

| Component | Description |
|----------|-------------|
| Kosaraju SCC | 2 DFS passes |
| Condensation DAG | compresses SCC to DAG |
| Topological Sort | DFS postorder |
| DAG Shortest Path | min-DP |
| DAG Longest Path | max-DP (critical path) |

Metrics = time (nanoTime) + operation counters.

---

## 3. Datasets

All located in: /data

| group | purpose |
|-------|---------|
| small_* | correctness |
| medium_* | mid stress |
| large_* | performance |

---

## 4. Final Results (Table №2 — ns)

| dataset | sccCount | dagNodes | sccNs | topoNs | longestNs |
|--------:|---------:|---------:|------:|-------:|----------:|
| data/small-1.json | 2 | 2 | 0.109375 | 1.015125 | 1.678500 |
| data/small-2.json | 1 | 1 | 0.042125 | 0.006375 | 0.004833 |
| data/small-3.json | 6 | 6 | 0.024917 | 0.018042 | 0.005542 |
| data/medium-1.json | 1 | 1 | 0.139042 | 0.005083 | 0.003292 |
| data/medium-2.json | 1 | 1 | 0.113333 | 0.006250 | 0.004166 |
| data/medium-3.json | 1 | 1 | 0.075459 | 0.004667 | 0.003167 |
| data/large-1.json | 1 | 1 | 0.388500 | 0.006667 | 0.004708 |
| data/large-2.json | 1 | 1 | 0.171208 | 0.006750 | 0.004417 |
| data/large-3.json | 1 | 1 | 0.656667 | 0.006417 | 0.004250 |

---

## 5. Complexity Summary

| Algorithm | Complexity |
|----------:|:-----------|
| Kosaraju SCC | O(V + E) |
| Condensation DAG | O(V + E) |
| Topological Sort | O(V + E) |
| DAG shortest | O(V + E) |
| DAG longest | O(V + E) |

Matches theory.

---

## 6. Conclusions & Recommendations

| Method | Use case |
|-------|----------|
| SCC | detect cycles |
| Condensation DAG | remove cycles |
| Topological Sort | valid schedule |
| Shortest Path | minimal cost/time |
| Longest Path | critical path |

This workflow is applicable to Smart City scheduling, logistics, manufacturing, and university timetabling.

---

## 7. Build & Run

build:
mvn clean install
run all tests:
mvn test
run all datasets (performance):
mvn exec:java -Dexec.mainClass="org.example.util.PerformanceAnalyzer"
run one dataset:
mvn exec:java -Dexec.mainClass="org.example.Main" -Dexec.args="data/small-1.json"

```bash
mvn clean install

Results saved to: results.csv
---

**By Abzhamal Nazerke**  
SE-2423