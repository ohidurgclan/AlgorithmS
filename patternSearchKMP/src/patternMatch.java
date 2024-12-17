import java.util.*;

public class patternMatch {
    public static int[] lpsMisMatch(String p) {
        int[] lps = new int[p.length()];
        int j = 0; // Previous lps length
        
        for (int i = 1; i < p.length(); ) {
            if (p.charAt(i) == p.charAt(j)) {
                lps[i] = ++j;
                i++;
            } else {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        return lps;
    }
    public static List<Integer> kmpSearch(String text, String pattern) {
        List<Integer> matchIndices = new ArrayList<>();
        int[] lps = lpsMisMatch(pattern);
        int i = 0; // text
        int j = 0; // pattern 
        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == pattern.length()) {
                matchIndices.add(i - j);
                j = lps[j - 1];
            } else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }
        return matchIndices;
    }
    
    public static void main(String[] args) {
        Scanner inputString = new Scanner(System.in);

        // Read the main string
        System.out.print("\nEnter String: ");
        String text = inputString.nextLine();
        System.out.println("\n");
        while (true) {
            System.out.print("Enter the pattern to search: ");
            String pattern = inputString.nextLine();
            List<Integer> matches = kmpSearch(text, pattern);
            System.out.println("\nPattern found at indices: " + matches);
            System.out.println("\n1. Continue with a new pattern");
            System.out.println("2. Exit the program");
            System.out.print("Enter your choice: ");
            int choose = inputString.nextInt();
            inputString.nextLine();
            if (choose == 2) {
                System.out.println("Exit");
                break;
            }
        }
        inputString.close();
    }
}