// ----------------------------------------------------------------------
// Gabrielle Goodwin
// ----------------------------------------------------------------------
// This is the skeleton for CS111 PS1 Task 2. 
// Flesh out the run() method to draw the Buggle Olympics symbol.

import java.awt.*;

public class Rings extends BuggleWorld
{
        public void setup ()
        {
                setDimensions(31, 31);   //set up 31 x 31 grid
        } 
  
        public void run () {

                // ------------------------------------------------------------
                // Create and initialize the five buggle Olympians. 
                // Do not modify this code!

                Buggle rex = new Buggle();
                rex.setPosition(new Location(8, 11));

                Buggle cy = new Buggle();
                cy.setColor(Color.cyan);
                cy.setPosition(new Location(7, 15));

                Buggle maggie = new Buggle();
                maggie.setColor(Color.magenta);
                maggie.setPosition(new Location(17, 7));
                maggie.setHeading(Direction.NORTH);

                Buggle blithe = new Buggle();
                blithe.setColor(Color.blue);
                blithe.setPosition(new Location(11, 17));

                Buggle yelena = new Buggle();
                yelena.setColor(Color.yellow);
                yelena.setPosition(new Location(9, 23));
                yelena.setHeading(Direction.SOUTH);

                // ------------------------------------------------------------
                // Put your code here:
                rex.forward(7);//rex begins red ring
                rex.left();
                rex.forward(5); //rex stops to let blue by
                
                blithe.forward(5); //blithe starts blue ring
                
                rex.forward(6); //rex goes over blue ring
                
                yelena.forward(14); //yelena begins yellow ring
                yelena. left();
                yelena.forward(11); //yelena stops to let cyan go
                
                cy.forward(14); // cy goes over yellow and red
                cy.right();
                cy.forward(7); // cy stops to let magenta go
                
                maggie.forward(14); //maggie goes over yellow and cyan
                maggie.right();
                maggie.forward(7); //maggie stops to let blue go
                
                yelena.forward(3); //yelena goes over cyan
                yelena.left();
                yelena.forward(14); // yelena goes over magenta
                yelena.left();
                yelena.forward(11); //yelena stops to let blue go
                
                blithe.forward(9);//blithe goes over magenta and yellow
                blithe.left();
                blithe.forward(14);
                blithe.left();
                blithe.forward(14);
                blithe.left();
                blithe.forward(5);//blithe stops to let rex go
                
                rex.forward(3);//rex goes over yellow
                rex.left();
                rex.forward(14);
                rex.left();
                rex.forward(14);
                rex.left();
                rex.forward(5); //rex stops to let cyan go
                
                blithe.forward(9);
                blithe.left();// blithe back at start
                
                yelena.forward(3);
                yelena.left(); //yelena back at start
                
                maggie.forward(7);//maggie goes over blue
                maggie.right();
                maggie.forward(14);
                maggie.right();
                maggie.forward(14);
                maggie.right(); //maggie back at start
                
                cy.forward(7); //cy goes over magenta
                cy.right();
                cy.forward(14);
                cy.right();
                cy.forward(14);
                cy.right(); // cy back at start
                
                rex.forward(2);//rex goes over cyan, rings complete
                
          
          
        

    
        } // end of the run() method

        //---------------------------------------------------------------------
        // The following main() method allows this applet to run as an application

        public static void main (String[] args)
        {
                runAsApplication(new Rings(), "Rings"); 
        }

  
} // class Rings
