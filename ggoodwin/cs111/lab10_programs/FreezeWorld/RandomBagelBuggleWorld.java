//Modified by Stella Nov 07:
//main() was added
//default values changed
//setUp() and reset() were modified so the program can run 

// Modified by Elena: changed the default values of the parameters,
// Also changed so that bagels are generated on the entire grid, 
// including the bottom row. 
// Also allow only even values of width, height
// Oct. 2001

import java.awt.*;

public class RandomBagelBuggleWorld extends BuggleWorld {
  Randomizer rand = new Randomizer();
  String parameterNames [] = {"width", "height", "bagels", "buggles", "rounds"};
  protected ParameterFrame params;
  //protected TextField resultField1 = new TextField(6);
  protected int width, height;
  protected int buggles, rounds; // for Freeze Tag

  public static void main (String[] args) {
    runAsApplication(new RandomBagelBuggleWorld(), "RandomBagelBuggleWorld"); 
  }
  
  
    public void setup() {
    params = new ParameterFrame("Freeze Tag!", 200, 200, parameterNames);
    params.setIntParam("width", 20);
    params.setIntParam("height", 20);
    params.setIntParam("bagels", 50);
    params.setIntParam("buggles", 10);
    params.setIntParam("rounds", 12);
    
    params.setVisible(true);
  } 
    
      // Extend state initialization to add bagels as well.
    public void reset() {
      width = params.getIntParam("width");
      height = params.getIntParam("height");
      buggles = params.getIntParam("buggles");
      int bagels = params.getIntParam("bagels");
      rounds = params.getIntParam("rounds");
      
      if ((width>0) && (height>0)) {
        setDimensions(width,height);
        super.reset();
        if ((bagels>0) && (bagels<=((height-1)*width))) {
          placeBagels(bagels, width, height);    
        }
      }
    }

 
  public void placeBagels(int bagels, int width, int height) {
    if (bagels > 0) {
      int x = rand.intBetween(1,width);
      int y = rand.intBetween(1,height);
      if (isBagelAt(new Location(x,y))) {
    //System.out.println("lose (" + x + ", " + y + ")");
    // We lose; try again!
    placeBagels(bagels, width, height);
      } else {
    // We win; place a bagel
    //System.out.println("win (" + x + ", " + y + ")");
    addBagel(new Location(x,y));
    placeBagels(bagels - 1, width, height);
      }
    }
  }

}