package comprehensive;

import java.io.FileNotFoundException;

/**
 * GPT-Negative1. Takes in arguments from the commandLine and returns k probable words
 * based on a seed word. If the 4th Argument "all" is used it returns a randomly selected weighted k words.
 * If the 4th argument "one" is used it returns the most probable k words after the previous output word.
 *
 * @author Elijah Tolton and Canon Curtis
 * @version 4/23/2024
 */
public class TextGenerator {
    /**
     * Text Generator interface with command line. Our model predicts that word(s) that come after seed.
     * Assume "seed" is contained in input file.
     *
     * @param args -
     *             args[0]  - First Argument File path and name of input file.
     *             args[1] - Second Argument String the "seed" word.
     *             args[2] - Third Argument int k number of words to output. NonNegative.
     *             args[3] - Optional Fourth Argument String "all" or "one" if "one" returns most probable word.
     *                       If "all" returns random weighted word.
     * @throws FileNotFoundException - File path is not found
     */
    public static void main(String[] args) throws FileNotFoundException {
        //input arguments into constructor
        TextGeneratorFunctor func;
        if(args.length == 3)
             func = new TextGeneratorFunctor(args[0], args[1],args[2]);
        else
            func = new TextGeneratorFunctor(args[0], args[1], args[2], args[3]);

        //If no fourth argument exists
        if(func.getAllOrOne() == null){
            int numOutput = Integer.parseInt(args[2]);
            fourthArgNull(numOutput, func);
            return;
        }

        //If 4th arg is one
        if(!func.getAllOrOne())
            fourthArgOne(func);

        //If 4th arg is all
        if(func.getAllOrOne())
            fourthArgAll(func);

    }

    /**
     * If there is no 4th argument. Prints the k most probable words after "seed" word. If k is greater than the number
     * of words after "seed" prints all words after "seed".
     *
     * @param numOutput - number of words to be printed
     * @param func - TextGeneratorFunctor object with given parameters
     */
    private static void fourthArgNull(int numOutput, TextGeneratorFunctor func){
        BinaryMaxHeap<KeyValuePair> heap = func.no4thParameter(); //Make heap of probable words after seed
        if(numOutput > heap.size()) // If k is greater than heap size, k = heap size
            numOutput = heap.size();

        for(int i = 0; i < numOutput; i++)
            System.out.print(heap.extractMax().getWord() + " "); //Print out probable word in order of most probable
    }

    /**
     * Reads file and prints out the most likely string starting from seed word
     *
     * @param func - TextGeneratorFunctor constructed with given parameters
     */
    private static void fourthArgOne(TextGeneratorFunctor func) {
        //find words
        var list = func.fourthArgumentOne();

        //print words
        for (String word : list)
            System.out.print(word + " ");
    }

    /**
     * Reads file and prints out a randomly selected slew of strings from weighted probabilities
     *
     * @param func - TextGeneratorFunctor constructed with given parameters
     */
    private static void fourthArgAll(TextGeneratorFunctor func) {
        //find words
        var list = func.fourthArgumentAll();

        //print words
        for (String word : list)
            System.out.print(word + " ");
    }
}
