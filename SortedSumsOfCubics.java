/**
* Task:
*  Write a program that prints out all integers of the form 𝑎3+𝑏3, where 𝑎 and 𝑏 are integers in the range [0,𝑛], 
*  in sorted order while using 𝑂(𝑛) space. That is, you cannot use an array of size 𝑛2 and then sort it.
* @author Isaac Sahle
* Date: Jul 08, 2016 
*/  
import java.util.*;
import java.lang.*;
public class SortedSumsOfCubics{
/**
* Node containing a value i and value j
* as well as a comparison method to compare
* to other nodes 
*/
public static class NumNode{
	int value;
	int i;
	int j;
	public NumNode(int val,int indexi, int indexj){
		value = val;
		i = indexi;
		j = indexj;
	}

	public int compareTo(NumNode comp){
		if(value > comp.value){
			return 1;
		}else{
			return -1;
		}
	}
}
/**
* Min heap used to store a maximum of n+1 elements
* as we compute and print out the cubic pairs 
*/
public static class Heap {    
    private ArrayList<NumNode> heap = new ArrayList<NumNode>();
    /**
    * checks if heap is empty  
    */
    public boolean isEmpty(){
		return heap.isEmpty();
    }
    /**
    * Insert Numnode into heap and bubble up as 
    * far as necessary 
    * @param val node to be inserted 
    */
    public void insert(NumNode val){
        heap.add(val);
        int index = heap.size() - 1;
        while (index >= 1 && (heap.get((index - 1) / 2).compareTo(heap.get(index)) == 1)) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        } 
    } 
    /**
    * Delete root node in heap (i.e smallest element in heap)
    * @return deleted_node the node which has been deleted 
    */  
    public NumNode delete(){
        int heap_s = heap.size();
        NumNode deleted_node = top();
    	heap.set(0,heap.get(heap_s - 1));
    	heap.remove(heap_s - 1);
    	
    	int index = 0;
        
        while (((index * 2) + 1) < heap.size()) {
            int s_child = (index * 2) + 1;

            if (((index * 2) + 2) < heap.size() && heap.get(((index * 2) + 1)).compareTo(heap.get(((index * 2) + 2))) == 1){
                s_child = (index * 2) + 2;
            } 
            
            if (heap.get(index).compareTo(heap.get(s_child)) == 1) {
                swap(index, s_child);
            } else {
                break;
            }

            index = s_child;
        }
    	
    	return deleted_node;
    }
    /**
    * Gets a copy of the root node in the heap
    * without altering the heap 
    * @return root node  
    */
    public NumNode top(){
        if(heap.isEmpty()){
        	return  new NumNode(-1,-1,-1);	
        }else{
        	return heap.get(0);	
        } 
	}
    /**
    * Swap two elements in the heap by index values 
    * @param index1 index2 indeces of elements to be swapped
    */
    public void swap(int index_1, int index_2) {
        NumNode tmp = heap.get(index_1);
        heap.set(index_1,heap.get(index_2));
        heap.set(index_2,tmp);        
    }
}

/**
* Computes and prints all the sums of cubic pairs
* from values from 0 - n
* @param n range of values 0 - n
*/
 public static void SortedSumsOfCubics(int n){
    Heap binheap = new Heap();	 	
    //first iteration; fill heap with n+1 elements
 	for (int x = 0;x <= n;x++) {
			int sum = (x*x*x) + 0;
			NumNode node = new NumNode(sum,x,0);
			binheap.insert(node);		 			
 	}

    //constantly remove and insert based on each nodes values of i and j
    int size = binheap.heap.size();
 	NumNode removed_node = binheap.delete();
 	//run size test to see at most n+1 elements at a time
 	while(!(removed_node.i == n && removed_node.j == n)){
 		  if(removed_node.j < removed_node.i){
 		  	 //print and insert
 		  	System.out.print(removed_node.value + ", ");
			int sum = (removed_node.i*removed_node.i*removed_node.i) + ((removed_node.j)+1)*((removed_node.j)+1)*((removed_node.j)+1);
 		  	NumNode insertNode = new NumNode(sum,removed_node.i,(removed_node.j)+1);
 		  	binheap.insert(insertNode);				
 		  }else{
            //just print 
 		  	System.out.print(removed_node.value + ", ");
 		  }
 		  //remove node
 		  removed_node = binheap.delete();
 	}
 	//Last value
 	System.out.println(removed_node.value);
 }

	public static void main(String[] args) {
		if(args.length != 1){
            System.out.println("Usage: java SortedSumsOfCubics <integer>");
        }
        Integer num = new Integer(args[0]);
        int number = num.parseInt(args[0]);
        SortedSumsOfCubics(num);
	}
}