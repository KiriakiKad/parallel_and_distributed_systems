# Parallel RGB to Grayscale Image Conversion

This project demonstrates a **parallelized implementation** of converting RGB images to grayscale using Java. The implementation divides the image processing work across multiple threads to improve performance, particularly on multi-core systems.

## Description

The grayscale conversion is based on the luminance-preserving formula:

Gray = 0.299 * Red + 0.587 * Green + 0.114 * Blue


Each thread is responsible for converting a subset of the image rows to grayscale. This is done using fine-grained parallelism based on the number of threads provided.

## File Structure

- `ParallelRGBtoGrayscaleConversion.java`: Main class to perform the parallel image processing.
- `RGBThread.java`: Custom thread class that handles part of the image processing.
