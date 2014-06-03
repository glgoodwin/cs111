import java.awt.*; // use colors

public class LectureShowcase extends AnimationShowcase {

  public LectureShowcase () {
    addAnimation("Rotators", new RotatorAnimation());
    addAnimation("Pulsars", new PulsarAnimation());
    addAnimation("Bouncing Balls", new BouncingBallAnimation());
    addAnimation("TreeLiners", new TreeLinerAnimation());
    addAnimation("All Together Now", new TogetherAnimation());
  }
    
  public static void main (String[] args) {
    runAsApplication(new LectureShowcase(), "LectureShowcase"); 
  }
}
