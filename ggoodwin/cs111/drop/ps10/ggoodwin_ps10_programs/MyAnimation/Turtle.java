// Simple turtle graphics package
// Create by Lyn on 3/14/97
// Updated on 11/3/97
// Updated 1/13/03 by Alice Tiao, with supervision from Elena Machkasova, for PC and Mac Compatibility


import java.awt.*;       // Import Abstract Window Toolkit

public class Turtle {
	
	private double x;         			// x position of turtle
	private double y;         			// y position of turtle
	private double heading;   			// heading of turtle, in degrees
	private boolean pendown;				// is turtle in drawing mode?
	private Color color; 						// Red is the default 
	private Graphics gfx;        		// graphics with which turtle draws
	private Rectangle bounds; 	  		// bounds of drawing surface
	private Canvas cvs;					// canvas within which turtle draws
	
	public Turtle() {
		this.color = Color.red; 
		home();
		TurtleWorld.currentWorld.addTurtle(this);
	}
	
	public void home() {
		this.x = 0.0;
		this.y = 0.0;
		this.heading = 0.0;
		this.pendown = true;
	}
	
	// Forward
	public void fd(double dist) {
		double rads = degreesToRadians(heading);
		this.moveTo(x + (dist * Math.cos(rads)), y + (dist * Math.sin(rads)));
	}
	
	public void fd (int dist) {
		fd((double) dist);
	}

	// Backward
	public void bd(double dist) {
		this.fd(- dist);
	}
	
	public void bd (int dist) {
		bd((double) dist);
	}


	// Left	
	public void lt(double degrees) {
		this.heading = heading + degrees;
	}
	
	public void lt (int degrees) {
		lt((double) degrees);
	}


	// Right	
	public void rt(double angle) {
		this.lt(- angle);
	}
	
	public void rt (int degrees) {
		rt((double) degrees);
	}

	// Pen Up	
	public void pu() {
		this.pendown = false;
	}

	// Pen Down	
	public void pd() {
		this.pendown = true;
	}
	
	// Position
	
	public Point getPosition () {
		return new Point ((int) x, (int) y);
	}
	
	public void setPosition (Point p) {
		x = (double) p.x;
		y = (double) p.y;
	}
	
	public void setPosition (int ix, int iy) {
		x = (double) ix;
		y = (double) iy;
	}
	
	public void setPosition (double dx, double dy) {
		x = dx;
		y = dy;
	}
	
	// Heading

	public double getHeading () {
		return heading;	
	}
	
	public void setHeading (double degrees) {
		heading = degrees;
	}
	
	public void setHeading (int degrees) {
		heading = (double) degrees;
	}
	
	// Color
	
	public Color getColor () {
		return color;
	}
	
	public void setColor (Color c) {
		color = c;
	}

	
	
	// Other

		
	public void moveTo(double newx, double newy) {
		/*For testing purposes:
			System.out.println("Move: heading = " + heading 
							+ "; x = " + x 
							+ "; y = " + y 
							+ "; newx = " + newx
							+ "; newy = " + newy);
		*/	
		if (pendown) {
			ensureGraphics();
			ensureCanvas();				//added by Alice Tiao on 1/13/03 for PC compatibility
										//see notes in TurtleWolrd.getTurtleCanvas()
			this.gfx.setColor(color);
			this.gfx.drawLine(screenX(x), screenY(y), screenX(newx), screenY(newy));
		}
		x = newx;
		y = newy;
	}
	
	private void ensureCanvas () {
		//added by Alice Tiao (1/13/03)
		this.cvs = TurtleWorld.currentWorld.getTurtleCanvas();
		this.bounds = cvs.getBounds();
	}
	
	private void ensureGraphics () {
		this.gfx = TurtleWorld.currentWorld.getTurtleGraphics();
		//this.bounds = gfx.getClipRect();		//changed by Alice Tiao 1/13/03
												//replaced with code in ensureCanvas
	}
	
	private int screenX(double xcoord) {
		return (int) Math.round(bounds.x + (bounds.width / 2) + xcoord);
	}
	
	private int screenY(double ycoord) {
		return (int) Math.round(bounds.y + (bounds.height / 2) - ycoord);
	}
	
	private static double degreesToRadians (double degrees) {
		return 2 * Math.PI * (degrees / 360);
	}
	

	
}
