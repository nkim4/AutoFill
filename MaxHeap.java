package proj3;
/**
 * This class holds the Max Heap in the array heap. This heap also starts
 * with a defualt size of 10. 
 * @author Nicholas Kim
 * @section 03
 * @date 4/7/14
 * @param <AnyType> 
 */
public class MaxHeap<AnyType extends Comparable<? super AnyType>> {
	private static int Default_Size = 10;
	private AnyType[] heap;
	private int currentSize;

	/**
	 * This method uses the other constructor to build heap with a
	 * default size of 10.
	 */
	public MaxHeap() {
		this(Default_Size);
	}
	/**
	 * This method builds the heap with the size capacity and sets currentSize 
	 * to 0. Since Heaps do not have anything in the index 0, The size is set
	 * at capacity + 1.
	 * @param capacity
	 */
	public MaxHeap(int capacity) {
		currentSize = 0;
		heap = (AnyType[]) new Comparable[capacity + 1];
	}
	/**
	 * This method is very important in the insert method. This method creates a new heap with
	 * the size "size". This method then copies the values inside the original heap into the
	 * new heap.
	 * @param size The new size you want to set the array.
	 */
	private void enlargeArray(int size) {
		AnyType[] newArray = (AnyType[]) new Comparable[size];
		for (int i = 0; i < heap.length; i++) {
			newArray[i] = heap[i];
		}
		heap = newArray;
	}
	/**
	 * This method inserts new elements into the heap. This method uses englargeArray to
	 * always make sure that the array will be big enough to fit the new elements.
	 * @param x the object you want to add into the heap.
	 */
	public void insert(AnyType x) {
		if (currentSize == heap.length - 1) {
			enlargeArray(heap.length * 2 + 1);
		}
		int hole = ++currentSize;
		for (; hole > 1 && x.compareTo(heap[hole / 2]) > 0; hole /= 2) {
			heap[hole] = heap[hole / 2];
		}
		heap[hole] = x;
	}
	/**
	 * A simple method that check to see if the heap of the object is empty
	 * @return true or false depending if the heap is empty.
	 */
	public boolean isEmpty() {
		if (currentSize == 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * This method prints the root of the MaxHeap which is its element in index 1.
	 * If the heap is empty, The method will print out a message to show that the
	 * heap is empty.
	 */
	public void printHeapRoot() {
		if (isEmpty()) {
			System.out.println("This heap is empty!!");
		} else {
			System.out.println(heap[1].toString());
		}
	}
	/**
	 * This method uses a for loop to print out each value of the heap.
	 */
	public void printHeap() {
		if (isEmpty()) {
			System.out.println("This heap is empty!!");
		} else {
			for (AnyType a : heap) {
				System.out.println(a.toString());
			}
		}
	}
	/**
	 * This method returns the string that has all the elements in the heap
	 * in the required format.
	 * @return returns a string with all the elements of the heap in it.
	 */
	public String toString() {
		int count = 0;
		String finalString = " --> The heaps contains:";
		for (int i = 1; i < heap.length; i++) {
			if (heap[i] != null) {
				count++;
				finalString = finalString + "\n" + "[" + count + "] "
						+ heap[i].toString();
			}
		}
		return finalString;

	}
	/**
	 * This method simply prints out the first 3 elements in the heap.
	 */
	public void printImmediateOptions() {
		for (int i = 1; i < 4; i++) {
			if (heap[i] != null) {
				System.out.println(heap[i].toString());
			} else {
				System.out.println("null");
			}
		}
	}
}
