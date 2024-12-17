// import java.io.*;
// import java.util.*;

// class GraphEdge implements Comparable<GraphEdge> {
//     int node;
//     int cost;

//     public GraphEdge(int node, int cost) {
//         this.node = node;
//         this.cost = cost;
//     }
//     @Override
//     public int compareTo(GraphEdge other) {
//         return this.cost - other.cost;
//     }
// }
// public class primsAlgorithm {
//     private static Map<Integer, List<GraphEdge>> graph = new HashMap<>();
//     public static void main(String[] args) {
//         String filePath = "src\\mstGraph.txt"; 
//         readGraphFromFile(filePath);

//         int startNode = 1;
//         primsMST(startNode);
//     }

//     private static void primsMST(int startNode) {
//         PriorityQueue<GraphEdge> minHeap = new PriorityQueue<>();
//         Set<Integer> inMST = new HashSet<>();
//         Map<Integer, Integer> cost = new HashMap<>();
//         Map<Integer, Integer> parent = new HashMap<>();
//         for (int node : graph.keySet()) {
//             cost.put(node, Integer.MAX_VALUE);
//         }
//         cost.put(startNode, 0);
//         minHeap.add(new GraphEdge(startNode, 0)); 
//         while (!minHeap.isEmpty()) {
//             GraphEdge currentEdge = minHeap.poll();
//             int currentNode = currentEdge.node;
//             if (inMST.contains(currentNode)) {
//                 continue;
//             }
//             inMST.add(currentNode);
//             if (parent.containsKey(currentNode)) {
//                 System.out.println("Edge: " + parent.get(currentNode) + " - " + currentNode + " with cost: " + cost.get(currentNode));
//             }
//             for (GraphEdge neighbor : graph.get(currentNode)) {
//                 int neighborNode = neighbor.node;
//                 int edgeCost = neighbor.cost;
//                 if (!inMST.contains(neighborNode) && edgeCost < cost.get(neighborNode)) {
//                     cost.put(neighborNode, edgeCost);
//                     parent.put(neighborNode, currentNode);
//                     minHeap.add(new GraphEdge(neighborNode, edgeCost));
//                 }
//             }
//         }
//     }
//     private static void readGraphFromFile(String filePath) {
//         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//             String line;
//             while ((line = br.readLine()) != null) {
//                 String[] parts = line.split(" ");
//                 int srcNode = Integer.parseInt(parts[0]);
//                 int distNode = Integer.parseInt(parts[1]);
//                 int cost = Integer.parseInt(parts[2]);

//                 graph.computeIfAbsent(srcNode, k -> new ArrayList<>()).add(new GraphEdge(distNode, cost));
//                 graph.computeIfAbsent(distNode, k -> new ArrayList<>()).add(new GraphEdge(srcNode, cost));
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }