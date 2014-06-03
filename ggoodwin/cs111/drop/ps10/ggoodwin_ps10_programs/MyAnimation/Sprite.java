import java.awt.*; // use graphics

// A Sprite is something which partakes in an animation.
// Please see the online Animation notes for the public contract to this class.
public abstract class Sprite {

	// Instance variables
	
	// Relevant for Sprite
	protected int width, height;
	private int stateNumber;
	private boolean debugMode;
	
	// Relevant for being cast in an animation
	private String name;
	private boolean active, visible;
		
	public Sprite () { // default constructor
		this.width=300;
		this.height=200;
		this.stateNumber = 1;
		this.debugMode = false;
		this.name = "";
		this.active = true;
		this.visible = true;
	}
	
	// String methods
	public String className () {
		return "Sprite";
	}
	
	public String toString () {
		return className() + ":" + getName()
									+ "[bounds=("+this.width+","+this.height+")"
									+ "; stateNumber="+this.stateNumber
									+ "; debugMode="+this.debugMode
									+ "; name="+this.name
									+ "; active="+this.active
									+ "; visible="+this.visible
									+ "]";
	}
	
	public String getState () {
		// Unless overridden, returns same string as toString()
		return toString();
	/*
		return className() + ":" + getName()
									+ "[stateNumber="+this.stateNumber
									+ "; debugMode="+this.debugMode
									+ "; active="+this.active
									+ "; visible="+this.visible
									+ "]";
	*/
	}
	
	// accessor methods
	
	public final Dimension getBounds () {
		return new Dimension (this.width, this.height);
	}
	
	public final int getStateNumber () {
		return this.stateNumber;
	}
	
	public final boolean isDebugMode () {
		return this.debugMode;
	}
		
	public final String getName () {
		return this.name;
	}
	
	public final boolean isActive () {
		return this.active;
	}
	
	public final boolean isVisible () {
		return this.visible;
	}
		
	// methods that set values
  public void setBounds (int width, int height) {
    this.width = width;
    this.height = height;
  }
  
  public void setBounds (Dimension d) {
    this.setBounds (d.width, d.height);
  }
  	
	public final void setDebugMode (boolean newDebugMode) {
		this.debugMode = newDebugMode;
	}
	
	public final void setName (String name) {
		this.name = name;
	}

	public final void setActive (boolean newActiveState) {
		this.active = newActiveState;
	}
	
	public final void setVisible (boolean newVisibleState) {
		this.visible = newVisibleState;
	}
		
	// Methods that draw and update the Sprite's state.
	// These methods should be overridden by subclasses.

	protected abstract void drawState (Graphics g);
		// Code to draw the Sprite.
		
	protected abstract void updateState ();
		// Code to update the Sprite's state.
	
	protected abstract void resetState ();
		// Code to set the Sprite back to its initial state.


	// Methods that draw and update the Sprite.
	public final String draw (Graphics g) {
		if (visible) {
			this.drawState(g);
		}
		String debugMsg="";
		if (debugMode) {
			if (stateNumber == 1) {
				debugMsg = this.toString();
			} else {
				debugMsg = this.getState();
			}
		}
		return debugMsg;
	}
	
	public final void update () {
		if (active) {
			if (stateNumber==Integer.MAX_VALUE) {
				stateNumber=1; // prevent overflow errors
			} else {
				this.stateNumber++;
			}
			this.updateState();
		}
	}

	public final void reset () {
		this.stateNumber = 1;
		this.debugMode = false;
		this.active = true;
		this.visible = true;
		resetState();
	}
	
}
