// A simple Turtle graphics example.
// Created by Lyn, 3/14/97
// Updated 11/3/97
// Updated 1/13/03 by Alice Tiao, with supervision from Elena Machkasova, for PC and Mac Compatibility

import java.awt.*;       // Import Abstract Window Toolkit
import java.applet.*;    // Import Applet stuff
import java.util.*;    	// Vectors et al.

public class TurtleWorld extends Applet {
	
	public static TurtleWorld currentWorld;
	private Canvas turtleCanvas;
	private Graphics turtleGraphics;
	private Panel controlPanel;
	private Button runButton;
	private Button resetButton;
	private Vector turtles = new Vector();
	
	public void init() {
		currentWorld = this;
		turtleCanvas = new Canvas();
		runButton = new Button("Run");
		resetButton = new Button ("Reset");
		controlPanel = new Panel();
		controlPanel.setLayout(new GridLayout(1,2));
		controlPanel.add(runButton);
		controlPanel.add(resetButton);
		this.setLayout(new BorderLayout());
		this.add("Center", turtleCanvas);
		this.add("South", controlPanel);
		
		this.setup(); // Allow user-defined initializations
	}
	
	public void addTurtle(Turtle t) {
		turtles.addElement(t);
	}
	
	public void setup() {
		// Method intended to be overridden by subclasses
		// System.out.println("setup()");
	}
	
	
	public void runAction() {
		ensureGraphics();	
		currentWorld = this;
		this.run();
	}
	
	public void run() { 
		// Method intended to be overridden by subclasses
		System.out.println("Default run() action.");
	}
	
	
	public void resetAction() {
		currentWorld = this;
		ensureGraphics();
		Dimension d = turtleCanvas.size();
		turtleGraphics.setColor(Color.white);
		turtleGraphics.fillRect(0, 0, d.width, d.height);
		this.reset();
		for (int i = 0; i < turtles.size(); i++) {
			((Turtle) turtles.elementAt(i)).home();
		}
	}
	
	public void reset () {
		// Method intended to be overridden by subclasses
	}
	
	public void ensureGraphics() {
		if (turtleGraphics == null) {
			turtleGraphics = turtleCanvas.getGraphics();
			//getGraphics returns the graphic but not the dimensions of the canvas
			//which is needed by Turtle.screenX() and screenY() (calculated using Rectangle bounds)
		}
	}
	
	public Canvas getTurtleCanvas(){
	//created by alice tiao (1/13/02)
	//problem with getClipRect() by Graphis gfx in Turtle.java
	//PC doesnt' pass on the information of getClipRect (returns null) from
	//Canvas to Graphics
		return turtleCanvas;
	}
 
	public Graphics getTurtleGraphics () {
		return turtleGraphics;
	}
	
	public boolean handleEvent(Event evt) {
		if (evt.id == Event.WINDOW_DESTROY) {
			System.exit(0);
		}
		return super.handleEvent(evt);		
	}
	
	public boolean action(Event event, Object arg) {
		if (arg.equals("Run")) {
			
			this.runAction();
			return true;
		} else if (arg.equals("Reset")) {
			currentWorld = this;
			this.resetAction();
			return true;
		} 
		return false;
	}

	
}
 