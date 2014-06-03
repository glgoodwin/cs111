import java.awt.*;
/*
 * FILE NAME: PuppyWorld.java
 * AUTHOR: CS111 staff
 * DATE: Feb4 2009
 * COMMENTS: CS111 Lab 2 
 *
 * MODIFICATION HISTORY: Based on a similar program
 * used in lectures
 * 
 * */
public class PuppyWorld extends BuggleWorld {
  
  public void setup() {
    //sets the dimensions of the grid
    setDimensions(9,9); 
  }
  
  public void run() {
    PuppyBuggle spot = new PuppyBuggle();
    spot.buildDogHouse();
  }
  
  // The following main method is needed to run the applet as an application
  public static void main (String[] args) {
    runAsApplication(new PuppyWorld(), "PuppyWorld"); 
  }
} //end of class PuppyWorld
//------------------------------------------------------------------------------

// PuppyBuggle defines a special kind of Buggle, that knows how to
// buildDogHouse
class PuppyBuggle extends Buggle {
  public void buildDogHouse() {
    this.forward(2);
    this.left();
    this.forward(2);
    this.left();
    this.forward(2);
    this.left();
    this.forward(2);
    this.left();
  }
}