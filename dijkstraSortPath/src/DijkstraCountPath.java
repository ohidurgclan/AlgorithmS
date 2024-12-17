import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class DijkstraCountPath {
    static class Graph {
        int[][] adjMatrix;
        int nOfVertex;
        int source;
        int dest;
        @SuppressWarnings("resource")
        Graph(String filename) throws FileNotFoundException {
            Scanner Input = new Scanner(new File(filename));
            source = Input.nextInt() - 1; // Convert to 0-based index
            dest = Input.nextInt() - 1; // Convert to 0-based index
            List<int[]> matrixList = new ArrayList<>();
            while (Input.hasNextLine()) {
                String line = Input.nextLine();
                if (!line.trim().isEmpty()) {
                    String[] values = line.split(" ");
                    int[] row = Arrays.stream(values).mapToInt(Integer::parseInt).toArray();
                    matrixList.add(row);
                }
            }
            nOfVertex = matrixList.size();
            adjMatrix = new int[nOfVertex][nOfVertex];
            for (int i = 0; i < nOfVertex; i++) {
                adjMatrix[i] = matrixList.get(i);
            }
        }
        
        void dijkstra() {
            int[] dist = new int[nOfVertex];
            int[] pathCount = new int[nOfVertex];
            boolean[] visited = new boolean[nOfVertex];
            List<List<Integer>> paths = new ArrayList<>();
            for (int i = 0; i < nOfVertex; i++) {
                paths.add(new ArrayList<>());
            }
            Arrays.fill(dist, Integer.MAX_VALUE);
            dist[source] = 0;
            pathCount[source] = 1;
            paths.get(source).add(source);
            for (int count = 0; count < nOfVertex - 1; count++) {
                int u = minDistance(dist, visited);
                visited[u] = true;
                for (int v = 0; v < nOfVertex; v++) {
                    if (!visited[v] && adjMatrix[u][v] != 0) {
                        int newDist = dist[u] + adjMatrix[u][v];
                        if (newDist < dist[v]) {
                            dist[v] = newDist;
                            pathCount[v] = pathCount[u];
                            paths.set(v, new ArrayList<>(paths.get(u)));
                            paths.get(v).add(v); 
                        } else if (newDist == dist[v]) {
                            pathCount[v] += pathCount[u];
                        }
                    }
                }
            }
            printResult(dist[dest], pathCount[dest], paths.get(dest));
        }

        int minDistance(int[] dist, boolean[] visited) {
            int min = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int v = 0; v < nOfVertex; v++) {
                if (!visited[v] && dist[v] <= min) {
                    min = dist[v];
                    minIndex = v;
                }
            }
            return minIndex;
        }
        void printResult(int cost, int numPaths, List<Integer> path) {
            if (cost == Integer.MAX_VALUE) {
                System.out.println("There is no paths");
            } else {
                System.out.println("\nNumber of shortest paths: " + numPaths);
                System.out.println("Path: " + path.stream().map(v -> (v + 1)).toList() + " at cost: " + cost + "\n"); // Convert to 1-based index
            }
        }
        public static void main(String[] args) {
            try {
                Graph graph = new Graph("src\\graphMatrix.txt");
                graph.dijkstra();
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + e.getMessage());
            }
        }
    }
}