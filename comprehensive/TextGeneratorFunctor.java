package comprehensive;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.LinkedList;
import java.util.HashMap;

/**
 * Class that contains most of the logic of TextGenerator. Reads and lowercase the file.
 * Decides what output is needed, selects appropriate data structure with efficient runtime and returns output.
 *
 * @author - Elijah Tolton and Canon Curtis
 * @version - 4/23/2024
 */
public class TextGeneratorFunctor {
    private File inputFile;
    private Scanner fileReader;
    private String seed;
    private int k;
    private Boolean allOrOne; // null doesn't exist, true if "all" false if "one"

    private BinaryMaxHeap<KeyValuePair> wordHeap = new BinaryMaxHeap<>();

    /**
     * Functor object constructor without the fourth parameter. The object created will read the file and only
     * look at the words that come after the initial "seed" word. allOrOne is null.
     *
     * @param filePath - filepath of file
     * @param seed - seed word. We calculate probable words after seed
     * @param k - k number of probable words want to be returned
     * @throws FileNotFoundException - if File path is not found
     */
    public TextGeneratorFunctor(String filePath, String seed, String k) throws FileNotFoundException{
        try {
            inputFile = new File(filePath);
            fileReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e + "");
        }
        this.seed = seed;
        this.k = Integer.parseInt(k); //k should always be an int
        allOrOne = null; //if no 4th parameter is given
    }

    /**
     * Constructor with 4th parameter option. If allOrOne = "all" instance var is true. If allOrOne = "one" instance var is false.
     *
     * @param filepath - file path of file we're reading
     * @param seed - what word in the file we will initially look at
     * @param k - k number of words that will be returned
     * @param allOrOne - true if param is "all" false if param is "one"
     * @throws FileNotFoundException - If filepath cannot be found
     */
    public TextGeneratorFunctor(String filepath, String seed, String k, String allOrOne) throws FileNotFoundException {
        this(filepath, seed, k);
        this.allOrOne = allOrOne.equals("all");
    }

    /**
     * Reads text file and adds all words proceeding seed word to BinaryMaxHeap in O(N) time
     *
     * @return BinaryMaxHeap containing all the words that commonly proceed after the seed word.
     */
    public BinaryMaxHeap<KeyValuePair> no4thParameter(){
        HashMap<String, KeyValuePair> map = new HashMap<>();
        String temp = nextWord();

        //loop through all words
        while(fileReader.hasNext()){ //O(N) loop
            //checks file for seed word
            if(temp.equals(seed)){
                //if found adds word after seed to hashmap
                temp = nextWord();
                if(map.containsKey(temp)) //O(1) average case
                    map.get(temp).incrementOccurrence(); //O(1) average
                else
                    map.put(temp, new KeyValuePair(temp, 1)); //O(1) average
            } else {
                temp = nextWord();
            }
        } //total O(N)

        KeyValuePair[] arr = map.values().toArray(new KeyValuePair[0]); //O(N)
        wordHeap.buildHeap(arr); //O(N)

        return wordHeap;
    }

    /**
     * Reads text file and constructs a nested HashMap with the previousWord mapped to a
     * hashMap of the all words that come after it.
     *
     * @return - double hashmap containing all words and possible following words
     */
    private HashMap<String, HashMap<String, KeyValuePair>> readFileToHash() {
        //Construct HashMaps
        HashMap<String, HashMap<String, KeyValuePair>> map = new HashMap<>();
        String curr = nextWord();
        String next;

        //read files
        while(fileReader.hasNext()){ //O(N)
            //if map doesn't contain word, add it
            if(!map.containsKey(curr))
                map.put(curr, new HashMap<>());

            next = nextWord();

            //Check if next is in associated hashMap
            if(!map.get(curr).isEmpty() && map.get(curr).containsKey(next))
                map.get(curr).get(next).incrementOccurrence();
            else
                map.get(curr).put(next, new KeyValuePair(next, 1));

            //move to next word
            curr = next;
        }

        //double hash table
        return map;
    }

    /**
     * Turns double HashTable and utilizes a binary max heap to find the most likely
     * k words from previous seed word
     *
     * @return a linked list of the most likely string of words originating from a seed
     */
    public LinkedList<String> fourthArgumentOne() {
        //get double hashmap
        var hashMap = readFileToHash();

        //Keep track of MostProbableWord
        HashMap<String, BinaryMaxHeap<KeyValuePair>> heapMap = new HashMap<>();

        //Initialize previousOutput to seed value
        String previousOut = seed;

        //Output
        LinkedList<String> out = new LinkedList<>();

        //Add k most probable words after previous Output to out List
        for (int i = 0; i < k; i++) {
            //If we don't already have a BinaryHeap for previousOut word
            if(!heapMap.containsKey(previousOut)) { //O(1)
                //If there are no words after the previously output word.
                if(hashMap.get(previousOut) == null || hashMap.get(previousOut).isEmpty()) {
                    out.addLast(previousOut); //O(1)
                    previousOut = seed;
                //Create new BinaryHeap from HashMap and peek max
                } else {
                    //words after previous output word
                    KeyValuePair[] arr = hashMap.get(previousOut).values().toArray(new KeyValuePair[0]); //O(N)
                    BinaryMaxHeap<KeyValuePair> probableWords = new BinaryMaxHeap<>(); //O(1)
                    probableWords.buildHeap(arr); // O(N)

                    //Put previous output word in a hashMap to heap of probable words
                    heapMap.put(previousOut, probableWords);
                    out.addLast(previousOut); //O(1)
                    previousOut = heapMap.get(previousOut).peek().getWord(); //O(1)
                }
            } else {
                //if word had already been added, add word or seed to output
                if (hashMap.get(previousOut) == null || hashMap.get(previousOut).isEmpty()) {
                    out.addLast(previousOut); //O(1)
                    previousOut = seed;
                } else {
                    out.addLast(previousOut); //O(1)
                    previousOut = heapMap.get(previousOut).peek().getWord(); //O(1)
                }
            }
        } //total O(k * N)

        return out;
    }

    /**
     * If 4th argument is "all" it returns a weighted probable word of the previously output word.
     *
     * @return - list of k probable words after previous output word based on probably
     */
    public LinkedList<String> fourthArgumentAll(){
        //Get File data
        var hashMap = readFileToHash();
        String curr = seed;
        LinkedList<String> out = new LinkedList<>();

        //Keep track if already got KeyValuePair array of a word
        HashMap<String, KeyValuePair[]> arrayMap = new HashMap<>();

        //Loop through and get k probable words to output
        for(int i = 0; i < k; i++) {
            //already have a KeyValuePair array
            if(arrayMap.containsKey(curr)){
                //If it is null set back to seed
                if(hashMap.get(curr) == null || hashMap.get(curr).isEmpty()) {
                    out.addLast(curr);
                    curr = seed;
                } else {
                    //Take random word based on probability from array
                    out.addLast(curr);
                    curr = nextRandomWord(arrayMap.get(curr));
                }
            } else {
                if(hashMap.get(curr) == null || hashMap.get(curr).isEmpty()) {
                    out.addLast(curr);
                    curr = seed;
                } else {
                    //add array to hashMap to not calculate again.
                    var temp = hashMap.get(curr).values().toArray(new KeyValuePair[0]);
                    arrayMap.put(curr, temp);
                    out.addLast(curr);
                    curr = nextRandomWord(temp);
                }
            }
        }

        return out;
    }


    /**
     * Returns the next word that comes after previous output randomly based on probabilities.
     *
     * @param arr - list of words and probabilities that come after previous output
     * @return - KeyValuePair of next word to be output
     */
    private String nextRandomWord(KeyValuePair[] arr){
        Random rnd = new Random();
        int totalOccurences = 0;
        int lowerBound = 0;
        int upperBound;
        //Total number of occurrences
        for(int i = 0; i < arr.length; i++)
            totalOccurences += arr[i].getOccurrence();

        int value = rnd.nextInt(totalOccurences);

        //Put values into an array
        for(int i = 0; i < arr.length; i++){
            upperBound = lowerBound + arr[i].getOccurrence();
            //If the value is within interval return value
            if(lowerBound <= value && value < upperBound){
                return arr[i].getWord();
            }
            lowerBound = upperBound;
        }
        //can't find
        return null;
    }

    /**
     * Returns the next word in file without punctuation and lowercase
     * @return - next word in file
     */
    public String nextWord(){
        String nextWord;

        //Do while nextWord isn't an empty String
        do {
            //split words at anything not letter or number
            var splitWords = fileReader.next().toLowerCase().split("\\W");

            //Replace any punctuation in first word in array.
            if(splitWords.length >= 1)
                nextWord = splitWords[0].replaceAll("\\W", "");
            else
                nextWord = "";
        } while (nextWord.isEmpty());

        return nextWord;
    }

    /**
     * Getter for allOrOne value
     * @return - Boolean
     */
    public Boolean getAllOrOne(){
        return allOrOne;
    }
}
