public class DeadEndWorld extends MazeWorld
{
    public void setup()
    {
        setDimensions(15, 15);
    }

    public void run ()
    {
        DeadEndBuggle deanna = new DeadEndBuggle();
        deanna.tick1024();
    }

    public static void main (String[] args)
    {
        runAsApplication(new DeadEndWorld(), "DeadEndWorld"); 
    }

}

