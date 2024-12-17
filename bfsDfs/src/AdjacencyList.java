import java.io.*;
import java.util.*;

public class AdjacencyList {

    // Map to store the graph: node -> list of neighbors
    private static Map<Integer, List<Integer>> graph = new HashMap<>();

    public static void main(String[] args) {
        String filePath = "src\\graph.txt";  // Specify the file path here

        // Read the graph from the file
        readGraphFromFile(filePath);

        // Print the adjacency list representation of the graph
        printAdjacencyList();
    }

    // Function to read the graph from a file (space-separated adjacency list)
    private static void readGraphFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int node = Integer.parseInt(parts[0]);  // First value is the node
                List<Integer> neighbors = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    neighbors.add(Integer.parseInt(parts[i]));  // Rest are neighbors
                }
                graph.put(node, neighbors);  // Add to graph
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to print the adjacency list representation of the graph
    private static void printAdjacencyList() {
        System.out.println("Adjacency List Representation:");
        for (int node : graph.keySet()) {
            System.out.print(node + ": ");
            for (int neighbor : graph.get(node)) {
                System.out.print(neighbor + " ");
            }
            System.out.println();
        }
    }
}