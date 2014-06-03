import java.applet.*;
import java.awt.*; 
import java.util.*;
import javax.swing.*;

// An implementation of an Animation Player.
// This implementation allows users to add multiple animations to the player.
// It also has a user-configurable GUI (same as SimpleAnimationPlayer).
// Please see the online Animation notes for the public contract to this class.
public class AnimationShowcase extends SimpleAnimationPlayer 
{
    private Vector animations;
    private int currentAnimationIndex;
    protected Choice animationChoice;
    protected boolean playOnSelection;
                
    public AnimationShowcase ()
    {
        animationChoice = new Choice();
        animationChoice.setBackground(Color.white);
        animations = new Vector();
        currentAnimationIndex = -1;
        playOnSelection = false;
    }
        
    protected void addAnimation (String name, Animation a)
    {
        animationChoice.addItem(name);
        animations.addElement(a);
    }
        
    protected void setPlayOnSelection ()
    {
        playOnSelection = true;
    }
        
    public void init ()
    {
        this.setLayout(new BorderLayout());
        this.add("Center", ap); 
        this.add("North", makeControlPanel());
        ap.setFrameSize();
    }
        
    public void start ()
    {
        if (currentAnimationIndex == -1) { // first time loading applet
            if (animations.size()>0) switchAnimations(0);
        } else {
            super.start();
        }
    }
    protected void addControls(Panel p)
    {
        p.add(animationChoice);
        if (showResetButton) p.add(resetButton);
        if (showPlayButton) p.add(playButton);
        if (showStopButton) p.add(stopButton);
        if (showNextButton) p.add(nextButton);
        if (showGotoButton) p.add(gotoButton);
        if (showFrameNumber) {
            p.add(frameNumberField);
            ap.setFrameNumberDisplay(this);
        }
    }
        
    public boolean action (Event evt, Object arg)
    {
        if (evt.target.equals(animationChoice)) {
            switchAnimations(animationChoice.getSelectedIndex());
        } else {
            return super.action(evt,arg);
        }
        return true;
    }
        
    protected void switchAnimations (int newAnimationIndex)
    {
        if (newAnimationIndex == currentAnimationIndex) return;
        ap.setAnimation((Animation)animations.elementAt(newAnimationIndex));
        currentAnimationIndex = newAnimationIndex;
        ap.resetIfNecessary();
        if (playOnSelection) ap.play();
    }
    
    //----------------------------------------------------------------------
    /*** [brian, 12/01/07] New code for running AnimationShowcase applet as an application ***/
  
    public static void main (String[] args) {
      runAsApplication(new AnimationShowcase(), "AnimationShowcase"); 
    }
    
    public static void runAsApplication (final AnimationShowcase applet, final String name) {
      // Schedule a job for the event-dispatching thread:
      // creating and showing this animationshowcase applet. 
      javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() { // this is Java's thread run() method, not AnimationShowcase's!
          JFrame.setDefaultLookAndFeelDecorated(true); // enable window decorations. 
          JFrame frame = new JFrame(name); // create and set up the window.

          frame.setSize(600, 400);
          
          // Using EXIT_ON_CLOSE empirically exits all instances of an application.
          // Use DISPOSE_ON_CLOSE to get rid of just one. 
          frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
          
          // Need to add to frame and make visible *before* init 
          // so that attempts to reset dimensions will work. 
          frame.add(applet, BorderLayout.CENTER); // add applet to window
          //frame.setVisible(true); // display the window.
          applet.init(); // initialize the applet
          // Need to make visible again *after* init in case
          //  something like setDimensions is not called in init. 
          frame.setVisible(true); // display the window.
        }
      });
    }
    //----------------------------------------------------------------------

}
