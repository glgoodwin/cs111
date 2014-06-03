
/** 
* FILE NAME: ShowImagePanel.java
* AUTHOR:  CS230 Staff
* WHAT:  A simple program that demonstrates how to show images in a GUI,
*        how to create panels using the BorderLayout, how to create custom
*        colors and how to create buttons with actionListeners.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ShowImagePanel extends JPanel {
  //instance vars
  String img1string;  // the name of the first image
  JLabel statusLabel;
  JPanel picPanel;



  
  /**
   * Creates a ShowImagePanel object
   *  by initialize the instance variables of the GUIs buttons and labels.
   *  Creates the panel that contains the ShowImage GUI
   *
   */
  public ShowImagePanel() {
    // These images are stored in the local images directory, which lives 
    // in the same folder as this java program
    img1string = "images/wc.jpg";

    // create the GUI by creating a JPanel and adding other panels to it
    setLayout(new BorderLayout(10,10));  // horizontal and vertical gaps of 10 pixels between panels
    add(makeNorthPanel(), BorderLayout.NORTH);
    add(makeWestPanel(), BorderLayout.WEST);
    add(makeEastPanel(), BorderLayout.EAST); 
    

    // create and make other panels

  }
 
  /**
   * Creates and returns a JPanel. 
   * @return JPanel The Panel that contains the top, status label
   *
   */
  private JPanel makeNorthPanel() {
    // create northPanel using default FlowLayout manager
    JPanel northPanel = new JPanel();
    statusLabel = new JLabel("Here's your picture", JLabel.CENTER);
    statusLabel.setFont(new Font("Serif", Font.ITALIC, 16));
    northPanel.add(statusLabel);
    return northPanel;
  }
  
  /**
   * Creates and returns a JPanel. 
   * @return JPanel The Panel that contains the
   * three picture choice buttons and the Quit button
   */
    private JPanel makeSouthPanel() {


  }

    /**
     * Creates and returns a JPanel. 
     * @return JPanel The Panel contains no elements
     */
    private JPanel makeWestPanel () {
      // create eastPanel using BorderLayout manager
      //although it does not contain anything, it adds a bit of breathing space
      JPanel eastPanel = new JPanel();
      eastPanel.setLayout(new BorderLayout(20, 20));
      return eastPanel;
    }
  
    /**
     * Creates and returns a JPanel. 
     * @return JPanel The Panel contains no elements
     */
    private JPanel makeEastPanel () {
      // create eastPanel using BorderLayout manager
      //although it does not contain anything, it adds a bit of breathing space
      JPanel eastPanel = new JPanel();
      eastPanel.setLayout(new BorderLayout(20, 20));
      return eastPanel;
    }
  
    /**
     * Creates and returns a JPanel. 
     * @return JPanel The Panel contains the picture display area, bordered
     *                on the left and right sides with colors and text.
     *  This method also supplies an example of creating your own colors in Java
     */
    private JPanel makeCenterPanel (String pic) {
      // create centerPanel using BorderLayout manager
      // make this where the picture appears

      JPanel centerPanel = new JPanel();
      centerPanel.setLayout(new BorderLayout(20,20));
      JPanel imgPanel = new ShowImage(pic);  // creates a panel with picture in it





      return centerPanel;
    }

  
  /**
   * ButtonListener class implements the ActionListener
   *  so that the program responds to button clicks.
   *  There are four buttons: 3 picture choice buttons and one quit button.
   *  Upon a button click, the picture is updated in the center panel,
   *  and the statusLabel text is also updated.
   *
   */ 

 private class ButtonListener implements ActionListener {
   public void actionPerformed(ActionEvent event) {
     // check to see if the user clicked on anything
     // and do the appropriate action

   }




   
 } //end ActionListener
} //end ShowImagePanel
