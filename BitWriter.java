import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BitWriter {
    private ByteArrayOutputStream output;
    private int currentByte;
    private int numBitsFilled;

    public BitWriter() {
        output = new ByteArrayOutputStream();
        currentByte = 0;
        numBitsFilled = 0;
    }

    public void writeBits(String bits) throws IOException {
        for (char bit : bits.toCharArray()) {
            currentByte = (currentByte << 1) | (bit - '0');
            numBitsFilled++;
            if (numBitsFilled == 8) {
                flush();
            }
        }
    }

    public void flush() throws IOException {
        if (numBitsFilled > 0) {
            currentByte <<= (8 - numBitsFilled); // Pad remaining bits
            output.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }
    }

    public byte[] toByteArray() throws IOException {
        flush();
        return output.toByteArray();
    }
}
