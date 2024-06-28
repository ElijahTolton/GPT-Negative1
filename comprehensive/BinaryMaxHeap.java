package comprehensive;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Implementation of a BinaryMaxHeap. A Binary Search Tree with the property
 * of every element below the parent node is less than the parent's value.
 *
 * @author Elijah Tolton and Canon Curtis
 * @version 4/11/2024
 * @param <T> - object in node
 */

public class BinaryMaxHeap<T> implements PriorityQueue<T> {

    private T[] binMaxHeapData;
    private int capacity;
    private int size;
    private Comparator<? super T> cmp;

    /**
     * Default constructor used to create an empty binary heap,
     * it is assumed that the elements are ordered using their natural ordering
     */
    public BinaryMaxHeap(){
        capacity = 26;
        binMaxHeapData = (T[]) new Object[capacity];
        cmp = (Comparator<? super T>) new genericComparator();
        size = 0;
    }

    /**
     * Constructor creates an empty binary heap assumes that the elements
     * are ordered using the provided Comparator object
     *
     * @param cmp - Comparator that orders elements of T.
     */
    public BinaryMaxHeap(Comparator<? super T> cmp){
        this();
        this.cmp = cmp;
    }

    /**
     * Constructor creates a binary heap constructed from the given list.
     * Also, it is assumed that the elements are ordered using their natural ordering
     *
     * @param list - list of data
     */
    public BinaryMaxHeap(List<? extends T> list){
        size = list.size();
        capacity = list.size();
        cmp = (Comparator<? super T>) new genericComparator();
        binMaxHeapData = (T[]) new Object[capacity];
        buildHeap(list);
    }

    /**
     * Constructs a BinaryMaxHeap from a list of data and orders them according to the given comparator
     *
     * @param list - list of data
     * @param cmp - given Comparator
     */
    public BinaryMaxHeap(List<? extends T> list, Comparator<? super T> cmp){
        size = list.size();
        capacity = list.size();
        this.cmp = cmp;
        binMaxHeapData = (T[]) new Object[capacity];
        buildHeap(list);
    }

    /**
     * Creates an array representing a BinaryMaxHeap from a given list.
     * O(N) runtime, faster than individually inserting all elements.
     *
     * @param list - list of data
     * @return - basic array representing a BinaryMaxHeap
     */
    public void buildHeap(List<? extends T> list){
        binMaxHeapData = (T[]) list.toArray(binMaxHeapData);
        size = list.size();

        for (int i = (size / 2) - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Creates an array representing a BinaryMaxHeap from a given list.
     * O(N) runtime, faster than individually inserting all elements.
     *
     * @param arr - list of data
     * @return - basic array representing a BinaryMaxHeap
     */
    public void buildHeap(T[] arr){
        binMaxHeapData = arr;
        size = arr.length;

        for (int i = (size / 2) - 1; i >= 0; i--) {
            percolateDown(i);
        }
    }


    /**
     * Finds k Largest elements by extractingMax k times. Leaves BinaryMaxHeap unchanged.
     *
     * @param k - number of large elements
     * @return List of Largest K elements in heap
     */
    public List<T> findKLargest(int k){
        ArrayList<T> out = new ArrayList<>();
        var temp = binMaxHeapData;
        //Extract max elements
        for(int i = 0; i < k; i++)
            out.add(this.extractMax());

        binMaxHeapData = temp;
        return out;
    }

    /**
     * Private helper method that swaps the two elements at the given indices.
     *
     * @param curr - index at curr element
     * @param next - index at next element
     */
    private void swap(int curr, int next){
        T temp = binMaxHeapData[curr];
        binMaxHeapData[curr] = binMaxHeapData[next];
        binMaxHeapData[next] = temp;
    }

    /**
     * Private helper method to move up the BinaryMaxHeap and swap proper elements.
     *
     * @param i - index to percolate up at
     */
    private void percolateUp(int i){
        int parentInd = (i - 1) / 2;

        while(i > 0 && cmp.compare(binMaxHeapData[i], binMaxHeapData[parentInd]) > 0){
            swap(i, parentInd);
            i = parentInd;
            parentInd = (i - 1) / 2;
        }
    }

    /**
     * Helper method to resize BinaryMaxHeap backing array if too many elements
     */
    private void resize(){
        capacity *= 2;
        var temp  = (T[]) new Object[capacity];
        for(int i = 0; i < size; i++){
            temp[i] = binMaxHeapData[i];
        }
        binMaxHeapData = temp;
    }

    /**
     * Adds the given item to this priority queue.
     * O(1) in the average case, O(log N) in the worst case
     *
     * @param item
     */
    @Override
    public void add(T item) {
        //number of elements is greater than capacity
        if(size >= capacity)
            resize();

        //Add item
        binMaxHeapData[size] = item;
        size++;

        //Find correct spot
        percolateUp(size - 1);
    }

    /**
     * Returns, but does not remove, the maximum item this priority queue.
     * O(1)
     *
     * @return the maximum item
     * @throws NoSuchElementException if this priority queue is empty
     */
    @Override
    public T peek() throws NoSuchElementException {
        if(size == 0)
            throw new NoSuchElementException("Priority Queue is empty");
        return binMaxHeapData[0];
    }

    /**
     * Finds correct spot for the node by moving down tree using comparator
     *
     * @param i - index to start percolation at
     */
    private void percolateDown(int i) {
        while (true) {
            int leftChildIndex = (2 * i) + 1;
            int rightChildIndex = (2 * i) + 2;

            //only need to check if the left index exists
            if (leftChildIndex >= size)
                break;

            //ternary operator to find greater child
            int greaterChild = (rightChildIndex < size && cmp.compare(binMaxHeapData[rightChildIndex], binMaxHeapData[leftChildIndex]) > 0) ? rightChildIndex : leftChildIndex;

            //swap if index is less than child
            if (cmp.compare(binMaxHeapData[i], binMaxHeapData[greaterChild]) < 0) {
                swap(i, greaterChild);
                i = greaterChild;
            } else {
                break;
            }
        }
    }

    /**
     * Returns and removes the maximum item this priority queue.
     * O(log N)
     *
     * @return the maximum item
     * @throws NoSuchElementException if this priority queue is empty
     */
    @Override
    public T extractMax() throws NoSuchElementException {
        //check if empty
        if(size == 0)
            throw new NoSuchElementException("PriorityQueue is empty");

        //swap max with last element
        T maxValue = binMaxHeapData[0];
        binMaxHeapData[0] = binMaxHeapData[size - 1];
        binMaxHeapData[size - 1] = null;
        size--;

        //percolate and return
        percolateDown(0);
        return maxValue;
    }

    /**
     * Returns the number of items in this priority queue.
     * O(1)
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if this priority queue is empty, false otherwise.
     * O(1)
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Empties this priority queue of items.
     * O(1)
     */
    @Override
    public void clear() {
        capacity = 26;
        size = 0;
        binMaxHeapData = (T[]) new Object[capacity];
    }

    /**
     * Creates and returns an array of the items in this priority queue,
     * in the same order they appear in the backing array.
     * O(N)
     * <p>
     * (NOTE: This method is needed for grading purposes. The root item
     * must be stored at index 0 in the returned array, regardless of
     * whether it is in stored there in the backing array.)
     */
    @Override
    public Object[] toArray() {
        //create and assign new array containing filled elements
        Object[] result = (T[]) new Object[size];

        for (int i = 0; i < size; i++) {
            result[i] = binMaxHeapData[i];
        }

        return result;
    }

    /**
     * Generates the DOT encoding of this graph as string, which can be
     * pasted into http://www.webgraphviz.com to produce a visualization.
     */
    public StringBuilder generateDot() {
        StringBuilder dot = new StringBuilder();

        for(int i = 0; i < size; i++) {
            if((i * 2) + 1 < size && binMaxHeapData[(i * 2) + 1] != null)
                dot.append("\t\"" + binMaxHeapData[i] + "\" -> \"" + binMaxHeapData[(i * 2) + 1] + "\"\n");
            if((i * 2) + 2 < size && binMaxHeapData[(i * 2) + 2] != null)
                dot.append("\t\"" + binMaxHeapData[i] + "\" -> \"" + binMaxHeapData[(i * 2) + 2] + "\"\n");

        }

        return dot;
    }

    /**
     * Returns capacity of backing array
     */
    public int getCapacity() {
        return capacity;
    }


    /**
     * Generic Comparator class to use when comparator is not specified
     */
    private class genericComparator implements Comparator<Comparable<? super T>> {
        /**
         * Compares two Comparables using the compareTo method
         *
         * @param o1 the first object to be compared.
         * @param o2 the second object to be compared.
         * @return positive int if o1 is greater than o2, negative if o1 is lesser than o2, and 0 if equal
         */
        @Override
        public int compare(Comparable<? super T> o1, Comparable<? super T> o2) {
            return o1.compareTo((T) o2);
        }
    }
}
