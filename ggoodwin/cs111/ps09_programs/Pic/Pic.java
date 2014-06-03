import java.io.*;
import java.util.*;
public class Pic {
 //instance variables
  private int height;
  private int width;
  private int pix;
  private int[][] picture;
  
  //constructor
  public void Pic(String textFileName) throws FileNotFoundException{
    readInTextFile(textFileName);
  }
    
  //instance methods
    public static int[][] readInTextFile(String textFileName) throws FileNotFoundException {
          int[][] pix = null;

          Scanner reader = new Scanner (new File(textFileName));
               
          int height=reader.nextInt();
          int width=reader.nextInt();
          pix = new int[height][width];

          // Read in image data
          for (int i = 0; i < height; i++) 
            for (int j = 0; j < width; j++)
            pix[i][j] = reader.nextInt();
      
          reader.close();      

          return pix;
  }
    public int getHeight(){
      return height;
    }
    
    public int getWidth(){
      return width;
    }
    
    public int getPixel(int row, int col){
      return picture [row][col];
    }
      
    public void setPixel(int row, int col, int value){
      picture[row][col] = value;
    }
    
    //public int[][] getPixArray(){  
    //}

 


}
