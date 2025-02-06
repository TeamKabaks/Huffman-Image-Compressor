import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;

public class HuffmanImageCompressor {

    public void compressImage(String inputPath, String outputCompressed, String treeFile) throws IOException {
        
        BufferedImage image = ImageIO.read(new File(inputPath));
        if (image == null) {
            throw new IOException("Unsupported image format or invalid file.");
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = image.getRGB(0, 0, width, height, null, 0, width);

        // Delta encoding for each color channel (R, G, B)
        int[] deltaPixels = new int[pixels.length * 3];
        int prevR = 0, prevG = 0, prevB = 0;
        for (int i = 0; i < pixels.length; i++) {
            int r = (pixels[i] >> 16) & 0xFF;
            int g = (pixels[i] >> 8) & 0xFF;
            int b = pixels[i] & 0xFF;
            deltaPixels[i * 3] = r - prevR;
            deltaPixels[i * 3 + 1] = g - prevG;
            deltaPixels[i * 3 + 2] = b - prevB;
            prevR = r;
            prevG = g;
            prevB = b;
        }

        // Compute frequency of each delta value
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int value : deltaPixels) {
            freqMap.put(value, freqMap.getOrDefault(value, 0) + 1);
        }

        HuffmanNode root = buildHuffmanTree(freqMap);

        try {
            exportHuffmanTree(root, treeFile);
            System.out.println("Huffman tree exported to " + treeFile);
        } catch (IOException e) {
            System.err.println("Error exporting Huffman tree: " + e.getMessage());
        }

        // Generate Huffman Codes
        Map<Integer, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);

        // Encode image using Huffman Codes
        StringBuilder encodedData = new StringBuilder();
        for (int value : deltaPixels) {
            encodedData.append(huffmanCodes.get(value));
        }

        // Convert to bit array
        BitSet bitSet = new BitSet(encodedData.length());
        for (int i = 0; i < encodedData.length(); i++) {
            if (encodedData.charAt(i) == '1') {
                bitSet.set(i);
            }
        }

        // Save compressed data
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(outputCompressed))) {
            oos.writeInt(width);
            oos.writeInt(height);
            oos.writeInt(encodedData.length());
            oos.writeObject(bitSet);
        }

        // Calculate and display compression ratio
        long originalSize = new File(inputPath).length();
        long compressedSize = new File(outputCompressed).length() + new File(treeFile).length();
        double compressionRatio = (double) compressedSize / originalSize;
        System.out.printf("Compression ratio: %.2f%%\n", (1 - compressionRatio) * 100);
    }
    public void decompressImage(String compressedFile, String treeFile, String outputPath) throws IOException, ClassNotFoundException {
        // Load Huffman Tree
        HuffmanNode root = importHuffmanTree(treeFile);

        // Generate Huffman Codes from the tree
        Map<Integer, String> huffmanCodes = new HashMap<>();
        generateCodes(root, "", huffmanCodes);

        Map<String, Integer> reverseCodes = new HashMap<>();
        for (Map.Entry<Integer, String> entry : huffmanCodes.entrySet()) {
            reverseCodes.put(entry.getValue(), entry.getKey());
        }

        // Load compressed data
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(compressedFile))) {
            int width = ois.readInt();
            int height = ois.readInt();
            int bitLength = ois.readInt();
            BitSet bitSet = (BitSet) ois.readObject();

            // Decode bit stream
            StringBuilder bitString = new StringBuilder();
            for (int i = 0; i < bitLength; i++) {
                bitString.append(bitSet.get(i) ? '1' : '0');
            }

            // Convert bit sequence to pixel values
            List<Integer> deltaPixels = new ArrayList<>();
            String temp = "";
            for (char bit : bitString.toString().toCharArray()) {
                temp += bit;
                if (reverseCodes.containsKey(temp)) {
                    deltaPixels.add(reverseCodes.get(temp));
                    temp = "";
                }
            }

            // Reverse delta encoding
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int prevR = 0, prevG = 0, prevB = 0;
            int index = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = (prevR + deltaPixels.get(index++)) & 0xFF;
                    int g = (prevG + deltaPixels.get(index++)) & 0xFF;
                    int b = (prevB + deltaPixels.get(index++)) & 0xFF;
                    int pixel = (r << 16) | (g << 8) | b;
                    image.setRGB(x, y, pixel);
                    prevR = r;
                    prevG = g;
                    prevB = b;
                }
            }

            // Save decompressed image
            ImageIO.write(image, "png", new File(outputPath));
            System.out.println("Decompressed image saved to " + outputPath);
        }
    }
    

    private static HuffmanNode buildHuffmanTree(Map<Integer, Integer> freqMap) {
        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            pq.offer(new HuffmanNode(entry.getKey(), entry.getValue()));
        }
        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(-1, left.frequency + right.frequency);
            parent.left = left;
            parent.right = right;
            pq.offer(parent);
        }
        return pq.poll(); // Root of the Huffman tree
    }

    private static void exportHuffmanTree(HuffmanNode root, String outputFile) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            exportTree(root, writer);
        }
    }

    private static void exportTree(HuffmanNode node, BufferedWriter writer) throws IOException {
        if (node == null) {
            writer.write("null\n");
            return;
        }
        writer.write(node.value + " " + node.frequency + "\n");
        exportTree(node.left, writer);
        exportTree(node.right, writer);
    }

    private static HuffmanNode importHuffmanTree(String treeFile) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(treeFile))) {
            return importTree(reader);
        }
    }

    private static HuffmanNode importTree(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        if (line == null || line.equals("null")) {
            return null;
        }
        String[] parts = line.split(" ");
        int value = Integer.parseInt(parts[0]);
        double frequency = Double.parseDouble(parts[1]);
        HuffmanNode node = new HuffmanNode(value, frequency);
        node.left = importTree(reader);
        node.right = importTree(reader);
        return node;
    }

    private static void generateCodes(HuffmanNode node, String code, Map<Integer, String> huffmanCodes) {
        if (node.left == null && node.right == null) {
            huffmanCodes.put(node.value, code);
            return;
        }
        generateCodes(node.left, code + "0", huffmanCodes);
        generateCodes(node.right, code + "1", huffmanCodes);
    }
}

