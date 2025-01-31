import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;

// Node class for Huffman Tree
class Node implements Comparable<Node> {
    int frequency;
    char character;
    Node left, right;

    Node(char character, int frequency) {
        this.character = character;
        this.frequency = frequency;
        left = right = null;
    }

    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }
}

// Huffman Coding Class
public class HuffmanCoding {
    private Map<Character, Integer> frequencyMap = new HashMap<>();
    private Map<Character, String> huffmanCodes = new HashMap<>();

    // Build the Huffman Treea
    public Node buildTree(Map<Character, Integer> frequencyMap) {
        PriorityQueue<Node> queue = new PriorityQueue<>();

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            queue.add(new Node(entry.getKey(), entry.getValue()));
        }

        while (queue.size() > 1) {
            Node left = queue.poll();
            Node right = queue.poll();
            Node internalNode = new Node('\0', left.frequency + right.frequency);
            internalNode.left = left;
            internalNode.right = right;
            queue.add(internalNode);
        }

        return queue.poll(); // Root of the Huffman Tree
    }

    // Generate Huffman Codes
    public void generateCodes(Node root, String code) {
        if (root == null) return;

        // If this is a leaf node, save its character and code
        if (root.left == null && root.right == null) {
            huffmanCodes.put(root.character, code);
            return;
        }

        generateCodes(root.left, code + '0');
        generateCodes(root.right, code + '1');
    }

    // Encode the input data
    public String encode(String input) {
        for (char character : input.toCharArray()) {
            frequencyMap.put(character, frequencyMap.getOrDefault(character, 0) + 1);
        }

        Node root = buildTree(frequencyMap);
        generateCodes(root, "");

        StringBuilder encodedString = new StringBuilder();
        for (char character : input.toCharArray()) {
            encodedString.append(huffmanCodes.get(character));
        }
        return encodedString.toString();
    }

    // Decode the encoded data
    public String decode(Node root, String encodedString) {
        StringBuilder decodedString = new StringBuilder();
        Node currentNode = root;

        for (char bit : encodedString.toCharArray()) {
            currentNode = (bit == '0') ? currentNode.left : currentNode.right;

            // If leaf node, append the character to decodedString
            if (currentNode.left == null && currentNode.right == null) {
                decodedString.append(currentNode.character);
                currentNode = root; // Go back to root for the next character
            }
        }
        return decodedString.toString();
    }

    // Main Method for Testing
    public static void main(String[] args) {
        String input = "lena.png"; // Example input
        HuffmanCoding huffmanCoding = new HuffmanCoding();

        // Encoding
        String encoded = huffmanCoding.encode(input);
        System.out.println("Encoded: " + encoded);

        // Decoding
        Node root = huffmanCoding.buildTree(huffmanCoding.frequencyMap);
        String decoded = huffmanCoding.decode(root, encoded);
        System.out.println("Decoded: " + decoded);
    }
}