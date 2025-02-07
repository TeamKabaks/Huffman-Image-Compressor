public class HuffmanNode implements Comparable<HuffmanNode> {
    int value;
    double frequency;
    HuffmanNode left;
    HuffmanNode right;
    
    public HuffmanNode(int value, double frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    // This compares the nodes which is helpful in Priority Queue to automatically sort nodes
    @Override
    public int compareTo(HuffmanNode other) {
        return Double.compare(this.frequency, other.frequency);
    }
}