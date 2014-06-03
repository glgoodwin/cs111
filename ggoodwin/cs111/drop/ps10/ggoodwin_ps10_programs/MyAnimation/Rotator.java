import java.awt.*;

// Class of rotating arc sprites
class Rotator extends Sprite
{ 
        
    private int x; // x position of filled arc center - constant
    private int y; // y position of filled arc center - constant
    private int radius; // radius of filled arc - constant
    private int angle; // current angle of filled arc - changes at each update
    private int arcAngle; // arc angle of filled arc - constant
    private int dAngle; // amount by which angle should change on each update - constant
    private Color color; // color of filled arc - constant.
        
    public Rotator(int x, int y, int radius, int arcAngle, int dAngle, Color color)
    {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.angle = 0;
        this.arcAngle = arcAngle;
        this.dAngle = dAngle;
        this.color = color;
    }

    public void drawState(Graphics g)
    {
        g.setColor(color);
        g.fillArc(x - radius, y - radius, 2*radius, 2*radius, angle, arcAngle);
    }
   
    public void updateState()
    {
        angle = angle + dAngle;
    }
        
    public void resetState()
    {
        angle = 0;
    }
        
    // For debugging:
        
    public String className()
    {
        return "Rotator";
    }
        
    public String toString()
    {
        return className() + "["
            + "x=" + x
            + "; y=" + y
            + "; radius=" + radius
            + "; angle=" + angle
            + "; arcAngle=" + arcAngle
            + "; dAngle=" + dAngle      
            + "; color=" + color
            + "]";
    }
}

