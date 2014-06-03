public class MazeTest {

  // FILE NAME: MazeTest.java
  // WRITTEN BY:
  // WHEN:

  // PURPOSE: provides a main() method with code to test the methods in the 
  // Maze class, to find a path through a maze

  public static void main (String[] args) {
    // simple maze with only one new Place choice at each location
    int[][] array1 = {{0, 1, 1, 1, 1},
		      {0, 0, 0, 1, 1},
		      {1, 1, 0, 1, 1},
		      {1, 1, 0, 0, 1},
		      {1, 1, 1, 0, 0}};
    Maze newMaze = new Maze(array1);
    // search the maze from the upper left corner (0,0) to the lower
    // right corner (4,4)

    System.out.println("searching new maze");
    newMaze.searchMaze(0, 0, 4, 4);
 
 int[][] array3 = {{0, 1, 1, 1, 1},
		      {0, 0, 0, 1, 1},
		      {1, 1, 1, 1, 1},
		      {1, 1, 0, 0, 1},
		      {1, 1, 1, 0, 0}};
    
   System.out.println("looking at impossible maze");  
     Maze secMaze = new Maze(array3);
    secMaze.searchMaze(0, 0, 4, 4);
     //larger maze with multiple paths to explore
    int[][] array2 = {{0, 0, 0, 1, 1, 1, 0, 1, 0, 1},
		      {1, 0, 1, 1, 0, 0, 1, 1, 0, 1},
		      {0, 0, 1, 1, 0, 0, 1, 0, 0, 1},
		      {0, 1, 1, 0, 0, 1, 0, 0, 0, 0},
		      {0, 0, 1, 1, 0, 0, 0, 1, 1, 0},
		      {1, 0, 1, 1, 0, 1, 1, 1, 0, 0},
		      {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
		      {0, 0, 1, 1, 0, 1, 1, 0, 1, 1},
		      {0, 1, 0, 0, 0, 1, 1, 0, 0, 0},
		      {0, 1, 0, 1, 0, 0, 0, 1, 0, 0}};
    Maze largeMaze = new Maze(array2);
    System.out.println("searching big Maze"); 
    largeMaze.searchMaze(0, 0, 9, 9);
    
  }
  
}
