/* Gabrielle Goodwin
 * 4/12/10
 * task 1
 */ 

import java.awt.*;       // Import Abstract Window Toolkit
import java.applet.*;    // Import Applet stuff
import javax.swing.*;    // Import Swing stuff

public class StepWorld extends TurtleWorld
{
 
    String parameterNames [] = {"n", "blockSize"};
    String parameterFields [] = {"20", "5"};
    ParameterFrame params;
    ButtonGroup decomposition = new ButtonGroup();
    JRadioButton rowDecomposition = new JRadioButton("StepFor_1", true);
    JRadioButton rectangleDecomposition = new JRadioButton("StepFor_2");

    //-----------------------------------------------------------------------
    // main() required to run the applet as an application
    public static void main (String[] args)
    {
 runAsApplication(new StepWorld(), "StepWorld"); 
    }
    //-----------------------------------------------------------------------

    public void setup()
    {
 params = new ParameterFrame("Step Parameters",
        TurtleWorld.frameSize, 0,
        250, 150,
        parameterNames, parameterFields);
 decomposition.add(rowDecomposition);
 decomposition.add(rectangleDecomposition);
 params.add(rowDecomposition);
 params.add(rectangleDecomposition);
 params.show();
    }
  
    public void run()
    {
 StepMaker stacy = new StepMaker();
 if (rowDecomposition.isSelected()) {   // Row decomposition
     stacy.stepsFor_1(params.getIntParam("n"), 
        params.getIntParam("blockSize"));
     stacy.lt(225);
     stacy.fd(100);
 } else {                               // Rectangle decomposition
     stacy.stepsFor_2(params.getIntParam("n"), 
        params.getIntParam("blockSize"));
     stacy.lt(225);
     stacy.fd(100);
 }
    }
}

class StepMaker extends Turtle
{
  
    // Uses a for loop to draw a set of steps consisting of n rows of blocks.
    // The first row should contain n blocks, the second row should contain
    // n - 1 blocks, the third row should contain n - 2 blocks, and so on.
    // The final row should contain 1 block.
    // Position and heading invariant.
    public void stepsFor_1(int n, int blockSize)
    {for(int rows =n; rows>0; rows= rows-1){
      rowOfBlocks(rows,blockSize);
      lt(90);
      fd(blockSize);
      rt(90);
    } 
    rt(90);
    fd(blockSize*n-1);
    lt(90);
    }
   
    
    
    
  
    // Use a for loop to draw a row consisting of n blocks, each with 
    // size blockSize.
    // Position and heading invariant.
    public void rowOfBlocks(int numBlocks, int blockSize)
    {for(int n = numBlocks; n>0; n=n-1){
      block(blockSize);
      fd(blockSize);
    }
    bd(blockSize*(numBlocks));
    
    }
  
    // Use a for loop to draw a square block with the given block size.
    // The turtle should return to its initial position and heading.
    public void block (int blockSize)
    { for(int n=4;n>0;n=n-1){
      fd(blockSize);
      lt(90);
    }
  
    }
 
    // Draws a set of steps with a base of n blocks, each of which is a 
    // block of size blockSize, and return the turtle to its initial
    // position and heading.  The problem is decomposed into drawing
    // n superimposed rectangles as described in the assignment
    // description.  A for loop should be used to draw the rectangles.
    public void stepsFor_2 (int n, int blockSize)
    { for(int num = n; num >0; num = num-1){
      int height = (n-(num-1))*blockSize;
      int length = num* blockSize;
      fd(length);
      lt(90);
      fd(height);
      lt(90);
      fd(length);
      lt(90);
      fd(height);
      lt(90);
    }

    
    }
}
