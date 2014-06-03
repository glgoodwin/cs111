/**
 * FILE NAME: Shape.java
 * WHO: Copyright by J. Barry DeRoos
 * Edited by Sohie, added decimal formatting
 * WHAT: Defines the Abstract Class Shape, for a closed geometric shape.
 * Propery: name - protected property name of the shape
 *   
 * Methods:
 * Abstract methods area() and perimeter()  - to be implemented in inheriting classes
 * final method compareTo() - compare shapes by area
 * final method to String() - provides a string representation of the shape
 */
import java.text.DecimalFormat;  //used to format output
public abstract class Shape {
  protected String name; 
  
  /**
   *   Shape Constructor
   *   @param shapeName  the name of the shape
   */
  public Shape(String shapeName) {
    this.name = shapeName;
  }
  
  /**
   *    Method abstract area() 
   *    To be implemented by the inheriting class.
   *
   *    @return the area of the shape.
   */
  abstract public double area();
  
    /**
   *    Method abstract perimeter() 
   *    To be implemented by the inheriting class.
   *
   *    @return the perimeter of the shape.
   */
  abstract public double perimeter();
  
  /**
   *  Method final compareTo()
   *  Compares the invoking and the param shapes by area
   *  @param otherShape  Shape instance to be compared to the invoker
   *  @return int   0 if the two shapes have the same area, 
   *                    positive integer is the invoking shape's area is greatet
   *                    negative integer is the invoking shape's area is smaller
   */
  final public int compareTo(Shape otherShape) {
    if (this.area() == otherShape.area()) return 0;
    if (this.area() > otherShape.area()) return 1;
    return -1;
  }
  
  /**
   *  Method final toString() <br>
   *  <blockquote>Final method to Display the Class</blockquote>
   *  @return String
   */
  final public String toString() {
    DecimalFormat fmt = new DecimalFormat("0.###");
    return this.name + " of area " + fmt.format(area());
  }
} // End Abstract Shape Class
