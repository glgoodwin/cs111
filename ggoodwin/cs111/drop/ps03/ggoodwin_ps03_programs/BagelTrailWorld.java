// [lyn, 9/29/07] Added toString() method to Rect.

// [lyn, 8/31/07 -- 9/1/07] 
// * Added static main() method for running as an application. 
// * Changed occurrences of markTrail() to use Location rather than Point
// * Adapted to new setup()/reset() model.

import java.awt.*;
import java.util.*;

public class BagelTrailWorld extends BuggleWorld {
	
  private Random rand = new Random(1515); // Set seed for repeatability; Empirically, 1111 gives a good first path.
  private boolean showDividers = false;

  public static void main (String[] args) {
    runAsApplication(new BagelTrailWorld(), "BagelTrailWorld"); 
  }
		
  public void reset() {
    // Extend default state initialization to make bagel trail as well. 
    super.reset();
    makeBagelTrail();
  }
	
  public void makeBagelTrail() {
    int targetX = oddBetween(1, cols);
    int targetY = oddBetween(1, rows);
    bagelPath(new Point(1,1), new Point(targetX, targetY), new Rect(1,1,targetX,targetY));
  }
	
  public void bagelPath (Point source, Point sink, Rect box) {
    if (source.x == sink.x) {
      verticalBagelLine(source.x, source.y, sink.y);
    } else if (source.y == sink.y) {
      horizontalBagelLine(source.x, sink.x, sink.y);
    } else {
      // Randomly choose between dividing horizontally and dividing vertically. 
      if (flip()) {
	divideHorizontally(source, sink, box);
      } else {
	divideVertically(source, sink, box);
      }
    }
  }
	
  public void divideHorizontally (Point source, Point sink, Rect box) {
    Point h  = randomHorizontalDivider(box);
    // h has an odd x and an even y
    // If source and sink are on same side of divide, don't go through door;
    // if source and sink are different sides of divide, go through door.
    if ((source.y < h.y) && (sink.y < h.y)) {
      bagelPath(source, sink, new Rect(box.left, box.bottom, box.right, h.y - 1));
    } else if ((source.y > h.y) && (sink.y > h.y)) {
      bagelPath(source, sink, new Rect(box.left, h.y + 1, box.right, box.top));
    } else {
      if (showDividers) {
	for (int x = box.left; x <= h.x - 1; x++) {
	  markTrail(new Location(x,h.y), Color.blue);
	}
	for (int x = h.x + 1; x <= box.right; x++) {
	  markTrail(new Location(x,h.y), Color.blue);
	}
      }
      bagelPath(source, 
		new Point(h.x, h.y - 1), 
		new Rect(box.left, box.bottom, box.right, h.y - 1));
      addBagel(h.x, h.y);
      bagelPath(new Point(h.x, h.y + 1),
		sink,
		new Rect(box.left, h.y + 1, box.right, box.top));
    }
  } 
  
  public void divideVertically (Point source, Point sink, Rect box) {
    Point v = randomVerticalDivider(box);
    // v has an even x and an odd y.
    // If source and sink are on same side of divide, don't go through door;
    // if source and sink are different sides of divide, go through door.
    if ((source.x < v.x) && (sink.x < v.x)) {
      bagelPath(source, sink, new Rect(box.left, box.bottom, v.x - 1, box.top));
    } else if ((source.x > v.x) && (sink.x > v.x)) {
      bagelPath(source, sink, new Rect(v.x + 1, box.bottom, box.right, box.top));
    } else {
      if (showDividers) { 
	for (int y = box.bottom; y <= v.y - 1; y++) {
	  markTrail(new Location(v.x, y), Color.blue);
	}
	for (int y = v.y + 1; y <= box.top; y++) {
	  markTrail(new Location(v.x, y), Color.blue);
	}
      }
      bagelPath(source, 
		new Point(v.x - 1, v.y), 
		new Rect(box.left, box.bottom, v.x - 1, box.top));
      addBagel(v.x, v.y);
      bagelPath(new Point(v.x + 1, v.y),
		sink,
		new Rect(v.x + 1, box.bottom, box.right, box.top));
    }
  }
	
  public void verticalBagelLine(int x, int y1, int y2) {
    // Add a vertical line of bagels
    for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
      addBagel(x, y);
    }
  }
	
  public void horizontalBagelLine(int x1, int x2, int y) {
    // Add a horizontal line of bagels
    for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
      addBagel(x, y);
    }
  }
	
  public Point randomHorizontalDivider (Rect box) {
    // Assume box has odd x & y coords at all corners.
    // Return a point in the box with an odd x and an even y
    return new Point(oddBetween(box.left, box.right), evenBetween(box.bottom, box.top));	
  }
	
  public Point randomVerticalDivider (Rect box) {
    // Assume box has odd x & y coords at all corners.
    // Returns a point in the box with an even x and an odd y
    return new Point(evenBetween(box.left, box.right), oddBetween(box.bottom, box.top));	
  }


  public boolean isEven (int n) {
    return (n % 2) == 0;
  }
	
  public boolean isOdd (int n) {
    return (n % 2) == 1;
  }

  public int randomBetween (int lo, int hi) {
    return lo + (Math.abs(rand.nextInt()) % (hi + 1 - lo));
  }
  
  public boolean flip () {
    return randomBetween(0,1) == 0;
  }

  public int evenBetween (int lo, int hi) {
    // Assume hi > lo. 
    // Return an even number between lo and hi, inclusive;
    int evenLo, evenHi;
    if (isEven(lo))
      evenLo = lo;
    else
      evenLo = lo + 1;
    if (isEven(hi))
      evenHi = hi;
    else
      evenHi = hi - 1;
    return evenLo + (2 * randomBetween(0, (evenHi - evenLo) / 2));
  }
 	
  public int oddBetween (int lo, int hi) {
    // Assume hi > lo. 
    // Return an odd number between lo and hi, inclusive;
    int oddLo, oddHi;
    if (isOdd(lo))
      oddLo = lo;
    else
      oddLo = lo + 1;
    if (isOdd(hi))
      oddHi = hi;
    else
      oddHi = hi - 1;
    return oddLo + (2 * randomBetween(0, (oddHi - oddLo) / 2));
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


