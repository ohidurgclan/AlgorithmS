import java.io.*;
import java.util.*;

class Node implements Comparable<Node> {
    int vertex, cost;
    public Node(int vertex, int cost) {
        this.vertex = vertex;
        this.cost = cost;
    }
    public int compareTo(Node other) {
        return this.cost - other.cost;
    }
}
public class dijkstraSortPath {
    private Map<Integer, List<Node>> adjacencyList = new HashMap<>();
    private int V;
    private int[] dist;
    private int[] prevNode;
    public void graphFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String[] firstLine = br.readLine().split(" ");
        int source = Integer.parseInt(firstLine[0]);
        int destination = Integer.parseInt(firstLine[1]);
        V = 0;
        String line;
        while ((line = br.readLine()) != null) {
            String[] filePart = line.split(" ");
            int u = Integer.parseInt(filePart[0]);
            int v = Integer.parseInt(filePart[1]);
            int cost = Integer.parseInt(filePart[2]);
            V = Math.max(V, Math.max(u, v));
            adjacencyList.computeIfAbsent(u, k -> new ArrayList<>()).add(new Node(v, cost));
        }
        dijkstra(source, destination);
        br.close();
    }
    public void dijkstra(int source, int destination) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist = new int[V + 1]; 
        prevNode = new int[V + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prevNode, -1);
        pq.add(new Node(source, 0));    // Start from source node
        dist[source] = 0;

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int u = current.vertex;
            if (!adjacencyList.containsKey(u)) continue;
            for (Node nextNode : adjacencyList.get(u)) { // Traverse all connect node of CNode
                int v = nextNode.vertex;
                int cost = nextNode.cost;
                if (dist[u] + cost < dist[v]) {  // find a shorter path
                    dist[v] = dist[u] + cost;
                    pq.add(new Node(v, dist[v]));
                    prevNode[v] = u;
                }
            }
        }
        printShortestPath(source, destination);
    }
    public void printShortestPath(int source, int destination) {
        if (dist[destination] == Integer.MAX_VALUE) {
            System.out.println("No path exists from " + source + " to " + destination);
            return;
        }
        System.out.println("\nShortest distance from " + source + " --> " + destination + " at the cost == " + dist[destination]);
        System.out.print("\nPath: ");
        printPath(destination);
        System.out.print("\n");
        System.out.println();
    }
    private void printPath(int currentNode) {
        if (prevNode[currentNode] == -1) {
            System.out.print(currentNode);
            return;
        }
        printPath(prevNode[currentNode]);
        System.out.print(" -> " + currentNode);
    }
    public static void main(String[] args) throws IOException {
        dijkstraSortPath dijkstra = new dijkstraSortPath();
        dijkstra.graphFromFile("src\\graphList.txt");
    }
}