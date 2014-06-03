/* FILE NAME: HurdleWorld.java
 * AUTHOR: CS111 Staff
 * WHAT: For the purpose of Lab: Hurdler Exercise
 *
 *
*/
import java.awt.*;

public class HurdleWorld extends BuggleWorld {

  Randomizer rand = new Randomizer(5);
  
   public void run () { 
   Hurdler happy = new Hurdler();
   happy.tick16();
 }  
 
 // The following main method is needed to run the applet as an application
 public static void main (String[] args) {
   runAsApplication(new HurdleWorld(), "HurdleWorld"); 
 }
 
  //You don't need to understand the following HighHurdleWorld methods.
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
    addVerticalWall(x, 0);
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
          if(isAtEnd()){
               //do nothing
          }else if(isFacingWall()){
               left();
               forward();
               right();
               forward();
               right();
               forward();
               left();
          }else{ forward();}
     }
          public boolean isAtEnd() {
               if (isFacingWall()) {
                    brushUp();
                    left();
                    forward();
                    right();
                    if(isFacingWall()){
                         return true;
                    }else{
                              right();
                              backward();
                              left();
                              brushDown();
                              return false;}
               }
          
          }
}
                
           
               
           
                  
               
              
            
        

               
          
          
          
 


     



