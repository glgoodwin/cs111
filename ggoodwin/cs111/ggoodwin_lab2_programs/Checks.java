import java.awt.*;
/*
 * FILE NAME: Checks.java
 * AUTHOR: Sohie
 * DATE: Feb 2010
 * COMMENTS: CS111 Lab 2
 * Draws a simple Check Board in Buggle world.
 *
 * MODIFICATION HISTORY:
 * 
 * */

public class Checks extends BuggleWorld {
  
  public void run () {
    //Draws a little triangle of three colored squares
    Buggle betty = new Buggle();
    betty.brushUp();
    betty.forward();
    betty.brushDown();
    betty.forward();
    betty.brushUp();
    betty.left();
    betty.forward();
    betty.brushDown();
    betty.backward();
    betty.right();
    betty.brushUp();
    betty.forward();
    betty.brushDown();
    betty.forward();
    betty.left();
  } // end of run()
  
  // The following main method is needed to run the applet as an application
  public static void main (String[] args) {
    runAsApplication(new Checks(), "Checks Simple"); 
  }
} // class CheckSimple
