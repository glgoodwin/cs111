import java.awt.*; // use graphics

// This Sprite fills the background with the given color.
public class ColorBackground extends Sprite
{
    private Color color;

    public ColorBackground()
    {
        this.color = Color.white;
    }

    public ColorBackground(Color c)
    {
        this.color = c;
    }

    // Required Sprite methods
    protected void drawState (Graphics g)
    {
        g.setColor(this.color);
        g.fillRect(0,0,this.width,this.height);
    }
    
    protected void updateState ()
    {
        // do nothing
    }

    protected void resetState ()
    {
        // do nothing
    }

    // For Debugging
    public String className ()
    {
        return "ColorBackground";
    }
  
    public String toString ()
    {
        return className() + ":" + getName() + "[color="+this.color+"]";
    }
}
