import java.awt.*; // use graphics

public class BuggleBoard extends Sprite{//draws a buggle board with a few buggles moving around on it
  //instance methods
  private Color c1;//Colors of the three buggles on the boards
  private Color c2;
  private Color c3;
  private int inity1;// initial y coord of the three buggles
  private int inity2;
  private int inity3;
  private int x1;//x coord of the three buggles
  private int x2;
  private int x3;
  private int y1;
  private int y2;
  private int y3;
  //constructor
  public BuggleBoard(Color col1, Color col2, Color col3,int x_1, int y_1, int x_2, int y_2, int x_3, int y_3){//creates three buggles in every other space on the buggle board
    c1 = col1;
    c2 = col2;
    c3 = col3;
    inity1 = y_1;
    inity2 = y_2;
    inity3 = y_3;
    x1 = x_1;
    x2 = x_2;
    x3 = x_3;
    y1 = y_1;
    y2 = y_2;
    y3 = y_3;
  }
  
  protected void drawState(Graphics g){
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
    
    g.setColor(c1);//draws the first buggle
    Polygon buggle1 = new Polygon();
    buggle1.addPoint(x1,y1);
    buggle1.addPoint(x1+50,y1);
    buggle1.addPoint(x1+25,y1-50);
    g.drawPolygon(buggle1);
    g.fillPolygon(buggle1);
    g.fillRect(x1,y1,50,(inity1-y1));//this buggle's "brush" is down
    
                    
    g.setColor(c2);//draws the second buggle
    Polygon buggle2 = new Polygon();
    buggle2.addPoint(x2,y2);
    buggle2.addPoint(x2+50,y2);
    buggle2.addPoint(x2+25,y2-50);
    g.drawPolygon(buggle2);
    g.fillPolygon(buggle2);
               
    g.setColor(c3);//draws the third buggle
    Polygon buggle3 = new Polygon();
    buggle3.addPoint(x3,y3);
    buggle3.addPoint(x3+50,y3);
    buggle3.addPoint(x3+25,y3-50);
    g.drawPolygon(buggle3);
    g.fillPolygon(buggle3);
    
    g.setColor(Color.blue);
    g.fillOval(70,170,50,50);
    g.setColor(Color.white);
    g.fillOval(85,185,20,20);
             
  }
  
  protected void updateState() {
    if(y1==70){
      ;//keeps the buggles for moving off the screen
    }else{//moves buggle forward
    y1 = y1-5;
    y2 = y2-5;
    y3 = y3-5;
    }}
  
  protected void resetState(){//sets the buggles back to the beginning 
    y1 = inity1;
    y2 = inity2;
    y3 = inity3;
  
  }}