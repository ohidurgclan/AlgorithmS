package com.dfs;
/**
 * @author Md Ohidur Rahman
 * @ID 221002406
 */
import java.util.*;

class Tree {
    int val;
    List<Tree> children;

    Tree(int val) {
        this.val = val;
        children = new ArrayList<>();
    }
}

public class DFSLeafNodeCount {
    public static void main(String[] args) {
        String br = System.lineSeparator();
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the number of nodes in the tree: ");
        int n = input.nextInt();
        System.out.print(br);
        // Constracting the tree
        Tree root = new Tree(1);
        for (int i = 2; i <= n; i++) {
            System.out.print("Enter the parent node for node " + i + ": ");
            int parentVal = input.nextInt();
            Tree parent = findNode(root, parentVal);
            if (parent != null) {
                parent.children.add(new Tree(i));
            } else {
                System.out.println("Parent node not found. Please enter a valid parent node.");
                i--; // Decrement i to retry input for this node
            }
        }
        // Counting leaf nodes
        int leafCount = countLeafNodes(root);
        System.out.println(br + "Number of leaf nodes in the tree: " + leafCount);
    }
    
    // Find Nodes in User Inputed Tree
    private static Tree findNode(Tree root, int val) {
        if (root == null || root.val == val) {
            return root;
        }
        for (Tree child : root.children) {
            Tree found = findNode(child, val);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
    // Count leaf nodes using DFS
    private static int countLeafNodes(Tree root) {
        if (root == null) {
            return 0;
        }
        if (root.children.isEmpty()) {
            return 1; // Leaf node
        }
        int count = 0;
        for (Tree child : root.children) {
            count += countLeafNodes(child);
        }
        return count;
    }
}