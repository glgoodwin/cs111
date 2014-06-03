import java.awt.*;// Use Graphics

public class BuggleGrid extends Sprite{//draws a buggle world grid, is inanimate... values already given based
  //on the needs for my presentation...just needed to draw a board and didnt know how else to do so
  //instance variables
  private int x;
  private int y;
  private int width;
  private int height;
  
  public BuggleGrid(int xx, int yy, int wid, int hei){
    x = xx;
    y = yy;
    width = wid;
    height = hei;
  }
  
  public void drawState(Graphics g){
      g.setColor(Color.black);//draws the buggle board
    g.drawLine(20,20,20,320);
    g.drawLine(70,20,70,320);
    g.drawLine(120,20,120,320);
    g.drawLine(170,20,170,320);
    g.drawLine(220,20,220,320);
    g.drawLine(270,20,270,320);
    g.drawLine(320,20,320,320);
    g.drawLine(20,20,320,20);
    g.drawLine(20,70,320,70);
    g.drawLine(20,120,320,120);
    g.drawLine(20,170,320,170);
    g.drawLine(20,220,320,220);
    g.drawLine(20,270,320,270);
    g.drawLine(20,320,320,320);
    
    g.setColor(Color.red);
    g.drawString("Waffles!",30,30);
    g.drawString("Pancakes!",75,75);
    g.drawString("Breakfast Burritos!",175,300);
    g.drawString("Cereal!",15,100);
    g.drawString("Eggs!",250,90);
    
  }
  
  public void resetState(){
    ;
  }
  
  public void updateState(){
    ;
  }
}
  
  
