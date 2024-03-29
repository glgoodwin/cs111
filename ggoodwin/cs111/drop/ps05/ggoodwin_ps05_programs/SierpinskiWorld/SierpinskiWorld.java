//Gabrielle Goodwin
//PS5 Task 4
//CS 111

import java.awt.*;       // Import Abstract Window Toolkit
import java.applet.*;    // Import Applet stuff

// Drawing Sierpinski gaskets with turtles.

public class SierpinskiWorld extends TurtleWorld 
{
     String parameterNames [] = {"levels", "side"};
     String parameterFields [] = {"5", "400"};
     ParameterFrame params;
 
     //-----------------------------------------------------------------------
     // This main() method is needed to run the applet as an application
     public static void main (String[] args)
     {
          runAsApplication(new SierpinskiWorld(), "SierpinskiWorld"); 
     }
     //-----------------------------------------------------------------------

     public void setup()
     {
          params = new ParameterFrame("Sierpinski Parameters",
                                      TurtleWorld.frameSize, 0,
                                      180, 105,
                                      parameterNames, parameterFields);
     }
     public void run() 
     {
          SierpinskiMaker sierpa = new SierpinskiMaker();
          // Move to good initial position using simple geometry
          double radius = params.getIntParam("side") / Math.sqrt(3);
          sierpa.pu();
          sierpa.lt(90);
          sierpa.bd(radius/4.0);
          sierpa.rt(60);
          sierpa.bd(radius);
          sierpa.rt(30);
          sierpa.pd();
          //sierpa.sierpinski(params.getIntParam("levels"), //these commands were not working with the interface, 
                                                     //all I could get was a really small triangles so I commented them 
             //(double) params.getIntParam("side"));  // and added a new command line. Changes to the parameters
         // sierpa.sierpinski(200,2);                   //have to be done here though.
      }


// Add your SierpinskiMaker class definition below.
public class SierpinskiMaker extends Turtle{

public void sierpinski(double side, double levels){
  {if(levels==1){ // makes just a plain triangle in the given size 
   
    fd(side);
    lt(120);
    fd(side);
    lt(120);
    fd(side);
    lt(120);
    
  }else if(levels>1){
    fd(side);
    lt(120);
    fd(side);
    lt(120);
    fd(side);
    lt(120);
    sierpinski(side/2,levels-1);
    lt(60);
    fd(side/2);
    rt(60);
   sierpinski(side/2,levels-1);
    fd(side/2);
    rt(120);
    fd(side/2);
    lt(120);
    sierpinski(side/2,levels-1);
    bd(side/2);
}
}}}
}

  
  
  
    
