/** CS111 Staff
  *
  */

public class LabArray{
  public static void main (String [] args)
  {
  
 // Sample arrays for testing the code
    int[] a = {};
    int[] b = {8};
    int[] c = {9, -1, 3, 6, -10};
    int[] d = {8,3,-21,1000,3112,12};
    
    
    // Testing code:
    System.out.println("Testing printArray()");
    System.out.print("Array a: ");
    printArray(a);
    
    System.out.print("Array b: ");  
    printArray(b);
    
    System.out.print("Array c: ");  
    printArray(c);  
    
    System.out.print("Array d: ");  
    printArray(d);
    
    
  }
  //---------------------------------------------------------------
  
  // Prints out the input array 
  public static void printArray(int [] a) {
    System.out.print("[");
    for (int i = 0; i < a.length; i++) {
      System.out.print(a[i]);
      if (i < (a.length - 1)) 
        System.out.print(", ");
    }
    System.out.println("]");
  }
  
  // add a different version, to use a while-loop
  
}
