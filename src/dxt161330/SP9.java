/**
 * @author Dinesh, Kautil
 * SP9: Comparision of Insertion sort and different takes of Merge Sort
 * Ver 1.0: 10/29/2018
 * 
 */

package dxt161330;

import java.util.Random;

public class SP9 {
	public static Random random = new Random();
	public static int numTrials = 100;

	public static void main(String[] args) {
		int n = 10; 
		int choice = 2;

		if (args.length > 0) { n = Integer.parseInt(args[0]); }
		if (args.length > 1) { choice = Integer.parseInt(args[1]); }

		int[] arr = new int[n];
		for (int i=0; i<n; i++) {
			arr[i] = i;
		}

		Timer timer = new Timer();
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
			break;  // etc
		} 
		timer.end();
		timer.scale(numTrials);

		System.out.println("Choice: " + choice + "\n" + timer);
	}

	public static void insertionSort(int[] arr) {
		int element;
		int j;
		for (int i = 1; i < arr.length; i++) {
			element = arr[i];
			for (j = (i-1); j >= 0 && element < arr[j]; j--) {
				arr[j+1] = arr[j];
			}
			arr[++j] = element;
		}
	}

	public static void mergeSort1(int[] arr) {
		mergeSortOne(arr, 0, arr.length - 1);
	}


	private static void mergeSortOne(int[] arr, int start, int end) {
		if (start == end) {
			return;
		}
		int mid = start + (end - start)/2;
		mergeSortOne(arr, start, mid);
		mergeSortOne(arr, mid + 1, end);
		mergeOne(arr, start, mid, end);
	}

	private static void mergeOne(int[] arr, int start, int mid, int end) {

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


	/** Timer class for roughly calculating running time of programs
	 *  @author Dinesh, Kautil
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

	/** @author Dinesh, Kautil : based on algorithm described in a book
	 */


	/* Shuffle the elements of an array arr[from..to] randomly */
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