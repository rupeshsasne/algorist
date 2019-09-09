package com.radix2.hr.graph;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingDouble;

public class Kruskal {

    public static class UF {
        private final int[] ids;
        private final int[] size;

        public UF(int n) {
            ids = new int[n];
            size = new int[n];

            for (int i = 0; i < ids.length; i++) {
                ids[i] = i;
            }

            Arrays.fill(size, 1);
        }

        public boolean connected(int p, int q) {
            return root(p) == root(q);
        }

        public void union(int p, int q) {
            int pRoot = root(p);
            int qRoot = root(q);

            if (pRoot == qRoot)
                return;

            if (size[pRoot] > size[qRoot]) {
                ids[qRoot] = ids[pRoot];
                size[pRoot] += size[qRoot];
            } else {
                ids[pRoot] = ids[qRoot];
                size[qRoot] += size[pRoot];
            }
        }

        private int root(final int p) {
            int trav = p;

            while (ids[trav] != trav) {
                ids[trav] = ids[ids[trav]];
                trav = ids[trav];
            }

            return trav;
        }
    }

    public static class Edge {
        private final int v;
        private final int w;
        private final double weight;

        public Edge(int v, int w, double weight) {
            this.v = v;
            this.w = w;
            this.weight = weight;
        }

        public int either() {
            return v;
        }

        public int other(int endpoint) {

            if (endpoint == v) {
                return w;
            }

            if (endpoint == w) {
                return v;
            }

            throw new IllegalArgumentException();
        }

        @Override
        public String toString() {
            return v + " - " + w + " : " + weight;
        }
    }

    public static class EdgeWeighedGraph {
        private final List<Edge>[] bag;

        public EdgeWeighedGraph(int vertices) {
            //noinspection unchecked
            bag = new List[vertices + 1];

            for (int i = 0; i < bag.length; i++) {
                bag[i] = new ArrayList<>();
            }
        }

        public void addEdge(Edge edge) {
            int v = edge.either();
            int w = edge.other(v);

            bag[v].add(edge);
            bag[w].add(edge);
        }

        public int v() {
            return bag.length;
        }

        public Iterable<Edge> adj(int v) {
            return bag[v];
        }

        public Iterable<Edge> edges() {
            Set<Edge> list = new HashSet<>();

            for (List<Edge> adjList : bag) {
                list.addAll(adjList);
            }

            return list;
        }
    }

    public static int kruskals(EdgeWeighedGraph edgeWeighedGraph) {
        List<Edge> mst = new ArrayList<>();
        int mstWeight = 0;

        Queue<Edge> pq = new PriorityQueue<>(comparingDouble(o -> o.weight));

        for (Edge edge : edgeWeighedGraph.edges()) {
            pq.add(edge);
        }

        UF uf = new UF(edgeWeighedGraph.v() - 1);

        while (!pq.isEmpty() && mst.size() != edgeWeighedGraph.v() - 1) {
            Edge minEdge = pq.poll();

            int v = minEdge.either();
            int w = minEdge.other(v);

            if (!uf.connected(v - 1, w - 1)) {
                uf.union(v - 1, w - 1);
                mst.add(minEdge);
                mstWeight += minEdge.weight;
            }
        }

        return mstWeight;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String[] gNodesEdges = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int gNodes = Integer.parseInt(gNodesEdges[0]);
        int gEdges = Integer.parseInt(gNodesEdges[1]);

        EdgeWeighedGraph edgeWeighedGraph = new EdgeWeighedGraph(gNodes);

        IntStream.range(0, gEdges).forEach(i -> {
            try {
                String[] gFromToWeight = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");
                Edge edge = new Edge(Integer.parseInt(gFromToWeight[0]), Integer.parseInt(gFromToWeight[1]), Double.parseDouble(gFromToWeight[2]));
                edgeWeighedGraph.addEdge(edge);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();

        int res = kruskals(edgeWeighedGraph);

        System.out.println(res);
    }
}
