import java.awt.*;

public class RugWorld extends BuggleWorld
{
        public void setup()
        {
                setDimensions(21, 21);
        }       

        public void run()
        {
                RugBuggle weaver = new RugBuggle();

                weaver.makeRug(Color.green, Color.yellow,
                               Color.cyan, Color.pink);
        }

        // -----------------------------------------------------------------
        // Method for running this applet as an application

        public static void main (String [] args)
        {
                runAsApplication(new RugWorld(), "RugWorld");
        }
}


class RugBuggle extends Buggle
{

        // Define makeRug() and all the rest of your methods here:
  
}
