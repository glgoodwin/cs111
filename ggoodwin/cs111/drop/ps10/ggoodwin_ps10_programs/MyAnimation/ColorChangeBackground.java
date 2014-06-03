import java.awt.*; // use graphics

// This Sprite fills the background with the given color.
public class ColorChangeBackground extends Sprite 
{
    // Instance Variables  
    private Color c1;//first color
    private Color c2;//second color
    private Color curColor;//allows color to change
  
    // Constructors
    public ColorChangeBackground(Color c_1, Color c_2) 
    { c1 = c_1;
      c2 = c_2;
      curColor = c1;
   
      
       
    }


    // Required Sprite methods
    protected void drawState (Graphics g) 
    {   g.setColor(Color.black);
        g.drawRect(20,20,300,300);
        g.setColor(curColor);
        g.fillRect(20,20,300,300);
    }
    
    protected void updateState () 
    { if(curColor.equals(c1)){
      curColor = c2;
    }else{
      curColor = c1;}}
    

    protected void resetState () 
    {curColor = c1;
    }}