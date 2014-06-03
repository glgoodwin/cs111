import java.awt.*;

class BouncingBall extends Sprite 
{ 
    private int radius; // Radius of the ball - constant
    private int initX; // Initial x position of ball center - constant
    private int initY; // Initial y position of ball center - constant
    private int x; // Current x position of ball center - updated on each step
    private int y; // Current y position of ball center - updated on each step
    private int dx; // Amount to change x position on each step. The magnitude
    // of dx does not change, but the sign does. 
    private int dy; // Amount to change y position on each step. The magnitude
    // of dy does not change, but the sign does. 
    private Color color; // Color of the ball
        
    public BouncingBall(int ix, int iy, int radius, int dx, int dy, Color color)
    {
        this.radius = radius;
        this.initX = ix;
        this.initY = iy;
        this.dx = dx;
        this.dy = dy;
        this.color = color;
        this.resetState();
    }

    public void resetState ()
    {
        this.x = this.initX;
        this.y = this.initY;
    }
        
    public void drawState (Graphics g)
    {  
        g.setColor(color);
        g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
    }
        
    public void updateState()
    {
        x = x + dx;
        y = y + dy;
        if (x < radius) { 
            x = radius; 
            dx = -dx; 
        }
        if (x + radius >= width) { 
            x = width - radius; 
            dx = -dx; 
        }
        if (y < radius) { 
            y = radius; 
            dy = -dy;
        }
        if (y + radius >= height) { 
            y = height - radius; 
            dy = -dy; 
        }
    }
        
    // For debugging:
        
    public String className()
    {
        return "BouncingBall";
    }
    public String toString()
    {
        return className() + "["
            + "radius=" + radius
            + "; initX=" + initX
            + "; initY=" + initY
            + "; x=" + x
            + "; y=" + y
            + "; dx=" + dx
            + "; dy=" + dy
            + "; color=" + color
            + "]";
    }
}
