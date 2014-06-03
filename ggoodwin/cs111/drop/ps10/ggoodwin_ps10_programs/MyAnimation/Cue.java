// Cues store information about when Sprites should have
// certain properties turned on or off.
public class Cue 
{
    public final static int SPRITE_ACTIVE = 1;
    public final static int SPRITE_INACTIVE = 2;
    public final static int SPRITE_HIDE = 3;
    public final static int SPRITE_SHOW = 4;
    public final static int SPRITE_DEBUG_ON = 5;
    public final static int SPRITE_DEBUG_OFF = 6;
                
    private Sprite s;
    private int frameNumber, type;
        
    public Cue (Sprite s, int frameNumber, int id)
    {
        this.s = s;
        this.frameNumber = frameNumber;
        this.type = id;
    }

    public boolean execute (int currentFrameNumber)
    {
        if (frameNumber==currentFrameNumber) {
            return this.execute();
        } else {
            return false;
        }               
    }
                
    private boolean execute ()
    {
        switch (type) {
        case Cue.SPRITE_ACTIVE:
            s.setActive(true); break;
        case Cue.SPRITE_INACTIVE:
            s.setActive(false); break;
        case Cue.SPRITE_HIDE:
            s.setVisible(false); break;
        case Cue.SPRITE_SHOW:
            s.setVisible(true); break;
        case Cue.SPRITE_DEBUG_ON:
            s.setDebugMode(true); break;
        case Cue.SPRITE_DEBUG_OFF:
            s.setDebugMode(false); break;
        default:
            return false;
        }
        return true;
    }
        
    public String toString ()
    {
        return s.className()+" "+s.getName()+"; frame "+frameNumber+": "+cueTypeToString(type);
    }
        
    private String cueTypeToString (int id)
    {
        switch (type) {
        case Cue.SPRITE_ACTIVE:
            return "SPRITE_ACTIVE";
        case Cue.SPRITE_INACTIVE:
            return "SPRITE_INACTIVE";
        case Cue.SPRITE_HIDE:
            return "SPRITE_HIDE";
        case Cue.SPRITE_SHOW:
            return "SPRITE_SHOW";
        case Cue.SPRITE_DEBUG_ON:
            return "SPRITE_DEBUG_ON";
        case Cue.SPRITE_DEBUG_OFF:
            return "SPRITE_DEBUG_OFF";
        default:
            return "UNKNOWN_CUE_TYPE";
        }               
    }
}
