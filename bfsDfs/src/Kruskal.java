import java.io.File;
import java.util.*;

class Edge implements Comparable<Edge> {
    int src, dest, weight;

    public Edge(int src, int dest, int weight) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
    }

    public int compareTo(Edge other) {
        return this.weight - other.weight;
    }
}

public class Kruskal {
    static class Union {
        int[] parent, locaTion;

        public Union(int n) {
            parent = new int[n];
            locaTion = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                locaTion[i] = 0;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int X = find(x);
            int Y = find(y);

            if (X != Y) {
                if (locaTion[X] > locaTion[Y]) {
                    parent[Y] = X;
                } else if (locaTion[X] < locaTion[Y]) {
                    parent[X] = Y;
                } else {
                    parent[Y] = X;
                    locaTion[X]++;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        File file = new File("src\\vertices.txt");
        Scanner inputFile = new Scanner(file);
        ArrayList<Integer> vertices = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        while (inputFile.hasNextInt()) {
            vertices.add(inputFile.nextInt());
        }
        inputFile.close();
        Scanner inputUser = new Scanner(System.in);
        System.out.println("\nEnter The Destination Node and their Cost for each Node(source)");
        System.out.print("Saperated By Space and Must Be end With 0\n\n");
        Map<String, Integer> edgeMap = new HashMap<>();
        for (int i = 0; i < vertices.size(); i++) {
            int vertex = vertices.get(i);
            System.out.println("Enter destination and weight For vertex " + vertex + ":");
            while (true) {
                int desNode = inputUser.nextInt();
                if (desNode == 0) {
                    break;
                }
                int weight = inputUser.nextInt();
                String edgeKey = Math.min(vertex, desNode) + "-" + Math.max(vertex, desNode);
                if (!edgeMap.containsKey(edgeKey) || edgeMap.get(edgeKey) > weight) {
                    edgeMap.put(edgeKey, weight);
                    edges.add(new Edge(vertex, desNode, weight));
                }
            }
        }
        inputUser.close();
        Collections.sort(edges);
        Union Uni = new Union(vertices.size());
        ArrayList<Edge> mst = new ArrayList<>();
        for (Edge edge : edges) {
            int srcInDx = vertices.indexOf(edge.src);
            int destInDx = vertices.indexOf(edge.dest);
            if (Uni.find(srcInDx) != Uni.find(destInDx)) {
                mst.add(edge);
                Uni.union(srcInDx, destInDx);
            }
        }
        System.out.println("\nMinimum Spanning Tree");
        System.out.println("Source --> Destination == Cost\n");
        for (Edge edge : mst) {
            System.out.println(edge.src + "   -->   " + edge.dest + "   ==   " + edge.weight);
        }
    }
}