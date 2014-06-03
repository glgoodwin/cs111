// CS111 Arrays Lab
//Modified by stella Nov 07:
//Results are printed on Console now.
//main() was added
//switch statements were turned into ifs, so students can understand them, if they decide 
//to look at the definition of FreezeBuggle class
//other minor modifications to the code
// Modified by Sohie nov0703
// Created by Elena Machkasova

import java.awt.*;

public class FreezeTag extends RandomBagelBuggleWorld {
  FreezeBuggle [] players; //instance variable
  
//------------------------------------------------------------------------------
// The following main method is needed to run the applet as an application
  
  public static void main (String[] args) {
    runAsApplication(new FreezeTag(), "FreezeTag"); 
  }
//--------------------------------------------------------------------------------
  
  public void run () {
    // variables "buggles" and "rounds" are 
    //read from the ParameterFrame and intialized in RandomBagelBuggleWorld
    //bugels is the number of buggles 
    
    // create an array of FreezeBuggles participitating in the game

    // play the given number of rounds
  
    
    //print the results in Console
    
  }
  
  
 
  
 
  
 
  

} //FreezeTag


//****************************************************************
// The FreezeBuggle class definition does not need to be altered
class FreezeBuggle extends Buggle {
  Randomizer rand = new Randomizer();
  
  //Sets the position of the invoking buggle to
  // a random one, within a grid of dimensions w and h
  public void randomPosition(int w, int h)  {
    int x = rand.intBetween(1,w);
    int y = rand.intBetween(1,h);
    setPosition(new Location(x, y));
  }
  
  //sets the color of the invoking buggle to 
  //one of the folloowing colors, randomly:
  // red, yellow, green, cyan or magenta.
  public void randomColor()  {
    Color c = Color.red;
    int r = rand.intBetween(1, 5);
    
    if (r == 1) {
      setColor(Color.red);
      return;
    }
    if (r == 2) {
      setColor(Color.yellow);
      return;
    }
    if (r == 3) {
      setColor(Color.green);
      return;
    }
    if (r == 4) {
      setColor(Color.cyan);
      return;
    }
    if (r == 5) {
      setColor(Color.magenta);
      return;
    }
  }
  
  //Changes the direction of the invoking buggle
  // in a random fashion
  public void randomTurn() {
    int t = rand.intBetween(1, 4);
    if (t == 1) left();
    if (t == 2) left(); left();
    if (t == 3) right();
    if (t == 4) ; // don't turn, in this case
  }
} //FreezeBuggle



