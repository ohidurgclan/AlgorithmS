import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge> {
    int node; // Destination node
    int cost; // Edge weight
    int src; // Source node

    public Edge(int node, int cost, int src) {
        this.node = node; // Set destination node
        this.cost = cost; // Set cost (weight)
        this.src = src; // Set source node
    }

    @Override
    public int compareTo(Edge other) {
        return this.cost - other.cost; // Compare edges by cost for priority queue
    }
}

public class distinctPrimsMST {

    private static Map<Integer, List<Edge>> graph = new HashMap<>();
    private static Map<String, List<Edge>> alternativeEdges = new HashMap<>(); // Tracks alternative edges
    private static List<Edge> mstEdges = new ArrayList<>(); // List of edges in the first MST

    public static void main(String[] args) {
        String filePath = "src\\disGraph.txt"; // Specify the file path here

        // Read the graph from the file
        readGraphFromFile(filePath);

        // Start Prim's algorithm from a source node (let's say node 1)
        int startNode = 1;
        int mstWeight = mstPrimsAlgo(startNode);

        System.out.println("MST Weight: " + mstWeight);

        // Calculate and print distinct MSTs
        int distinctMSTCount = printDistinctMSTs();
        System.out.println("Number of distinct MSTs: " + distinctMSTCount);
    }

    // Prim's Algorithm to find the Minimum Spanning Tree (MST)
    private static int mstPrimsAlgo(int startNode) {
        int totalMSTCost = 0; // Total weight of the MST

        PriorityQueue<Edge> minHeap = new PriorityQueue<>();
        Set<Integer> inMST = new HashSet<>();
        Map<Integer, Integer> cost = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        // Initialize costs to "infinity"
        for (int node : graph.keySet()) {
            cost.put(node, Integer.MAX_VALUE);
        }

        cost.put(startNode, 0);
        minHeap.add(new Edge(startNode, 0, -1)); // Start with the source node

        while (!minHeap.isEmpty()) {
            Edge currentEdge = minHeap.poll();
            int currentNode = currentEdge.node;

            if (inMST.contains(currentNode)) {
                continue;
            }

            inMST.add(currentNode);
            totalMSTCost += currentEdge.cost;

            // If it's a valid edge, add it to the MST
            if (currentEdge.src != -1) {
                mstEdges.add(currentEdge);

                String key = Math.min(currentEdge.src, currentNode) + "-" + Math.max(currentEdge.src, currentNode);
                alternativeEdges.putIfAbsent(key, new ArrayList<>());
                alternativeEdges.get(key).add(currentEdge);
            }

            // Process neighbors
            for (Edge neighbor : graph.get(currentNode)) {
                int neighborNode = neighbor.node;
                int edgeCost = neighbor.cost;

                if (!inMST.contains(neighborNode) && edgeCost < cost.get(neighborNode)) {
                    cost.put(neighborNode, edgeCost);
                    parent.put(neighborNode, currentNode);
                    minHeap.add(new Edge(neighborNode, edgeCost, currentNode));
                }

                // Track alternative edges with the same cost between the same nodes
                String edgeKey = Math.min(currentNode, neighborNode) + "-" + Math.max(currentNode, neighborNode);
                if (edgeCost == cost.get(neighborNode)) {
                    alternativeEdges.putIfAbsent(edgeKey, new ArrayList<>());
                    alternativeEdges.get(edgeKey).add(new Edge(neighborNode, edgeCost, currentNode));
                }
            }
        }

        return totalMSTCost;
    }

    // Function to print and count distinct MSTs by looking at alternative edges
    private static int printDistinctMSTs() {
        // Start with the original MST as the first one
        System.out.println("MST 1:");
        printMST(mstEdges);

        int mstCount = 1;

        // Check for alternative edges to generate distinct MSTs
        for (Map.Entry<String, List<Edge>> entry : alternativeEdges.entrySet()) {
            List<Edge> alternatives = entry.getValue();

            if (alternatives.size() > 1) {
                for (int i = 1; i < alternatives.size(); i++) {
                    List<Edge> newMST = new ArrayList<>(mstEdges);

                    // Replace the original edge with the alternative
                    Edge alternativeEdge = alternatives.get(i);
                    newMST = replaceEdgeInMST(newMST, alternatives.get(0), alternativeEdge);

                    System.out.println("MST " + (++mstCount) + ":");
                    printMST(newMST);
                }
            }
        }

        return mstCount;
    }

    // Function to replace an edge in the MST with an alternative
    private static List<Edge> replaceEdgeInMST(List<Edge> mst, Edge originalEdge, Edge newEdge) {
        List<Edge> newMST = new ArrayList<>();
        for (Edge edge : mst) {
            if (edge.src == originalEdge.src && edge.node == originalEdge.node && edge.cost == originalEdge.cost) {
                newMST.add(newEdge);
            } else {
                newMST.add(edge);
            }
        }
        return newMST;
    }

    // Function to print the edges of a given MST
    private static void printMST(List<Edge> mst) {
        for (Edge edge : mst) {
            System.out.println("Edge: " + edge.src + " - " + edge.node + " with cost: " + edge.cost);
        }
        System.out.println();
    }

    // Function to read the graph from a file (space-separated: node1 node2 cost)
    private static void readGraphFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int node1 = Integer.parseInt(parts[0]); // First node
                int node2 = Integer.parseInt(parts[1]); // Second node
                int cost = Integer.parseInt(parts[2]); // Cost of the edge

                // Add the edge from node1 to node2
                graph.computeIfAbsent(node1, k -> new ArrayList<>()).add(new Edge(node2, cost, node1));

                // Add the edge from node2 to node1 (since it's an undirected graph)
                graph.computeIfAbsent(node2, k -> new ArrayList<>()).add(new Edge(node1, cost, node2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}