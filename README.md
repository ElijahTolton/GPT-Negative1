# TextGenerator Project - CS 2420 (Intro to Algorithms and Data Structures)  
**University of Utah**  
**Final Comprehensive Project**

## Overview
This program is a text generator designed to analyze a given input text file and output the most probable `k` words that follow a specified seed word. The program adapts based on the parameters provided, efficiently predicting word sequences using advanced data structures and algorithms learned in CS 2420.

## Features
1. **Efficient Word Prediction:**  
   The program can generate a sequence of words based on an input text and a seed word. It uses probability to determine the next most likely words and offers three modes depending on the provided arguments.
   
2. **Data Structures Used:**  
   - **Nested HashMap:** Tracks word occurrences and their frequencies relative to a given word.
   - **BinaryMaxHeap:** Maintains the most probable words in a max-priority queue, allowing for fast retrieval of the most likely next word.
   
3. **Modes of Operation:**  
   Depending on the fourth argument:
   - **No Fourth Argument:** Outputs the `k` most probable words after the seed using a BinaryMaxHeap.
   - **"one" as Fourth Argument:** Outputs the most probable single word after each word, using a nested HashMap and BinaryMaxHeap for efficient lookup.
   - **"all" as Fourth Argument:** Randomly selects the next word based on weighted probabilities using a nested HashMap without BinaryMaxHeap for more random, but still probable, results.

## Program Components
- **TextGenerator Class:**  
  Handles input arguments and coordinates the generation of text based on which method is used. It interacts with the TextGeneratorFunctor to determine how to process and generate words based on different cases.
  
- **TextGeneratorFunctor Class:**  
  Performs the bulk of the text analysis and word prediction logic. It reads the file, processes the words, and computes the output based on the parameters. It uses:
  - **no4thParameter():** Finds the `k` most probable words after the seed word.
  - **fourthArgumentOne():** Finds the single most probable word after each word in the sequence.
  - **fourthArgumentAll():** Randomly selects words based on weighted probabilities of occurrences.

## Runtime Efficiency
- **No Fourth Argument:**  
  Expected time complexity: O(file size + N + k lg N), where `N` is the number of words following the seed. The runtime increases with `k`, as more words need to be extracted from the heap.
  
- **Fourth Argument "one":**  
  Expected time complexity: O(file size + k(N + N)), with `N` representing the number of words in the inner HashMap for each word. This method is efficient in retrieving the most probable word.

- **Fourth Argument "all":**  
  Expected time complexity: O(file size + k(N + N)), similar to the "one" case but includes an additional step for weighted random word selection, which adds some complexity.

## Design Considerations
The program efficiently handles large input texts by leveraging the fast access properties of HashMaps and the sorting capabilities of BinaryMaxHeap. Potential improvements could involve further optimizing memory usage or refining the random selection process to reduce overhead.

## Experimentation and Results
We tested the program with large text files (e.g., *Moby Dick*) and measured the runtime for each mode with varying values of `k` (ranging from 100 to 2,000 words). The results aligned with the expected time complexities, with noticeable runtime increases as `k` grew.
