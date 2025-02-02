# Huffman Image Compressor

## Overview

This project is an image compression tool implemented in Java that uses Huffman Coding to compress and decompress PNG images. Huffman Coding is a lossless data compression algorithm that assigns variable-length codes to input characters, with shorter codes assigned to more frequent characters. This project provides a simple interface to open PNG images, create or update Huffman trees, compress images, and decompress Huffman-coded images.

## Features

1. **Open PNG File**: Load a locally stored PNG image and render it.
2. **Train Huffman Tree**: Extract pixel information from the loaded image to create a new Huffman tree or update an existing one. The Huffman tree is saved in a `.HUFF` file.
3. **Compress Image**: Convert the loaded image into a Huffman-coded format, saving it as a new file with a custom extension (e.g., `.BEA`).
4. **Open Huffman Coded Image**: Load and render a Huffman-coded image file.

## How It Works

### Huffman Coding

Huffman Coding is a method of compressing data by creating a binary tree of nodes. The steps are as follows:

1. **Frequency Calculation**: Calculate the frequency of each pixel value in the image.
2. **Tree Construction**: Build a Huffman tree where each leaf node represents a pixel value and its frequency. The tree is constructed by repeatedly combining the two nodes with the lowest frequencies into a new node until one node remains.
3. **Code Assignment**: Traverse the Huffman tree to assign binary codes to each pixel value. More frequent pixels get shorter codes.
4. **Compression**: Replace each pixel in the image with its corresponding Huffman code.
5. **Decompression**: Use the Huffman tree to convert the binary codes back to the original pixel values.

### Implementation Details

1. **Opening PNG Files**: The program uses Java's image processing libraries to load and display PNG images.
2. **Training the Huffman Tree**: The program reads the pixel data from the image, calculates the frequency of each pixel, and constructs the Huffman tree. The tree can be saved to a `.HUFF` file for later use.
3. **Compressing Images**: The program traverses the Huffman tree to generate codes for each pixel and writes the compressed data to a new file.
4. **Decompressing Images**: The program reads the Huffman-coded file and uses the Huffman tree to reconstruct the original image.

## Repository Structure

- `src/`: Contains the Java source code.
  - `Main.java`: The main class that handles user input and program flow.
  - `HuffmanTree.java`: Implements the Huffman tree construction and traversal.
  - `ImageCompressor.java`: Handles image loading, compression, and decompression.
- `images/`: Sample PNG images for testing.
- `output/`: Directory where compressed and decompressed images are saved.
- `README.md`: This file.

## Usage

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/yourusername/image-compressor-huffman.git
   cd image-compressor-huffman
   ```

2. **Compile the Program**:
   ```bash
   javac src/*.java -d out/
   ```

3. **Run the Program**:
   ```bash
   java -cp out/ Main
   ```

4. **Follow the On-Screen Instructions**:
   - Open a PNG image.
   - Train a new Huffman tree or use an existing one.
   - Compress the image.
   - Open and view the compressed image.

## Customization

- **File Extensions**: You can change the file extensions used for saving Huffman trees and compressed images by modifying the relevant constants in the code.
- **Image Handling**: To support additional image formats, you can extend the image loading and saving functionality using Java's image I/O libraries.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request with your changes.
1. Fork the repository
2. Create your feature branch ('''git checkout -b feature/AmazingFeature''')
3. Commit your changes ('''git commit -m 'Add some AmazingFeature'''')
4. Push to the branch ('''git push origin feature/AmazingFeature''')
5. Open a Pull Request 

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Acknowledgments

- This project was created as a part of CMSC 123 at University of the Philippines Tacloban College.
- Based on Huffman coding algorithm for efficient data compression.
- Huffman, D. A. (1952). "A Method for the Construction of Minimum-Redundancy Codes". Proceedings of the IRE.

---
