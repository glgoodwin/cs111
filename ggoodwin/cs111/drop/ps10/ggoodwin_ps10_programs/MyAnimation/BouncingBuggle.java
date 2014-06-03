import java.awt.*; // use graphics

public class BouncingBuggle extends Sprite{//draws a buggle that bounces up and down
  //instance variables
  private int x;//x coord of buggle
  private int y;//y coord of buggle
  private int size;//size of buggle
  private int inity;//allows reset of state
  private Color color;//sets color
  
    public BouncingBuggle(Color c, int xx, int yy, int howBig){
    x = xx;
    y = yy;
    size = howBig;
    color = c;
    inity = yy;

  }
    public void drawState(Graphics g){
    g.setColor(color);
    Polygon bouncingBuggle = new Polygon();
    bouncingBuggle.addPoint(x,y);
    bouncingBuggle.addPoint(x+size,y+(size/2));
    bouncingBuggle.addPoint(x,y+size);
    g.drawPolygon(bouncingBuggle);
    g.fillPolygon(bouncingBuggle);
    }
     public void updateState(){
    if(y == inity){
      y = y-5;
    }else{
      y = inity;}}
     
     public void resetState(){
       y = inity;
     }
}