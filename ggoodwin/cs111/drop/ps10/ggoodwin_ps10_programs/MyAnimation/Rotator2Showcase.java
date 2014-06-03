import java.awt.*; // use colors

public class Rotator2Showcase extends AnimationShowcase {

  public Rotator2Showcase () {
    addAnimation("Rotators2", new Rotator2Animation());
  }
    
  public static void main (String[] args) {
    runAsApplication(new Rotator2Showcase(), "Rotator2Showcase"); 
  }
}
