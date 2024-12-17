import java.io.File;
import java.util.*;

public class DFSGraph {
    static int count = 0;

    public static void main(String[] args) throws Exception {
        File file = new File("src/vertices.txt");
        Scanner fileInput = new Scanner(file);

        ArrayList<Integer> vertices = new ArrayList<>();
        while (fileInput.hasNextInt()) {
            vertices.add(fileInput.nextInt());
        }
        fileInput.close();

        ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            graph.add(new ArrayList<>());
        }

        Scanner inputEdge = new Scanner(System.in);
        System.out.print("\nEnter Adjacency List For Each Vertex");
        System.out.print("\nNote: Saperated by Space And Must End With 0\n\n");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.print("Adjacency list for vertex " + vertices.get(i) + " : ");
            while (true) {
                int nearBeNode = inputEdge.nextInt();
                if (nearBeNode == 0) {
                    break;
                }
                graph.get(i).add(nearBeNode);
            }
        }
        System.out.println("\n\n");
        String[] label = new String[vertices.size()]; // Label (Discovered and Finished")
        int[] parent = new int[vertices.size()]; // Parent of each vertex
        int[] discovery = new int[vertices.size()]; // Discovery time
        int[] finishing = new int[vertices.size()]; // Finishing time

        // Initialize parent and label arrays
        Arrays.fill(parent, -1);
        Arrays.fill(label, "Undiscovered");

        for (int i = 0; i < vertices.size(); i++) {
            if (label[i].equals("Undiscovered")) {
                functionDFS(graph, i, label, parent, discovery, finishing, vertices);
            }
        }

        System.out.println("\nVertex | Parent | DiscoveryTime | FinishingTime");
        for (int i = 0; i < vertices.size(); i++) {
            System.out.printf("%4d   | %6d | %12d  | %12d%n", vertices.get(i), parent[i], discovery[i], finishing[i]);
        }

        inputEdge.close();
    }

    // DFS function
    public static void functionDFS(ArrayList<ArrayList<Integer>> graph, int vertexIdx, String[] label, int[] parent,
            int[] discovery, int[] finishing, ArrayList<Integer> vertices) {
        label[vertexIdx] = "Discovered";
        discovery[vertexIdx] = ++count;
        System.out.println("Discovered vertex: " + vertices.get(vertexIdx) + " at time " + discovery[vertexIdx]);
        for (int nearBeNode : graph.get(vertexIdx)) {
            int nearBeIndex = vertices.indexOf(nearBeNode);
            if (label[nearBeIndex].equals("Undiscovered")) {
                parent[nearBeIndex] = vertices.get(vertexIdx);
                functionDFS(graph, nearBeIndex, label, parent, discovery, finishing, vertices);
            }
        }
        label[vertexIdx] = "finished";
        finishing[vertexIdx] = ++count;
        System.out.println("Finished vertex: " + vertices.get(vertexIdx) + " at time " + finishing[vertexIdx]);
    }
}
