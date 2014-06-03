/* FILE NAME: LabKnitWorld.java
 * AUTHOR: Sohie
 * DATE: Sept 21 2009
 * COMMENTS: Uses methods to draw the picture as shown in the lab4 page.
 * MODIFICATION HISTORY: Based on older, similar file...
*/
import java.awt.*;
import java.applet.Applet;
import java.util.*;

public class LabKnitWorld extends PictureWorld {
// Making knitting patterns based on Escher's knitting primitives.
// See Doris Schattshneider, "M.C. Escher: Visions of Symmetry", pp 48--49.

  
  //------------------------------------------------------------------------------
  // The following main method is needed to run the applet as an application

  public static void main (String[] args) {
    runAsApplication(new LabKnitWorld(), "LabKnitWorld"); 
  }
  //------------------------------------------------------------------------------

 public void initializePictureChoices() {
 // Define your pictures here and in auxiliary methods.
 // Add your pictures to the menu via "addPictureChoice(String name, Picture pic);"
 
   // Some helpful color abbreviations:
   Color black = Color.black;
   Color blue = Color.blue;
   Color cyan = Color.cyan;   
   Color green = Color.green;   
   Color magenta = Color.magenta;
   Color red = Color.red;   
   Color white = Color.white;
   Color yellow = Color.yellow;   
      
    addPictureChoice("A white", A(white,white,white,white,white));
    addPictureChoice("B white", B(white,white,white,white,white));
    addPictureChoice("A colors", A(red,blue,green,yellow,magenta));
    addPictureChoice("B colors", B(red,blue,green,yellow,magenta));
    
   //start looking at the pictures, so you become familiar and make sure you have the basic
   //builting blocks correct, before you put them together
   
    //addPictureChoice("Afh180 blue, magenta", flipHorizontally(clockwise180(A(magenta, magenta,blue, blue, blue))));


    addPictureChoice("labKnit1(red,green)", labKnit1(red,green));
    addPictureChoice("labKnit2(magenta,blue)", labKnit2(magenta,blue));

    
    //addPictureChoice("labKnit2(magenta,blue)", labKnit2(magenta,blue));
   

    //addPictureChoice("labKnit3(blue,red,cyan,magenta)-top left", labKnit3(blue,red,cyan,magenta));
    //addPictureChoice("labKnit3(blue,red,cyan,magenta)-top right", labKnit3(blue,red,cyan,magenta));
    
    //addPictureChoice("labKnit3(blue,red,cyan,magenta) ", labKnit3(blue,red,cyan,magenta));
  }
  
  //Skeleton for writing the method for labKnit1 pattern
 public Picture labKnit1 (Color c1, Color c2) {
      Picture UL = A(c1,c2,c1,c1,c2);
      Picture UR = A(c2,c1,c2,c2,c1);
      Picture LL = A(c1,c2,c1,c1,c2);
      Picture LR = A(c2,c1,c2,c2,c1);
       return fourSame(fourSame(fourPics(UL,UR, LL, LR)));
 }
       
 public Picture labKnit2 (Color c1, Color c2) {
      Picture UL = clockwise180(B(c2,c2,c1,c1,c1));
      Picture UR = flipHorizontally(A(c1,c1,c2,c2,c2));
      Picture LL = B(c2,c2,c1,c1,c1);
      Picture LR = flipVertically(A(c1,c1,c2,c2,c2));
      return fourSame(fourSame(fourPics(UL,UR,LL,LR)));
    


 }



 //****************************************************************
// Knitting auxiliary methods
  
  public Picture tileKnit (Picture p1, Picture p2, Picture p3, Picture p4) {
   return fourSame(fourSame(fourPics(p1, p2, p3, p4)));
  }
  
  public Picture fourSame (Picture p) {
   return fourPics(p, p, p, p);
  }
  
  public Picture fourPics (Picture p1, Picture p2, Picture p3, Picture p4) {
   return above(beside(p1,p2), beside(p3,p4));
  }
    
  // Knitting primitives. You do not have to understand these in order to 
  // do the problem set. 

 public Picture A(Color c1, Color c2, Color c3, Color c4, Color c5) {
  return
  overlay(poly1(c1),
          overlay(poly2(c2),
                  overlay(poly3(c3),
                          overlay(poly4(c4),
                                  overlay(poly5(c5),
                                          border(Color.black))))));
 }
 
 public Picture B(Color c1, Color c2, Color c3, Color c4, Color c5) {
  return
  overlay(poly3(c3),
          overlay(poly2(c2),
                  overlay(poly1(c1),
                          overlay(poly4(c4),
                                  overlay(poly5(c5),
                                          border(Color.black))))));
 }
 
 public Rect border (Color c) {
  return new Rect (0.0, 0.0, 1.0, 1.0, c, false);
 }
  
  public Picture poly1(Color c) {
  Poly poly = new Poly (c, true);
  poly.addPoint(0.4, 0.0);
  poly.addPoint(0.6, 0.0);
  poly.addPoint(0.2, 1.0);
  poly.addPoint(0.0, 1.0);
  poly.addPoint(0.0, 0.8);
   return overlay(new Line (0.6, 0.0, 0.2, 1.0, Color.black),
                   overlay(new Line (0.4, 0.0, 0.0, 0.8, Color.black),
                           poly));
  }
  
   public Picture poly2(Color c) {
  Poly poly = new Poly (c, true);
  poly.addPoint(0.0, 0.6);
  poly.addPoint(0.0, 0.4);
  poly.addPoint(1.0, 0.8);
  poly.addPoint(1.0, 1.0);
  poly.addPoint(0.8, 1.0);
   return overlay(new Line (0.0, 0.4, 1.0, 0.8, Color.black),
                   overlay(new Line (0.0, 0.6, 0.8, 1.0, Color.black),
                           poly));
  }
  
   public Picture poly3(Color c) {
  Poly poly = new Poly (c, true);
  poly.addPoint(0.4, 1.0);
  poly.addPoint(1.0, 0.4);
  poly.addPoint(1.0, 0.6);
  poly.addPoint(0.6, 1.0);
   return overlay(new Line (0.4, 1.0, 1.0, 0.4, Color.black),
                   overlay(new Line (0.6, 1.0, 1.0, 0.6, Color.black),
                           poly));
  }
  
  public Picture poly4(Color c) {
  Poly poly = new Poly (c, true);
  poly.addPoint(0.0, 0.0);
  poly.addPoint(0.2, 0.0);
  poly.addPoint(0.0, 0.2);
   return overlay(new Line (0.2, 0.0, 0.0, 0.2, Color.black),
                   poly); 
 }
  
  public Picture poly5(Color c) {
  Poly poly = new Poly (c, true);
  poly.addPoint(0.8, 0.0);
  poly.addPoint(1.0, 0.0);
  poly.addPoint(1.0, 0.2);
  return overlay(new Line (0.8, 0.0, 1.0, 0.2, Color.black),
                   poly); 
 }
    //------------------------------------------------------------------------------
  // The following main method is needed to run the applet as an application

}
