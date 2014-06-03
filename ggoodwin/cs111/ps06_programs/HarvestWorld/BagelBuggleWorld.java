// [MAS, Spring 2008] Tidied up code (fixed indentation, etc.)
// [lyn, 8/31/07 -- 9/1/07] 
// * Added static main() method for running as an application. 
// * Changed uses of isBagelAt() and addBagel() to use Location
//   rather than Point.
// * Adapted to new setup()/reset() model.
// [lyn, 9/3/07] Fixed bug in reset() by setting dimensions *before* 
// super.reset().

import java.awt.*;

public class BagelBuggleWorld extends BuggleWorld
{

        Randomizer rand = new Randomizer();
        String parameterNames [] = {"width", "height", "bagels"};
        String resultNames [] = {"bagels"};
        protected ParameterFrame params;
        protected int width, height;

        public static void main (String[] args)
        {
                runAsApplication(new BagelBuggleWorld(), "BagleBuggleWorld"); 
        }
  
        public void setup() {
                params = new ParameterFrame("HarvestWorld", 200, 150,
                                            parameterNames, resultNames);
                params.setIntParam("width", 9);
                params.setIntParam("height", 9);
                params.setIntParam("bagels", 33);
                params.show();
                // resetHookAfter(); Calling resetHookAfter() in setup 
                // doesn't work in this version of BuggleWorld
        }
  
        public void reset()
        {
                width = params.getIntParam("width");
                height = params.getIntParam("height");
                int bagels = params.getIntParam("bagels");
                if (width <= 0) {
                        throw new BuggleException ("width must be "
                                                   + "greater than 0: "
                                                   + width);
                }
                if (height <= 0) {
                        throw new BuggleException ("height must be "
                                                   + "greater than 0: "
                                                   + height);
                }
                if (bagels < 0) {
                        throw new BuggleException ("bagels must be "
                                                   + "nonnegative: "
                                                   + bagels);
                }
                if (bagels > ((height - 1) * width)) {
                        throw new BuggleException ("bagels must be <= "
                                                   + "((height - 1) * width): "
                                                   + bagels);
                }
                setDimensions(width, height);
                super.reset();
                // Extend default state initialization to add bagels trail.
                placeBagels(bagels, width, height);
        }
  
        // does not place bagels in bottom row
        public void placeBagels(int bagels, int width, int height)
        {
                if (bagels > 0) {
                        int x = rand.intBetween(1, width);
                        int y = rand.intBetween(2, height);
                        if (isBagelAt(new Location(x, y))) {
                                /*
                                System.out.println("lose (" + x + ", "
                                                   + y + ")");
                                */
                                // We lose; try again!
                                placeBagels(bagels, width, height);
                        } else {
                                // We win; place a bagel
                                /*
                                System.out.println("win (" + x + ", "
                                                   + y + ")");
                                */
                                addBagel(new Location(x, y));
                                placeBagels(bagels - 1, width, height);
                        }
                }
        }
}
