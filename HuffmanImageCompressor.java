import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import javax.imageio.ImageIO;

public class HuffmanImageCompressor {
    private static class HuffmanNode implements Comparable<HuffmanNode> {
        int value;
        double frequency;
        HuffmanNode left;
        HuffmanNode right;

        public HuffmanNode(int value, double frequency) {
            this.value = value;
            this.frequency = frequency;
        }

        // This compares the nodes which is helpful in Priority Queue to automatically sort nodes
        @Override
        public int compareTo(HuffmanNode other) {
            return Double.compare(this.frequency, other.frequency);
        }
    }
    
    // Method to compress images

    public static void compressImage(String inputPath, String outputPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputPath));
        Map<Integer, Double> rgbFrequency = new HashMap<>();
        HashMap<Integer, Integer> rgbCount = new HashMap<>();
       
        // Getting the pixel count
        int totalPixels = image.getWidth() * image.getHeight();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = image.getRGB(x, y);
                rgbCount.put(rgb, rgbCount.getOrDefault(rgb, 0) + 1);
            }
        }

        // Converting the pixel count to frequencies
        
        for (Map.Entry<Integer, Integer> entry : rgbCount.entrySet()) {
            int rgb = entry.getKey();
            int count = entry.getValue();
            double frequency = (double) count / totalPixels;
            rgbFrequency.put(rgb, frequency);
        }

        // Utilization of Priority Queue in Building the Huffman Tree

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (Map.Entry<Integer, Double> entry : rgbFrequency.entrySet()) {
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
        

        HuffmanNode root = pq.poll();
        Map<Integer, String> pixelCodes = new HashMap<>();
        encodePixels(root, "", pixelCodes);

        // 
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(outputPath))) {
            dos.writeInt(image.getWidth());
            dos.writeInt(image.getHeight());
            dos.writeInt(pixelCodes.size());
            for (Map.Entry<Integer, String> entry : pixelCodes.entrySet()) {
                dos.writeInt(entry.getKey());
                dos.writeUTF(entry.getValue());
            }

            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int pixel = image.getRGB(x, y);
                    dos.writeUTF(pixelCodes.get(pixel));
                }
            }
        }

        System.out.println("Compressed image saved to: " + outputPath);
    }

    public static void decompressImage(String compressedPath, String outputPath) throws IOException {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(compressedPath))) {
            int width = dis.readInt();
            int height = dis.readInt();
            int numCodes = dis.readInt();
            Map<String, Integer> codeToPixel = new HashMap<>();
            for (int i = 0; i < numCodes; i++) {
                int pixel = dis.readInt();
                String code = dis.readUTF();
                codeToPixel.put(code, pixel);
            }

            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    String code = dis.readUTF();
                    int pixel = codeToPixel.get(code);
                    image.setRGB(x, y, pixel);
                }
            }

            ImageIO.write(image, "png", new File(outputPath));
            System.out.println("Decompressed image saved to: " + outputPath);
        }
    }

    private static void encodePixels(HuffmanNode node, String code, Map<Integer, String> pixelCodes) {
        if (node.left == null && node.right == null) {
            pixelCodes.put(node.value, code);
            return;
        }

        encodePixels(node.left, code + "0", pixelCodes);
        encodePixels(node.right, code + "1", pixelCodes);
    }

    public static void main(String[] args) throws IOException {
        String inputPath = "assets/inputs/lena.png";
        String compressedPath = "assets/compressed_files/kabaks.kabak";
        String outputPath = "assets/outputs/kabaks.png"; 
        compressImage(inputPath, compressedPath);   
        decompressImage(compressedPath, outputPath);    
    }
}