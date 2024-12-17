import java.util.*;

public class allSequences {
    public static int[][] lenOfLCS(String X, String Y) {
        int m = X.length(), n = Y.length();
        int[][] lcsTable = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    lcsTable[i][j] = lcsTable[i - 1][j - 1] + 1;
                } else {
                    lcsTable[i][j] = Math.max(lcsTable[i - 1][j], lcsTable[i][j - 1]);
                }
            }
        }
        return lcsTable;
    }
    public static Set<String> findComLcs(int[][] lcsMatrix, String X, String Y, int i, int j) {
        if (i == 0 || j == 0) {
            Set<String> base = new HashSet<>();
            base.add("");
            return base;
        }
        if (X.charAt(i - 1) == Y.charAt(j - 1)) {
            Set<String> smallerLCS = findComLcs(lcsMatrix, X, Y, i - 1, j - 1);
            Set<String> result = new HashSet<>();
            for (String s : smallerLCS) {
                result.add(s + X.charAt(i - 1));
            }
            return result;
        } else {
            Set<String> result = new HashSet<>();
            if (lcsMatrix[i - 1][j] >= lcsMatrix[i][j - 1]) {
                result.addAll(findComLcs(lcsMatrix, X, Y, i - 1, j));
            }
            if (lcsMatrix[i][j - 1] >= lcsMatrix[i - 1][j]) {
                result.addAll(findComLcs(lcsMatrix, X, Y, i, j - 1));
            }
            return result;
        }
    }
    public static List<String> allComSeq(String X, String Y) {
        int[][] lcsTable = lenOfLCS(X, Y);
        Set<String> allLCS = findComLcs(lcsTable, X, Y, X.length(), Y.length());
        List<String> sortLcs = new ArrayList<>(allLCS);
        sortLcs.sort((a, b) -> {
            if (a.length() != b.length()) {
                return b.length() - a.length();
            }
            return a.compareTo(b);
        });

        return sortLcs;
    }
    public static void main(String[] args) {
        Scanner inputString = new Scanner(System.in);
        System.out.print("\n First sequence: ");
        String X = inputString.nextLine();
        System.out.print("Second sequence: ");
        String Y = inputString.nextLine();
        List<String> result = allComSeq(X, Y);
        System.out.println("\nAll Subsequences in Descending Order: \n");
        for (String seq : result) {
            System.out.println(seq);
        }
        System.out.println("\n");
        inputString.close();
    }
}
