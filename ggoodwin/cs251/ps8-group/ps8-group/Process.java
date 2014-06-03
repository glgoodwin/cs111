

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
  public static IntList IL= new IntList();
  public static int q;
  public static IntList qlist= new IntList();
   
  // Define process here:
  public static IntList process (Intlist xs){
   return scan1 (xs, xs.head());         
  }
  
  // Define scan1 here:
  public static IntList scan1 (IntList ys, int f){
    qlist=ys;
    if (ys.isEmpty()){
     return mapxs(f);
     
    }else if ((ys.head() % 2) == 0){
      return scan2(ys.tail(), f);
      
    } else {
      int y2 = (ys.head())/2;
      IntList yy= new IntList();
      yy = (scan1 (ys.tail(), (f + y2)));
      return yy.prepend(y2, yy);        
  }
  }
  

  // Define scan2 here:
  public static IntList scan2 (IntList zs, int g){
    qlist=zs;
    if (zs.isEmpty()){
     return mapxs(g);
     
    }else if ((zs.head() % 2) == 0){
      return scan1(zs.tail(), g);
      
    } else {
      int z2 = (zs.head())/2;
      IntList zz= new IntList();
      zz = (scan2 (zs.tail(), (g * z2)));
      return zz.prepend(z2, zz);      
    
  }
  }
  
  
  // Define mapxs here:
  public static IntList mapxs (int u){
    q=u;
    return mapq(qlist);  
    
  }

  // Define mapq here:
  public static IntList mapq (IntList alist){
    if (alist.isEmpty()) {
      return alist;
    }else{
      return alist.prepend(q, mapq(alist.tail(), q));
   
      }
  }

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

