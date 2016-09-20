
/**
* Computes whether three values in a set of numbers adds 
* to 225
* @author Isaac Sahle
* Date: May 27, 2016 
*/	

import java.util.Scanner;
import java.util.Vector;
import java.util.Arrays;
import java.io.File;

public class TripleSum{
	

	/**
	* Sorts and returns an array of integers 
	* using the counting sort algorithm.
	* @param A an array of integers
	* @return sorted array of integers 
	*/	
    public static int[] countingSort(int[] A) {
    
	    //determine maximum integer in array A  
	    int length = A.length;
	    int maxValue = A[0];
	    for (int i = 1; i < length; i++) {
	      if (A[i] > maxValue) {
	        maxValue = A[i];
	      }
	    }
	 
		//create a new array for counting occurences of the
	 	// integers in A
	 	int[] countedArr = new int[maxValue + 1];

	 	//count all occurences in the array A 
	    for (int i = 0;  i < length; i++) {
	      	countedArr[A[i]] = countedArr[A[i]] + 1;
	    }
	    //sum up all occurences in the array used for counting 
		countedArr[0] = countedArr[0] - 1;
	    for (int i = 1; i < countedArr.length;i++) {
	    	countedArr[i] = countedArr[i] + countedArr[i - 1];
	    }
	    //build back sorted array by traversing A backwards 
	    int sortedArray [] = new int [length];
	    for (int i = length - 1;i >= 0;i--) {
	    	sortedArray[countedArr[A[i]]] = A[i];
	    	countedArr[A[i]] = countedArr[A[i]] - 1;
	    }

	    return sortedArray;
    }

	/**
	* Takes an array of non-negative integers, and determines whether 
	* there are three numbers which add to 225
	* @param A an array of non-negative integers
	* @return true if there is such a pair, and false if not
	*/
	public static boolean TripleSum225(int A []){
		//Sort array
		A = countingSort(A);
		int length = A.length;
		int filtered [] = new int [226];
		for (int i = 0; i < 226; i++)
        filtered[i] = -1;
				
		int index = 0;
		//filter numbers greater than 225,and duplicates to insure no more than
		//a range of values from 0-225 (226 values)
		int count = 0;
		while(A[index] <= 225 && index != length - 1){
				if(index == 0){
					filtered[count] = A[index];
					count++;
				}else if(filtered[count - 1] != A[index]){
					filtered[count] = A[index];
					count++;
				}
				index++;
		}
		//best case: all ints greater than or equal to 225
		if(index == 0)
			return false;

		//search for at least one combination which adds to 225 	
		int end = count - 1;
		int p_1;
		for(p_1 = 0; p_1 < 226; p_1++){
				if(filtered[p_1] != -1){
				int p_2 = p_1 + 1;
				int p_3 = end;
				while(p_2 <= p_3){
					if(filtered[p_1] + filtered[p_2] + filtered[p_3] > 225){
						p_3--;
					}else if(filtered[p_1] + filtered[p_2] + filtered[p_3] < 225){
						p_2++;
					}else{
						//found triple 
						return true;
					}
				}

			}else{
				break;
			}
		}
		return false;	
	}

	public static void main(String[] args) {
		Scanner s;
		if (args.length > 0){
			try{
				s = new Scanner(new File(args[0]));
			} catch(java.io.FileNotFoundException e){
				System.out.printf("Unable to open %s\n",args[0]);
				return;
			}
			System.out.printf("Reading input values from %s.\n",args[0]);
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of non-negative integers. Enter a negative value to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt() && (v = s.nextInt()) >= 0)
			inputVector.add(v);

		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);

		long startTime = System.currentTimeMillis();

		boolean pairExists = TripleSum225(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("Array %s three values which add to 225.\n",pairExists? "contains":"does not contain");
		System.out.printf("Total Time (seconds): %.4f\n",totalTimeSeconds);
	}

}