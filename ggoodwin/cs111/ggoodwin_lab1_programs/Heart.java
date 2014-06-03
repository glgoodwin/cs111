import java.awt.*;

/* cs111 Lab1
 * Write CS in buggleworld
 * Gabrielle Goodwin
 * 1.27.2010
 */

public class Heart extends BuggleWorld {
     public void setup() {
          setDimensions(13,13); // Creates a 13x13 buggleworld grid
     }
     
     public void run() {
          // all the code in here is executed when the user clicks the run button
          // this program will draw a heart in buggleworld
          Buggle red= new Buggle();
          red.brushUp();
          red.left();
          red.forward(7);
          red.right();
          red.forward(3);
          red.brushDown();
          red.forward(2);
          red.brushUp();
          red.forward(3);
          red.brushDown();
          red.forward(2);
          red.right();
          red.brushUp();
          red.forward(); //second line of heart
          red.brushDown();
          red.right();
          red.forward();
          red.brushUp();
          red.forward(2);
          red.brushDown();     
          red.forward();
          red.brushUp();
          red.forward();
          red.brushDown();
          red.forward();
          red.brushUp();
          red.forward(2);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.left();
          red.forward();//3rd line of heart
          red.brushDown();
          red.left();
          red.forward();
          red.brushUp();
          red.forward(4);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.forward(4);
          red.brushDown();
          red.forward();
          red.right();
          red.brushUp();
          red.forward();//4th line of heart
          red.right();
          red.forward(2);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.forward(7);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.left();
          red.forward(); //5th line of heart
          red.left();
          red.forward(2);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.forward(5);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.right();
          red.forward(); //6th line of heart
          red.right();
          red.forward(2);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.forward(3);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.left();
          red.forward(); //7th line of heart 
          red.left();
          red.forward(2);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.forward(1);
          red.brushDown();
          red.forward();
          red.brushUp();
          red.right();
          red.forward();//bottom of heart
          red.right();
          red.forward(2);
          red.brushDown();
          red.right();
          red.forward();
          red.brushUp();
          red.backward();
               
               
          
          
          
              
               
          
     }
      
          
          
          public static void main (String[] args) {
          runAsApplication(new Heart(), "Heart"); // the new Heart() creates a new Cs object
                                            // and this must match the name of the class
                                            // and the name of the java file
                                            // the Cs in quotes is the title of the object
          
          }
     }