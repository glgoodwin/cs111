public class FollowWorld extends BagelTrailWorld
{
    public void setup()
    {
        setDimensions(15, 15);
    }
  
    public void run ()
    {
        Follower folla = new Follower();
        folla.tick128(); // Would be nicer to stop when find last bagel, 
                         // assume a predetermined number of steps for now.
    }  

    public static void main (String[] args)
    {
        runAsApplication(new FollowWorld(), "FollowWorld"); 
    }
  
}
