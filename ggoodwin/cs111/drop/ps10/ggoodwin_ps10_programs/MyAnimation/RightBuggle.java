import java.awt.*; // use graphics

// This Sprite is a Right-facing Buggle which moves along the x-axis.
public class RightBuggle extends Sprite 
{
    // Instance Variables
  
    // variables to keep track of initial state of Buggle
    private Color c;     // color of Buggle
    private int size;   // size of Buggle
    private int startX; // initial x-coordinate of Buggle
    private int y;      // y-coordinate of Buggle (never changes)
    private int dx;     // distance Buggle moves in each frame
    // dx>0 moves Buggle to the right; dx<0 moves Buggle to the left (backwards)
  
    // variable to keep track of current state of Buggle
    private int currentX; // current x-coordinate of Buggle
  
    // Constructors
    public RightBuggle () 
    {
        this.c = Color.red;
        this.size = 40;
        this.startX = 0;
        this.y = 0;
        this.dx = 5;
        this.resetState();
    }
  
    public RightBuggle (Color c, int size, int startX, int startY, int dx) 
    {
        this.c = c;
        this.size = size;
        this.startX = startX;
        this.y = startY;
        this.dx = dx;
        this.resetState();
    }
  
    // Required Sprite methods
    protected void drawState (Graphics g) 
    {
        Polygon p = new Polygon();
        p.addPoint(currentX, y);
        p.addPoint(currentX+size, y+size/2);
        p.addPoint(currentX, y+size);
        p.addPoint(currentX, y);
        g.setColor(c);
        g.fillPolygon(p);
        g.setColor(Color.black);
        g.drawPolygon(p);
    }
  
    protected void updateState () 
    {
        currentX = currentX + dx;  

    }
  
    protected void resetState () 
    {
        currentX = startX;
    }
  
    // For Debugging
    public String className () 
    {
        return "RightBuggle";
    }
  
    public String toString () 
    {
        return className() + ":" + getName()
            + "[color="+c
            + "; size="+size
            + "; startX="+startX
            + "; startY="+y
            + "; dx="+dx
            + "]";
    }
  
    public String getState () 
    {
        return className() + ":" + getName()
            + "[currentX="+currentX
            + "]";
    }
}
  
