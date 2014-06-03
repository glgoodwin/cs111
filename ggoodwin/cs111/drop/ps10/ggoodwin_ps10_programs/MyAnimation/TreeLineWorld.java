// Counting the lines drawn by a turtle in an assymetric tree
// Created by Lyn on 03/10/03.

import java.awt.*;       // Import Abstract Window Toolkit
import java.applet.*;    // Import Applet stuff

public class TreeLineWorld extends TurtleWorld {

	String parameterNames [] = {"Length", "Angle", "ShrinkLeft", "ShrinkRight", "MinLength"};
	String resultNames [] = {"Lines"};
	ParameterFrame params;
	
	public void setup() {
		params = new ParameterFrame("Tree Parameters",200, 100, parameterNames, resultNames);
		params.setDoubleParam("Length", 150);
		params.setDoubleParam("Angle", 45);
		params.setDoubleParam("ShrinkLeft", 0.7);
		params.setDoubleParam("ShrinkRight", 0.5);
		params.setDoubleParam("MinLength", 3);
		params.show();
	}
		
	public void run() {
		TreeLiner trellaine = new TreeLiner();
		// Move to position in bottom center, facing up
    	trellaine.pu();
		trellaine.lt(90);
		trellaine.bd((this.size().height)/2);
		trellaine.pd();
		
		// Draw tree
		int lines = trellaine.tree(params.getDoubleParam("Length"), 
				       			   params.getDoubleParam("Angle"), 
				                   params.getDoubleParam("ShrinkLeft"),
				                   params.getDoubleParam("ShrinkRight"),
				                   params.getDoubleParam("MinLength")
				                   );
		params.setIntResult("Lines", lines);
	}
	  
}


class TreeLiner extends Turtle {

	public int tree (double length, double angle, double shrinkL, 
	                 double shrinkR,double min) {
		if (length < min) {
			return 0; 
		} else {
			fd(length);
			rt(angle);
			tree(length * shrinkR, angle, shrinkL, shrinkR, min);
			lt(2*angle);
			tree(length * shrinkL, angle, shrinkL, shrinkR, min);
			rt(angle);
			bd(length);
			return 0;
		}
	}
 
}