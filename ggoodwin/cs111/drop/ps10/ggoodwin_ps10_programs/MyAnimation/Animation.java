import java.awt.*; // use graphics
import java.util.*; // use Vector

// An Animation is a script which describes how Sprites are orchestrated
// to produce the animation.
// Please see the online Animation notes for the public contract to this class.
public class Animation {

	// Instance Variables
	
	// Specify Number Repeats
	public final static int FOREVER = -1;
	public final static int NO_MAX_FRAMES = Integer.MAX_VALUE;
	
	private Vector sprites, cues, cuesClone;
	
  private int fps, frameDelay, numberFrames, numberRepeats, frameNumber;
  
  private AnimationPlayer screen; // where the animation will be drawn
	private Image frame; // the image buffer for double-buffering
	private int frameWidth, frameHeight;
	private boolean resizeable, debugOn, initialized;
	private boolean hasBeenReset = false;
		
  public Animation () {
  	this.sprites = new Vector();
  	this.cues = new Vector();
    this.fps = 12;
    this.numberFrames = 60;
    this.numberRepeats = 0;
    this.frameNumber = 1;
 		this.frameWidth = 300;
		this.frameHeight = 200;
		this.resizeable = true;
		this.debugOn = false;
		this.initialized = false;
  }
  
  private void init () {
  		// System.out.println("Enter Animation.init(): frameWidth = " + frameWidth + "; frameHeight = " + frameHeight);
		this.initializeAnimation();
		this.setSpriteBounds();
		this.cuesClone = (Vector)cues.clone();
		this.checkForCues();
		this.initialized = true;
  		// System.out.println("Exit Animation.init(): frameWidth = " + frameWidth + "; frameHeight = " + frameHeight);
  }
  
  // [lyn and elena, 5/3/02] Adding to guarantee that every sprite receives 
  // a reset() message before an animation starts.
  public void resetIfNecessary () {
  	if (! hasBeenReset) {
  		this.reset();
  	}
  }
  
  public final boolean isInitialized () {
  	if (!initialized) {
  		init();
  	}
  	return initialized;
  }
  
  // methods that return information about the animation
  
  public String className () {
    return "Animation";
  }
  
  public String toString () {
		return className() + "[frame="+this.frameNumber
      + "; fps="+this.fps
      + "; numberFrames="+this.numberFrames
      + "; numberRepeats="+this.numberRepeats
      + "; frameSize=("+this.frameWidth+","+this.frameHeight+")"
      + "; resizeable="+this.resizeable
      + "; debugOn="+this.debugOn
			+ ";\n  sprites="+this.spritesToString()
      + ";\n  cues="+this.cuesToString()
			+ "]";
  }

	public String cuesToString () {
		Enumeration ce = cuesClone.elements();
		StringBuffer cueString = new StringBuffer();
		while (ce.hasMoreElements()) {
			cueString.append("\n    "+((Cue)ce.nextElement()).toString());
		}
		return cueString.toString();
	}
	
	public String spritesToString () {
		Enumeration se = sprites.elements();
		StringBuffer spriteString = new StringBuffer();
		while (se.hasMoreElements()) {
			Sprite s = (Sprite)se.nextElement();
			if (s.getStateNumber()==1) {
				spriteString.append("\n    "+s.toString());
			} else {
				spriteString.append("\n    "+s.getState());
			}
		}
		return spriteString.toString();		
	}
	
	public int getFps () {
		return this.fps;
	}
	
	public int getNumberFrames () {
		return this.numberFrames;
	}
	
	public int getNumberRepeats () {
		return this.numberRepeats;
	}

	public int getFrameNumber () {
		return this.frameNumber;
	}
	
	public Dimension getFrameSize () {
		return new Dimension(frameWidth, frameHeight);
	}
	
	public boolean isResizeable () {
		return this.resizeable;
	}
	
	public boolean isDebugOn () {
		return this.debugOn;
	}
	
	public boolean isAnimationEnd () {
		return frameNumber >= numberFrames;
	}
	
  // methods that set properties of the animation
  public void setFps (int newFps) {
  	this.fps = newFps;
  }
  
  public void setNumberFrames (int frames) {
  	this.numberFrames = frames;
  }
  
	public void setNumberRepeats (int repeats) {
		this.numberRepeats = repeats;
	}
	
	public boolean resize (int width, int height) {
		// System.out.println("width = " + width + "; height = " + height + "; resizeable = " + resizeable);
		if (resizeable) {
	  		this.frameWidth = width;
  			this.frameHeight = height;
  			frame = screen.createImage(this.frameWidth,this.frameHeight);
    		this.setSpriteBounds();
    		return true;	
   		 } else {
    		return false;
    	}
	}
		
  protected void setFrameSize (int width, int height) {
  	this.frameWidth = width;
  	this.frameHeight = height;
  	this.resizeable = false;
  }

	private void setSpriteBounds () {
		Enumeration se = sprites.elements();
		while (se.hasMoreElements()) {
			((Sprite)se.nextElement()).setBounds(frameWidth,frameHeight);
		}
	}
	
	public void setDebugOn () {
		this.debugOn = true;
	}
	
	public void setDebugOff () {
		this.debugOn = false;
	}
	
	public void setScreen (AnimationPlayer c) {
		this.screen = c;
	}
	
	public void setFrameNumber (int newFrameNumber) {
		this.reset();
		if (newFrameNumber<1) return;
		while (frameNumber<newFrameNumber) {
			this.prepareNextFrame();
		}
	}
	
	public Image getFrame () {
		if (debugOn) {
			System.out.println(this.toString());
		}
		if (frame==null) {
			this.frame = screen.createImage(this.frameWidth,this.frameHeight);	
		}
   		Graphics fg = frame.getGraphics();
    	fg.setColor(Color.white);
    	fg.fillRect(0,0,this.frameWidth,this.frameHeight);
		Enumeration se = sprites.elements();
		while (se.hasMoreElements()) {
			String debugMsg = ((Sprite)se.nextElement()).draw(fg);
			if (!debugMsg.equals("")) {
				System.out.println(frameNumber+"::"+debugMsg);
			}
		}
		fg.dispose();
		return frame;    	
	}
	
	public Image getNextFrame () {
		this.prepareNextFrame();
		return getFrame();
	}
	
	private void prepareNextFrame () {
		// if (numberFrames!=NO_MAX_FRAMES) {
		this.frameNumber++;
		this.checkForCues();
		Enumeration se = sprites.elements();
		while (se.hasMoreElements()) {
			((Sprite)se.nextElement()).update();
		}
	}
	
	private void checkForCues () {
		if (cuesClone.size()>0) {
			int i=0;
			while (i<cuesClone.size()) {
				if (((Cue)cuesClone.elementAt(i)).execute(frameNumber)) {
					cuesClone.removeElementAt(i);
				} else {
					i++;
				}
			}
		}		
	}
	
	public void reset () {
		this.frameNumber = 1;
		this.cuesClone = (Vector)cues.clone();
		Enumeration se = sprites.elements();
		while (se.hasMoreElements()) {
			((Sprite)se.nextElement()).reset();
		}
		this.checkForCues();
		hasBeenReset = true;
	}

	// methods for people creating Animations
		
	public void initializeAnimation () {
		// intended to be overridden by subclasses
	}

	public void addSprite (Sprite s) {
		sprites.addElement(s);
	}
	
  public void addSprite (Sprite s, String name) {
  	s.setName(name);
  	sprites.addElement(s);
  }
	
	public void setActive (Sprite s, int frameNumber) {
		addCue(s, frameNumber, Cue.SPRITE_ACTIVE);
	}
	
	public void setVisible (Sprite s, int frameNumber) {
		addCue(s, frameNumber, Cue.SPRITE_SHOW);
	}
	
	public void setActiveAndVisible (Sprite s, int frameNumber) {
		setActive(s, frameNumber);
		setVisible(s, frameNumber);
	}
	
	public void setInactive (Sprite s, int frameNumber) {
		addCue(s, frameNumber, Cue.SPRITE_INACTIVE);
	}
	
	public void setHidden (Sprite s, int frameNumber) {
		addCue(s, frameNumber, Cue.SPRITE_HIDE);
	}
	
	public void setInactiveAndHidden (Sprite s, int frameNumber) {
		setInactive(s, frameNumber);
		setHidden(s, frameNumber);
	}
	
	public void setActiveAndVisibleRange (Sprite s, int startFrameNumber, int endFrameNumber) {
		setActiveAndVisible(s, startFrameNumber);
		setInactiveAndHidden(s, endFrameNumber+1);
	}
	
	public void setInactiveAndHiddenRange (Sprite s, int startFrameNumber, int endFrameNumber) {
		setInactiveAndHidden(s, startFrameNumber);
		setActiveAndVisible(s, endFrameNumber+1);
	}
	
	public void setDebugOn (Sprite s, int frameNumber) {
		addCue(s, frameNumber, Cue.SPRITE_DEBUG_ON);
	}
	
	public void setDebugOff (Sprite s, int frameNumber) {
		addCue(s, frameNumber, Cue.SPRITE_DEBUG_OFF);
	}
	
	public void setDebugRange (Sprite s, int startFrameNumber, int endFrameNumber) {
		setDebugOn(s, startFrameNumber);
		setDebugOff(s, endFrameNumber+1);
	}
	
	private void addCue (Sprite s, int frameNumber, int action) {
		cues.addElement(new Cue(s, frameNumber, action));
	}

}
