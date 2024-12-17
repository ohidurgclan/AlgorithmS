package com.bfs;
/**
 * @author Md Ohidur Rahman
 * @ID 221002406
 */
import java.util.*;

class Graph {
    private int V;
    private LinkedList<Integer> adj[];

    Graph(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    boolean cyclicGraph() {
        boolean visited[] = new boolean[V];
        for (int i = 0; i < V; i++) {
            if (!visited[i]) {
                if (againCyclic(i, visited, -1))
                    return true;
            }
        }
        return false;
    }
    boolean againCyclic(int v, boolean visited[], int parent) {
        LinkedList<Integer> queue = new LinkedList<>();
        int current, i;
        visited[v] = true;
        queue.add(v);
        while (!queue.isEmpty()) {
            current = queue.poll();

            Iterator<Integer> it = adj[current].iterator();
            while (it.hasNext()) {
                i = it.next();

                if (!visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                } else if (i != parent) {
                    return true;
                }
            }
        }
        return false;
    }
}
public class FindCycleBfs {
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of vertices: ");
        int V = input.nextInt();
        Graph g = new Graph(V);

        System.out.println("Enter the adjacency list of the graph: ");
        for (int i = 0; i < V; i++) {
            int vertex = input.nextInt();
            while (true) {
                int near = input.nextInt();
                if (near == -1)
                    break;
                g.addEdge(vertex, near);
            }
        }

        if (g.cyclicGraph())
            System.out.println("Graph Has Cycle.");
        else
            System.out.println("Graph Has no cycle.");
    }
}
0 1 3
0 2 14
0 7 5
0 4 2
1 3 16
1 6 4
1 7 17
2 3 7
2 6 19
4 6 8
4 7 12