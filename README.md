# Quadtree Image Compression (ICS 202)

This project implements quadtree-based compression and decompression for 256×256 grayscale images as part of the ICS 202 (Data Structures & Algorithms) course.

## Files
- `ImgQuadTree.java`  
  Builds a quadtree from a preorder traversal file and reconstructs the original image using recursive traversal.

- `ImgQuadTreeFileCreator.java`  
  Converts an uncompressed image file into a compressed quadtree representation using recursive quadrant partitioning.

- `ImgQuadTreeDisplayer.java`  
  Provided utility used to display the reconstructed image from the quadtree.

- `Project_report.pdf`  
  Project report describing the design, algorithms, testing, and challenges.

## Key Concepts
- Quadtrees
- Recursion
- Tree traversal (preorder)
- Divide-and-conquer
- File I/O
