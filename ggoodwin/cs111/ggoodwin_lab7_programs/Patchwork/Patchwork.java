import java.awt.*;
import java.applet.Applet;
import java.util.*;

public class Patchwork extends PictureWorld {

 
  //------------------------------------------------------------------------------
  // The following main method is needed to run the applet as an application

  public static void main (String[] args) {
    runAsApplication(new Patchwork(), "Patchwork"); 
  }

	public void initializePictureChoices() {
		this.addPictureChoice("patchQuilt0",
		   patchwork(0, Color.red, Color.yellow, Color.blue, Color.green));
		this.addPictureChoice("patchQuilt1",
		   patchwork(1, Color.red, Color.yellow, Color.blue, Color.green));
		this.addPictureChoice("patchQuilt2",
		   patchwork(2, Color.red, Color.yellow, Color.blue, Color.green));
		this.addPictureChoice("patchQuilt3",
		   patchwork(3, Color.red, Color.yellow, Color.blue, Color.green));
		this.addPictureChoice("patchQuilt4",
		   patchwork(4, Color.red, Color.yellow, Color.blue, Color.green));
		this.addPictureChoice("patchQuilt5",
		   patchwork(5, Color.red, Color.yellow, Color.blue, Color.green));
		this.addPictureChoice("patchQuilt6",
		   patchwork(6, Color.red, Color.yellow, Color.blue, Color.green));
	} // initializePictureChoices()
	
	public Picture patchwork(int levels, Color c1, Color c2, Color c3, Color c4){
		// Replace this stub
		return empty();
	} // patchwork()


			
	// These are the only auxiliary methods that you should need for this program
	
	public Picture fourPics (Picture p1, Picture p2, Picture p3, Picture p4) {
		return above(beside(p1,p2), beside(p3,p4));
	} // fourPics()

 	public Picture patch (Color c) {
 		return overlay (new Rect(0.0, 0.0, 1.0, 1.0, Color.black, false),
 		                 new Rect(0.0, 0.0, 1.0, 1.0, c, true));
 	} // patch()
 	
} // class Patchwork
