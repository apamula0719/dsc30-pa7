/*
 * Name: TODO
 * PID:  TODO
 */

import java.util.NoSuchElementException;

/**
 * Title: dHeap Description: This program creates a Heap with d branching factor
 *
 * @author Aneesh Pamula
 * @since 5/24/2023
 *
 * @param <T> the type of elements held in this collection
 */
public class dHeap<T extends Comparable<? super T>> implements HeapInterface<T> {

    private T[] heap;   // backing array
    private int d;      // branching factor
    private int nelems; // number of elements
    private boolean isMaxHeap; // indicates whether heap is max or min
    private final int DEFAULT_SIZE = 6;

    /**
     * Initializes a binary max heap with capacity = 6
     */
    @SuppressWarnings("unchecked")
    public dHeap() {
        heap = (T[]) new Comparable[DEFAULT_SIZE];
        d = 2;
        nelems = 0;
        isMaxHeap = true;
    }

    /**
     * Initializes a binary max heap with a given initial capacity.
     *
     * @param heapSize The initial capacity of the heap.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int heapSize) {
        heap = (T[]) new Comparable[heapSize];
        d = 2;
        nelems = 0;
        isMaxHeap = true;
    }

    /**
     * Initializes a d-ary heap (with a given value for d), with a given initial
     * capacity.
     *
     * @param d         The number of child nodes each node in the heap should have.
     * @param heapSize  The initial capacity of the heap.
     * @param isMaxHeap indicates whether the heap should be max or min
     * @throws IllegalArgumentException if d is less than one.
     */
    @SuppressWarnings("unchecked")
    public dHeap(int d, int heapSize, boolean isMaxHeap) throws IllegalArgumentException {
        if(d < 1){
            throw new IllegalArgumentException();
        }
        heap = (T[]) new Comparable[heapSize];
        this.d = d;
        nelems = 0;
        this.isMaxHeap = isMaxHeap;
    }

    @Override
    public int size() {
        return nelems;
    }

    @Override
    public T remove() throws NoSuchElementException {
        if(nelems == 0)
            throw new NoSuchElementException();
        T root = heap[0];
        heap[0] = heap[nelems-1];
        heap[nelems - 1] = null;
        nelems--;
        trickleDown(0);
        return root;
    }

    @Override
    public void add(T item) throws NullPointerException {
        if(item == null)
            throw new NullPointerException();
        if(nelems == heap.length)
            resize();
        heap[nelems] = item;
        bubbleUp(nelems);
        nelems++;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        heap = (T[]) new Comparable[nelems];
        nelems = 0;
    }

    @Override
    public T element() throws NoSuchElementException {
        if(nelems == 0)
            throw new NoSuchElementException();
        return heap[0];
    }

    private void trickleDown(int index) {
        //Find greatest/least of all children
        if(d*index + 1 >= nelems)//No more trickling if the node has no children
            return;
        int childToSwap = minMaxChild(index, isMaxHeap);
        int compare = heap[index].compareTo(heap[childToSwap]);
        if(compare < 0) {//If parent is less than child
            if(isMaxHeap) {
                //Swap with that child
                swapValues(index, childToSwap);
                //Trickle down from that position
                trickleDown(childToSwap);
            }
        }
        if(compare > 0){//If parent is greater than child
            if(!isMaxHeap){
                //Swap with that child
                swapValues(index, childToSwap);
                //Trickle down from that position
                trickleDown(childToSwap);
            }
        }
    }

    private int minMaxChild(int index, boolean max){//Finds the min(or max) of the children
        int desiredIndex = d*index+1;
        for(int i = d*index + 1; i <= d*index + d; i++){//Going through child indices
            if(i >= nelems)
                break;
            int compare = heap[i].compareTo(heap[desiredIndex]);//Compare it to the lowest or greatest
            if(compare > 0){//If it is greater
                if(max)//And we are looking for the greatest
                    desiredIndex = i;
            }
            if(compare < 0){//If it is lower
                if(!max)//And we are looking for the lowest
                    desiredIndex = i;
            }
        }
        return desiredIndex;
    }
    private void bubbleUp(int index) {
        if(index == 0)
            return;
        int compare = heap[index].compareTo(heap[parent(index)]);
        if(compare > 0){//When this value is greater than its parent
            if(isMaxHeap){
                swapValues(index, parent(index));
                bubbleUp(parent(index));
            }
            //Don't do anything if it is a min heap, swap and continue bubbling if max heap
        }
        if(compare < 0){//When this value is less than its parent
            if(!isMaxHeap){
                swapValues(index, parent(index));
                bubbleUp(parent(index));
            }
            //Don't do anything if it is a max heap, swap and continue bubbling if min heap
        }
    }
    private void swapValues(int i, int j){
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
    @SuppressWarnings("unchecked")
    private void resize() {
        T[] newHeap = (T[]) new Comparable[nelems*2];
        for(int i = 0; i < nelems; i++)
            newHeap[i] = heap[i];
        heap = newHeap;
    }

    private int parent(int index) {
        return (index - 1) / this.d;
    }
    public void printing(){
        for(T data : heap){
            System.out.print(data + " ");
        }
    }
}
