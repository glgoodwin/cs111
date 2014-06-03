import java.util.*;  // for Scanner class

/**
 * FILE NAME:TestShape.java
 * WHO:  CS230
 *
 * WHAT: Tests the Shape Class and its hierarchy
 *       Enters various Shape objects into an array
 *       Sorts the array using a selection sort
 *       Display the results
 */

public class TestShape {
  /**
   *  sortArray static method
   *  does a selection sort on the input array of type shapes
   *  @param a an array of type Shapes
   *  @param numElts the number of non-null elements in the array
   *  (if the array does *not* contain empty slots at the end, 
   *  numElts will be the same as the length of the array)
   */
  private static void sortArray (Shape[] a, int numElts) {
    // sorts the input Shape[] in increasing order, 
    // according to the area of each Shape obj
    Shape maxShape;  // maximum integer
    int maxIndex;   // index of maximum integer
    int i, j;
    for (j = numElts - 1; j > 0; j--) {
      maxIndex = 0;
      maxShape = a[0];
      for (i = 1; i <= j; i++) {
        if (a[i].compareTo(maxShape) > 0) {
          maxShape = a[i];
          maxIndex = i;
        }
      }
      swap(a, maxIndex, j);
    }
  }  
  
  /** 
   * exchanges the contents of input array at positions i and j
   */
  private static void swap (Shape[] a, int i, int j) {
    // exchanges the contents of locations i and j in input array
    Shape temp = a[i];
    a[i] = a[j];
    a[j] = temp;
  }
  
  /**
   * printArray static method
   * prints out an array of Shapes
   * @param a an array of Shapes
   **/
  private static void printArray(Shape[] a){
    for (int i=0; i<a.length; i++) {
      if (a[i] != null)
        System.out.println(" "+a[i]);
    }  
  }
  
  /**
   *  static main method
   *  Method to read in data, from the standard input. 
   *  Then, sorts and displays the inputted Shape objects according to 
   *  their area
   *
   *    @param args an array of strings
   */
  public static void main(String[ ] args) {
    int maxNumShapes = 100; //the total number of the shape we process *cannot*
                            //be more than that!
    Shape [] array = new Shape[maxNumShapes];
    
    int i = 0; //counts the number of Shape objects (non-null objects) in the array
   
    //  Read in, from the standard input (the keyboard), the type
    // of shape and the required shape information.  Then, place
    // all shape objects in an array and sort based on area and
    // print the results.

    Scanner scan = new Scanner(System.in);
    
    String letterShape;
    double radius;
    double base;
    double height;
    double side1;
    double side2;
    double side3;
    
    // Read the shapes from the standard input
    do {
      System.out.print(" Which shape: circle (c)\n"+
		       "           rectangle (r)\n"+
		       "            triangle (t)\n"+
                       "                quit (q):");
      letterShape = scan.next();
      if (letterShape.equals("c")) { //it is a circle
        System.out.print("            Enter radius:");
        radius = scan.nextFloat();
        //String radiusStr = scan.next();
        //radius = Float.parseFloat(radiusStr);
        array[i] = new Circle(radius);
        i++;
      }
      else if (letterShape.equals("r")){ //it is a rectangle
	  System.out.println("        Enter base:");
	  base = scan.nextFloat();
	  System.out.println("        Enter height:");
	  height = scan.nextFloat();
	  array[i] = new Rect(base,height);
	  i++;
	      }
      else if (letterShape.equals("t")){//it is a triangle
	  System.out.println("         Enter side 1:");
	  side1 = scan.nextFloat();
	  System.out.println("        Enter side 2:");
	  side2 = scan.nextFloat();
	  System.out.println("        Enter side 3:");
	  side3 = scan.nextFloat();
	  array[i] = new Triangle(side1,side2,side3);
	  i++;
      }
    } while (!letterShape.equals("q"));

    // At this point, array contains i number of objects of type Shape
    sortArray(array, i);
    // print out the sorted array of Shape objects
    System.out.println(". . . . . . . . . . . . . . . . . . . . . . ");
    System.out.println("Here are the contents of the sorted array: ");
    printArray(array);
    System.out.println("goodbye!");
  }//End main()
  
  } //End TestShape     
