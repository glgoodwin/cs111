// Interface for int -> int functions
interface IntFun {
  public int apply (int x); 
}

// The identity function
class IdFun implements IntFun {
  public int apply (int x) { 
    return x;
  }
}

// Put other classes implementing the IntFun interface here:
// (Alternatively, you can use anonymous inner classes instead.)

// The Process class defines the process, scan1, scan2, mapxs and mapq methods. 
public class Process {

  // Handy way of introducing abbreviation IL. for IntList operations:
  // IL.empty, IL.isEmpty, IL.head, IL.tail, IL.prepend.
  public static IntList IL;

  // Define process here:

  // Define scan1 here:

  // Define scan2 here:

  // Define mapxs here:

  // Define mapq here:

  // Testing method. E.g.: 
  // [lyn@jaguar ps5] java Process [3,4,5,6,7]
  // [1,2,3,13,15,17,19,21]
  // [lyn@jaguar ps5] java Process [5,7,4,5,7]
  // [2,3,2,3,35,47,29,35,47]
  public static void main (String [] args) {
    if (args.length == 1) {
      System.out.println(process(IL.fromString(args[0])));
    } else {
      System.out.println("unrecognized main option");
    }
  }

}

