import java.io.*;
import java.util.*;
public class Checkerboard{
     //instance Variables
     private int height; //always make instance variables private 
     private int width;
     private String[][] board; // allows different things in different spots: 2d string array
     
     // methods 
     //creates a 10x10 Checkerboard
     public Checkerboard (String symbol){//put same symbol in all the squares
          //constructor should initialize instance variables
          height = 10;
          width =  10;   
          board =  new String[10][10];//2D array, 10 rows, 10 coluumns 
          //use a loop to put the symbol in each spot in the array
          for(int i= 0; i< 10; i++){//loop through all the rows
               for(int j=0;j< 10; j++){//loop through all the coluumns 
                    if((((i%2)==0)&&((j%2)==0))||((((i%2)!=0)&&((j%2)!=0)))){
                         board[i][j]=symbol;
                    }else{
                         board[i][j]=" ";
                    }}}}
     public Checkerboard(String symbol, int size){
          height = size;
          width = size;
          board = new String[height][width];
          for(int i= 0; i< height; i++){//loop through all the rows
               for(int j=0;j< width; j++){//loop through all the coluumns 
                    if((((i%2)==0)&&((j%2)==0))||((((i%2)!=0)&&((j%2)!=0)))){
                         board[i][j]=symbol;
                    }else{
                         board[i][j]=" ";
                    }}}}
     public int getLength(){
          return height;
}
     public int getWidth(){
          return width;
     }
     public void displayBoard(){
           for(int i= 0; i<height ; i++){
               
                for(int j=0;j< width; j++){
                    System.out.print(board[i][j]);
                    }System.out.println("");
     }
     }
     public void drawBackwardDiagonal(String symbol){
          for(int i= 0; i<height; i++){
               for(int j=0;j< width; j++){
                    if(i==j){
                         board[i][j] = symbol;
                    }else{
                         board[i][j]=board[i][j];
                    }}}}
     public void drawForwardDiagonal(String symbol){
          for(int j=0;j< width; j++){
                    for(int i= 0; i<height; i++){
                    if(j+i==width-1){
                         board[i][j] = symbol;
                    }else{
                         board[i][j]=board[i][j];
                    }}}}
     public void doubleWidth(){//need to finish 
           for(int i= 0; i<height ; i++){
                for(int j=0;j< 2*width; j++){
                    System.out.print(board[i][j]);
                    System.out.print(board[i][j]);
                    }System.out.println("");
           }
     }
     public void overlay(Checkerboard board2){//need to finish
          for(int i= 0; i<height ; i++){
                for(int j=0;j< width; j++){
                     if((i==i)&&(j==j)){
                          board2[i][j]=board[i][j];
                     }else{
                          board[i][j]=board[i][j];
                     }
                }
          }}
                           
     

              
               
 public static void main(String args[]){
                    Checkerboard myboard = new Checkerboard("#");
                    Checkerboard miniBoard = new Checkerboard("-",4);
                    myboard.overlay(miniBoard);
                    myboard.displayBoard();
 }



}