/* Gabrielle Goodwin
 * CS 111 PS 4
 * Task 3 quilt world
 */ 


import java.awt.*;
import java.applet.Applet;
import java.util.*;

public class QuiltWorld extends PictureWorld
{
    //-------------------------------------------------------------------------
    // The following method permits the applet to run as an application

    public static void main (String[] args)
    {
        runAsApplication(new QuiltWorld(), "QuiltWorld"); 
    }
    //-------------------------------------------------------------------------
  
    public void initializePictureChoices()
    {
        addPictureChoice("quilt1()", quilt1());
        addPictureChoice("quilt2()", quilt2());
        addPictureChoice("patch(Color.red)", patch(Color.red));
        addPictureChoice("triangles(Color.red, Color.blue)",
                         triangles(Color.red, Color.blue));

        addPictureChoice("patch_2x2(Color.red)", patch_2x2(Color.red));
        addPictureChoice("triangles_2x2(Color.red, Color.blue)",
                         triangles_2x2(Color.red, Color.blue));
        addPictureChoice("LL(triangles(Color.red, Color.blue))", 
                          LL(triangles(Color.red, Color.blue)));
        addPictureChoice("LLNest(patch(Color.red), "
                         + "triangles(Color.red, Color.blue))",
                         LLNest(patch(Color.red),
                                triangles(Color.red, Color.blue)));

        addPictureChoice("corner(Color.red, Color.blue, Color.green, "
                         + "Color.darkGray,  Color.cyan, Color.magenta)", 
                         corner(Color.red, Color.blue, Color.green,
                                Color.darkGray, Color.cyan, Color.magenta));
        addPictureChoice("quadrant1(Color.red, Color.blue, Color.green, "
                         + "Color.darkGray, Color.cyan)", 
                         quadrant1(Color.red, Color.blue, Color.green,
                                   Color.darkGray, Color.cyan));
        addPictureChoice("quadrant2(Color.blue, color.green, Color.darkGray, "
                         + "Color.cyan, Color.magenta)", 
                         quadrant2(Color.blue, Color.green, Color.darkGray,
                                   Color.cyan, Color.magenta));
        addPictureChoice("quadrant3(Color.magenta, Color.darkGray)",
                         quadrant3(Color.magenta, Color.darkGray));

    } // initializePictureChoices()

    // Original quilt
    public Picture quilt1 ()
    { return rotations(corner(Color.red, Color.blue, Color.green,
                                Color.darkGray, Color.cyan, Color.magenta)); 
    }

    // This is the quilt2 shown at the bottom of the ps writeup.
    // Do this method last.
    public Picture quilt2 ()
    { 
      return rotations(corner(Color.blue, Color.green, Color.red, Color.green, Color.red, Color.blue));
    }
 
    public Picture corner (Color c1, Color c2, Color c3, Color c4,
                           Color c5, Color c6)
    { return fourPics(quadrant2(c2,c3,c4,c5,c6),quadrant3(c6,c4),quadrant1(c1,c2,c3,c4,c5),
                      quadrant2(c2,c3,c4,c5,c6));
    }
 
    public Picture quadrant1 (Color c1, Color c2, Color c3,
                              Color c4, Color c5)
    { 
      Picture TL = fourPics(patch_2x2(c1),patch_2x2(c1),patch_2x2(c3),patch_2x2(c3));//makes a 4x4 patch
      Picture TR = greenCorner(c2,c3,c4,c5);
      Picture BL = fourPics(triangles_2x2(c3,c1),checkerPatch(c1,c2),triangleLL(c1,c2,c3),triangles_2x2(c3,c1));
      Picture BR = clockwise90(TL);
                                                                                      
      return fourPics(TL,TR,BL,BR);

    }

    public Picture quadrant2 (Color c2, Color c3, Color c4,
                              Color c5, Color c6)
    { Picture TR = LLNest(fourSame(triangles_2x2(c5,c6)),triangles_2x2(c4,c5));
      Picture TL = fourSame(triangles_2x2(c5,c6));
      Picture BL = greenCorner(c2,c3,c4,c5);
      Picture BR = TR;                     
      return fourPics(TR, TL, BL, BR);
    }

    public Picture quadrant3 (Color tri1, Color tri2)
    {
      return LLNest(triangles(tri1,tri2),(LLNest(triangles(tri1,tri2),
                                                 LLNest(triangles(tri1,tri2),triangles(tri1,tri2)))));
      }

    // Auxiliary methods 
    public Picture greenCorner(Color c2, Color c3, Color c4, Color c5){
      return fourPics(triangles_2x2 (c2,c4), triangles_2x2(c4,c5), patch_2x2(c3),
               triangles_2x2(c2,c4));
    }
    public Picture triangleLL(Color c1,Color c2,Color c3){//used in quadrant 3
      return LLNest((fourPics(patch(c3),patch(c2),empty(),patch(c3))),(triangles(c1,c2)));
    }
    public Picture checkerPatch(Color c1,Color c2){ // creates a 2x2 checker pattern...quadrant 3
      return fourPics(patch(c1),patch(c2),patch(c2),patch(c1));
                      }
   
    

    //  Insert your definitions of the auxiliary methods specified in the 
    //  assignment here:
     public Picture patch_2x2 (Color c){//returns a 2x2 patch of specified color
      return fourSame(patch(c));
     }
     
     public Picture triangles_2x2 (Color c1, Color c2){
      return fourPics(triangles(c1,c2),patch(c2),patch(c1),triangles(c1,c2)); 
     }
     
     public Picture LL (Picture p){
       return fourPics(empty(),empty(),p,empty());
       }
     
     public Picture LLNest (Picture p1, Picture p2){
       return overlay(LL(p2),p1);
     }
    
       
     
                         
                                                                       


    // QuiltWorld auxiliary methods -- already defined for you 
  
    public Picture fourPics (Picture p1, Picture p2, Picture p3, Picture p4)
    {
        return above(beside(p1, p2), beside(p3, p4));
    }
  
    public Picture fourSame (Picture p)
    {
        return fourPics(p, p, p, p);
    }
  
    public Picture rotations (Picture p)
    {
        return fourPics(clockwise270(p), p, clockwise180(p), clockwise90(p));
    }
  
    public Picture LLcorner (Picture p1, Picture p2)
    {
        return fourPics(p1, p1, p2, p1);
    }
  
 
  
    // Methods for generating the primitive pictures for this problem. 
    // You do not have to understand these in order to do the problem set. 
  
    public Picture patch(Color c)
    {
        return overlay (new Rect(0.0, 0.0, 1.0, 1.0, Color.black, false),
                        new Rect(0.0, 0.0, 1.0, 1.0, c, true));
    }
  
    public Picture triangles(Color c1, Color c2)
    {
        return overlay (new Rect(0.0, 0.0, 1.0, 1.0, Color.black, false),
                        overlay (new Line(0.0, 1.0, 1.0, 0.0, Color.black),
                                 overlay(tri(c1, true),
                                         clockwise180(tri(c2, true)))));
                                    
    }
  
    public Picture tri(Color c, boolean isFilled)
    {
        Poly triPoly = new Poly (c, isFilled);
        triPoly.addPoint(0.0, 0.0);
        triPoly.addPoint(0.0, 1.0);
        triPoly.addPoint(1.0, 0.0);
        return triPoly;
    }
  
} // class QuiltWorld