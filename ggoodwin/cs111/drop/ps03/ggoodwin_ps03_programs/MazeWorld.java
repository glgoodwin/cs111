// [lyn, 9/29/07] In this particular copy of MazeWorld, set seed to 16
// to get first 3 mazes to cover three possible configurations. 

// [lyn, 8/31/07 -- 9/1/07] 
// * Added static main() method for running as an application. 
// * Removed addBagel() from initState(); what the heck was it doing there, anyway?
// * Adapted to new setup()/reset() model.

import java.awt.*;
import java.util.*;

public class MazeWorld extends BuggleWorld {
	
  private Randomizer rand = new Randomizer(16); // Set seed for repeatability.

  public static void main (String[] args) {
    runAsApplication(new MazeWorld(), "MazeWorld"); 
  }
		
  public void reset() {
    // Extend default state initialization to make maze as well. 
    super.reset();
    buildMaze(new Rect(0, 0, cols, rows)); // Assumes borders are already walls. 
    // what the heck was this doing here:
    // addBagel(rand.intBetween(1,cols), rand.intBetween(1,rows));
  }
	
  public void buildMaze (Rect box) {
    if ((box.bottom == box.top - 1) || (box.left == box.right - 1)) {
      // Do nothing
    } else {
      // Randomly choose between dividing horizontally and dividing vertically. 
      if (rand.flip()) {
	divideHorizontally(box);
      } else {
	divideVertically(box);
      }
    }
  }
	
  public void divideHorizontally (Rect box) {
    // System.out.println("divideHorizontally(" + box.toString() + ")");
    Point p  = randomHorizontalDivider(box);
    for (int x = box.left; x <= p.x - 1; x++) {
      addHorizontalWall(x, p.y);
    }
    for (int x = p.x + 1; x < box.right; x++) {
      addHorizontalWall(x, p.y);
    }
    buildMaze(new Rect(box.left, box.bottom, box.right, p.y));
    buildMaze(new Rect(box.left, p.y, box.right, box.top));
  } 
	
  public void divideVertically (Rect box) {
    // System.out.println("divideVertically(" + box.toString() + ")");
    Point p  = randomVerticalDivider(box);
    for (int y = box.bottom; y <= p.y - 1; y++) {
      addVerticalWall(p.x, y);
    }
    for (int y = p.y + 1; y < box.top; y++) {
      addVerticalWall(p.x, y);
    }
    buildMaze(new Rect(box.left, box.bottom, p.x, box.top));
    buildMaze(new Rect(p.x, box.bottom, box.right, box.top));
  } 
  
  public Point randomHorizontalDivider (Rect box) {
    // Return a point (x,y) such that:
    //  box.left <= x < box.right
    //  box.bottom < y < box.top
    return new Point(rand.intBetween(box.left, box.right - 1),
		     rand.intBetween(box.bottom + 1, box.top - 1));
  }
  
  public Point randomVerticalDivider (Rect box) {
    // Return a point (x,y) such that:
    //  box.left < x < box.right
    //  box.bottom <= y < box.top
    return new Point(rand.intBetween(box.left + 1, box.right - 1),
		     rand.intBetween(box.bottom, box.top - 1));
  }

}

class Rect {
  // A rectangle class better suited to this problem than the AWT's Rectangle
  
  public int left, bottom, right, top;
	
  public Rect(int l, int b, int r, int t) {
    left = l;
    bottom = b;
    right = r;
    top = t;
  }
	
  public String toString() {
    return "Rect[left=" + left 
      + "; bottom=" + bottom 
      + "; right=" + right
      + "; top=" + top
      + "]";
  }
	
}


