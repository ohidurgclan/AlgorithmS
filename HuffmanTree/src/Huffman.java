import java.util.*;

class HuffmanNode {
    char leTTer;
    int freQ;
    HuffmanNode left, right;
    HuffmanNode(char letter, int freq) {
        this.leTTer = letter;
        this.freQ = freq;
        this.left = null;
        this.right = null;
    }
}
// Code By Md Ohidur Rahman S_ID: 221002406
class HuffmanEncoding {
    private static Map<Character, String> huffmanCodes = new HashMap<>();
    static class NodeComparator implements Comparator<HuffmanNode> {
        public int compare(HuffmanNode leafOne, HuffmanNode leafTwo) {
            return leafOne.freQ - leafTwo.freQ;
        }
    }
    // Store Huffman Tree
    public static HuffmanNode buildTree(Map<Character, Integer> freqMap) {
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(new NodeComparator());
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            priorityQueue.add(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        // Tree making
        while (priorityQueue.size() > 1) {
            HuffmanNode left = priorityQueue.poll();
            HuffmanNode right = priorityQueue.poll();
            HuffmanNode parent = new HuffmanNode('-', left.freQ + right.freQ);
            parent.left = left;
            parent.right = right;
            priorityQueue.add(parent);
        }
        return priorityQueue.poll();
    }
    // Traverse tree for Generate Code
    public static void letterCode(HuffmanNode root, String code) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.leTTer, code);
        }
        letterCode(root.left, code + "0");
        letterCode(root.right, code + "1");
    }
    public static void main(String[] args) {
        Scanner inputString = new Scanner(System.in);
        System.out.println("\nWrite The Sentence: ");
        String input = inputString.nextLine();
        inputString.close();
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : input.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }
        HuffmanNode root = buildTree(freqMap);
        letterCode(root, "");
        System.out.println("\nHuffman Frequency Code:");
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        int totalBits = 0;
        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            char letTer = entry.getKey();
            int code = entry.getValue();
            int codeLength = huffmanCodes.get(letTer).length();
            totalBits += code * codeLength;
        }
        System.out.println("\nLength of Encoded Message (in bits): " + totalBits +"\n");
    }
}