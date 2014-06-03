// Subclass of Picture World used as an example

import java.awt.*;
import java.applet.Applet;
import java.util.*;

public class MyPictureWorld extends PictureWorld 
{

    //------------------------------------------------------------------------
    // The following method permits the applet to run as an application
  
    public static void main (String[] args)
    {
        runAsApplication(new MyPictureWorld(), "MyPictureWorld"); 
    }
    //------------------------------------------------------------------------
  
    public void initializePictureChoices() 
    {
        // Define your pictures here and in auxiliary methods.
        // Add your pictures to the menu using
        // addPictureChoice(String name, Picture pic);
          
        // Note: any method invocation "this.<method name>(<actual params>)"
        //       can be abbreviated as "<method name>(<actual params>)"
          
        Picture rp     = this.patch(Color.red);
        Picture bp     = this.patch(Color.blue);
        Picture gw     = this.wedge(Color.green);
        Picture mark   = this.checkmark(Color.red, Color.blue);
        Picture leaves = this.twoLeaves(Color.green);
        Picture chex   = fourPics(rp, bp, bp, rp);
          
        this.addPictureChoice("red patch",  rp);
        this.addPictureChoice("blue patch", bp);
        this.addPictureChoice("wedge",      gw);   
        this.addPictureChoice("mark",       mark); 
        this.addPictureChoice("leaves",     leaves); 
        this.addPictureChoice("chex",       chex); 
          
        // Below are some examples that you can test out.
        // Note that the "this." can be omitted in all the operations; 
        // we do this to enhance readability.
          
        // The following menu items are commented out so as not
        // to clutter up the menu.  But you can comment them back in 
        // to test them out. 
        /*
        // Tests of clockwise90(), clockwise180(), clockwise270(), 
        // flipHorizontally(), flipVertically(), and flipDiagonally().
           
        addPictureChoice("wedgeClockwise90",       clockwise90(gw));
        addPictureChoice("wedgeClockwise180",      clockwise180(gw));
        addPictureChoice("wedgeClockwise270",      clockwise270(gw));
        addPictureChoice("wedgeFlipHorizontally",  flipHorizontally(gw));
        addPictureChoice("wedgeFlipVertically",    flipVertically(gw));
        addPictureChoice("wedgeFlipDiagonally",    flipDiagonally(gw));
           
        addPictureChoice("markClockwise90",        clockwise90(mark));
        addPictureChoice("markClockwise180",       clockwise180(mark));
        addPictureChoice("markClockwise270",       clockwise270(mark));
        addPictureChoice("markFlipHorizontally",   flipHorizontally(mark));
        addPictureChoice("markFlipVertically",     flipVertically(mark));
        addPictureChoice("markFlipDiagonally",     flipDiagonally(mark));
           
        addPictureChoice("leavesClockwise90",      clockwise90(leaves));
        addPictureChoice("leavesClockwise180",     clockwise180(leaves));
        addPictureChoice("leavesClockwise270",     clockwise270(leaves));
        addPictureChoice("leavesFlipHorizontally", flipHorizontally(leaves));
        addPictureChoice("leavesFlipVertically",   flipVertically(leaves));
        addPictureChoice("leavesFlipDiagonally",   flipDiagonally(leaves));
        */
        /*
        // Tests of above() and beside()
           
        addPictureChoice("wedgeOverMark",          overlay(gw, mark));
        addPictureChoice("markOverWedge",          overlay(mark, gw));
        addPictureChoice("redBesideBlue",          beside(rp, bp));
        addPictureChoice("redAboveBlue",           above(rp, bp));
        addPictureChoice("redBesideBlue2",         beside(rp, bp, 0.75));
        addPictureChoice("redAboveBlue2",          above(rp, bp, 0.75));
        addPictureChoice("wedgeBesideMark",        beside(gw, mark));
        addPictureChoice("MarkBesideWedge",        beside(mark, gw));
        addPictureChoice("wedgeAboveMark",         above(gw, mark));
        addPictureChoice("MarkAboveWedge",         above(mark, gw));
        */
        /*
        // Tests of fourPics() and fourSame()
           
        addPictureChoice("blueWedgeCheckmarkRed",  fourPics(bp, gw, mark, rp));

        addPictureChoice("chex",    chex); 
        addPictureChoice("chex4",   fourSame(chex));
        addPictureChoice("chex16",  fourSame(fourSame(chex)));
        addPictureChoice("chex64",  fourSame(fourSame(fourSame(chex))));
        addPictureChoice("chex256",
                         fourSame(fourSame(fourSame(fourSame(chex)))));
           
        addPictureChoice("wedge4", fourSame(gw));
        addPictureChoice("mark4",  fourSame(mark));
           
        addPictureChoice("leaves on red grid", 
                         overlay(leaves, fourSame(fourSame(fourSame(rp)))));
        */  
          
        /*
        // Tests of rotations()
           
        addPictureChoice("wedgeRotations",  rotations(gw)); 
        addPictureChoice("markRotations",   rotations(mark));   
        addPictureChoice("chexRotations",   rotations(chex));  
        addPictureChoice("leavesRotations", rotations(leaves)); 
        */
          
        /*
        // Tests of rotations2()
           
        addPictureChoice("wedgeRotations2",  rotations2(gw)); 
        addPictureChoice("markRotations2",   rotations2(mark));   
        addPictureChoice("chexRotations2",   rotations2(chex));  
        addPictureChoice("leavesRotations2", rotations2(leaves)); 
        */
          
        /*
        // Tests of tiling()
           
        addPictureChoice("wedgeTiling",  tiling(gw)); 
        addPictureChoice("markTiling",   tiling(mark)); 
        addPictureChoice("chexTiling",   tiling(chex)); 
        addPictureChoice("leavesTiling", tiling(leaves));
        */
          
        /*
        // Tests of wallpaper()
           
        addPictureChoice("wedgeWallpaper",  wallpaper(gw)); 
        addPictureChoice("markWallpaper",   wallpaper(mark)); 
        addPictureChoice("markClockwise90Wallpaper", 
                         wallpaper(clockwise90(mark))); 
        addPictureChoice("chexWallpaper",   wallpaper(chex));
        addPictureChoice("leavesWallpaper", wallpaper(leaves));
        */
          
        /*
        // Tests of design()
           
        addPictureChoice("wedgeDesign",           design(gw)); 
        addPictureChoice("markDesign",            design(mark)); 
        addPictureChoice("markClockwise90Design", design(clockwise90(mark))); 
        addPictureChoice("chexDesign",            design(chex));
        addPictureChoice("leavesDesign",          design(leaves));
        */
          
        /*
        // Tests of UROverlay()
           
        Picture redTri = tri(Color.red);
        addPictureChoice("red triangle",        redTri);
        addPictureChoice("UROverlayRedTriBlue", UROverlay(redTri, bp));
        addPictureChoice("UROverlay4RedTri",    UROverlay4(redTri));
        addPictureChoice("redTri quilt",        UROverlayQuilt(redTri));
        addPictureChoice("wedge quilt",         UROverlayQuilt(gw));
        addPictureChoice("wedge clockwise90 quilt",
                         UROverlayQuilt(clockwise90(gw)));
        addPictureChoice("wedge clockwise180 quilt",
                         UROverlayQuilt(clockwise180(gw)));
        addPictureChoice("wedge clockwise270 quilt",
                         UROverlayQuilt(clockwise270(gw)));
        addPictureChoice("mark quilt",          UROverlayQuilt(mark));
        addPictureChoice("mark clockwise90 quilt",
                         UROverlayQuilt(clockwise90(mark)));
        addPictureChoice("leaves quilt",        UROverlayQuilt(leaves));
        */
    } // initializePictureChoices()
     
    // Some helpful auxiliary methods:
     
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

    public Picture rotations2 (Picture p) 
    {
        return fourPics(p, clockwise90(p), clockwise180(p), clockwise270(p));
    } 

    public Picture tiling (Picture p) 
    {
        return fourSame(fourSame(fourSame(fourSame(p))));
    } 

    public Picture wallpaper (Picture p) 
    {
        return rotations(rotations(rotations(rotations(p))));
    } 

    public Picture design (Picture p) 
    {
        return rotations2(rotations2(rotations2(rotations2(p))));
    }

    public Picture UROverlay (Picture p1, Picture p2) 
    {
        return overlay(fourPics(empty(), p1, empty(), empty()),
                       p2);
    }

    public Picture UROverlay4 (Picture p) 
    {
        Picture ur2 = UROverlay(p, p);
        return UROverlay(UROverlay(ur2, empty()), ur2);
    }

    public Picture UROverlayQuilt (Picture p) 
    {
        return rotations(UROverlay4(p));
    }                 
     
    // Below are methods for generating the pictures used above. 
     
    public Picture patch (Color c) 
    {
        return overlay (new Rect(0.0, 0.0, 1.0, 1.0, Color.black, false),
                        new Rect(0.0, 0.0, 1.0, 1.0, c, true));
    }

    public Picture wedge (Color c) 
    {
        Poly w = new Poly(c, true);
        w.addPoint(0.0, 0.0);
        w.addPoint(1.0, 0.0);
        w.addPoint(1.0, 0.5);
        return w;
    }

    public Picture checkmark (Color c1, Color c2) 
    {
        return overlay (new Line(0.0, 0.5, 0.5, 0.0, c1),
                        new Line(0.5, 0.0, 1.0, 1.0, c2));
    }

    public Picture tri (Color c) 
    {
        Poly t = new Poly(c, true);
        t.addPoint(0.0, 0.0);
        t.addPoint(0.0, 1.0);
        t.addPoint(1.0, 0.0);
        return t;
    }

    public Picture twoLeaves (Color c) 
    {
        Line branch = new Line(0.0, 0.0, 0.5, 0.5, c);
        Poly leaf1 = new Poly(c, true);

        // Add points in counterclockwise fashion. 
        // Two consecutive points will be connected by a line. 
        leaf1.addPoint(0.0, 0.0);
        leaf1.addPoint(0.5, 0.0);
        leaf1.addPoint(1.0, 0.25);
        leaf1.addPoint(0.5, 0.25);
        Poly leaf2 = new Poly(c, true);

        // Add points in counterclockwise fashion. 
        // Two consecutive points will be connected by a line. 
        leaf2.addPoint(0.25, 0.25);
        leaf2.addPoint(0.5,  0.625);
        leaf2.addPoint(0.5,  1.0);
        leaf2.addPoint(0.25, 0.625);

        // Challenge: instead of defining leaf1 and leaf2 from scratch, 
        // it is possible to define them both in terms of a single leaf 
        // picture. Can you figure out how?
        return overlay(leaf1, overlay(leaf2, branch));
    }
} // class MyPictureWorld
