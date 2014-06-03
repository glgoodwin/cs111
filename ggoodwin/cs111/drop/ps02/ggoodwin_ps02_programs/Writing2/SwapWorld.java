import java.awt.*;

public class SwapWorld extends BuggleWorld 
{ 
  public void setup ()
        {
}
        public void run () 
        {
                SwapBuggle bg1 = new SwapBuggle();
                SwapBuggle bg2 = new SwapBuggle();
                SwapBuggle bg3 = new SwapBuggle();

                Location lcn1  = new Location(6, 3);
                Location lcn2  = new Location(4, 5);

                bg1.setPosition(lcn1);
                bg2.setPosition(lcn2);
                bg3.setPosition(new Location(lcn1.x - lcn2.x, 
                                             lcn1.y + lcn2.y));
                bg2.setColor(Color.blue);
                bg1.setColor(Color.green);
                bg2.left();
                bg3.right();
                bg2.swap(bg3);
                bg3.swap(bg1);
        }
         public static void main (String [] args)
        {
                runAsApplication(new SwapWorld(), "SwapWorld");
        }
}

class SwapBuggle extends Buggle 
{
        public void swap (Buggle bg1) 
        {
                Location lcn1 = this.getPosition();
                Location lcn2 = bg1.getPosition();
                Color c1      = this.getColor();
                Color c2      = bg1.getColor();

                this.setPosition(lcn2);
                bg1.setPosition(lcn1);
                this.setColor(c2);
                bg1.setColor(c1);
        }
}