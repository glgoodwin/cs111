/*Gabrielle Goodwin
 * 04/18/2010
 * Take Home Exam 2
 * Task 1
 */ 

import java.awt.*;
//import javax.swing.*;

public class TriangleWorld extends BuggleWorld
{
  
    //-------------------------------------------------------------------------
    // The main() method is needed to run the applet as an application
  
    public static void main (String[] args)
    {
 runAsApplication(new TriangleWorld(), "TriangleWorld"); 
    }
  
    // Instance variables
    String parameterNames [] = {"triangleSize", "buggleColor"};
    String resultNames [] = {"droppedBagels"};
    protected ParameterFrame params;
  
    public void setup ()
    {
 // Set up parameter window
 params = new ParameterFrame("TriangleWorld", 300, 200,
        parameterNames, resultNames);
 params.setIntParam("triangleSize", 16);
 params.setColorParam("buggleColor", Color.red);
 params.show();
    }
  
    public void reset ()
    {
 int triangleSize = params.getIntParam("triangleSize");
 int gridSize = 2 * triangleSize;
 setDimensions(gridSize, gridSize);
 params.setIntResult("droppedBagels", 0);
 super.reset();
    }
  
    public void run ()
    {
 TriangleBuggle tristen = new TriangleBuggle();
 tristen.brushUp();
 tristen.setColor(params.getColorParam("buggleColor"));
 int side = params.getIntParam("triangleSize");
 params.setIntResult("droppedBagels", tristen.drawAllTriangles(side));

    }
  
}
// class TriangleWorld
class TriangleBuggle extends Buggle{// new class inherits all the buggle methods
  public int dropBagelLine(int length){//drops a line of bagels of given length
    if(length != 0){
      dropBagel();
      forward();
      int bagels = 1 + dropBagelLine(length-1);//recursive method
      backward();
      return bagels;//gives back number of bagels dropped
    }return 0;
  }
  public int drawTriangle(int n){// draws a triangle with given height and length of n
    if(n == 1){
      dropBagel();
      return 1;
    }else if(n !=0){
      int bagels = dropBagelLine(n);
      forward();
      right();
      forward();
      left();
      int droppedBagels = bagels + drawTriangle(n-1);//recursive method
      left();
      forward();
      right();
      backward();//iterative method       
      return droppedBagels;
  }return 0;
  }
  public void checkerLine(int n){ //colors a line with checker pattern
    if(n!=1 && n!=0){
      forward();
      setCellColor(getColor());
      forward();
      checkerLine(n-2);
      backward(2);
    }
  }
  public void checkerTriangle(int n){// creates checker pattern for a whole triangle
    if(n == 1){
       ;} else
    if(n!=0){
      checkerLine(n);
      forward();
      right();
      forward();
      left();
      checkerTriangle(n-1);//recursive method
      right();
      backward();
      left();
      backward();//Iterative Method
    }
  }
  public int drawTriangleRow(int n){// creates a row of triangles in varying heights
    if(n!=0){
    left();
    forward(n-1);
    right();
    Location l = getPosition();
    int bagels = drawTriangle(n);
    checkerTriangle(n);
    forward(n-1);
    right();
    forward(n-1);
    left();
    forward();
    int droppedBagels= bagels + drawTriangleRow(n/2);
    setPosition(l);
    left();
    forward();
    right();
    return droppedBagels;//gives back number of bagels used in row
    }return 0;
  }
 public int drawAllTriangles(int n){// draws final pattern
    if(n!=0){
    int bagels = drawTriangleRow(n);
    int droppedBagels = bagels + drawAllTriangles(n/2);//makes method recursive
    return droppedBagels;
    } setPosition(new Location(1,1));//makes method iterative
     return 0;
 }
}
  

  


  

  
  


