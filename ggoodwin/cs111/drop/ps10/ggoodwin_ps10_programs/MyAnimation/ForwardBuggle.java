import java.awt.*;

class ForwardBuggle extends Sprite
  //instance variables
  private int v1;//the vertices of the polygon that will make up the buggle
  private int v2;
  private int v3;
  private int deltaVx; //how much each of the vertices will change by on the x axis
  private int deltaVy; //how much each of the verices will change by on the y axis
  private Color color; //the color of the buggle
  
  //constructor
  public ForwardBuggle(int p1, int p2, int p3, int deltax, int deltay, Color col){
    v1 = p1;
    v2 = p2;
    v3 = p3;
    deltaVx = deltax;
    deltaVy = deltay;
    color = col;
  }
  
  //instance methods
  
