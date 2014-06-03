import java.awt.*;

public class Writing2 extends BuggleWorld
{
        public void setup ()
        {
                setDimensions(25, 25); 
        }

        // write the word "JAVA" around the perimeter of the grid
        public void run ()
        {
                // ellie will perform all writing. 
                LetterBuggle ellie = new LetterBuggle(); 

                // Write "JAVA" at the bottom of the picture
                ellie.writeName(Color.red);
                ellie.turnCorner1();
                ellie.writeName(Color.cyan);
                ellie.turnCorner2();
                ellie.writeName(Color.green);
                ellie.turnCorner2();
                ellie.writeName(Color.blue);
                ellie.forward(4);
                ellie.left();

                // Statements that write the other three occurrences of
                // "JAVA" and return ellie to her initial state go here.
    
        }

        // ------------------------------------------------------------------
        // Enables this applet to run as an application

        public static void main (String [] args)
        {
                runAsApplication(new Writing2(), "Writing2");
        }
}


class LetterBuggle extends Buggle
{

        // Write the word "JAVA", in the appropriately colored letters, 
        // by invoking methods with appropriate color parameters for 
        // writing the individual letters. 
        public void writeName (Color c)
        {

               this.brushUp();
               this.forward();
               this.brushDown();// Add code here to position this buggle correctly to 
                // start writing.
        
       
 
                this.writeJ(c);
                this.writeA(c);
                this.writeV(c);
                this.writeA(c);
               

                // Add statements here to write the "A V A" in the 
                // correct colors.
                
                

        }

        // Write the letter "J" in the given color and 
        // position buggle to write the next letter. 
        public void writeJ(Color c)
        {
        this.setColor(c); 
        this.left();
        this.forward();
        this.backward();
        this.right();
        this.forward(3);
        this.left();
        this.forward(4);
        this.backward(4);
        this.brushUp();
        this.right();
        this.forward(2);
        this.brushDown(); //ready to begin writing A
      
                // Statements to implement method go here.
          
        
  
        }
        public void writeA(Color c)
        {
        this.setColor(c);  
        this.left();
        this.forward(4);
        this.right();
        this.forward(3);
        this.right();
        this.forward(2);
        this.right();
        this.forward(3);
        this.backward(3);
        this.left();
        this.forward(2);
        this.left();
        this.forward();
        this.brushUp();
        this.forward();
        }
        
        public void writeV(Color c)
        {
        this.setColor(c);
        this.left();
        this.forward();
        this.brushDown();
        this.forward(3);
        this.right();
        this.forward();
        this.brushUp();
        this.forward(1);
        this.right();
        this.brushDown();
        this.forward(4);
        this.brushUp();
        this.right();
        this.forward();
        this.setCellColor(c);
        this.right();
        this.right();
        this.forward(3);
        this.brushDown();//ready to draw a
        }
        
        public void turnCorner1()
        {
         this.brushUp(); 
         this.forward(4);
         this.left();
         
        }
        public void turnCorner2()
        {  
        this.brushUp();
        this.forward(4);
        this.left();
        
        }
}
        // Below, define methods for writing the letters "A" and "V",
        // as well as any other methods you find helpful. 
                     


    
      