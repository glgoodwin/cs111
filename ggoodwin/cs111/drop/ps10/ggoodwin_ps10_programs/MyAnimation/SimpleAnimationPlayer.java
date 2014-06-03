import java.applet.*; // use applet
import java.awt.*; // use AWT

// An implementation of an Animation Player.
// This implementation allows users to add one animation to the player.
// It also has a user-configurable GUI.
// Please see the online Animation notes for the public contract to this class.
public class SimpleAnimationPlayer extends Applet implements FrameNumberDisplay {
	
	// Animation Player Component
	protected AnimationPlayer ap;
	protected String animationName;
	
	// GUI components, can be customized by subclasses
	protected Color fieldColor, editColor;
	protected Label nameLabel;
	protected TextField frameNumberField;
	protected Button resetButton, playButton, stopButton, nextButton, gotoButton;
	
	// Booleans for deciding whether GUI shows components or not.
	protected boolean playOnStart;
	protected boolean showResetButton, showPlayButton, showStopButton;
	protected boolean showNextButton, showGotoButton, showFrameNumber, showControlPanel;
		
	public SimpleAnimationPlayer () {
		ap = new AnimationPlayer();
		playOnStart = false;
		showResetButton = true;
		showPlayButton = true;
		showStopButton = true;
		showNextButton = true;
		showGotoButton = true;
		showFrameNumber = true;
		showControlPanel = true;
		fieldColor = Color.white;
		editColor = new Color(255,102,102);
	}
		
	protected void addAnimation (String name, Animation a) {
		if (animationName==null) { // can only add 1 animation
			animationName = name;
			ap.setAnimation(a);
		}
	}

	public void init () {
		this.setLayout(new BorderLayout());
		this.add("Center", ap);
		if (showControlPanel) {
			this.add("South", makeControlPanel());	
		ap.setFrameSize();
		}
	}

	public void start () {
		if (playOnStart) ap.play();
	}
	
	protected void initializeGUIComponents () {
		if (animationName!=null)
		nameLabel = makeNameLabel(animationName);
		resetButton = makeButton("RESET");
		playButton = makeButton("PLAY");
		stopButton = makeButton("STOP");
		nextButton = makeButton("NEXT");
		gotoButton = makeButton("GOTO");
		frameNumberField = makeFrameNumberField();
	}

	protected Panel makeControlPanel () {
		Panel p = new Panel();
		p.setBackground(Color.black);
		initializeGUIComponents();
		addControls(p);
		return p;	
	}
	
	protected void addControls(Panel p) {
		p.add(nameLabel);
		if (showResetButton) p.add(resetButton);
		if (showPlayButton) p.add(playButton);
		if (showStopButton) p.add(stopButton);
		if (showNextButton) p.add(nextButton);
		if (showGotoButton) p.add(gotoButton);
		if (showFrameNumber) {
			p.add(frameNumberField);
			ap.setFrameNumberDisplay(this);
		}
	}
			
	// methods for setting booleans -- easy customization

	protected void setScreenColor (Color c) {
		ap.setScreenColor(c);
	}
	
	protected void setPlayOnStart () {
		this.playOnStart = true;
	}
	
	protected void setPlayOnPaint () {
		ap.setPlayOnPaint(true);
	}
		
	protected void hideResetButton () {
		showResetButton = false;
	}
	
	protected void hidePlayButton () {
		showPlayButton = false;
	}
	
	protected void hideStopButton () {
		showStopButton = false;
	}
	
	protected void hideNextButton () {
		showNextButton = false;
	}
		
	protected void hideGotoButton () {
		showGotoButton = false;
	}
	
	protected void hideFrameNumberDisplay () {
		if (!showGotoButton) { // can only hide if no goto button present
			showFrameNumber = false;
		}
	}

	protected void hideControlPanel () {
		showControlPanel = false;	
	}
		
	protected Label makeNameLabel (String name) {
		Label l = new Label(name, Label.CENTER);
		l.setForeground(new Color(255,102,102));
		return l;
	}
	
	protected Button makeButton (String label) {
		Button b = new Button(label);
		b.setBackground(new Color(00,128,128));
		b.setForeground(Color.black);
		return b;
	}
	
	protected TextField makeFrameNumberField () {
		TextField tf = new TextField(3);
		if (showGotoButton) tf.setEditable(true);
		else tf.setEditable(false);
		tf.setBackground(fieldColor);
		return tf;
	}
 
	public void setFrameNumberDisplay (int newFrameNumber) {
		frameNumberField.setText(Integer.toString(newFrameNumber));
	}
	
	public void stop () {
		ap.stop();
	}
	
  public boolean handleEvent (Event evt) {
    if (evt.id == Event.KEY_PRESS) {
      if (evt.target.equals(frameNumberField)) {
				frameNumberField.setBackground(editColor);
      }
    }
    return super.handleEvent(evt);
  }
  	 	
  public boolean action (Event evt, Object arg) {
  	if (evt.target.equals(resetButton)) {
  		ap.reset();
    } else if (evt.target.equals(playButton)) {
      ap.play();
    } else if (evt.target.equals(stopButton)) {
    	ap.stop();
    } else if (evt.target.equals(nextButton)) {
    	ap.nextFrame();
    } else if (evt.target.equals(gotoButton)) {
    	ap.gotoFrameNumber(Integer.parseInt(frameNumberField.getText()));
    	frameNumberField.setBackground(fieldColor);
    } else {
      return super.action(evt,arg);
    }
    return true;
  }	
	
}