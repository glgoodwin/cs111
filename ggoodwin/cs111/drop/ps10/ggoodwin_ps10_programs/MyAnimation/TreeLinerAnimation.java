import java.awt.*; // 

class TreeLinerAnimation extends Animation { 
	
	public TreeLinerAnimation() {
		TreeLinerSprite tl1 = new TreeLinerSprite(0,1,0.5,45,0.7,0.5,1,Color.pink);
		TreeLinerSprite tl2 = new TreeLinerSprite(0,0.8,0.7,40,0.6,0.6,1,Color.green);
		TreeLinerSprite tl3 = new TreeLinerSprite(0,0.9,0.3,25,0.45,0.65,1,Color.blue);
		TreeLinerSprite tl4 = new TreeLinerSprite(0,0.75,0.6,30,0.65,0.5,1,Color.magenta);
		this.addSprite(tl1);
		this.addSprite(tl2);
		this.addSprite(tl3);
		this.addSprite(tl4);
		this.setNumberFrames(Animation.NO_MAX_FRAMES);
		// this.setNumberFrames(50);
		// this.setNumberRepeats(2);
		// this.setNumberRepeats(Animation.FOREVER);
		// this.setDebugOn();
		// this.setInactive(r2,50);
		// this.setActive(r2,100);
		// this.setHidden(r1,20);
		// this.setVisible(r1,40);
	}
	
}
