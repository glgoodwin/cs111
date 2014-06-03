import java.awt.*;
/* Gabrielle Goodwin
 * CS 111 Lab 2
 * Feb. 3, 2010
 * Checks2.java
 * */
public class Checks2 extends BuggleWorld
{
      public void setup() {
    //sets the dimensions of the grid
    setDimensions(9,9); 
  }
      
      public void run(){
           
     CheckerBuggle bob= new CheckerBuggle();
     bob.drawChecker(Color.blue);
     
      }
      
     // you'll need to write these methods;
     //setup(), main(), run()
     
 // closes Checks1 class

// The following main method is needed to run the applet as an application
  public static void main (String[] args) { runAsApplication(new Checks2(), "Checks2");
  }

class CheckerBuggle extends Buggle
{
     
     public void drawChecker(Color c) {
    this.setColor(c);      
    this.brushUp();
    this.forward();
    this.brushDown();
    this.forward();
    this.brushUp();
    this.left();
    this.forward();
    this.brushDown();
    this.backward();
    this.right();
    this.brushUp();
    this.forward();
    this.brushDown();
    this.forward();
    this.left();  }
     
     // this is where you define a new method
     
     }  
  }
 // closes CheckerBuggle class