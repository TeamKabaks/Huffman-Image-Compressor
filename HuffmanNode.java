public class HuffmanNode implements Comparable<HuffmanNode> {
    public int frequency;
    public int pixel;
    public HuffmanNode left, right;

    public HuffmanNode(int frequency, int pixel) {
        this.frequency = frequency;
        this.pixel = pixel;
        left = right = null;
    }

    public int compareTo(HuffmanNode other) {
        return frequency - other.frequency;
    }
}
