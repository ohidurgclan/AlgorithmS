import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class changeCombination {
    public static void countWays(int[] coins, int N) {
        List<List<List<Integer>>> listComb = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            listComb.add(new ArrayList<>());
        }
        listComb.get(0).add(new ArrayList<>());
        for (int coin : coins) {
            for (int j = coin; j <= N; j++) {
                for (List<Integer> combination : listComb.get(j - coin)) {
                    List<Integer> newCombination = new ArrayList<>(combination);
                    newCombination.add(coin);
                    listComb.get(j).add(newCombination);
                }
            }
        }
    // Code By Md Ohidur Rahman S_ID: 221002406
        if (listComb.get(N).isEmpty()) {
            System.out.println("No Combo Found.");
        } else {
System.out.println("\nTotal Combinations Can be Make for " + N + " taka: " + listComb.get(N).size());
            System.out.println("Combinations Below:");
            for (List<Integer> combination : listComb.get(N)) {
                System.out.println(combination);
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] coins = {1, 5, 10};
        System.out.println("\nInput The Number: ");
        int N = input.nextInt();
        countWays(coins, N);
        System.out.println("\n");
        input.close();
    }
}