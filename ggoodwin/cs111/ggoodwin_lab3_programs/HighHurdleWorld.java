/* FILE NAME: HighHurdleWorld.java
 * AUTHOR: Sohie
 * WHEN: 
 * WHAT: For the purpose of Lab. 
 * Task : HighHurdler
 * MODIFICATION HISTORY: 
*/
import java.awt.*;

public class HighHurdleWorld extends BuggleWorld {

  Randomizer rand = new Randomizer(5);
  
   public void run () { 
   Hurdler happy = new Hurdler();
   happy.tick16();
 }  
 
 //---------------------
 // The following main method is needed to run the applet as an application
 
 public static void main (String[] args) {
   runAsApplication(new HighHurdleWorld(), "HighHurdleWorld"); 
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
class Hurdler extends TickBuggle {
 
  //What the buggle will do in a tick:
  //1: If it has arived at the end, it will do nothing, "mission accomplishe"!
  //2: If it is facing a hurdle, it will jump over it.
  //3: If the road ahead is clear, it will move ahead.
 
 
 public void tick(){
          if ((getPosition().x==10)&&(getPosition().y==1)){
               //do nothing, at end of grid)
          }else{ if (isFacingWall()) { 
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
               
          }else{
      
               forward();}



   
 
  
          }
 }
}


