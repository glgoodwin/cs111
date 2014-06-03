import java.awt.*;

/* cs111 Lab1
 * Write CS in buggleworld
 * Gabrielle Goodwin
 * 1.27.2010
 */

public class Cs extends BuggleWorld {
     public void setup() {
          setDimensions(13,13); // Creates a 13x13 buggleworld grid
     }
     
     public void run() {
          // all the code in here is executed when the user clicks the run button
          // carl draws a "c"
          Buggle carl= new Buggle();
          carl.setColor(Color.blue);
          carl.brushUp();
          carl.forward();
          carl.left();
          carl.forward();
          carl.brushDown();
          carl.forward(3);
          carl.brushUp();
          carl.right();
          carl.forward();
          carl.brushDown();
          carl.forward(3);
          carl.brushUp();
          carl.right();
          carl.forward();
          carl.brushDown();
          carl.forward();
          carl.brushUp();
          carl.forward();
          carl.brushDown();
          carl.forward();
          carl.brushUp();
          carl.right();
          carl.forward();
          carl.brushDown();
          carl.forward(3);
          carl.brushUp();
          carl.setPosition(new Location(7,2));
          carl.left();
          carl.left();
          
          // buggle sue draws the "s"
          
          Buggle sue= new Buggle();
          sue.brushUp();
          sue.forward(11);
          sue.left();
          sue.forward(4);
          sue.brushDown();
          sue.dropBagel();
          sue.left();
          sue.forward(3);
          sue.left();
          sue.forward(2);
          sue.left();
          sue.forward(3);
          sue.right();
          sue.forward(2);
          sue.right();
          sue.forward(3);
          sue.dropBagel();
          sue.forward();
          
               
        
          
          
     }
     
     public static void main (String[] args) {
          runAsApplication(new Cs(), "Cs"); // the new Cs() creates a new Cs object
                                            // and this must match the name of the class
                                            // and the name of the java file
                                            // the Cs in quotes is the title of the object
          
     }
}