import java.util.*;

public class Maze {

  // FILE NAME: Maze.java
  // WRITTEN BY: 
  // WHEN: 

  // PURPOSE: new class to implement maze search using a Stack to store
  // the current path as the search proceeds

  // MODIFICATION HISTORY:

  // instance variables

  int[][] mazeArr;       // current maze pattern
  int[][] visitArr;      // locations that have been visited during search
  Place current;         //keeps track of the current place
  Stack<Place> stk;      //keeps track of the path 

  // *** insert code for the constructor method
    public Maze(int [][] mazeArr){
	this.mazeArr = mazeArr;
	visitArr = mazeArr;
	current = new Place(0,0);//set to 0 by default
	stk = new Stack<Place>();
    }

  // *** insert code to search the maze from start to goal locations
	// 1 means wall, 0 means open, 8 means visited

	public void searchMaze(int startRow, int startCol, int endRow, int endCol){  
            current = new Place(startRow,startCol);//set current to the first place
	    stk.push(current);// put the first place on the stack
	   
	    visitArr[startRow][startCol]= 8;//sets start as visited
	    while( (mazeArr[current.getRow()][current.getCol()] != mazeArr[endRow][endCol])){
	        if(searchAround(current)==true){
//when it is true I will set current to 8 and then set the next item on the stack to current and run searchAround again with 
		visitArr[current.getRow()][current.getCol()] = 8;
		stk.push(current);
		}else if((current.getRow()==0 && current.getCol()== 0)){// this maze is impossible
			System.out.println("This maze is unsolvable *gasp*");
			return; // get out of while loop
		}else{
		    //when it is false I will pop the stack, set current place to the next place on the stack and run searchAround with current.
		stk.pop();
		current = stk.peek();}}//}end while loop
    // the maze is at the end and finished
       	System.out.println("done");
      	showPath(stk);
		}


	public boolean searchAround(Place here){// looks for any open places adjacent to the current place
 // want to check i+1, j+i, i-1, j-1 and see if any ==0 when I find one that does I will add it to the stack and return true, otherwise return false
	    int i = here.getRow();
	    int j = here.getCol();
	     if(i<mazeArr.length){
	
		if(i<(mazeArr.length-1) && visitArr[i+1][j]== 0){
		  
		    current = new Place(i+1,j);//resets current
		
		    return true;
		}else 
		    if(i>0 && visitArr[i-1][j]==0){
			current = new Place(i-1,j);
		
			return true;
		    }else if(j<mazeArr.length){
			if(j<(mazeArr[0].length-1) && visitArr[i][j+1]==0){
		       current = new Place(i,j+1);
		   
		       return true;
		   }else
		       if(j>0 && visitArr[i][j-1]==0){
			   current = new Place(i,j-1);
		
			   return true;}}
	 
	    }  return false;
	    }
	 
  public static void printArray (int[][] arr) {
    // prints the contents of a 2D square array, with labels on the
    // rows and columns
    int i, j; 
    System.out.println("array contents:");
    System.out.print("col");
    for (i = 0; i < arr.length; i++)
      System.out.print("  " + i);
    System.out.println();
    System.out.println("row");
    for (i = 0; i < arr.length; i++) {
      System.out.print(" " + i + "   ");
      for (j = 0; j < arr.length; j++)
	System.out.print(arr[i][j] + "  ");
      System.out.println();
    }
  }

  public void showPath (Stack<Place> stk) {
    // prints path locations on the input stack, and displays final path 
    // superimposed on the maze array
    int size = mazeArr.length;
    int[][] display = new int[size][size];
    int i, j;
    for (i = 0; i < size; i++)    // copy mazeArr into display array
      for (j = 0; j < size; j++)
	display[i][j] = mazeArr[i][j];
    Stack<Place> tempStack = new Stack<Place>();
    while (!stk.empty()) {   // flip stack contents onto tempStack
      tempStack.push(stk.pop());
    }
    Place P;  // print path and show path in display array
    System.out.println("Path on the Stack from start to goal: ");
    while (!tempStack.empty()) {
      P = tempStack.pop();
      System.out.println(P);
      display[P.getRow()][P.getCol()] = 8;	    
      stk.push(P);      // restores the original stack
    }
    System.out.println("final path:");
    printArray(display);
  }
  
}

