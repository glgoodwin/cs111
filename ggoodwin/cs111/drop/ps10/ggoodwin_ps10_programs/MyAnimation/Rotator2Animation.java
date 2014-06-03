import java.awt.*; // 

class Rotator2Animation extends Animation {
 
  public Rotator2Animation() {

        Rotator r1 = new Rotator(400,200,100,30,5,Color.pink);
        Rotator r2 = new Rotator(300,275,75,270,-2,Color.gray);
        this.addSprite(r1);
        this.addSprite(r2);

        this.setNumberFrames(100);
        this.setNumberRepeats(2);
        //this.setDebugOn();
        this.setInactive(r2,40);
        this.setActive(r2,80);
        this.setHidden(r1,20);
        this.setVisible(r1,60);
        this.setFps(6);
    }
}
