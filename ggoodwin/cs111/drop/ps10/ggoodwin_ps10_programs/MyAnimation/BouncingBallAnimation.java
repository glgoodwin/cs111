import java.awt.*; // 

class BouncingBallAnimation extends Animation
{ 
    public BouncingBallAnimation()
    {
        this.addSprite(new BouncingBall(50,50,30,2,2,Color.magenta));
        this.addSprite(new BouncingBall(150,150,25,3,-3,Color.cyan));
        this.addSprite(new BouncingBall(250,250,40,1,4,Color.green));
        this.setNumberFrames(Animation.NO_MAX_FRAMES);
        // this.setNumberFrames(100);
        // this.setNumberRepeats(2);
        // this.setNumberRepeats(Animation.FOREVER);
    }
}
