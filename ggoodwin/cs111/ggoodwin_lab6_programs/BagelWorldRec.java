/* FILE NAME: BagelWorldRec.java
 * AUTHOR: CS111 Staff
 * DATE: 
 * COMMENTS: Defines some recursive methods
 * on Buggle World, for the purposes of the first lab on recursion.
 * MODIFICATION HISTORY: First version of the file contains
 * bagelLine() and bagelRect(), written for the purpose of
 * the first lecture on Recursion.
 * Notice that bagelLine() can be useful for -and is used in- 
 * several of the other methods too.
 * 
 * 
*/
import java.awt.*;
public class BagelWorldRec extends BuggleWorld {

  public static void main (String[] args) {
    runAsApplication(new BagelWorldRec(), "BagelWorldRec"); 
  }
  
  public void run() {
    //Add your testing code here
    BagelBuggleRec betty = new BagelBuggleRec();
    betty.rectRug(5);
    //betty.brushUp();
    //betty.bagelRect(4,5);
    //betty.brushDown();

  }
  
  
  //set the size of the grid to the dimensions you need.
  public void setup() {
    setDimensions(20,20);
  }
  
  
}

class BagelBuggleRec extends Buggle {
//Add more methods here

     
 //***************************************************************
//Draws a rectangle, as you saw in lecture
  public void bagelRect(int w, int h) {
    if (h == 0) {  // base case: nothing to do
    } else {   // general, recursive case
      bagelLine(w);         // draw bottom line
      
      left();               // move to the
      forward();            // next row
      right();
      
      bagelRect(w, h - 1);  // draw subrectangle
      
      left();               // undo state changes
      backward();
      right();
    }
  }

//***************************************************************
  
  //bagelLine()
  //Written for the purposes of the first lecture on Recursion
  //It produces a line of n bagels, forward from the buggle's
  //position.
  //The state of the buggle does not change after the method's
  //invocation.
  //Interesting side-effect:
  //The cell at distance n+1 is visited during the method execution!
  public void bagelLine(int n) {
    if (n == 0) return; //base case: do nothing
    //general case:
    dropBagel();
    forward();
    bagelLine(n - 1);
    backward();
  }
  public void bagelLine2(int n){
       if(n==0){
            ;} else{
    dropBagel();
    forward();
    bagelLine2(n - 1);
    
  }
  }  
  public void rect(int n){
       bagelLine2(n);
       forward();
       backward();
       left();
       bagelLine2(n);
       forward();
       backward();     
       left();
       bagelLine2(n);
       forward();
       backward();
       left();
       bagelLine2(n);
  }
  public void throwBagel(int dist) {
       if( dist == 0){ // base case
            dropBagel();
       }else{// recursive case
            forward();
            throwBagel(dist-1);
            backward();
            
}
}
  public void skip(int n){
 
       if(n-1 == 0){
       dropBagel();
       }
       else {
         dropBagel();
         forward(2);
         skip(n-1);
         System.out.println("Skip");
       backward(2);
       }
      }
  public void triangle(int n){
       brushUp();
       if(n==0){
           setPosition(new Location (1,1));
       }else{
            bagelLine(n);
            left();
            forward();
            right();
            triangle(n-1);
       }
}
  public void rectRug(int n){
       if(n==0){
            setPosition(new Location(1,1));
                        }else{
                  forward(2);
                  left();
                  forward(2);
                  right();
                 rect(n);
  
       
}
  }
}

