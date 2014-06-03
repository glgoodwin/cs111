/* Gabrielle Goodwin
 * CS 111 PS 5
 * 3/7/10
 * Task 2
 * */
public class Triangle{

 public static void main(String[] args) {
   Triangle t = new Triangle();
  t.drawTriangle(6);
   
   
  
  }
 /* Prints out to the screen a left-justified 
 * right triangle of asterisks consisting of the specified
 * number of rows.
 */
 public void drawTriangle( int rows){
   if (rows > 0){
     asteriks(rows);
     System.out.println(' ');
     drawTriangle(rows - 1);
   }
 }
   public void asteriks(int n){
     if(n > 0){
     System.out.print('*');
     asteriks(n-1);
 }
   }}
    