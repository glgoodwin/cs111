/*Gabrielle Goodwin
 * PS 6 task 2
 * 3/15/10
 * */

import java.awt.*;
import java.applet.Applet;
import java.util.*;

public class ColorTree extends PictureWorld
{
        /* Some useful abbreviations.
         * Feel free to add your own. 
         */
        static final Color blue = Color.blue;
        static final Color green = Color.green;
        static final Color orange = Color.orange;
        static final Color red = Color.red;
        static final Color yellow = Color.yellow;

        /* Permit applet to run as an applicaiton */
        public static void main (String[] args)
        {
                runAsApplication(new ColorTree(), "ColorTree"); 
        }
  
        public void initializePictureChoices()
        {
   
                addPictureChoice("oneBranchTree(orange, green)",
                                 oneBranchTree(orange, green));
                addPictureChoice("makeColorTree(red, yellow, blue, 0)",
                                 makeColorTree(red, yellow, blue, 0));
                addPictureChoice("makeColorTree(red, yellow, blue, 1)",
                                 makeColorTree(red, yellow, blue, 1));
                addPictureChoice("makeColorTree(red, yellow, blue, 2)",
                                 makeColorTree(red, yellow, blue, 2));
                addPictureChoice("makeColorTree(red, yellow, blue, 3)",
                                 makeColorTree(red, yellow, blue, 3));
                addPictureChoice("makeColorTree(red, yellow, blue, 4)",
                                 makeColorTree(red, yellow, blue, 4));
                addPictureChoice("makeColorTree(red, yellow, blue, 5)",
                                 makeColorTree(red, yellow, blue, 5));
                addPictureChoice("makeColorTree(red, yellow, blue, 6)",
                                 makeColorTree(red, yellow, blue, 6));
        }

        // Add your method(s) here
 

        // Primitive picture
 
        public Picture oneBranchTree(Color c1, Color c2)
        {
                Poly trunk = new Poly(c1, true);
                trunk.addPoint(0.4, 0.0);
                trunk.addPoint(0.4, 0.4);
                trunk.addPoint(0.5, 0.5);
                trunk.addPoint(0.6, 0.4);
                trunk.addPoint(0.6, 0.0);
   
                Poly rBranch = new Poly(c2, true);
                rBranch.addPoint(0.5, 0.5);
                rBranch.addPoint(0.7, 1.0);
                rBranch.addPoint(0.8, 1.0);
                rBranch.addPoint(0.6, 0.4);

                return overlay(rBranch, trunk);
        } 
        public Picture makeColorTree( Color c1, Color c2, Color c3, int levels){
          Picture tree = overlay(oneBranchTree(c1,c3),flipHorizontally(oneBranchTree(c1,c2)));// creates a level 1 tree with 2 branches
          if(levels>1){
            return above(beside(makeColorTree(c2,c3,c1,levels-1),makeColorTree(c3,c1,c2,levels-1)),tree);
            //creates a tree with a specified number of levels
          }else if(levels ==1){
            return tree;}
          return empty();// returns an empty picture when levels = 0
        }
            
}
         
