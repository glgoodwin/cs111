/*Gabrielle Goodwin
 * 4/29/12010
 * Pset 10 task 1
 */ 

import java.awt.*;

class Spinner extends Sprite 
{
    // flesh out this class

    // Declare your instance variables here: 
  private int initRadius;
  private int maxRadius;
  private int xCoord;
  private int yCoord;
  private int delta_radius;
  private Color colorOne;
  private Color colorTwo;
  private Color curColor;
  
  public Spinner(int x, int y, int radius, int dRadius, Color color1, Color color2)
  { xCoord = x;
    yCoord = y;
    initRadius = radius;
    delta_radius = dRadius;
    colorOne = color1;
    colorTwo = color2;
    maxRadius = radius;
    curColor = color1;
    
  }
  public void resetState()
  { initRadius = maxRadius;
    curColor = colorOne;
    
  }
  public void updateState()
  { initRadius = initRadius - delta_radius;//makes the spinner 
    System.out.println("update state " + initRadius);
    if(initRadius > maxRadius && curColor.equals(colorOne)){
      curColor = colorTwo;
      initRadius = maxRadius;
      delta_radius = -delta_radius;
      
    }else if(initRadius > maxRadius && curColor.equals(colorTwo)){ 
      curColor = colorOne;
      initRadius = maxRadius;
      delta_radius = -delta_radius;
      
    } else if(initRadius <= 0 && curColor.equals(colorOne)){
      curColor= colorTwo;
      initRadius = 0;
      delta_radius = -delta_radius;
      
    }else if(initRadius <= 0 && curColor.equals(colorTwo)){
      curColor= colorOne;
      initRadius = 0;
      delta_radius = -delta_radius;
      }}
  
      
      
    
  
  public void drawState(Graphics g)
  { //draws the spinners
    g.setColor(curColor);
    g.fillOval(xCoord-initRadius,yCoord-maxRadius,2*initRadius,2*maxRadius);
    
    //draws the black line that "holds up" the spinners
    g.setColor(Color.black);
    g.drawLine(xCoord,0,xCoord,yCoord-maxRadius);
        
    }
 

}

