import java.awt.*;
public class MovingUpText extends Sprite{
  //instance methods
  private Color c1;//color of text
  private int inity;//allows reset
  private int x;//x and y coord
  private int y;
  private String string;//text that is displayed
  
  //constructor
  public MovingUpText(Color c_1, int xx, int yy, String string1){
    c1 = c_1;
    x = xx;
    inity = yy;
    y = yy;
    string = string1;
  }
  
  protected void drawState(Graphics g){
    g.setColor(c1);
    g.drawString(string,x,y);
    
  }
  
  protected void resetState(){
    y = inity;
    
  }
  
  protected void updateState(){
    y = y-5;
  }
}