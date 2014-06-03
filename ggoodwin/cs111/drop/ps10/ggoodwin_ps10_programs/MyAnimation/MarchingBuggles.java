import java.awt.*; // use graphics

public class MarchingBuggles extends Sprite{
  //instance methods
  private int x;//x and y coords of the 4 buggles
  private int y;
  private int x1;
  private int y1;
  private int x2;
  private int y2;
  private int x3;
  private int y3;
  private int size;//size, same ofr all buggles
  private int initx;//allows reset
  private int inity;
  private Color color;//color same for all buggles
  
    public MarchingBuggles(Color c, int xx, int yy, int howBig){
    x = xx;
    y = yy;
    x1 = x+5;
    y1 = y+50;
    x2 = x+50;
    y2 = y;
    x3= x2+5;
    y3 = y1;
    size = howBig;
    initx = xx;
    inity = yy;
    color = c;
  }
  public void drawState(Graphics g){
    g.setColor(Color.black);
    g.drawRect(20,20,300,300);
    g.fillRect(20,150,300,100);
    
    g.setColor(Color.yellow);
    g.drawLine(20,200,320,200);
    //buggle 1
    g.setColor(color);
    Polygon armyBuggle1 = new Polygon();
    armyBuggle1.addPoint(x,y);
    armyBuggle1.addPoint(x+size,y+(size/2));
    armyBuggle1.addPoint(x,y+size);
    g.drawPolygon(armyBuggle1);
    g.fillPolygon(armyBuggle1);
    
    g.setColor(Color.black);
    g.drawString("Army",x+1,y+(size/2));
    //buggle 2
    g.setColor(color);
    Polygon armyBuggle2 = new Polygon();
    armyBuggle2.addPoint(x1,y1);
    armyBuggle2.addPoint(x1+size,y1+(size/2));
    armyBuggle2.addPoint(x1,y1+size);
    g.drawPolygon(armyBuggle2);
    g.fillPolygon(armyBuggle2);
    
    g.setColor(Color.black);
    g.drawString("Army",x1+1,y1+(size/2));
    //buggle 3
    g.setColor(color);
    Polygon armyBuggle3 = new Polygon();
    armyBuggle3.addPoint(x2,y2);
    armyBuggle3.addPoint(x2+size,y2+(size/2));
    armyBuggle3.addPoint(x2,y2+size);
    g.drawPolygon(armyBuggle3);
    g.fillPolygon(armyBuggle3);
    
    g.setColor(Color.black);
    g.drawString("Army",x2+1,y2+(size/2));
    //buggle 4
    g.setColor(color);
    Polygon armyBuggle4 = new Polygon();
    armyBuggle4.addPoint(x3,y3);
    armyBuggle4.addPoint(x3+size,y3+(size/2));
    armyBuggle4.addPoint(x3,y3+size);
    g.drawPolygon(armyBuggle4);
    g.fillPolygon(armyBuggle4);
    
    g.setColor(Color.black);
    g.drawString("Army",x3+1,y3+(size/2));
    
    
  }
  
  public void updateState(){
    if(x+size <=300){
    x= x+1;
    if(y == inity){
      y = y-2;
    }else{
      y = inity;}}

    if(x1+size <=300){
    x1= x1+1;
    if(y1 == inity+50){
      y1 = y1-2;
    }else{
      y1 = inity+50;}}
  
  if(x2+size <=300){
    x2= x2+1;
    if(y2 == inity){
      y2 = y2-2;
    }else{
      y2 = inity;}}
  
  if(x3+size <=300){
    x3= x3+1;
    if(y3 == inity+50){
      y3 = y3-2;
    }else{
      y3 = inity+50;}}
  }
    
  
  public void resetState(){
    y = inity;
    x = initx;
    y1 = inity+50;
    x1 = initx+5;
    x2 = initx+50;
    y2 = inity;
    x3 = initx+55;
    y3 = inity+50; 
  }
}
  
  