import java.util.Arrays;
import java.util.Random;

public class Sort {
	public static void selectionSort(int[] arr) {
		for (int start = 0; start < arr.length; start++) {
			int minNum = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int i = start; i < arr.length; i++) {
				if (arr[i] <= minNum) {
					minNum = arr[i];
					minIndex = i;
				}
			}
			swap(arr, start, minIndex);
		}
	}
	
	public static void insertionSort(int[] arr) {
		for (int sorted = 1; sorted < arr.length; sorted++) {
			int insert = arr[sorted];
			int insertIndex = sorted;
			for (int i = 0; i < sorted; i++) {
				if (insert < arr[i]) { // insert it at i
					insertIndex = i;
					break;
				}
			}
			for (int k = sorted; k > insertIndex; k--) {
				arr[k] = arr[k-1];
			}
			arr[insertIndex] = insert;
		}
	}
	
	public static void bubbleSort(int[] arr) {
		boolean sorted = false;
		int end = arr.length;
		while (!sorted) {
			sorted = true;
			for (int i = 1; i < end; i++) {
				if (arr[i] < arr[i-1]) {
					swap(arr, i, i-1);
					sorted = false;
				}
			}
			end--;
		}
	}
	
	public static void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;
	}
	
	public static void heapSort(int[] arr) {
		buildHeap(arr);
		sortHeap(arr);
	}
	
	public static void buildHeap(int[] arr) {
		int end = arr.length-1; // the end inclusive
		for (int i = end; i >= 0; i--) {
			trickle(arr, i, end);
		}
	}
	
	/**
	 * Assumes that buildHeap(arr) has been called
	 * @param arr
	 */
	public static void sortHeap(int[] arr) {
		for (int end = arr.length-1; end >= 0; end--) {
			swap(arr, end, 0);
			trickle(arr, 0, end-1);
		}
	}
	/**
	 * 
	 * @param arr
	 * @param index the index that we want to trickle down from
	 * @param end end of heap inclusive
	 */
	public static void trickle(int[] arr, int index, int end) {
		int swapTo = index;
		int left = leftChild(index);
		int right = rightChild(index);
		if (left <= end && arr[left] > arr[swapTo]) { swapTo = left; }
		if (right <= end && arr[right] > arr[swapTo]) { swapTo = right; }
		
		// if swapTo doesn't change then we are done
		if (swapTo != index) {
			// swap index with the larger child (swapTo)
			swap(arr, swapTo, index);
			trickle(arr, swapTo, end);
		}
	}
	
	public static int leftChild(int x) {
		return 2*x+1;
	}
	public static int rightChild(int x) {
		return 2*x+2;
	}
	public static int parent(int x) {
		return (x-1)/2;
	}
	
	public static void mergeSort(int[] arr) {
		mergeSort(arr, 0, arr.length-1);
	}
	/**
	 * Sort the interval [a, b]
	 * @param arr
	 * @param a
	 * @param b
	 */
	public static void mergeSort(int[] arr, int a, int b) {
		if (b-a+1 < 2) {
			return;
		} else {
			int mid = a + (b-a)/2;
			mergeSort(arr, a, mid);
			mergeSort(arr, mid+1, b);
			merge(arr, a, mid+1, b);
		}
	}
	
	/**
	 * Merges [a,b) with [b, c]
	 * @param arr
	 * @param a
	 * @param b
	 * @param c
	 */
	public static void merge(int[] arr, int a, int b, int c) {
		int[] temp = new int[c-a+1];
		int left1 = a;
		int left2 = b;
		int i = 0;
		while (left1 < b && left2 <= c && i < temp.length) {
			if (arr[left1] < arr[left2]) {
				// add the smallest element from the first interval to temp and ++ the left1 pointer
				temp[i] = arr[left1];
				left1++;
			} else {
				// add the smallest element from the second interval to temp and ++ the left2 pointer
				temp[i] = arr[left2];
				left2++;
			}
			i++;
		}
		
		while (left1 < b) { // copy the rest of the first interval into temp if necessary
			temp[i] = arr[left1];
			left1++;
			i++;
		}
		while (left2 <= c) { // copy the rest of the first interval into temp if necessary
			temp[i] = arr[left2];
			left2++;
			i++;
		}
		// copy temp into arr
		for (int k = 0; k < temp.length; k++) {
			arr[a+k] = temp[k];
		}
	}
	
	public static void main(String[] args) {
		int maxLength = 10;
		int maxElement = 20;
		
		Random r = new Random();
		for (int i = 0; i < 1000; i++) {
			// make array
			int length = r.nextInt(maxLength);
			int[] arr = new int[length];
			for (int k = 0; k < length; k++) {
				arr[k] = r.nextInt(maxElement);
			}
			// test sorting
			int[] orig = arr.clone();
			int[] clone = arr.clone();
			mergeSort(arr);
			Arrays.sort(clone);
			if (!Arrays.equals(arr, clone)) {
				System.out.println("My algorithm sorted it wrong");
				System.out.println("Original: " + Arrays.toString(orig));
				System.out.println("Incorrectly sorted: " + Arrays.toString(arr));
				System.out.println("Correctly sorted: " + Arrays.toString(clone));
			}
		}
		System.out.println("end");
	}
}
