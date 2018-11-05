/**
 * @author Dinesh, Kautil
 * SP9: Comparision of Insertion sort and different takes of Merge Sort
 * Ver 1.0: 10/29/2018
 * 
 */

package dxt161330;

import java.util.Random;

public class SP9 {
	 
	public static Random random = new Random(); // to generate pseudo random numbers
	public static int numTrials = 100; // to run mergesort 100 times in a loop and take the average time.
	private static int MERGE_SORT_THRESHOLD = 11;
	public static void main(String[] args) {
		int n = 10; // size of the array
		int choice = 1 + random.nextInt(4); 

		if (args.length > 0) { n = Integer.parseInt(args[0]); }
		if (args.length > 1) { choice = Integer.parseInt(args[1]); }

		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = i;
		}

		Timer timer = new Timer();
		//  1.Insertion Sort 	2.Merge sort (take 1) 	3.Merge sort (take 2) 	4. Merge sort (take 3).
		switch (choice) {
		case 1:
			Shuffle.shuffle(arr);
			numTrials = 1;
			insertionSort(arr);
			break;
		case 2:
			for(int i=0; i<numTrials; i++) {
				Shuffle.shuffle(arr);
				mergeSort1(arr);
			}
			break;
		case 3:
			for(int i=0; i<numTrials; i++) {
				Shuffle.shuffle(arr);
				mergeSort2(arr);
			}
			break;
		case 4:
			for(int i=0; i<numTrials; i++) {
				Shuffle.shuffle(arr);
				mergeSort3(arr);
			}
			//System.out.println(Arrays.toString(arr));  
			break;
		} 
		timer.end();
		timer.scale(numTrials);

		System.out.println("Choice: " + choice + "\n" + timer);
	}

	/**
	 * In-place sort of the array using insertion sort
	 * @param arr array to be sorted
	 */
	public static void insertionSort(int[] arr) {
		insertionSort(arr, 0, arr.length - 1);
	}

	/**
	 * Util function to refactor code so that merge sort can use it
	 * @param arr array to sort
	 * @param start start index
	 * @param end end index
	 */
	public static void insertionSort(int[] arr, int start, int end) {
		int element;
		int j;
		for (int i = (start+1); i <= end; i++) {
			element = arr[i];
			for (j = (i-1); j >= start && element < arr[j]; j--) {
				arr[j+1] = arr[j];
			}
			arr[++j] = element;
		}
	}

	/**
	 * In-place sort of the array using merge sort take 1
	 * @param arr array to sort
	 */
	public static void mergeSort1(int[] arr) {
		mergeSort1(arr, 0, arr.length - 1);
	}

	/**
	 * Util function for recursive call with merge sort take 1
	 * @param arr source array
	 * @param start start index
	 * @param end end index
	 */
	private static void mergeSort1(int[] arr, int start, int end) {
		if (start == end) {
			return;
		}
		int mid = start + (end - start)/2;
		mergeSort1(arr, start, mid);
		mergeSort1(arr, mid + 1, end);
		merge1(arr, start, mid, end);
	}

	/**
	 * merges the two parts of the array
	 * @param arr source array
	 * @param start start index
	 * @param mid end of first and start of second array
	 * @param end end index
	 */
	private static void merge1(int[] arr, int start, int mid, int end) {

		// Initializing two arrays leftArray and rightArray
		int[] lArray = new int[mid - start + 1];
		int[] rArray = new int[end - mid];

		System.arraycopy(arr, start, lArray, 0, mid - start + 1);
		System.arraycopy(arr, mid + 1, rArray, 0, end - mid);

		int lIndex = 0; // Index for lArray
		int rIndex = 0; // Index for rArray

		for(int k = start; k <= end; k++) {
			if ( (rIndex >= (rArray.length)) || ( (lIndex < lArray.length) && (lArray[lIndex] <= rArray[rIndex]) ) ) {
				arr[k] = lArray[lIndex++];
			} else {
				arr[k] = rArray[rIndex++];
			}
		}
	}

	/**
	 * In-place sort of the array using merge sort take 2
	 * @param arr array to sort
	 */
	private static void mergeSort2(int[] arr) {
		int[] tempArray = new int[arr.length];
		mergeSort2(arr, tempArray, 0, arr.length - 1);
	}

	/**
	 * Util function for recursive call with merge sort take 2
	 * @param arr source array
	 * @param tempArray temporary array to store elements
	 * @param start start index
	 * @param end end index
	 */
	private static void mergeSort2(int[] arr, int[] tempArray, int start, int end) {
		if ((end-start+1) < MERGE_SORT_THRESHOLD) {
			insertionSort(arr, start, end);
		}
		else {
			int mid = start + (end - start)/2;
			mergeSort2(arr, tempArray, start, mid);
			mergeSort2(arr, tempArray, mid + 1, end);
			merge2(arr, tempArray, start, mid, end);
		}
	}

	/**
	 * merges two parts of the array with the help of pre allocated tempArray
	 * @param arr source array
	 * @param tempArray temporary array to store elements
	 * @param start start index
	 * @param mid end of first and start of second array
	 * @param end end index
	 */
	private static void merge2(int[] arr, int[] tempArray, int start, int mid, int end) {
		System.arraycopy(arr, start, tempArray, start, end - start + 1);
		
		int i = start;
		int j = mid + 1;
		
		for(int k = start; k <= end; k++) {
			if ( (j > end) || ( (i <= mid) && (tempArray[i] <= tempArray[j]) ) ) {
				arr[k] = tempArray[i++];
			} else {
				arr[k] = tempArray[j++];
			}
		}
	}

	/**
	 * In-place sort of the array using merge sort take 3
	 * @param arr array to sort
	 */
	private static void mergeSort3(int[] arr) {
		int[] tempArray = new int[arr.length];
		System.arraycopy(arr, 0, tempArray, 0, arr.length);
		mergeSort3(arr, tempArray, 0, arr.length - 1);
	}

	/**
	 * Util function for recursive call with merge sort take 3
	 * @param arr array to sort
	 * @param tempArray temp array to store elements
	 * @param start start index
	 * @param end end index
	 */
	private static void mergeSort3(int[] arr, int[] tempArray, int start, int end) {
		if ((end-start+1) < MERGE_SORT_THRESHOLD) {
			insertionSort(arr, start, end);
		}
		else {
			int mid = start + (end - start)/2;
			mergeSort3(tempArray, arr, start, mid);
			mergeSort3(tempArray, arr, mid + 1, end);
			merge3(arr, tempArray, start, mid, end);
		}
	}

	/**
	 * merges two arrays with the help of pre allocated tempArray
	 * @param arr source array
	 * @param tempArray temporary array for elements
	 * @param start start index
	 * @param mid end of first and start of second array
	 * @param end end index
	 */
	private static void merge3(int[] arr, int[] tempArray, int start, int mid, int end) {
		int i = start;
		int j = mid + 1;
		int k = start;
		
		while (i <= mid && j <= end) {
			if (tempArray[i] <= tempArray[j]) {
				arr[k++] = tempArray[i++];
			} else {
				arr[k++] = tempArray[j++];
			}
		}
		
		while(i <= mid) {
			arr[k++] = tempArray[i++];
		}
		
		while(j <= end) {
			arr[k++] = tempArray[j++];
		}
	}


	/** Timer class for roughly calculating running time of programs
	 *  @author rbk
	 *  Usage:  Timer timer = new Timer();
	 *          timer.start();
	 *          timer.end();
	 *          System.out.println(timer);  // output statistics
	 */
	public static class Timer {
		long startTime, endTime, elapsedTime, memAvailable, memUsed;
		boolean ready;

		public Timer() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public void start() {
			startTime = System.currentTimeMillis();
			ready = false;
		}

		public Timer end() {
			endTime = System.currentTimeMillis();
			elapsedTime = endTime-startTime;
			memAvailable = Runtime.getRuntime().totalMemory();
			memUsed = memAvailable - Runtime.getRuntime().freeMemory();
			ready = true;
			return this;
		}

		public long duration() { if (!ready) { end(); }  return elapsedTime; }

		public long memory()   { if (!ready) { end(); }  return memUsed; }

		public void scale(int num) { elapsedTime /= num; }

		public String toString() {
			if(!ready) { end(); }
			return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
		}
	}

	/**
	 * Shuffle the elements of an array arr[from..to] randomly
	 * @author rbk
	 */
	public static class Shuffle {

		public static void shuffle(int[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static<T> void shuffle(T[] arr) {
			shuffle(arr, 0, arr.length-1);
		}

		public static void shuffle(int[] arr, int from, int to) {
			int n = to - from  + 1;
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		public static<T> void shuffle(T[] arr, int from, int to) {
			int n = to - from  + 1;
			Random random = new Random();
			for(int i=1; i<n; i++) {
				int j = random.nextInt(i);
				swap(arr, i+from, j+from);
			}
		}

		static void swap(int[] arr, int x, int y) {
			int tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		static<T> void swap(T[] arr, int x, int y) {
			T tmp = arr[x];
			arr[x] = arr[y];
			arr[y] = tmp;
		}

		public static<T> void printArray(T[] arr, String message) {
			printArray(arr, 0, arr.length-1, message);
		}

		public static<T> void printArray(T[] arr, int from, int to, String message) {
			System.out.print(message);
			for(int i=from; i<=to; i++) {
				System.out.print(" " + arr[i]);
			}
			System.out.println();
		}
	}
}