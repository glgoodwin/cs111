import java.awt.*; // 

class TogetherAnimation extends Animation
{ 
    public TogetherAnimation()
    {
        this.addSprite(new Pulsar(200,50,20,40,1,Color.red));
        this.addSprite(new Pulsar(300,200,0,200,5,Color.blue));
        this.addSprite(new Rotator(400,200,100,30,5,Color.pink));
        this.addSprite(new Rotator(300,275,75,270,-2,Color.gray));
        this.addSprite(new BouncingBall(50,50,30,2,2,Color.magenta));
        this.addSprite(new BouncingBall(150,150,25,3,-3,Color.cyan));
        this.addSprite(new BouncingBall(250,250,40,1,4,Color.green));
        this.addSprite(new TreeLinerSprite(0,1,0.5,45,0.7,0.5,1,Color.pink));
	this.addSprite(new TreeLinerSprite(0,0.8,0.7,40,0.6,0.6,1,Color.green));
	this.addSprite(new TreeLinerSprite(0,0.9,0.3,25,0.45,0.65,1,Color.blue));
	this.addSprite(new TreeLinerSprite(0,0.75,0.6,30,0.65,0.5,1,Color.magenta));
        this.setNumberFrames(Animation.NO_MAX_FRAMES);
        // this.setNumberFrames(50);
        // this.setNumberRepeats(2);
        // this.setNumberRepeats(Animation.FOREVER);
    }
}
