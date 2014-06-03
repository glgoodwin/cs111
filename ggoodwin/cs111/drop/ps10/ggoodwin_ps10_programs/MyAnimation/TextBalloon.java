import java.awt.*; // use graphics

public class TextBalloon extends Sprite{//creates a text balloon  
  //instance methods
  private Color c1;// text color
  private int x;
  private int width;
  private int y;
  private int height;
  private int inity;
  private String string; // the string of text that will appear in the balloon
  
  
  public TextBalloon(Color c_1, String string1, int xx, int yy, int balwid, int balhei){
    c1 = c_1;
    string = string1;
    x = xx;
    y = yy;
    inity = yy ;
    width = balwid;
    height = balhei;
  }
  
  public void drawState(Graphics g){
    g.setColor(Color.black);
    g.drawOval(x,y,width,height);
    g.setColor(c1);
    g.drawString(string,x+5,y+(height/2));

  }
  
  public void updateState(){
    if(y == inity){
      y = y-2;
    }else{
      y= y+2;
    }}
      
  
  public void resetState(){
    y = inity;
  }
  
}
  
  