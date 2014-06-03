import java.awt.*;

/*
 * Gabrielle Goodwin
 * CS111 Ps1
 * Task 1
 * 
 */

public class Writing extends BuggleWorld {

  public void setup() {
   setDimensions(21,7); //sets the dimensions of the grid
 
  }
  
  public void run()  {
    //Buggle fred draws "P.S.1"
    Buggle fred= new Buggle();
    fred.setColor(Color.blue);
    fred.brushUp();
    fred.forward(); //begin Drawing P
    fred.left();
    fred.brushDown();
    fred.forward(6);
    fred.right();
    fred.forward(5);
    fred.brushUp();
    fred.right();
    fred.forward();
    fred.brushDown();
    fred.forward(2);
    fred.brushUp();
    fred.right();
    fred.forward();
    fred.brushDown();
    fred.forward(4); //finish P
    fred.brushUp();
    fred.backward(6);
    fred.left();
    fred.forward(3);
    fred.dropBagel(); //adds first period
    fred.backward();
    fred.left();
    fred.forward(2);
    fred.setColor(Color.magenta);
   
    fred.brushDown(); //begin drawing S
    fred.right();
    fred.forward();
    fred.left();
    fred.forward(5);
    fred.left();
    fred.forward(3);
    fred.left();
    fred.forward(5);
    fred.right();
    fred.forward(3);
    fred.right();
    fred.forward(5);
    fred.right();
    fred.forward(2); //finish S
    fred.brushUp();
    fred.forward(4);
    fred.left();
    fred.forward(1);
    fred.dropBagel(); //adds second period
    fred.forward(2);
    fred.setColor(Color.yellow);
   
    fred.brushDown();//begin drawing 1
    fred.left(); 
    fred.forward(6);
    fred.right();
    fred.forward();//finish drawing 1
    fred.brushUp();
    fred.forward();
    fred.setColor(Color.black);
    
    fred.brushDown(); //begin drawing exclamation point
    fred.right();
    fred.forward(4);
    fred.brushUp();
    fred.forward(2); //finish drawing
    
  
  }
  
    public static void main (String[] args) {
          runAsApplication(new Writing(), "Writing");
          
    }
}