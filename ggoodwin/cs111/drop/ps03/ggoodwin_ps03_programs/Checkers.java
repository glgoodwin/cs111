/* Gabrielle Goodwin
 * CS 111 PS 3
 * Task 3
 * 2/11/2010
 * */

public class Checkers
{
 public static void main (String[] args)
 {
   top();
   middle();
   bottom();
   
}
 public static void top(){ //creates the top few lines of the checkerboard
   System.out.println("+------------------------+");
   System.out.println("|   ***   ***   ***   ***|");
   System.out.println("|   ***   ***   ***   ***|");
   System.out.println("|   ***   ***   ***   ***|");
 }
 public static void bottom(){ //creates the bottom few lines of the checkerboard
   System.out.println("|***   ***   ***   ***   |");
   System.out.println("|***   ***   ***   ***   |");
   System.out.println("|***   ***   ***   ***   |");
   System.out.println("+------------------------+");
 }
 
 public static void middle(){ //creates the middle of the checkerboard, can be used repeatedly to make larger checkerboards
   System.out.println("|***   ***   ***   ***   |");
   System.out.println("|***   ***   ***   ***   |");
   System.out.println("|***   ***   ***   ***   |");
   System.out.println("|   ***   ***   ***   ***|");
   System.out.println("|   ***   ***   ***   ***|");
   System.out.println("|   ***   ***   ***   ***|");
 }
   
 
}