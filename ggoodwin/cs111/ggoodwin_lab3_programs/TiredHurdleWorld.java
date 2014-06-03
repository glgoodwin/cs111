/* FILE NAME: TiredHurdleWorld.java
 * AUTHOR: CS111 Staff
 * WHEN: 
 * WHAT: For the purpose of Lab5, F07: 
 * Task 2, Exercise 3: TiredHurdler
 * MODIFICATION HISTORY: 
*/
import java.awt.*;

public class TiredHurdleWorld extends BuggleWorld {

  Randomizer rand = new Randomizer(5);
  
   public void run () { 
   TiredHurdler happy = new TiredHurdler(2); //can jump up to 2 hurdles
   //when done with programming, come back here to do more testings!
   
   happy.tick16();
 }  
 
 //---------------------
 // The following main method is needed to run the applet as an application
 
 public static void main (String[] args) {
   runAsApplication(new TiredHurdleWorld(), "TiredHurdleWorld"); 
 }
 
  //You don't need to understand the following HurdleWorld methods.
 public void reset() {
   super.reset();
   int hurdles = rand.intBetween(0,cols-1);
   placeHurdles(hurdles, cols); 
 }
 

 public void setup () {
   int side = rand.intBetween(5,16);
   setDimensions(side, side);
 }

  public void placeHurdles(int hurdles, int side) {
  if (hurdles > 0) {
   int x = rand.intBetween(1, side-1);
   if (isVerticalWallAt(x,0)) {
    //System.out.println("lose (" + x + ", " + y + ")");
    // We lose; try again!
    placeHurdles(hurdles, side);
   } else {
    // We win; place a bagel
    //System.out.println("win (" + x + ", " + y + ")");
     
     // Sohie messing around with varying hurdle heights
     // draw a hurdle of height 1, then flip a coin to see
     // if the hurdle should be a height 2
    addVerticalWall(x, 0);
    if (rand.intBetween(1,10) > 5) {
     addVerticalWall(x,1);
    }
    placeHurdles(hurdles - 1, side);
   }
  }
 }
 
}

//**************************************************************


class TiredHurdler extends Hurdler {
  private int hurdlesLeft; 
  // This is called an instance variable. It is the first time to see 
  // an instance variable. Such variables are used to keep state.
  // In this case, it will be used to keep track of how many more 
  // hurdles the buggle can still jump at any point.
  
  //TiredHurdlers are born with an extra piece of info: the max number of hurdles they can jump.
  //This is what the following constructor sets:
  public TiredHurdler(int maxHurdles) {
    this.hurdlesLeft = maxHurdles;//initially the buggle can jump maxHurdles
  }

  // What the buggle will do in a tick:
  public void tick(){
  // 1: If it has arrived at the end, it'll do nothing, "mission accomplishe"!
  if ((getPosition().x==10)&&(getPosition().y==1)){
               //do nothing, at end of grid)
          
  // 2: If it is facing a hurdle, then if it can still jump,
  }else if (isFacingWall()&& (hurdlesLeft !=0)){
               left();
               forward();
               right();    
               
               
               if (isFacingWall()){
                left();
               forward();
               right();
               forward();
               right();
               forward(2);
               left();
               }else{
              forward();
              right();
                forward();
                left();
               }
               

          
            
          }
  // it will jump over it. Otherwise, it will do nothing.
  // 3: If the road ahead is clear, it will move ahead.

} //end of TiredHurdler class

}

