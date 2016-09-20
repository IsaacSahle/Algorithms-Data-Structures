/**
* Solves a 3x3 ninepuzzle in the shortest amount of moves
* using a breadth first search  
* @author Isaac Sahle
* Date: Jul 29, 2016
*/ 
import java.util.*;
import java.io.*;
public class SolveNinePuzzle{
	
	public static HashSet<String> vertex_check = new HashSet<String>();
	public static ArrayList<Board> possible_boards = new ArrayList<Board>();
	/**
	* Board class representing a board 3x3 board state
	*/
	public static class Board{
		ArrayList<Board> children = new ArrayList<Board>();
    	Board parent = null;
		int board [][];
		String board_string;
		
		public Board(int b [][]){
			board = b;
			board_string = Arrays.deepToString(b);
		}
		//compare boards
		public boolean compare(Board other){
			for (int i = 0;i <  board.length;i++) {
				for (int j = 0;j < board[i].length;j++) {
					if(board[i][j] != other.board[i][j]){
						return false;
					}
				}
			}
			if(!(board_string.equals(other.board_string))){
				return false;
			}else{
				return true;
			}
		}

	    public void setParent(Board parent) {
	        this.parent = parent;
	    }

	    public void addChild(Board child) {
	        children.add(child);
	    }

	}
	/**
	* Tree of Boards; all children are unique
	* board states with relation to parent
	*/
	public static class Board_Tree{
		Board root;

		public Board_Tree(Board board){
				root = board;
		}

		public void insert(Board parent,Board child){
				parent.addChild(child);
				child.setParent(parent);
		}

		public void goal_board(Board goal){
			ArrayList<Board> list = new ArrayList<Board>();
			Board current = goal;
			while(current != null){
				list.add(0,current);
				current = current.parent;	
			}
		
			for (Board s:list) {
				int temp [][] = s.board;
				for(int i = 0; i < temp.length;i++){
					for (int j = 0;j < temp[i].length;j++) {
						System.out.print(temp[i][j] + " ");	
					}
					System.out.println();
				}
				System.out.println();
			}
		}

	}
	/**
	* Queue of Board Objects
	*/
	 public static class Queue{
		private LinkedList<Board> list = new LinkedList<Board>();

		public boolean isEmpty(){
			return list.isEmpty();
		}
		public boolean enqueue(Board s){
			list.addFirst(s);
			return true;
		}
		public Board dequeue(){
			if(!isEmpty()){
				return list.removeLast();
			}
			return null;
		}
		public Board peek(){
			if(!isEmpty()){
				return list.peek();
			}
			return null;	
		}
		public void destroy(){
			list.clear();
		}
	}
	/**
	* BFS algorithm to solve ninepuzzle in the least possible amount 
	* of moves
	* @param board the board to be solved
	* @return true if the puzzle is solvable false if not 
	*/
	public static boolean SolveNinePuzzle(int board [][]){
		for(int i = 0; i < board.length;i++){
				for (int j = 0;j < board[i].length;j++) {
					System.out.print(board[i][j] + " ");	
				}
				System.out.println();
		}
		System.out.println();
		System.out.println("Attempting to solve board ...");
 
		Queue node_queue = new Queue();
		Board start_board = new Board(board);
		int goal_board [][] = {{1,2,3},{4,5,6},{7,8,0}}; 
		Board goal = new Board(goal_board);
		//check if the start board is the solved board
		if(start_board.compare(goal)){
			return true;
		}
		//new tree for unique moves; root node is start board
		Board_Tree result_tree = new Board_Tree(start_board);
		node_queue.enqueue(start_board);
		//add root node string representation to hashset
		vertex_check.add(start_board.board_string);
		while(!node_queue.isEmpty()){
			Board v = node_queue.dequeue();
			compute_possible_moves(v);
			for(Board s: possible_boards){
				if(s.compare(goal)){
					result_tree.insert(v,s);
					result_tree.goal_board(s);
					return true;
				}else if(!(vertex_check.contains(s.board_string))){
					vertex_check.add(s.board_string);
					node_queue.enqueue(s);
					result_tree.insert(v,s);
				}else{
					//ignore
				}
			}
		}

		return false;
	}
	/**
	* Takes a board object and computes all legal
	* possible moves
	* @param s board which the moves will be computed from
	* 
	*/
	public static void compute_possible_moves(Board s){
		int b [][] = s.board;
		int x = -1;
		int y = -1;
		for (int i = 0;i <  b.length;i++) {
			for (int j = 0;j < b[i].length;j++) {
				if(b[i][j] == 0){
					x = i;
					y = j;
					break;
				}	
			}
		}

		int left [][];
		int right [][];
		int up [][];
		int down [][];
		possible_boards.clear();
		if(x - 1 != -1 && x >= 0 && x < 3){
			//up is a legal move
			int temporary_board [][] = new int[b.length][];
			for(int q = 0; q < b.length; q++)
			    temporary_board [q] = b[q].clone();
			

			int temp = temporary_board[x - 1][y];
			temporary_board[x - 1][y] = temporary_board[x][y];
			temporary_board[x][y] = temp;
			up = temporary_board;

			Board up_board = new Board(up);
			possible_boards.add(up_board);

		}
		if(x + 1 != 3 && x >= 0 && x < 3){
			//down is a legal move
			int temporary_board [][] = new int[b.length][];
			for(int q = 0; q < b.length; q++)
			    temporary_board [q] = b[q].clone();
			
			int temp = temporary_board[x + 1][y];
			temporary_board[x + 1][y] = temporary_board[x][y];
			temporary_board[x][y] = temp;
			down = temporary_board;
			Board down_board = new Board(down);
			possible_boards.add(down_board);
		}
		if(y - 1 != -1 && y >= 0 && y < 3){
			//left move is legal
			int temporary_board [][] = new int[b.length][];
			for(int q = 0; q < b.length; q++)
			    temporary_board [q] = b[q].clone();
			
			int temp = temporary_board[x][y - 1];
			temporary_board[x][y - 1] = temporary_board[x][y];
			temporary_board[x][y] = temp;
			left = temporary_board;
			Board left_board = new Board(left);
			possible_boards.add(left_board);

		}
		if(y + 1 != 3 && y >= 0 && y < 3){
			//right move is legal
			int temporary_board [][] = new int[b.length][];
			for(int q = 0; q < b.length; q++)
			    temporary_board [q] = b[q].clone();
			
			int temp = temporary_board[x][y + 1];
			temporary_board[x][y + 1] = temporary_board[x][y];
			temporary_board[x][y] = temp;
			right = temporary_board;
			Board right_board = new Board(right);
			possible_boards.add(right_board);

		}

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
		}else{
			s = new Scanner(System.in);
			System.out.printf("Enter a list of integers representing boards (nine numbers per board)\n");
		}

		Vector<Integer> inputVector = new Vector<Integer>();
		int v;
		while(s.hasNextInt()){
			v = s.nextInt();
			inputVector.add(v);
		}
		int count = 0;
		int board_count = 1;
		ArrayList<Integer> list = new ArrayList<Integer>();
		ArrayList<Double> time = new ArrayList<Double>();
		for (Integer  num:inputVector) {
			list.add(num);
			if(count == 8){
				int [][] board = new int[3][3];
				for(int i = 0; i < 3;i++){
					for(int j = 0; j < 3;j++){
						board[i][j] = list.remove(0);
					}
				}
				System.out.println("Reading board " + board_count);
				long startTime = System.currentTimeMillis();
				boolean solvable = SolveNinePuzzle(board);
				long endTime = System.currentTimeMillis();
				vertex_check.clear();
				possible_boards.clear();
				System.out.print("Board : " + board_count);
				System.out.println((solvable) ? " Solvable." : " Not Solvable.");
				double totalTimeSeconds = (endTime-startTime)/1000.0;
				time.add(totalTimeSeconds);
				board_count++;
				list.clear();
			}
			count++;
			if(count == 9){
				count = 0;
			}
		}
		Double total = new Double(0);
		board_count = board_count - 1;
		for(int i = 0; i < board_count;i++){
			total = total + time.get(i);
		}
		total = total/board_count;
		if(board_count == 1){
			System.out.println("Processed " + board_count + " board.");
		}else{
			System.out.println("Processed " + board_count + " boards.");		
		}
		System.out.printf("Average Time (seconds): %.4f\n",total);

	}

}