/*Gabrielle Goodwin
 * 4/15/2010
 * Task 2
 * PyramidWorld
 */ 

// Drawing Pyramids with turtles.

public class PyramidWorld extends TurtleWorld 
{
     String parameterNames []  = {"levels", "side"};
     String parameterFields [] = {"5", "20"};
     String resultNames []     = {"number"};
     ParameterFrame params;
 
     //-----------------------------------------------------------------------
     // This main() method is needed to run the applet as an application
     public static void main (String[] args)
     {
          runAsApplication(new PyramidWorld(), "PyramidWorld"); 
     }
     //-----------------------------------------------------------------------

     public void setup()
     {
          params = new ParameterFrame("Pyramid Parameters",
                                      TurtleWorld.frameSize, 0,
                                      200, 150,
                                      parameterNames, resultNames,
          parameterFields);
     }

     public void run() 
     {
          PyramidTurtle pam = new PyramidTurtle();
          int levels        = params.getIntParam("levels");
          double side       = (double)params.getIntParam("side");
          
          // Move to good initial position using simple geometry
          pam.pu();
          pam.bd((levels * side) / 2.0);
          pam.lt(90);
          pam.bd((levels * side) / 2.0);
          pam.rt(90);
          pam.pd();

          params.setIntResult("number", pam.completePyramid(levels, side));
     }
}


// Add your PyramidTurtle class definition below.
 class PyramidTurtle extends Turtle
{ 
  
  

  public void drawTriangle(int levels, double side){// invariant method to draw an equilateral triangle of a given length "side"
    if(levels == 0){                                // will be useful when making completePryamid method.
      ;
    }else{
    fd(side);                           
    lt(120);
    fd(side);
    lt(120);
    fd(side);
    lt(120);//finishes first triangle
    fd(side);//sets it in postition for next triangle
    drawTriangle(levels-1, side);//draws remaining triangles
    bd(side);//makes recursive
    }
  }
  

public int completePyramid(int levels, double side){
  if(levels==0){
    return 0;
  }else{ 
    drawTriangle(levels,side);//draws first level of pyramid
    lt(60);//puts turtle on next level
    fd(side);
    rt(60);
    int triangles = levels + completePyramid(levels-1, side);
    rt(60);
    fd(side);
    lt(60);//makes the method iterative
    return triangles;//returns the number of triangles
 
}
}
}