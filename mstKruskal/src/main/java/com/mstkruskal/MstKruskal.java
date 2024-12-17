package com.mstkruskal;
/**
 * @author Md Ohidur Rahman
 * @ID 221002406
 */
import java.util.*;

class Edge implements Comparable<Edge> {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
    @Override
    public int compareTo(Edge other) {
        return Integer.compare(this.weight, other.weight);
    }
}
class DisjointSet {
    int[] parent, rank;
    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }
    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX == rootY) return;
        if (rank[rootX] < rank[rootY]) {
            parent[rootX] = rootY;
        } else if (rank[rootX] > rank[rootY]) {
            parent[rootY] = rootX;
        } else {
            parent[rootY] = rootX;
            rank[rootX]++;
        }
    }
}
public class MstKruskal {
    public static List<Edge> findMST(List<Edge> edges, int n) {
        Collections.sort(edges);

        DisjointSet ds = new DisjointSet(n);

        List<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            int sourceRoot = ds.find(edge.source);
            int destinationRoot = ds.find(edge.destination);
            if (sourceRoot != destinationRoot) {
                mst.add(edge);
                ds.union(sourceRoot, destinationRoot);
            }
        }
        return mst;
    }
    public static int countMST(List<Edge> edges, int n) {
        List<Edge> mst = findMST(edges, n);
        Set<Integer> distinctWeights = new HashSet<>();
        for (Edge edge : mst) {
            distinctWeights.add(edge.weight);
        }
        int mstCount = distinctWeights.size();
        for (Edge edge : edges) {
            if (!distinctWeights.contains(edge.weight)) {
                List<Edge> tempEdges = new ArrayList<>(edges);
                tempEdges.remove(edge);
                List<Edge> tempMST = findMST(tempEdges, n);
                if (tempMST.size() == mst.size()) {
                    mstCount++;
                }
            }
        }
        return mstCount;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int n = input.nextInt();
        System.out.print("Enter the number of edges: ");
        int m = input.nextInt();
        List<Edge> edges = new ArrayList<>();
        System.out.println("Enter edges (Source Destination Weight): ");
        for (int i = 1; i <= m; i++) {
            int source = input.nextInt();
            int destination = input.nextInt();
            int weight = input.nextInt();
            edges.add(new Edge(source, destination, weight));
        }
        int mstCount = countMST(edges, n);
        System.out.println("Number of minimum spanning trees: " + mstCount);
    }
}
