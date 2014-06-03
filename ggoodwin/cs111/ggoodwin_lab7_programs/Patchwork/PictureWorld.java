/**************************************************************************
Name: PictureWorld.java

Description: 
  Implementation of CS111 PictureWorld classes. 
  PictureWorld is a Java microworld for picture combination inspired 
  by the picture microworld in MIT's 6.001, which was itself inspired
  by Peter Henderson's functional graphics work. 

History: 

  ----------------------------------------------------------------------
  [lyn, 9/24/07] Changed default frame size to 512x571, which empirically
    yields a 512x512 canvas on the Macs. Not only this this square, but
    it makes many filled polygon examples look better on the Macs. 
  ----------------------------------------------------------------------
  [lyn, 9/[19-20]/07] Made several changes to PictureWorld: 

  * Added runAsApplication (like in BuggleWorld), allowed PictureWorld
      instances to be run as applications rather than in a browser. 
      
      * Swapped meanings of flipHorizontally and flipVertically to be
      consistent with the rest of the world (e.g., PowerPoint figure
      manipulation.)

      * Converted GUI to Swing. As part of this:
      + Changed Choice to JComboBox, and made it use an ItemListener.
      + Changed PictureCanvas from being a Canvas to being a JComponent
        (else JComboBox menu won't paint over it).

	* Replace top-level menu item by "----------------Picture Menu----------------"
      (used to be empty before)

  Items still on the to-do list: 
  * There are some off-by-one bugs in Apple's JVM that makes some
    pictures look not so good (e.g., My quilt).
    * In the PictureWorld contract on the contract pages, should use the 
    green wedge rather than the blue isosceles triangle to illustrate operations. 
    * Would be nice to add other shapes, like circles, arcs, etc. 
    * Would be nice to add GIFs/JPEGs as pictures!

  ----------------------------------------------------------------------
  [Franklyn Turbak (lyn), fall'97] Created PictureWorld from scratch, 
  inspired by the picture microworld in MIT's 6.001, which was itself 
  inspired by Peter Henderson's functional graphics work. 
  ----------------------------------------------------------------------

**************************************************************************/

import java.awt.*;
import java.applet.Applet;
import java.util.*;
import java.awt.event.*; // [lyn, 9/19/09] new 
import javax.swing.*;    // [lyn, 9/19/09] new 

public class PictureWorld extends JApplet {
  
  private JComboBox pictureChoice;
  private PictureCanvas pictureCanvas;
  private Vector namedPictures;
  
  //----------------------------------------------------------------------
  /*** [lyn, 9/19/07] New code for running a PictureWorld applet as an application ***/
  
  public static void main (String[] args) {
    runAsApplication(new PictureWorld(), "PictureWorld"); 
  }
  
  public static void runAsApplication (final PictureWorld applet, final String name) {
    // Schedule a job for the event-dispatching thread:
    // creating and showing this buggle world applet. 
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
	public void run() { // this is Java's thread run() method, not BuggleWorlds!
	  JFrame.setDefaultLookAndFeelDecorated(true); // enable window decorations. 
	  JFrame frame = new JFrame(name); // create and set up the window.

	  frame.setSize(512, 571); // Default frame size (should make these settable variables!)
	  // [lyn, 9/24/07] Empirically, the above numbers give a 512x512 canvas on a Mac
	  // and a 502x505 canvas on Linux (don't know about PCs). 
	  
	  // Using EXIT_ON_CLOSE empirically exits all instances of an application.
	  // Use DISPOSE_ON_CLOSE to get rid of just one. 
	  frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); 
	  
	  // Need to add to frame and make visible *before* init 
	  // so that attempts to reset dimensions will work. 
	  frame.add(applet, BorderLayout.CENTER); // add applet to window
	  frame.setVisible(true); // display the window.
	  applet.init(); // initialize the applet
	  // Need to make visible again *after* init in case
	  //  something like setDimensions is not called in init. 
	  frame.setVisible(true); // display the window.
	}
      });
  }
  //----------------------------------------------------------------------
  
  
  public void init () {
    JPanel choicePanel = new JPanel();
    choicePanel.setBackground(Color.yellow);
    pictureCanvas = new PictureCanvas(this);
    this.setLayout(new BorderLayout());
    // this.setLayout(new GridLayout(1,2));
    namedPictures = new Vector();
    addPictureChoice("----------------Picture Menu----------------", empty());
    initializePictureChoices();
    pictureChoice = namedPictureChoices();
    pictureChoice.setBackground(Color.white);
    // Add an item listener that redisplays picture in canvas. 
    pictureChoice.addItemListener (new ItemListener(){
	public void itemStateChanged(ItemEvent e) {
	  if (e.getStateChange() == ItemEvent.SELECTED) {
	    pictureCanvas.paint();
	  }
	}
      });
    choicePanel.add(pictureChoice);
    this.add(pictureCanvas, BorderLayout.CENTER);
    this.add(choicePanel, BorderLayout.NORTH);
  }
  
  public void initializePictureChoices() {
    // A stub method that can be overridden by subclasses to add new pictures
    // to the list of picture choices at the top of the applet. 
    // Add a new choice via the invocation: addPictureChoice(name, pic), 
    // where name is a String and pic is a Picture. 
    
  }
  
  // Picture Combinators
  
  public Picture empty() {
    // The empty picture.
    return new Picture();
  }
  
  // [lyn, 9/20/07] Meaning changed
  public Picture flipVertically (Picture p) {
    return new TransformedPicture(p, 0.0, 1.0, 1.0, 1.0, 0.0, 0.0);
  }
  
  // [lyn, 9/20/07] Meaning changed
  public Picture flipHorizontally (Picture p) {
    return new TransformedPicture(p, 1.0, 0.0, 0.0, 0.0, 1.0, 1.0);
  }
  
  public Picture flipDiagonally (Picture p) {
    return new TransformedPicture(p, 0.0, 0.0, 0.0, 1.0, 1.0, 0.0);
  }
  
  public Picture clockwise90 (Picture p) {
    return new TransformedPicture(p, 0.0, 1.0, 0.0, 0.0, 1.0, 1.0);
  }
  
  public Picture clockwise180 (Picture p) {
    return clockwise90(clockwise90(p));
  }
  
  public Picture clockwise270 (Picture p) {
    return clockwise90(clockwise90(clockwise90(p)));
  }
  
  public Picture overlay (Picture p1, Picture p2) {
    PictureList pictures = new PictureList();
    pictures.add(p2);
    pictures.add(p1); 
    return pictures; 
  }
  
  public Picture beside (Picture p1, Picture p2, double fraction) {
    PictureList pictures = new PictureList();
    pictures.add(new TransformedPicture(p1, 0.0, 0.0, fraction, 0.0, 0.0, 1.0));
    pictures.add(new TransformedPicture(p2, fraction, 0.0, 1.0, 0.0, fraction, 1.0)); 
    return pictures; 
  }
  
  public Picture beside (Picture p1, Picture p2) {
    return beside(p1, p2, 0.5);
  }
  
  public Picture above (Picture p1, Picture p2, double fraction) {
    PictureList pictures = new PictureList();
    double aboveFrac = 1 - fraction;
    pictures.add(new TransformedPicture(p1, 0.0, aboveFrac, 1.0, aboveFrac, 0.0, 1.0));
    pictures.add(new TransformedPicture(p2, 0.0, 0.0, 1.0, 0.0, 0.0, aboveFrac)); 
    return pictures; 
  }
  
  public Picture above (Picture p1, Picture p2) {
    return above(p1, p2, 0.5);
  }
  
  // Manipulations of named pictures and picture choices
  
  public Picture selectedPicture() {
    // System.out.println("selectedPicture() = " + (((NamedPicture) namedPictures.get(pictureChoice.getSelectedIndex())).name));
    // Returns the picture that should be drawn in the canvas.
    return (((NamedPicture) namedPictures.get(pictureChoice.getSelectedIndex())).picture);
  }
  
  public JComboBox namedPictureChoices () {
    return new JComboBox(namedPictures);
  }
  
  public void addPictureChoice(String name, Picture pic) {
    // Insert new named picture in alphabetical order.
    insert(namedPictures, new NamedPicture(name, pic));
  }
  
  public void insert(Vector v, NamedPicture npic) {
    String name = npic.name;
    // Find insertion point.
    int i = v.size(); 
    while ((i > 0) && (((NamedPicture) v.elementAt(i-1)).name.compareTo(name) >= 0)) {
      i = i - 1;
    }
    if ((i < v.size()) && (((NamedPicture) v.elementAt(i)).name.compareTo(name) == 0)) {
      // If name already in table, replace entry with same name.
      v.setElementAt(npic, i);
    } else {
      // If name not already in table, shift larger elements to right and insert.
      v.addElement(npic); // Add a new slot at end of vector. Contents of this slot is irrelevant.
      for (int shift = v.size() - 2; shift >= i; shift--) {
	v.setElementAt(v.elementAt(shift), shift + 1);
      }
      v.setElementAt(npic, i);
    }
  }
  
  // Utilities
  
  public void printError (String s) {
    // Print error message in output area.
    // System.out.println("printError: " + s);
    String message = "PictureWorld Error: " + s;
    System.out.println(message);
  }
  
}

class NamedPicture {
  
  public String name;
  public Picture picture;
  
  public NamedPicture (String name, Picture picture) {
    this.name = name;
    this.picture = picture; 
  }
  
  // [lyn, 9/19/07] Added.
  public String toString () {
    return this.name;
  }
  
}

class PictureCanvas extends JComponent {
  
  private PictureWorld pw; // The picture world of which I am a part. 
  
  public PictureCanvas (PictureWorld pw) {
    this.pw = pw;
  }
  
  public void paint () {
    paint(this.getGraphics());
  }
  
  public void paint (Graphics g) {
    drawSelectedPicture(g);
  }
  
  public void drawSelectedPicture (Graphics g) {
    Dimension d = getSize();
    int w = d.width;
    int h = d.height;
    // System.out.println("w=" + w + ";h=" + h);
    // Clear canvas before drawing
    g.setColor(Color.white);
    g.fillRect(0,0,w,h);
    // Draw selected picture
    pw.selectedPicture().draw(g, new PictureFrame(new Vec(0,h), new Vec(w,0), new Vec(0, -h)));
  }
  
  
}

class Picture {
  // This class represents the empty picture.
  // Subclasses of this class can override the draw method to make a non-empty picture.
  
  public void draw (Graphics g, PictureFrame f) {
    // Default is to do nothing.
    // Override this method in a subclass to draw something.
  }
  
}

class PictureList extends Picture {
  
  private Vector pictures;
  
  public PictureList () {
    pictures = new Vector();
  }
  
  public void add (Picture p) {
    pictures.addElement(p);
  }
  
  public void draw (Graphics g, PictureFrame f) {
    Enumeration pics = pictures.elements();
    while (pics.hasMoreElements()) {
      Picture pic = (Picture) pics.nextElement();
      pic.draw(g, f);
    }
  }
  
}

class TransformedPicture extends Picture {
  // Returns a new picture that draws itself in the picture frame that 
  // results from transforming a given frame by the specified transform. 
  
  private Picture pic;
  private double ov_x, ov_y; // The unit frame point to which origin should be translated.
  private double xv_x, xv_y; // The unit frame point to which xAxis tip should be translated.
  private double yv_x, yv_y; // The unit frame point to which yAxis tip should be translated.
  
  
  public TransformedPicture (Picture pic, 
                             double ov_x, double ov_y,                              
                             double xv_x, double xv_y,                              
                             double yv_x, double yv_y) {
    this.pic = pic;
    this.ov_x = ov_x;
    this.ov_y = ov_y;
    this.xv_x = xv_x;
    this.xv_y = xv_y;
    this.yv_x = yv_x;
    this.yv_y = yv_y;
  }
  
  public void draw (Graphics g, PictureFrame f) {
    Vec origin = f.getOrigin();
    Vec xAxis = f.getXAxis();
    Vec yAxis = f.getYAxis();
    pic.draw(g, new PictureFrame(origin.add(xAxis.scale(ov_x)).add(yAxis.scale(ov_y)),
				 xAxis.scale(xv_x - ov_x).add(yAxis.scale(xv_y - ov_y)),
				 xAxis.scale(yv_x - ov_x).add(yAxis.scale(yv_y - ov_y))));
  }
  
}

class Line extends Picture {
  // A figure that is a colored line.
  
  private Vec from;
  private Vec to;
  private Color col;
  
  public Line (double x1, double y1, double x2, double y2, Color col) {
    this.from = new Vec(x1, y1);
    this.to = new Vec(x2, y2);
    this.col = col;
  }
  
  public Line (Vec from, Vec to, Color col) {
    this.from = from;
    this.to = to;
    this.col = col;
  }
  
  public void draw (Graphics g, PictureFrame f) {
    Vec tfrom = f.transformVec(from);
    Vec tto = f.transformVec(to);
    g.setColor(col);
    // System.out.println("drawLine(" + (int) tfrom.getX() + "," + (int) tfrom.getY() + "," + (int) tto.getX() + "," + (int) tto.getY() + ")");
    g.drawLine((int) tfrom.getX(), (int) tfrom.getY(), (int) tto.getX(), (int) tto.getY());
  }
  
}

class Poly extends Picture {
  // A closed polygonal figure consisting of a set of points.
  
  private Vector points;
  private Color col;
  private boolean isFilled;
  
  public Poly (Color col, boolean isFilled) {
    points = new Vector();
    this.col = col;
    this.isFilled = isFilled;
  }
  
  public void addPoint (double x, double y) {
    // Add a point (x,y) in the unit square to the polygon.
    points.addElement(new Vec(x,y));
  }
  
  /*
    public void draw (Canvas c, PictureFrame f) {
    int size = points.size();
    if (size >= 2) {
    Graphics g = c.getGraphics();
    g.setColor(col);
    Vec start = (Vec) points.elementAt(0);
    Vec tfrom = f.transformVec(start);
    for (int i = 1; i < size; i++) {
    Vec to = (Vec) points.elementAt(i);
    Vec tto = f.transformVec(to);
    g.drawLine((int) tfrom.getX(), (int) tfrom.getY(), (int) tto.getX(), (int) tto.getY());
    tfrom = tto; // Next from is current to.
    }
    // This is a closed polygon, so draw final line.
    Vec tto = f.transformVec(start);
    g.drawLine((int) tfrom.getX(), (int) tfrom.getY(), (int) tto.getX(), (int) tto.getY());
    }
    }
  */
  
  public void draw (Graphics g, PictureFrame f) {
    int size = points.size();
    if (size >= 2) {
      Polygon poly = new Polygon();
      for (int i = 0; i < size; i++) {
	Vec point = (Vec) points.elementAt(i);
	Vec tpoint = f.transformVec(point);
	// The "- 1" corrects for a bug in Symantec Cafe Applet Viewer,
	// but will screw up other Java interpreters.
	// poly.addPoint((int) tpoint.getX() - 1, (int) tpoint.getY() - 1);
	poly.addPoint((int) tpoint.getX(), (int) tpoint.getY());
      }
      // This is a closed polygon, so include original point as final.
      Vec start = (Vec) points.elementAt(0);
      Vec tstart = f.transformVec(start);
      // The "- 1" corrects for a bug in Symantec Cafe Applet Viewer,
      // but will screw up other Java interpreters.
      // poly.addPoint((int) tstart.getX() - 1, (int) tstart.getY() - 1);
      poly.addPoint((int) tstart.getX(), (int) tstart.getY());
      g.setColor(col);
      if (isFilled) {
	g.fillPolygon(poly);
      } else {
	g.drawPolygon(poly);
      }
    }
  }
}

class Rect extends Picture {
  // A picture that is a colored rectangle.
  // It is implemented as a Poly.
  
  private Poly poly; 
  
  public Rect (double x, double y, double width, double height, Color col, boolean isFilled) {
    poly = new Poly (col, isFilled);
    poly.addPoint(x, y);
    poly.addPoint(x + width, y);
    poly.addPoint(x + width, y + height);
    poly.addPoint(x, y + height);
    // Polys are closed, so final line will be added automatically.
  }
  
  public void draw (Graphics g, PictureFrame f) {
    poly.draw(g, f);
  }
  
}

class PictureFrame {
  // A representation of the area on a screen to which a unit square picture can
  // be mapped by a linear transformation. The area has three components:
  //   (1) a vector that specifies the origin of the frame relative to the origin
  //       of the Cartesian coordinate system.
  //   (2) a vector that specifies the x-axis of the frame relative to the origin
  //       of the frame.
  //   (3) a vector that specifies the y-axis of the frame relative to the origin
  //       of the frame.
  // Note that the x-axis and y-axis need not be orthogonal -- they can be at 
  // any angle relative to each other.
  // 
  // Use the name "PictureFrame" rather than "Frame" so as not to conflict with 
  // java.awt.Frame.
  
  private Vec origin;
  private Vec xAxis;
  private Vec yAxis;
  
  public PictureFrame(Vec origin, Vec xAxis, Vec yAxis) {
    this.origin = origin;
    this.xAxis = xAxis;
    this.yAxis = yAxis;
  }
  
  public Vec getOrigin () {
    return origin;
  }
  
  public Vec getXAxis () {
    return xAxis;
  }
  
  public Vec getYAxis () {
    return yAxis;
  }
  
  public Vec transformVec (Vec v) {
    // Transform the Vector v to this picture frame.
    // It is assumed that v is in the unit square. 
    return origin.add(xAxis.scale(v.getX()).add(yAxis.scale(v.getY())));
  }
  
}

class Vec {
  // Represents a 2-dimensional vector.
  // Like a Point, but the coordinates are doubles rather than integers.
  // Use the name "Vec" rather than "Vector" so as not to conflict with java.util.Vector.
  
  private double x;
  private double y;
  
  public Vec (double x, double y) {
    this.x = x;
    this.y = y;
  }
  
  public double getX () {
    return x;
  }
  
  public double getY() {
    return y;
  }
  
  public Vec add (Vec v) {
    // Returns a new vector that is the result of adding v to this vector. 
    return new Vec (x + v.x, y + v.y);
  }
  
  public Vec sub (Vec v) {
    // Returns a new vector that is the result of subtractin v from this vector. 
    return new Vec (x - v.x, y - v.y);
  }
  
  public Vec scale (double factor) {
    // Returns a new vector that is the result of scaling this vector by factor. 
    return new Vec (factor * x, factor * y);
  }
  
  public double magnitude () {
    // Returns the length of this vector.
    return Math.sqrt((x * x) + (y * y));
  }
  
  public double angle() {
    // Returns the angle of this vector in degrees (relative to the x-axis, counterclockwise).
    return radiansToDegrees(Math.atan2(y, x));
  }
  
  public static double radiansToDegrees(double rads) {
    return (180.0 * rads) / Math.PI;
  }
  
  public String toString() {
    return "Vec[x=" + x
      + "; y=" + y 
      + "]";
  }
  
  
}
