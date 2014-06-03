import java.awt.*; // 

class RotatorAnimation extends Animation {
 
  public RotatorAnimation() {
        Rotator r1 = new Rotator(400,200,100,30,5,Color.pink);
        Rotator r2 = new Rotator(300,275,75,270,-2,Color.gray);
        this.addSprite(r1);
        this.addSprite(r2);
        // What happens if we swap the two statements above?
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
