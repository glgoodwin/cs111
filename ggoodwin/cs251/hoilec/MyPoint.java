public class MyPoint {

  // Class variable
  private static int numPoints = 0; 

  // Instance variables
  private int x, y; 

  // Constructor method
  public MyPoint (int ix, int iy) {
    numPoints++; // count each point we make
    x = ix; // initialize coordinates
    y = iy;
  }

  // Instance methods 
  public int getX () {return x;}
  public void setX (int newX) {x = newX;}
  public int getY () {return y;}
  public void setY (int newY) {y = newY;}
  public void translate (int dx, int dy) {
    // Use setX and setY to illustrate "this"
    this.setX(x + dx); 
    this.setY(y + dy);
  }
  public String toString () {
    return "<" + x + "," + y + ">";
  }

  // Class methods
  public static int count () {
    return numPoints;
  }

  public static void testMyPoint () {
    MyPoint p1 = new MyPoint(3,4);
    MyPoint p2 = new MyPoint(5,6);
    System.out.println(p1.toString() + "; " + p2.toString());
    p1.setX(p2.getY()); // sets x of p1 to 6
    System.out.println(p1.toString() + "; " + p2.toString());
    p2.setY(MyPoint.count()); // sets y of p2 to 2
    System.out.println(p1.toString() + "; " + p2.toString());
    p1.translate(1,2); // sets x of p1 to 7 and y of p1 to 6
    System.out.println(p1.toString() + "; " + p2.toString());
  }

  public static void main (String[] args) {
    testMyPoint();
  }

}
