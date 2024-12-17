import java.io.File;
import java.util.*;
// Student Md Ohidur Rahman ID 221002406
public class App {
    public static void main(String[] args) throws Exception {
        File file = new File("src\\vertices.txt");
        Scanner fileScanner = new Scanner(file);

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
        ArrayList<Integer> vertices = new ArrayList<>();

        while (fileScanner.hasNextInt()) {
            vertices.add(fileScanner.nextInt());
        }
        fileScanner.close();

        for (int i = 0; i < vertices.size(); i++) {
            graph.add(new ArrayList<>());
        }

        Scanner inputScanner = new Scanner(System.in);
        System.out.println("Enter adjacency lists for each vertex:");
        System.out.println("Note: Saperate with Space And Must Be End With 0");
        for (int i = 0; i < vertices.size(); i++) {
            int vertex = vertices.get(i);
            System.out.print("Adjacency list for vertex " + vertex + ": ");
            
            while (inputScanner.hasNextInt()) {
                int neighbor = inputScanner.nextInt();
                if (neighbor == 0) {
                    break; 
                }
                graph.get(i).add(neighbor);
            }
        }
        inputScanner.close();

        // Step 4: Perform BFS starting from a given source vertex
        int source = vertices.get(0);  // Start from the first vertex (can be changed)
        bfs(graph, source, vertices);
    }

    // BFS function
    public static void bfs(ArrayList<ArrayList<Integer>> graph, int startVertex, ArrayList<Integer> vertices) {
        // BFS algorithm
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[vertices.size()];
        int startIdx = vertices.indexOf(startVertex);  // Find the index of the starting vertex

        // Start BFS from the startVertex
        queue.add(startIdx);
        visited[startIdx] = true;

        while (!queue.isEmpty()) {
            int currentIdx = queue.poll();
            int currentVertex = vertices.get(currentIdx);  // Map the index back to the vertex
            System.out.println("Visited vertex: " + currentVertex);

            // Visit all neighbors of the current vertex
            for (int neighbor : graph.get(currentIdx)) {
                int neighborIdx = vertices.indexOf(neighbor);  // Map neighbor to its index
                if (!visited[neighborIdx]) {
                    visited[neighborIdx] = true;
                    queue.add(neighborIdx);
                }
            }
        }
    }
}