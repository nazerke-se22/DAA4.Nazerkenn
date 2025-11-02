import json, random, os
os.makedirs("data", exist_ok=True)

def generate_graph(n, density=0.3, dag=False, add_cycle=False):
    edges = []
    for u in range(n):
        for v in range(n):
            if u == v: continue
            if random.random() < density:
                edges.append({"u": u, "v": v, "w": random.randint(1, 10)})
    if dag:
        edges = [e for e in edges if e["u"] < e["v"]]
    if add_cycle and n >= 3:
        edges += [{"u":0,"v":1,"w":1},{"u":1,"v":2,"w":1},{"u":2,"v":0,"w":1}]
    seen = set()
    unique = []
    for e in edges:
        key = (e["u"], e["v"])
        if key not in seen:
            seen.add(key)
            unique.append(e)
    return unique

def write(name, n, edges):
    obj = {"directed": True, "n": n, "edges": edges, "source": 0, "weight_model": "edge"}
    path = f"data/{name}.json"
    with open(path, "w") as f:
        json.dump(obj, f, indent=2)
    print(f"created {path} ({len(edges)} edges)")

random.seed(42)

# Small 3 variants (6-10 nodes)
for i in range(1,4):
    n = random.randint(6,10)
    if i == 3:
        edges = generate_graph(n, density=0.25, dag=True)
    else:
        edges = generate_graph(n, density=0.35, add_cycle=True)
    write(f"small-{i}", n, edges)

# Medium 3 variants (10-20)
for i in range(1,4):
    n = random.randint(10,20)
    edges = generate_graph(n, density=0.35, add_cycle=(i!=2))
    write(f"medium-{i}", n, edges)

# Large 3 variants (20-50)
for i in range(1,4):
    n = random.randint(20,50)
    edges = generate_graph(n, density=0.22, add_cycle=(i==1))
    write(f"large-{i}", n, edges)
