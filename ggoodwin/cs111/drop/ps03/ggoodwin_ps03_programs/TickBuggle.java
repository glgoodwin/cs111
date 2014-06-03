public class TickBuggle extends Buggle
{
    // A hook that allows subclass instances to perform some action
    // on every clock tick. 
    // The default action is to do nothing. 
    public void tick()
    {
                
    }
        
    // The following methods send a predetermined number of tick() messages to
    // a TickBuggle.  When we study recursion and iteration, we will see ways 
    // for buggles to perform an action until some condition is true.  For now,
    // we can only ask buggles to perform an action a predetermined number o
    // f times. 
        
    public void tick2()
    {
        this.tick();
        this.tick();
    }
        
    public void tick4()
    {
        this.tick2();
        this.tick2();
    }
        
    public void tick8()
    {
        this.tick4();
        this.tick4();
    }
        
    public void tick16()
    {
        this.tick8();
        this.tick8();
    }
        
    public void tick32()
    {
        this.tick16();
        this.tick16();
    }
        
    public void tick64()
    {
        this.tick32();
        this.tick32();
    }
        
    public void tick128()
    {
        this.tick64();
        this.tick64();
    }
        
    public void tick256()
    {
        this.tick128();
        this.tick128();
    }
        
    public void tick512()
    {
        this.tick256();
        this.tick256();
    }
        
    public void tick1024()
    {
        this.tick512();
        this.tick512();
    }
}
