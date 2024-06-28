package comprehensive;

/**
 * Class to store word and the probability of word occurring after seed.
 *
 * @author Elijah Tolton and Canon Curtis
 * @version 4/23/2024
 */
public class KeyValuePair implements Comparable<KeyValuePair> {
    //instance variables
    private String word;
    private int occurrence;

    /**
     * Constructor for basic word and frequency pairings
     *
     * @param word - word this object represents
     * @param occurrence - how many times this word appears after the seed
     * @param prob - percent of frequency this word appears after the seed
     */
    public KeyValuePair(String word, int occurrence, double prob){
        this.word = word;
        this.occurrence = occurrence;
    }

    /**
     * Constructor for basic word and occurrence
     *
     * @param word - word that the object represents
     * @param occurrence - how many times this word appears after the seed
     */
    public KeyValuePair(String word, int occurrence){
        this.word = word;
        this.occurrence = occurrence;
    }

    /**
     * Returns number of occurrences of word after the seed
     */
    public int getOccurrence() {
        return occurrence;
    }

    /**
     * Returns the string representing this word
     */
    public String getWord() {
        return word;
    }

    /**
     * Sets the occurrence to given integer
     * @param occurrence - new occurrence
     */
    public void setOccurrence(int occurrence) {
        this.occurrence = occurrence;
    }

    /**
     * Increases the occurrence by 1;
     */
    public void incrementOccurrence() {
        this.occurrence++;
    }

    /**
     * Compares this object to another of same class
     *
     * @param other the object to be compared.
     * @return positive int
     */
    @Override
    public int compareTo(KeyValuePair other) {
        if(this.occurrence - other.occurrence == 0)
            return other.word.compareTo(this.word);

        return this.occurrence - other.occurrence;
    }

    /**
     * Multiplies character value by index value and adds occurrence.
     *
     * @return - int value representation of KeyValuePair
     */
    @Override
    public int hashCode(){
        int sum = 0;
        char[] arrFirst = word.toCharArray();

        for(int i = 0; i < arrFirst.length; i++)
            sum += (int)arrFirst[i] * (5 * i + occurrence);

        return sum;
    }
}
