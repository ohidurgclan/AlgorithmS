package com.secondmstusingprims;
/**
 * @author Md Ohidur Rahman
 * @ID 221002406
 */
import java.util.*;

public class SecondMST {
    static class Edge {
        int source, destination, weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        List<Edge>[] adjacencyList;

        public Graph(int vertices) {
            this.vertices = vertices;
            adjacencyList = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
        }

        public void addEdge(int source, int destination, int weight) {
            Edge edge = new Edge(source, destination, weight);
            adjacencyList[source].add(edge);
            // For undirected graph, we add an edge from destination to source as well
            edge = new Edge(destination, source, weight);
            adjacencyList[destination].add(edge);
        }

        public void primMST() {
            boolean[] visited = new boolean[vertices];
            int[] parent = new int[vertices];
            int[] key = new int[vertices];

            for (int i = 0; i < vertices; i++) {
                key[i] = Integer.MAX_VALUE;
            }

            PriorityQueue<Edge> pq = new PriorityQueue<>(vertices, Comparator.comparingInt(edge -> edge.weight));
            key[0] = 0;
            pq.add(new Edge(-1, 0, 0));

            while (!pq.isEmpty()) {
                Edge edge = pq.poll();
                int u = edge.destination;

                if (visited[u]) continue;

                visited[u] = true;

                for (Edge neighbor : adjacencyList[u]) {
                    int v = neighbor.destination;
                    int weight = neighbor.weight;
                    if (!visited[v] && weight < key[v]) {
                        parent[v] = u;
                        key[v] = weight;
                        pq.add(new Edge(u, v, weight));
                    }
                }
            }

            // Print the MST
            printMST(parent);
        }

        private void printMST(int[] parent) {
            System.out.println("Minimum Spanning Tree:");
            System.out.println("Edge \tWeight");
            int totalWeight = 0;
            for (int i = 1; i < vertices; i++) {
                int weight = getWeight(parent[i], i);
                System.out.println(parent[i] + " - " + i + "\t" + weight);
                totalWeight += weight;
            }
            System.out.println("Total Weight: " + totalWeight);
        }

        private int getWeight(int u, int v) {
            for (Edge edge : adjacencyList[u]) {
                if (edge.destination == v) {
                    return edge.weight;
                }
            }
            return -1; // Return -1 if edge is not found (should not happen in MST)
        }

        public List<Edge> findSecondMST() {
            int originalTotalWeight = getTotalWeight();

            List<Edge> secondMST = null;
            int minDiff = Integer.MAX_VALUE;

            for (int i = 0; i < vertices; i++) {
                for (Edge edge : adjacencyList[i]) {
                    int u = edge.source;
                    int v = edge.destination;
                    if (u == v) continue;

                    removeEdge(u, v);

                    int newTotalWeight = getTotalWeight();
                    
                    if (newTotalWeight < originalTotalWeight && originalTotalWeight - newTotalWeight < minDiff) {
                        secondMST = getMSTEdges();
                        minDiff = originalTotalWeight - newTotalWeight;
                    }

                    addEdge(u, v, edge.weight);
                }
            }

            return secondMST;
        }

        private int getTotalWeight() {
            int totalWeight = 0;
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);
            visited[0] = true;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (Edge edge : adjacencyList[u]) {
                    if (!visited[edge.destination]) {
                        totalWeight += edge.weight;
                        visited[edge.destination] = true;
                        queue.add(edge.destination);
                    }
                }
            }

            return totalWeight;
        }

        private List<Edge> getMSTEdges() {
            List<Edge> mstEdges = new ArrayList<>();
            boolean[] visited = new boolean[vertices];
            Queue<Integer> queue = new LinkedList<>();
            queue.add(0);
            visited[0] = true;

            while (!queue.isEmpty()) {
                int u = queue.poll();
                for (Edge edge : adjacencyList[u]) {
                    if (!visited[edge.destination]) {
                        mstEdges.add(edge);
                        visited[edge.destination] = true;
                        queue.add(edge.destination);
                    }
                }
            }

            return mstEdges;
        }

        private void removeEdge(int u, int v) {
            adjacencyList[u].removeIf(edge -> edge.destination == v);
            adjacencyList[v].removeIf(edge -> edge.destination == u);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int vertices = scanner.nextInt();
        Graph graph = new Graph(vertices);

        System.out.print("Enter the number of edges: ");
        int edges = scanner.nextInt();

        System.out.println("Enter the edges in the format: Source Destination Weight");
        for (int i = 0; i < edges; i++) {
            int source = scanner.nextInt();
            int destination = scanner.nextInt();
            int weight = scanner.nextInt();
            graph.addEdge(source, destination, weight);
        }

        graph.primMST();

        List<Edge> secondMST = graph.findSecondMST();

        System.out.println("Second Minimum Spanning Tree:");
        if (secondMST != null) {
            for (Edge edge : secondMST) {
                System.out.println(edge.source + " - " + edge.destination + "\t" + edge.weight);
            }
        } else {
            System.out.println("No second minimum spanning tree found.");
        }
    }
}
