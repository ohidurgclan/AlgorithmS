import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
// Code By Md Ohidur Rahman St_ID: 221002406
class Edge { // Edge Class
    int startNode, destNode, edgeCost;
    Edge(int startNode, int destNode, int edgeCost) { // And Its Constructor
        this.startNode = startNode;
        this.destNode = destNode;
        this.edgeCost = edgeCost;
    }
}
public class DistinctPrimMST {
    private Map<Integer, List<Edge>> graph;
    public DistinctPrimMST(String filePath) throws FileNotFoundException {
        graph = new HashMap<>();
        readGraphFromFile(filePath);
    }
    private void readGraphFromFile(String filePath) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNextLine()) {
            String[] dataSplit = scanner.nextLine().split(" ");
            int startNode = Integer.parseInt(dataSplit[0]);
            int destNode = Integer.parseInt(dataSplit[1]);
            int edgeCost = Integer.parseInt(dataSplit[2]);
            if (!graph.containsKey(startNode)) {
                graph.put(startNode, new ArrayList<>());
            }
        // This Algorithm Specially For Undirected Graph And I Succeeed to Implements
        // (5 2 0) dest 0 Mean One Way Connection
            if (edgeCost > 0) {  // This if Finding Directed or undirected graph
                graph.get(startNode).add(new Edge(startNode, destNode, edgeCost));
            }
        }
        scanner.close();
    }
    public List<List<Edge>> findDistinctMSTs(int startNode) {
        List<List<Edge>> allMSTs = new ArrayList<>();
        boolean[] visited = new boolean[graph.size() + 1];
        primsHelper(startNode, new ArrayList<>(), visited, allMSTs);
        return allMSTs;
    }
    private void primsHelper(int node, List<Edge> currentMST, boolean[] visited, List<List<Edge>> allMSTs) {
        visited[node] = true;

        if (currentMST.size() == graph.size() - 1) {
            allMSTs.add(new ArrayList<>(currentMST));
            return;
        }
    // Read all edges from the currentNode and grouped by Cost
        List<Edge> edges = graph.getOrDefault(node, new ArrayList<>());
        edges.sort(Comparator.comparingInt(edge -> edge.edgeCost));
        Map<Integer, List<Edge>> edgeMap = new HashMap<>();
        for (Edge edge : edges) {
            edgeMap.putIfAbsent(edge.edgeCost, new ArrayList<>());
            edgeMap.get(edge.edgeCost).add(edge);
        }
        for (Map.Entry<Integer, List<Edge>> entry : edgeMap.entrySet()) {
            boolean[] newVisited = Arrays.copyOf(visited, visited.length);
            for (Edge edge : entry.getValue()) {
                if (!newVisited[edge.destNode]) {
                    List<Edge> copiedMST = new ArrayList<>(currentMST); // Create a copy
                    copiedMST.add(edge);
                    primsHelper(edge.destNode, copiedMST, newVisited, allMSTs);
                }
            }
        }
    }
    // Code By Md Ohidur Rahman St_ID: 221002406
    public static void main(String[] args) {
        try {
            DistinctPrimMST distinctPrimMST = new DistinctPrimMST("src\\Graph.txt"); // Change to your file path
            List<List<Edge>> distinctMSTs = distinctPrimMST.findDistinctMSTs(1); // Start from node 1
            int count = 1;
            for (List<Edge> mst : distinctMSTs) {
                int allEdgeCost = 0;
                System.out.println("\nMST " + count + ":");
                for (Edge edge : mst) {
                    System.out.println("Edge: " + edge.startNode + " -> " + edge.destNode + " with cost: " + edge.edgeCost);
                    allEdgeCost += edge.edgeCost;
                }
                System.out.println("Total edgeCost: " + allEdgeCost);
                count++;
            }
            System.out.println("\nNumber of Distinct MST: " + distinctMSTs.size() + System.lineSeparator());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}