import java.awt.*; // graphics stuff

// This is the AnimationPlayer GUI component.
// Include this in custom AnimationPlayer implementations.
// The actual playing of the animation takes place in a separate thread so the
// component will still be able to respond while an animation is playing.

// The following methods are available for configuring properties of the player:
//   setFrameNumberDisplay (FrameNumberDisplay gui) -- default null
//   setScreenColor (Color c) -- default black
//   setPlayOnPaint (boolean b) -- default false
//   setAnimation (Animation a) -- which animation the player will play

// The following methods are available for controlling the animation:
// reset(), play(), stop(), nextFrame(), gotoFrameNumber(int n), clearScreen()

// The following methods are available for getting information about this component:
//    isAnimationOn(), getScreenColor(), getScreenSize(), getFrameSize()
// Sizes are returned as instances of the Dimension class.

// It's recommended that you study the two implemented AnimationPlayers
// (i.e. SimpleAnimationPlayer and AnimationShowcase) before implementing
// one on your own.

public class AnimationPlayer extends Canvas implements Runnable {

	private int screenWidth, screenHeight, frameWidth, frameHeight;
 	private boolean animationOn, playOnPaint;
	private Animation a;
	private FrameNumberDisplay gui;
	private Thread player;
	private Color screenColor;
	
  public AnimationPlayer () {
  	this.screenWidth = 10;
  	this.screenHeight = 10;
    this.animationOn = false;
    this.playOnPaint = false;
    this.screenColor = Color.black;
  }
  
  // methods that return information about the animation player
  
  public String className () {
    return "AnimationPlayer";
  }
  
  public String toString () {
    return className() + "[screenSize=("+screenWidth+","+screenHeight+")"
      + "; frameSize=("+frameWidth+","+frameHeight+")"
      + "; isAnimationOn="+this.animationOn
      + "; playOnPaint="+this.playOnPaint
      + "; screenColor="+this.screenColor
      + ";\n  " // + this.a.toString()
      + "]";
  }

  public boolean isAnimationOn () {
    return animationOn;
  }
  
  public Color getScreenColor () {
  	return screenColor;
  }
  
  public Dimension getScreenSize () {
  	return new Dimension(screenWidth,screenHeight);
  }
  
  public Dimension getFrameSize () {
  	return new Dimension(frameWidth,frameHeight);
  }
  
	// methods that allow users to configure the animation player

	// if GUI implements the FrameNumberDisplay interface, let AnimationPlayer know
	public void setFrameNumberDisplay (FrameNumberDisplay gui) {
		this.gui = gui;
	}

	public void setScreenColor (Color c) {
		this.screenColor = c;
	}

	public void setPlayOnPaint (boolean b) {
		this.playOnPaint = b;
	}
			
	// set which animation the player should play
	public void setAnimation (Animation a) {
		// pop out current animation
		if (animationOn) {
			stop();
			resetAction();
		}
		if (this.a!=null) {
			this.a.setScreen(null); // detach from animation step 1
			if (isValid()) {
				clearScreen(); // wipe our screen clean
			}
		}
		if (a!=null && a.isInitialized()) {
			this.a = a; // detach from animation step 2
			this.a.setScreen(this);
			setFrameSize();
			if (isValid()) {
			    // Fix added by lyn in Spring'03
				resetIfNecessary();
				drawFrame();
			}
		} else {
			this.a = null; // detach from animation step 2
		}
	}

	
	// methods that allow users to control the animation
  public void reset () {
  	if (a!=null) {
	    if (animationOn) {
  	    stop();
    	}
    	resetAction();
  	}
  }	
  
  public void play () {
    if (a!=null && !animationOn) {
      this.animationOn = true;
      player = new Thread(this);
      player.start();
    }
  }

  public void stop () {
		if (player!=null && player.isAlive()) {
			player.stop();
		}
		player = null;
		if (animationOn) animationOn=false;
	}  

  public void nextFrame () {
  	if (a!=null) {
	  	if (animationOn) return;
 			this.drawNextFrame();
  	}
  }

  public void gotoFrameNumber (int newFrameNumber) {
		if (a!=null) {
	    if (animationOn) return;
  	  this.animationOn = true;
    	a.setFrameNumber(newFrameNumber);
    	this.drawFrame();
    	this.animationOn = false;
  	}
  }

	public void clearScreen () {
		Graphics g = this.getGraphics();
		g.setColor(screenColor);
		g.fillRect(0,0,this.screenWidth,this.screenHeight);
		g.dispose();
	}
	      
  // methods that draw and update the animation
  protected void drawFrame (Graphics g, boolean needUpdate) {
    Image offscreen;
	  if (needUpdate) {
  		offscreen = a.getNextFrame();
    } else {
  		offscreen = a.getFrame();
  	}
    g.drawImage(offscreen,Math.max(0,(screenWidth-frameWidth)/2),
     						Math.max(0,(screenHeight-frameHeight)/2),this);
    g.dispose();
    if (gui != null) {
      gui.setFrameNumberDisplay(a.getFrameNumber());
    }
  }
  
  protected void drawFrame () {
  	this.drawFrame(this.getGraphics(), false);
  }

  protected void drawNextFrame () {
    this.drawFrame(this.getGraphics(), true);    
  }
	
	// auxiliary methods

	protected void setFrameSize () {
	  	// [lyn, 5/1/02] Elaine's code used g.getClipRect here,
  		// but this behaves differently in CodeWarrior than in Semantic Cafe.
  		// So I use bounds instead.
		Rectangle bounds = this.getBounds();
		// System.out.println("Set frame size(); width = " + bounds.width + "; height = " + bounds.height);
  		screenWidth = bounds.width;
  		screenHeight = bounds.height;
  		if (a != null) {	
			a.resize(screenWidth,screenHeight);
			Dimension d = a.getFrameSize();
	  		frameWidth = d.width;
	  		frameHeight = d.height;
		}
			  		/* System.out.println("setFrameSize: " 
		                   + "screenWidth = " + screenWidth 
		                   + "; screenHeight = " + screenHeight
		                   + "; frameWidth = " + frameWidth
		                   + "; frameHeight = " + frameHeight
		                   + "; bounds = " + bounds); */

	}
		
  protected void resetAction () {
    a.reset();
    this.drawFrame();
  }
  
 protected void resetIfNecessary () {
    a.resetIfNecessary();
    this.drawFrame();
  }
  
	// separate thread for running the animation
  public void run () {
    int numberRepeats = a.getNumberRepeats();
    int frameDelay = 1000/a.getFps();
    if (a.isAnimationEnd()) {
			this.resetAction();
    }
    long time = System.currentTimeMillis();
    while (animationOn) {
     if (a.isAnimationEnd()) {
	  	if ((numberRepeats>0) || (numberRepeats==Animation.FOREVER)) {
	  		time = System.currentTimeMillis();
				resetAction();
				if (numberRepeats>0) numberRepeats--;
				} else { // stop animation
					animationOn = false;
				}
			} else {
				this.drawNextFrame();
			}
	  	time+=frameDelay;
	  	delay(Math.max(0,(int)(time-System.currentTimeMillis())));
    }
  }
	
  public void paint (Graphics g) {
  	if (a!=null) { // check for resized windows
  		// [lyn, 5/1/02] Moved some code from here into setFrameSize.
			setFrameSize();
			this.clearScreen(); // sets the screen color
			if (playOnPaint) {
				this.play();
			} else if (!animationOn) {
				this.drawFrame(g, false);
			} // otherwise, animation running, so let it run
  	}
  }

  // method which does the frame delay
  public static void delay (int n) {
    try {
      Thread.sleep(n);
    } catch (InterruptedException e) {}
  }

}
