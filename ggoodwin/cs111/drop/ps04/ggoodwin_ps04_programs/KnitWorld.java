/* Gabrielle Goodwin
 * CS 111 Ps 4
 * Task 2 Knit World
 */ 


import java.awt.*;
import java.applet.Applet;
import java.util.*;

// Making knitting patterns based on Escher's knitting primitives.
// See Doris Schattshneider, "M.C. Escher: Visions of Symmetry", pp 48--49.
public class KnitWorld extends PictureWorld
{
  
    //-------------------------------------------------------------------------
    // The following method permits the applet to run as an application
  
    public static void main (String[] args)
    {
        runAsApplication(new KnitWorld(), "KnitWorld"); 
    }
    //------------------------------------------------------------------------
  
    public void initializePictureChoices()
    {
        // Some helpful color abbreviations:
        Color black   = Color.black;
        Color blue    = Color.blue;
        Color cyan    = Color.cyan;   
        Color green   = Color.green;   
        Color magenta = Color.magenta;
        Color red     = Color.red;   
        Color white   = Color.white;
        Color yellow  = Color.yellow;   
      
        addPictureChoice("A white",  A(white,white,white,white,white));
        addPictureChoice("B white",  B(white,white,white,white,white));
        addPictureChoice("A colors", A(red,blue,green,yellow,magenta));
        addPictureChoice("B colors", B(red,blue,green,yellow,magenta));
    
        addPictureChoice("knit1(red, yellow)", knit1(red, yellow));
        addPictureChoice("knit1(yellow, red)", knit1(yellow, red));
        addPictureChoice("knit2(yellow, blue)", knit2(yellow, blue));    
        addPictureChoice("knit2(red, blue)", knit2(red, blue)); 
        addPictureChoice("knit3(blue, red, yellow)", knit3(blue, red, yellow));
        addPictureChoice("knit3(yellow, blue, red)", knit3(yellow, blue, red));
        addPictureChoice("knit4(blue, yellow)", knit4(blue, yellow));
        addPictureChoice("knit4(black, white)", knit4(black, white));
        addPictureChoice("knit4(red, red)", knit4(red, red));

        addPictureChoice("knit5(red, magenta, blue, green)",
                         knit5(red, magenta, blue, green));
        addPictureChoice("knit5(cyan, red, yellow, blue)",
                         knit5(cyan, red, yellow, blue));
        addPictureChoice("knit5(red, green, red, green)",
                         knit5(red, green, red, green)); 
/*
        addPictureChoice("knit6(black, blue)", knit6(black, blue));
        addPictureChoice("knit6(green, red)", knit6(green, red));
        addPictureChoice("knit6(red, green)", knit6(red, green));
*/
    } // initializePictureChoices()
  
    public Picture knit1(Color c1, Color c2)
    {
        Picture A1 = A(c1, c2, c1, c1, c2);
        Picture B1 = B(c2, c1, c2 ,c2, c1);
        return tileKnit(A1,B1,A1,B1);
    }
  
    public Picture knit2(Color c1, Color c2)
    {
        Picture A1 = A(c1, c2, c1, c2, c1);
        Picture B1 = B(c2, c1, c2 ,c1, c2);
        return tileKnit(flipVertically(B1),
                        flipVertically(A1),
                        clockwise180(B1),
                        clockwise180(A1));
    }
  
    public Picture knit3(Color c1, Color c2, Color c3)
    {
        return tileKnit(B(c1,c2,c1,c3,c1),
                        clockwise90(B(c1,c3,c2,c2,c1)),
                        flipHorizontally(B(c1,c3,c1,c2,c1)),
                        flipHorizontally(clockwise90(A(c1,c2,c3,c3,c1))));
    }

    public Picture knit4(Color c1, Color c2)
    {
        Picture A1 = A(c1, c2, c1, c1, c2);
        Picture B1 = B(c1, c2, c1, c2, c1);
        Picture A2 = A(c2, c1, c2, c1, c2);
        Picture B2 = B(c2, c1, c2, c2, c1);
        Picture A1_clockwise270 = clockwise270(A1);
        Picture B1_flipHorizontally = flipHorizontally(B1);
        Picture B2_flipDiagonally = flipDiagonally(B2);
        Picture B2_clockwise270 = clockwise270(B2);
        Picture A2_flipHorizontally = flipHorizontally(A2);
        Picture A1_flipDiagonally = flipDiagonally(A1);
    
        Picture tile1 = fourPics(B1, A1_clockwise270,
                                 B1_flipHorizontally, B2_flipDiagonally);
        Picture tile2 = fourPics(A2, B2_clockwise270,
                                 A2_flipHorizontally, A1_flipDiagonally);

        return fourSame(fourPics(tile1, tile2, tile1, tile2));
    }
  
    public Picture knit4_alternative(Color c1, Color c2)
    {
        Picture tile1 = fourPics(B(c1, c2, c1, c2, c1),
                                 clockwise270(A(c1, c2, c1, c1, c2)),
                                 flipHorizontally(B(c1, c2, c1, c2, c1)),
                                 flipDiagonally(B(c2,c1,c2,c2,c1)));
        Picture tile2 = fourPics(A(c2, c1, c2, c1, c2),
                                 clockwise270(B(c2, c1, c2, c2, c1)),
                                 flipHorizontally(A(c2, c1, c2, c1, c2)),
                                 flipDiagonally(A(c1,c2, c1, c1, c2)));

        return fourSame(fourPics(tile1, tile2, tile1, tile2));
    }
    public Picture knit5(Color c1, Color c2, Color c3, Color c4){ // creates the knit 5 pattern for any given color
      Picture UL = A(c1, c2, c3, c3, c4);
      Picture UR = flipVertically(B(c4, c3, c2, c2, c1));
      Picture LL = A(c3, c4, c1, c1, c2);
      Picture LR = flipVertically(B(c2, c1, c4, c4, c3));
      return tileKnit(UL,UR,LL,LR); //implements the tileKnit method
        
      
    }


  
    // Knitting auxiliary functions
  
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

} // class KnitWorld

