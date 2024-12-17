import java.io.*;
import java.util.*;

public class dfsTarget {

    private static Map<Integer, List<Integer>> graph = new HashMap<>();
    private static Map<Integer, Integer> discovery = new HashMap<>();
    private static Map<Integer, Integer> finishing = new HashMap<>();
    private static Map<Integer, Integer> parent = new HashMap<>();

    private static int time = 0;

    public static void main(String[] args) {
        readGraphFromFile("src\\graph.txt");
        for (int node : graph.keySet()) {
            discovery.put(node, -1);
            finishing.put(node, -1);
            parent.put(node, null); 
        }

        for (int node : graph.keySet()) {
            if (discovery.get(node) == -1) {
                dfs(node);
            }
        }
        System.out.println("Node\tDiscovery\tFinishing\tParent");
        for (int node : graph.keySet()) {
            System.out.println(node + "\t\t" + discovery.get(node) + "\t\t" + finishing.get(node) + "\t\t" + parent.get(node));
        }
    }

    private static void dfs(int node) {
        time++;
        discovery.put(node, time);
        for (int neighbor : graph.get(node)) {
            if (discovery.get(neighbor) == -1) { 
                parent.put(neighbor, node);  
                dfs(neighbor);  
            }
        }

        time++; 
        finishing.put(node, time);  
    }

   
    private static void readGraphFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                int node = Integer.parseInt(parts[0]); 
                List<Integer> neighbors = new ArrayList<>();
                for (int i = 1; i < parts.length; i++) {
                    neighbors.add(Integer.parseInt(parts[i]));
                }
                graph.put(node, neighbors); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}