import java.awt.*;

/*
 * FILE NAME: Hi.java
 * AUTHOR: Sohie
 * DATE: Sept 1 2007
 * COMMENTS: CS111 Lab 1 -- Task 1 -- 
 * Draws the word "Hi" in Buggle world.
 *
 * MODIFICATION HISTORY:
 * 
 * */

public class Hi extends BuggleWorld {

  public void setup() {
   setDimensions(9,9); //sets the dimensions of the grid
  }

  public void run () {
    
    // ben draws a "H"
    Buggle ben = new Buggle();
    ben.left();
    ben.forward(4);
    ben.backward(2);
    ben.right();
    ben.forward(2);
    ben.left();
    ben.forward(2);
    ben.backward(4);
    ben.right();
    ben.forward();
    
    // barb draws an "i"
    Buggle barb = new Buggle();
    barb.brushUp();
    barb.forward(5);
    barb.brushDown();
    barb.left();
    barb.setColor(Color.blue);
    barb.forward(3);
    barb.brushUp();
    barb.forward();
    barb.dropBagel(); // barb dots her "I"
    barb.backward(4);
    
  }
  
    //------------------------------------------------------------------------------
  // The following main method is needed to run the applet as an application

  public static void main (String[] args) {
    runAsApplication(new Hi(), "Hi"); 
  }
  
}

