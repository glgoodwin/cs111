/* FILE NAME: LabQuiltWorld.java
 * AUTHOR: CS111 Staff
 * DATE: 
 * COMMENTS: Uses methods to draw the picture as shown in the lab4 page.
 * MODIFICATION HISTORY: 
*/
import java.awt.*;
import java.applet.Applet;
import java.util.*;

public class LabQuiltWorld extends PictureWorld {
  //------------------------------------------------------------------------------
  // The following main method is needed to run the applet as an application

  public static void main (String[] args) {
    runAsApplication(new LabQuiltWorld(), "LabQuiltWorld"); 
  }
  //------------------------------------------------------------------------------

 public void initializePictureChoices() {
 
  // Some helpful color abbreviations:
  Color blue = Color.blue;
  Color red = Color.red;
  Color green = Color.green;
  Color yellow = Color.yellow;
  
  addPictureChoice("0: patch(red)", patch(red));
  addPictureChoice("0:triangles(red,blue)", triangles(red,blue));
  
  
  //addPictureChoice("makeSquare4x4 blue& green", makeSquare4x4(blue, green));
  addPictureChoice("makeQuilt blue, green & red", makeQuilt(blue, green, red));
  
  //addPictureChoice("makeQuilt blue, green & red", makeQuilt(blue, green, red));
  //addPictureChoice("makeQuilt pink, green & yellow", makeQuilt(Color.pink, green, yellow));
 }
 
 //Define your methods here
 public Picture makeQuilt(Color c1, Color c2, Color c3){
      Picture P1 = rotations(triangles(c2,c1));
      Picture P2 = rotations(triangles(c3,c1));
      return rotations(corner (P2, P1));
      
 }
 public Picture quadrant1(Color c1, Color c2, Color c3, Color c4){
      Picture UL= patch(c1);
      return UL;
 }
 

 
 
 
 
  //*******************************************************************      
 // Auxiliary methods 
  
 public Picture fourPics (Picture p1, Picture p2, Picture p3, Picture p4) {
  return above(beside(p1, p2), beside(p3, p4));
 }
  
  public Picture fourSame (Picture p) {
  return fourPics(p, p, p, p);
  }
  
  public Picture rotations (Picture p) {
  return fourPics(clockwise270(p), p, clockwise180(p), clockwise90(p));
  }
  
  public Picture corner (Picture p1, Picture p2) {
  return fourPics(p2, p2, p1, p2);
  }
  
  // Methods for generating the primitive pictures for this problem. 
  // You do not have to understand these in order to do the problem set. 
  
  public Picture patch(Color c) {
   return overlay (new Rect(0.0, 0.0, 1.0, 1.0, Color.black, false),
                    new Rect(0.0, 0.0, 1.0, 1.0, c, true));
  }
  
  // added by Elena Feb. 20 2002
  public Picture triangle(Color c) {
   return tri(c, true);
  }
  
  public Picture triangles(Color c1, Color c2) {
   return overlay (new Rect(0.0, 0.0, 1.0, 1.0, Color.black, false),
                    overlay (new Line(0.0, 1.0, 1.0, 0.0, Color.black),
                             overlay(tri(c1, true),
                                     clockwise180(tri(c2, true)))));
                                    
  }
  
  public Picture tri(Color c, boolean isFilled) {
   Poly triPoly = new Poly (c, isFilled);
   triPoly.addPoint(0.0, 0.0);
   triPoly.addPoint(0.0, 1.0);
   triPoly.addPoint(1.0, 0.0);
   return triPoly;
  }
  
}
