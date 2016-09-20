/**
* Task:
* The programming assignment is to implement an algorithm which counts the number of inversions in an input sequence
* @author Isaac Sahle
* Date: Jun 10, 2016 
*/	
import java.util.*;
import java.io.File;
public class CountInversions{

	/**
	* Computes and returns the total number of inversions 
	* in the integer array A
	* @param A an array of integers
	* @return number of inversions contianed in array A  
	*/	
	public static int CountInversions(int A[]){
		//create a counter for inversion, tracker for int array A,
		//temporary holder variable
		int count = 0;
		int p_1 = 0;
		int A_length = A.length;
		int hold = 0;
		int i = 1;
		//traverse array and count all inverions
		while (i < A_length){
			  p_1 = i;
			  hold = A[p_1];
			  //runs a maximum of k times, avoiding O(n^2) 
			  while (p_1 > 0 && A[p_1 - 1] > hold){
			  	  A[p_1] = A[p_1-1];
			  	  p_1--;
			  	  count++;
			  }
			  	  A[p_1] = hold; 
			  	  i++;
		}
			  
		return count;	
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
			System.out.printf("Enter a list of integers. Enter the 'q' to end the list.\n");
		}
		Vector<Integer> inputVector = new Vector<Integer>();

		int v;
		while(s.hasNextInt()){
			v = s.nextInt();
			inputVector.add(v);
		}
			
		int[] array = new int[inputVector.size()];

		for (int i = 0; i < array.length; i++)
			array[i] = inputVector.get(i);

		System.out.printf("Read %d values.\n",array.length);

		long startTime = System.currentTimeMillis();

		int inversions = CountInversions(array);

		long endTime = System.currentTimeMillis();

		double totalTimeSeconds = (endTime-startTime)/1000.0;

		System.out.printf("# of inversions: %d\n",inversions);
		System.out.printf("Total Time (seconds): %.4f\n",totalTimeSeconds);
				
	}

}