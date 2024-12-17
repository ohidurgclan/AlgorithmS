package com.knapsack01;
/**
 * @author Md Ohidur Rahman
 * @StudentID 221002406
 */
import java.util.Scanner;
public class Knapsack01 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Input the number of items: ");
        int n = input.nextInt();
        int[] weights = new int[n];
        int[] values = new int[n];
        System.out.println("Input the values of the items:");
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }
        System.out.println("Input the weight of the items:");
        for (int i = 0; i < n; i++) {
            weights[i] = input.nextInt();
        }
        System.out.print("Input the capacity of the knapsack Container: ");
        int capacity = input.nextInt();
        int[] collectedItems = knapsack(weights, values, capacity);
        int maximumBenefit = maximumBenefit(collectedItems, values);
        System.out.println("Maximum Benefit: " + maximumBenefit);
        System.out.print("Selected items:  ");
        for (int i = 1; i < collectedItems.length; i++) {
            if (collectedItems[i] == 1) {
                System.out.print(i + " , ");
            }
        }
        input.close();
    }
    public static int[] knapsack(int[] weights, int[] values, int capacity) {
        int n = weights.length;
        int[][] newArr = new int[n + 1][capacity + 1];
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    newArr[i][w] = 
                            Math.max(values[i - 1] + newArr[i - 1]
                                    [w - weights[i - 1]], newArr[i - 1][w]);
                } else {
                    newArr[i][w] = newArr[i - 1][w];
                }
            }
        }
        // Backtracing Loop to find selected items
        int[] checkItems = new int[n + 1];
        int i = n, w = capacity;
        while (i > 0 && w > 0) {
            if (newArr[i][w] != newArr[i - 1][w]) {
                checkItems[i] = 1;
                w -= weights[i - 1];
            }
            i--;
        }
        return checkItems;
    }
    public static int maximumBenefit(int[] items, int[] values) {
        int totalBenefit = 0;
        for (int i = 1; i < items.length; i++) {
            if (items[i] == 1) {
                totalBenefit += values[i - 1];
            }
        }
        return totalBenefit;
    }
}

