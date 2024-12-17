import java.io.*;
import java.util.*;

class Edge implements Comparable<Edge> {
    int startNode, destNode, cost;
    public int compareTo(Edge compareEdge) {
        return this.cost - compareEdge.cost;
    }
}
class Subset {
    int root, level;
}
// Code By Md Ohidur Rahman St_ID: 221002406
public class kruskal2NdMST {
    int V; // Number of vertices
    ArrayList<Edge> edges = new ArrayList<>(); // List of all edges
    // Read adjacency matrix and number of vertices from file
    public void readMatrixFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        V = Integer.parseInt(br.readLine()); // First line is the number of vertices
        
        for (int i = 1; i <= V; i++) { 
            String[] line = br.readLine().split(" ");
            for (int j = 1; j <= V; j++) { 
                int cost = Integer.parseInt(line[j - 1]);
                if (cost != 0) {
                    Edge edge = new Edge();
                    edge.startNode = i;
                    edge.destNode = j;
                    edge.cost = cost;
                    edges.add(edge);
                }
            }
        }
        br.close();
    }
    int find(Subset[] subSets, int i) {
        if (subSets[i].root != i)
            subSets[i].root = find(subSets, subSets[i].root);
        return subSets[i].root;
    }
    // Union two subSets
    void union(Subset[] subSets, int x, int y) {
        int X = find(subSets, x);
        int Y = find(subSets, y);

        if (subSets[X].level < subSets[Y].level)
            subSets[X].root = Y;
        else if (subSets[X].level > subSets[Y].level)
            subSets[Y].root = X;
        else {
            subSets[Y].root = X;
            subSets[X].level++;
        }
    }
    // Function to find MST by using Kruskal's algorithm
    ArrayList<Edge> kruskalMST() {
        ArrayList<Edge> result = new ArrayList<>();
        Collections.sort(edges); // Sort edges by cost
        Subset[] subSets = new Subset[V + 1];
        for (int i = 1; i <= V; i++) { // Init subSets
            subSets[i] = new Subset();
            subSets[i].root = i;
            subSets[i].level = 0;
        }
        int e = 0; // Number of edges in result
        int i = 0; // Index for sorted edges
        while (e < V - 1) {
            Edge nextEdge = edges.get(i++);
            int x = find(subSets, nextEdge.startNode);
            int y = find(subSets, nextEdge.destNode);
            if (x != y) {
                result.add(nextEdge);
                union(subSets, x, y);
                e++;
            }
        }
        return result;
    }
// Code By Md Ohidur Rahman St_ID: 221002406
    ArrayList<Edge> best2ndMST() {      // Find second-best MST
        ArrayList<Edge> firstMST = kruskalMST();
        Edge largestEdge = firstMST.get(firstMST.size() - 1); // Largest edge in the first MST
        edges.remove(largestEdge); // Remove the largest edge
        return kruskalMST(); // Return Call Again KruskalAlgo to find the second-best MST
    }
    // Print the second-best MST
    void print2ndMST(ArrayList<Edge> mst) {
        for (Edge edge : mst) {
            System.out.println(edge.startNode + " --> " + edge.destNode + " == " + edge.cost);
        }
    }
    public static void main(String[] args) throws IOException {
        kruskal2NdMST kruskal = new kruskal2NdMST();
        kruskal.readMatrixFromFile("src\\adjMatrix.txt");
        ArrayList<Edge> secondMST = kruskal.best2ndMST();
        System.out.println("\nSecond Best MST:\n");
        kruskal.print2ndMST(secondMST);
        System.out.println("\n");
    }
}
