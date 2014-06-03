import java.awt.*;
/*
 * FILE NAME: LabRugWorld.java
 * AUTHOR:  CS111 staff
 * COMMENTS: Uses methods to draw the rug as shown in the lab2 page.
 * MODIFICATION HISTORY:
 * 
 * */
 
public class LabRugWorld extends BuggleWorld {
 
 public void setup () {
  this.setDimensions(18, 18);
 }
 
 public void run () {
     // note that weaver is a RugBuggle, not an ordinary Buggle
   RugBuggle weaver = new RugBuggle ();
   weaver.makeRug(Color.red, Color.blue, Color.yellow);

   
   
   //Test the method you define, one at a time:
   
   
   //Test the four basic methods, for which code is given to you,
   //so you become familiar with what they do.
  
   //weaver.drawPattern1(Color.yellow);
   //weaver.drawPattern2(Color.red);
   //weaver.drawPattern3(Color.red); 
   //weaver.drawPattern4(Color.blue);
   
   
  
 }

  // The following main method is needed to run the applet as an application
  public static void main (String[] args) {
    runAsApplication(new LabRugWorld(), "LabRugWorld"); 
  }
}
/**************************************************************/

  // RugBuggle class defines a top-level method that draws the whole rug.
  // The three rings of it will be colored c1, c2 and c3, 
  // from the outside to the inside
class RugBuggle extends Buggle {
  
  public void makeRug (Color c1, Color c2, Color c3) {
    System.out.println("Inside makeRug");
     this.drawPattern1(c1);
     this.forward(3);
     this.drop5Bagels(c1);
     this.drop5Bagels(c1);//worling on corner
     
    
   
   
    
    
    


  }
  
//Define your methods here
  
 

  
   

  //******** A few helper methods are defined for you here, in case you need them... *************
  
    //Moves the buggle forward n steps, then it turns it left. 
 //It leaves no trace behind
  public void fwLeft(int steps) {
    this.brushUp();
    this.forward(steps);
    this.left();
  }
  
  //Moves the buggle forward n steps, then it turns it right. 
 //It leaves no trace behind
  public void fwRight(int steps) {
    this.brushUp();
    this.forward(steps);
    this.right();
  }
  
  //draws a line of 5 squares, colored c, each one containing a bagel
  //Ends 5 squares ahead of its starting position, with the same heading
  public void drop5Bagels(Color c) {
    System.out.println("drop5Bagels");
    this.setColor(c);
    this.brushDown();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
  }
  
  //draws a line of 2 squares, colored c, each one containing a bagel
  //Ends 2 squares ahead of its starting position, 
  //with the same heading and its brush down
  public void drop2Bagels(Color c) {
    //draws a line of 2 bagels
    System.out.println("Inside drop2Bagels");
    this.setColor(c);
    this.brushDown();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
  }
  
    //This method is not called anywhere
  //draws a corner 3x3 of squares, colored c, each one containing a bagel
  //Ends with its heading and position the same as the initial heading
  //and position
  public void drawPattern1(Color c) {
    //System.out.println("Inside drawPattern1");
    this.setColor(c);
    this.drawPattern2(c);
    this.brushUp();
    this.backward(3);
    this.left();
    this.brushDown();
    this.forward();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.brushUp();
    this.backward(2);
    this.right();
  }
  
  
  
  //This method is not called anywhere
  //draws a line of 3 squares, colored c, each one containing a bagel
  //Ends 3 squares ahead of its starting position, 
  //with the same heading and its brush down
  public void drawPattern2(Color c) {
    //System.out.println("Inside drawPattern2");
    this.setColor(c);
    this.brushDown();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
    this.dropBagel();
    this.forward();
  }
  
  //draws pattern 3, as shown in the Lab 3 web page
  //Buggle finishes 3 blocks forward from its initial position,
  //with the same heading and its brush down
  public void drawPattern3(Color c) {
    //System.out.println("Inside drawPattern3");
    this.setColor(c);
    brushUp();
    paintCell(c);
    dropBagel();
    forward(2);
    left();
    forward();
    paintCell(c);
    dropBagel();
    forward();
    left();
    forward(2);
    paintCell(c);
    dropBagel();
    left();
    forward();
    forward();
    left();
    forward(3);
    brushDown();
  }
  
  //draws pattern 4, as shown in the Lab 3 web page:
  //a shape of an "X" made of 3x3 bagels
//Buggle finishes in its initial position, 
  //with the same heading as it started, and its brush up
  public void drawPattern4(Color c) {
    //System.out.println("Inside drawPattern4");
    this.setColor(c);
    brushUp();
    paintCell(c);
    dropBagel();
    forward(2);
    paintCell(c);
    dropBagel();
    left();
    forward();
    left();
    forward();
    paintCell(c);
    dropBagel();
    forward();
    right();
    forward();
    dropBagel();
    paintCell(c);
    right();
    forward(2);
    paintCell(c);
    dropBagel();
    right();
    forward(2);
    left();
    backward(2);
    //System.out.println("exiting drawPattern4");
  }
}