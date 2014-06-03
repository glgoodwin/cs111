// Counting the lines drawn by a turtle in an assymetric tree
// Created by Lyn on 03/10/03.

import java.awt.*;       // Import Abstract Window Toolkit

class TreeLinerSprite extends TurtleSprite {

	private double length;
	private double initLength;
	private double dLength;
	private double initXRatio;
	private double angle;
	private double shrinkL; 
	private double shrinkR;
	private double minLength;
	
	public TreeLinerSprite (double initLength, double dLength, double initXRatio, double angle, 
													double shrinkL, double shrinkR, double minLength,
													Color color) {
		this.initLength = initLength;
		this.length = initLength;
		this.dLength = dLength;
		this.initXRatio = initXRatio;
		this.angle = angle;
		this.shrinkL = shrinkL;
		this.shrinkR = shrinkR;
		this.minLength = minLength;
		setColor(color);
	}

	public void tree (double len) {
		if (len >= minLength) {
			fd(len);
			rt(angle);
			tree(len * shrinkR);
			lt(2*angle);
			tree(len * shrinkL);
			rt(angle);
			bd(len);
		}
	}
	
	public void drawState(Graphics g) {
		initGraphics(g);
		g.setColor(getColor());
		// Move to position in bottom center, facing up
		setHeading(90);
		setPosition((int) (-width/2 + initXRatio*width), (int) (-height/2));
		tree(length);
	}
	
	public void updateState() {
		length = length + dLength;
	}
	
	public void resetState() {
		length = initLength;
	}
 
}