//Gabrielle Goodwin
//CS Pset 9

import java.io.*;
import java.util.*;
public class Pic {
 //instance variables
  private int height;
  private int width;
  private int pix;
  private int[][] picture;
  
  //constructor
  public Pic(String fileName) throws FileNotFoundException{
    picture = readInTextFile(fileName);
    height = picture.length;
    width = picture[0].length;
    pix= height*width;
    
  }
    
  //instance methods
    public static int[][] readInTextFile(String fileName) throws FileNotFoundException {
          int[][] pix = null;

          Scanner reader = new Scanner (new File(fileName));
               
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
    public int getHeight(){//return height
      return height;
    }
    
    public int getWidth(){//returns width
      return width;
    }
    
    public int getPixel(int row, int col){//returns pixels
      return picture [row][col];
    }
      
    public void setPixel(int row, int col, int value){//sets value for a pixel
      picture[row][col] = value;
    }
    
    public int[][] getPixArray(){//returns 2d array  
         return picture;
    }
    public void mirror(){//mirrors the image across a vertical line
         int [][] answer = new int[height][width]; 
         for (int i = 0; i < height; i++){
              for (int j = 0; j < width; j++){
              answer[i][(width-j)-1]= picture[i][j];
              }}picture=answer;}
       
    public void shrink(){//shrinks the image
         int [][] answer = new int[height][width];
         for (int i = 0; i < height; i= i+2){ 
              for (int j = 0; j < width; j=j+2){
                   if(((i%2)!=0)||((j%2)!=0)){
                        answer[i-1][j-1]= picture[i][j];
                   }}}picture=answer;}
    
    public void rotate90(){//rotates the image 90 degress
         int[][] answer = new int [height][width];
         for (int i = 0; i < height; i++){ 
              for (int j = 0; j < width; j++){                           
                   answer[j][(height-i)-1] = picture[i][j];
              }}picture=answer;}
    
    public void outputTextFile(String textFileName, int[][] pix) throws FileNotFoundException{
         //outputs the 2d array as txt
         PrintWriter bob = new PrintWriter(PicOps.getFolder()+textFileName);
         System.out.print(getHeight());
         System.out.println(getWidth());
         for(int i = 0; i<height; i++){
              for(int j = 0; j<width; j++){
                   System.out.print(getPixel(i,j));
              }System.out.println(" ");
         }}
         
         
              
    
 

public static void usage () {
 System.out.println("java Pic <command> <arg1>...\n"
 + "where <command> <arg1>... is:\n"
 + "  display <fileName>\n"
 + "  toTextFile <fileName.jpg>\n" 
 + "  textFileToJPEG <fileName.txt>\n"
 + "  getHeight <fileName.txt>\n"
 + "  getWidth <fileName.txt>\n"
 + "  getPixel <row> <col> <fileName.txt>\n"
 + "  setPixel <row> <col> <value> " + "<fileName.txt>\n"
 + "  getPixArray <fileName.txt>\n"
 + "  mirror <inFileName.txt> "+ "<outFileName.txt>\n"

 + "  shrink <inFileName.txt> " + "<outFileName.txt>\n"
 + "  rotate90 <inFileName.txt> " + "<outFileName.txt>\n"
 + "  readInTextFile <fileName.txt>\n");
 }
 
 public static void main (String[] args) throws IOException {
  if (args.length < 2) {
   usage();
  } else if (args[0].equals("display")) {
   String fileName = PicOps.getFolder() + args[1];
   PicOps.display(fileName);
  } else if (args[0].equals("toTextFile")) {
   String fileName = PicOps.getFolder() + args[1];
   System.out.println("Create text file for " + fileName);
   PicOps.toTextFile(fileName);
  } else if (args[0].equals("textFileToJPEG")) {
   String fileName = PicOps.getFolder() + args[1];
   System.out.println("Create JPEG file for " + fileName);
   PicOps.textFileToJPEG(fileName);
  } else if (args[0].equals("getHeight")) {
   String fileName = PicOps.getFolder() + args[1];
   Pic p = new Pic(fileName);
   System.out.println(p.getHeight());
  } else if (args[0].equals("getWidth")) {
   String fileName = PicOps.getFolder() + args[1];
   Pic p = new Pic(fileName);
   System.out.println(p.getWidth());
  } else if (args[0].equals("getPixel")) {
   if (args.length < 4) {
    usage();
   } else {
    int row = Integer.parseInt(args[1]);
    int col = Integer.parseInt(args[2]);
    String fileName = PicOps.getFolder() + args[3];
    Pic p = new Pic(fileName);
    System.out.println(p.getPixel(row, col));
    }
  } else if (args[0].equals("setPixel")) {
   if (args.length < 5) {
    usage();
   } else {
    int row = Integer.parseInt(args[1]);
    int col = Integer.parseInt(args[2]);
    int value = Integer.parseInt(args[3]);
    String fileName = PicOps.getFolder() + args[4];
   Pic p = new Pic(fileName);
    p.setPixel(row, col, value);
    PicOps.display(p.getPixArray());
    }
  } else if (args[0].equals("getPixArray")) {
   String fileName = PicOps.getFolder() + args[1];
   Pic p = new Pic(fileName);
   PicOps.display(p.getPixArray());
  } else if (args[0].equals("mirror")) {
   if (args.length < 3) {
    usage();
  } else {
   String inFileName = PicOps.getFolder()+ args[1];
   String outFileName = PicOps.getFolder()+ args[2];
   Pic p = new Pic(inFileName);
   p.mirror();
   PicOps.display(p.getPixArray());
   outputTextFile(outFileName, p.getPixArray());
  }
 
 } else if (args[0].equals("shrink")) {
  if (args.length < 3) {
   usage();
  } else {
   String inFileName = PicOps.getFolder()+ args[1];
   String outFileName = PicOps.getFolder()+ args[2];
   Pic p = new Pic(inFileName);
   p.shrink();
   PicOps.display(p.getPixArray());
   outputTextFile(outFileName, p.getPixArray());
  }
 } else if (args[0].equals("rotate90")) {
 if (args.length < 3) {
   usage();
  } else {
   String inFileName = PicOps.getFolder()+ args[1];
   String outFileName = PicOps.getFolder() + args[2];
   Pic p = new Pic(inFileName);
   p.rotate90();
   PicOps.display(p.getPixArray());
   outputTextFile(outFileName, p.getPixArray());
  }
 } else if (args[0].equals("readInTextFile")) {
  String fileName = PicOps.getFolder() + args[1];
  PicOps.display(readInTextFile(fileName));
 } else {
  usage();
 }
 }  // end of main method 
}

