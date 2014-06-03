/* Gabrielle Goodwin
 * 3/7/10
 * CS 111 PS 5
 * Task 1
 * */
public class IntergerRange{
  /* Given two integers, start and stop, the sum method should return
 * the sum of all integers that are greater than or equal to start and that 
 * are less than or equal to stop. If start is greater than stop, then 
 * the method should return 0.
 */
 public static void main(String[] args) {
   IntergerRange i = new IntergerRange();//creates an interger object to run the sum method
   int answer;
   
    answer = i.sum(-3,7);
    System.out.println("the sum of the integers is " + answer + ".");
  }
  public int sum(int start, int stop){ // defines the protocol for determining the sum
    if(start > stop){
      return 0;
    }else{
     return start + (sum(start + 1, stop));
    }
    
  }
  
  }
  

      