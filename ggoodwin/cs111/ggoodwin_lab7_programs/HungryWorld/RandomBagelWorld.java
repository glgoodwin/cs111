// Modified by Mark A. Sheldon, July 2007:
// * Fixed used of deprecated show() method
// * Elimated unused eastOrWest() method
// * Used randomPoint() method, which was defined but unused
// * Eliminated fixed size of ParameterFrame

// [lyn, 8/31/07 -- 9/1/07] 
// * Added static main() method for running as an application. 
// * Changed randomPoint() method to randomLocation() method.
// * Adapted to new setup()/reset() model.

import java.awt.*;

public class RandomBagelWorld extends BuggleWorld {
    Randomizer rand = new Randomizer();
    String parameterNames [] = {"side", "bagels"};
  public ParameterFrame params; //stella: I made this public, so it can be seen from
    //its subclass "HungryWorld", where we need the value of "side".

  public static void main (String[] args) {
    runAsApplication(new RandomBagelWorld(), "RandomBagelWorld"); 
  }
  
  public void setup() {
    params = new ParameterFrame("Hungry Parameters", 200, 50, parameterNames);
    //params = new ParameterFrame("Parameters", parameterNames);
    params.setIntParam("side", 6);
    params.setIntParam("bagels", 18);
    params.setVisible(true);
  } 
  
  // Extend state initialization to add bagels as well.
  public void reset() {
    int side = params.getIntParam("side");
    setDimensions(side, side);
    super.reset();
    int bagels = params.getIntParam("bagels");
    placeBagels(bagels, side);    
  }
  
  
   public void placeBagels(int bagels, int n) {
  if (bagels > 0) {
   int x = rand.intBetween(1,n);
   int y = rand.intBetween(1,n);
   if ((x == (n/2 + 1)) || isBagelAt(new Location(x,y))) {
    // We lose; try again!
    placeBagels(bagels, n);
   } else {
    // We win; place a bagel
    addBagel(new Location(x,y));
    placeBagels(bagels - 1, n);
   }
  }
 }
   
 /*  
  public void placeBagels(int bagels, int n) {
    if (bagels > 0) {
      Location p = randomLocation(n);
      if (isBagelAt(p)) {
 //System.out.println("lose (" + x + ", " + y + ")");
 // We lose; try again!
 placeBagels(bagels, n);
      } else {
 // We win; place a bagel
 //System.out.println("win (" + x + ", " + y + ")");
 addBagel(p);
 placeBagels(bagels - 1, n);
      }
    }
  }
  */
  private Location randomLocation (int n) {
    return new Location(rand.intBetween(1, n), rand.intBetween(1, n));
  }
}

