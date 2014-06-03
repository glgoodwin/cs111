import java.awt.*;

class Pulsar extends Sprite
{ 
    private int x; // x position of pulsar center - constant
    private int y; // y position of pulsar center - constant
    private int radius; // current radius of pulsar - changes with each update
    private int minRadius; // minimum radius of pulsar - constant
    private int maxRadius; // minimum radius of pulsar - constant
    private int dRadius; // amount by which to change raidus at each step.
    // The magnitude of this variable is constant, 
    // but the sign changes depending on whether the 
    // pulsar is growing (positive) or shrinking (negative)
    private Color color; // The color of the pulsar
        
    public Pulsar (int x, int y, int minRadius, int maxRadius, int dRadius, Color color)
    {
        this.x = x;
        this.y = y;
        this.radius = minRadius;
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        this.dRadius = dRadius;
        this.color = color;
    }

    public void drawState (Graphics g)
    {
        g.setColor(color);
        g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
    }
        
    public void updateState()
    {
        radius = radius + dRadius;
        if (radius > maxRadius) {
            radius = maxRadius;
            dRadius = - dRadius;
        } else if (radius < minRadius) {
            radius = minRadius;
            dRadius = - dRadius;        
        }               
    }
        
    public void resetState()
    {
        this.radius = minRadius;
    }
        
    // For debugging:
        
    public String className()
    {
        return "Pulsar";
    }
        
    public String toString()
    {
        return className() + "["
            + "x=" + x
            + "; y=" + y
            + "; radius=" + radius
            + "; minRadius=" + minRadius
            + "; maxRadius=" + maxRadius
            + "; dRadius=" + dRadius    
            + "; color=" + color        
            + "]";
    }
}
