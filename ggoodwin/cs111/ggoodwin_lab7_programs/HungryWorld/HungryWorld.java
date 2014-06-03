/* FILE NAME: HungryWorld.java
 * AUTHOR: CS111 Staff
 *
 * DATE: 
 *
 * COMMENTS: Creates a buggle World populated with bagels, in a random fashion, so
 * that no bagel is placed in the middle column.
 * Places a buggle in the middle of row one.
 * Sends the buggle out to eat the bagels, according to this rule:
 * In each row, the buggle will eat the bagels to its left or the right, only.
 * Specifically, it will eat the bagels to the right, if there are more bagels
 * to its left than its right. And it will eat the bagels to its left, 
 * if there are more bagels to its left than its right.
 * Each cell that used to contain an eaten bagel, is colored red.
 *
 * MODIFICATION HISTORY:
*/

import java.awt.*;

public class HungryWorld extends RandomBagelWorld {

 public void run () {
  int side = params.getIntParam("side");
  
  //get a HungryBuggle and initialize it
  HungryBuggle harry  = new HungryBuggle();
  harry.setPosition(new  Location(side/2 + 1,1));
  harry.setHeading(Direction.NORTH);
  harry.brushUp();
  
  //send it off to eat the bagels
 harry.eatOneRow();
 System.out.println(harry.countBagels());
 
 }  
 
  //------------------------------------------------------------------------------
  // The following main method is needed to run the applet as an application

  public static void main (String[] args) {
    runAsApplication(new HungryWorld(), "HungryWorld"); 
  }

}
/*****************************************************************************/

//Add you HungryBuggle class definition here
class HungryBuggle extends Buggle {
     
     public int countBagels(){
          if(!isFacingWall()){
               forward();
               if(isOverBagel()){     
                    int bagels = 1 + countBagels();
                    backward();
                    return bagels;
               }else{
                    int bagels = countBagels();
                    backward();
                    return bagels;
               }}else{
                    return 0;
               }
          }
          
public void eatOneRow(){
     right();
     int right = countBagels();
     left();
     left();
     int left = countBagels();
     right();
     if(left>right){
          left();
        forward();
        if(isOverBagel()){
        pickUpBagel();
        }}else{// working on eat one row, buggle recognizes when one row has more bagels than the other
             
          right();
          
     }}
}
          





          
     