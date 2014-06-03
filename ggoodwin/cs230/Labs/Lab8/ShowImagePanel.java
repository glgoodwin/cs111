
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
 private String img1string;  // the name of the first image
 private JLabel statusLabel;
 private JPanel picPanel;
    private JLabel east;
    private JLabel west;

    private JButton pic1Button;//the button to show picture 1
    private JButton pic2Button;//button to show picture 2
    private JButton pic3Button;//button to show picture 3
    private JButton quitButton;//quit Button

   



  
  /**
   * Creates a ShowImagePanel object
   *  by initialize the instance variables of the GUIs buttons and labels.
   *  Creates the panel that contains the ShowImage GUI
   *
   */
  public ShowImagePanel() {
    // These images are stored in the local images directory, which lives 
    // in the same folder as this java program
    img1string = "food_title.jpg";

    // create the GUI by creating a JPanel and adding other panels to it
    setLayout(new BorderLayout(10,10));  // horizontal and vertical gaps of 10 pixels between panels
    add(makeNorthPanel(), BorderLayout.NORTH);
    add(makeWestPanel(), BorderLayout.WEST);
    add(makeEastPanel(), BorderLayout.EAST); 
    add(makeSouthPanel(), BorderLayout.SOUTH);
   

    // create and make other panels
    picPanel = makeCenterPanel(img1string);
    add(picPanel,BorderLayout.CENTER);

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
	JPanel southPanel = new JPanel(); //making a south panel to hold buttons//add a button to this panel
	pic1Button = new JButton("Cake");
	pic2Button = new JButton("Cheeseburger");
	pic3Button = new JButton("Healthy");
	
	//make buttons show up in GUI
	southPanel.add(pic1Button);
	southPanel.add(pic2Button);
	southPanel.add(pic3Button);

	//add action listener to button
	pic1Button.addActionListener(new ButtonListener());
	pic2Button.addActionListener(new ButtonListener());
	pic3Button.addActionListener(new ButtonListener());
	return southPanel;
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

      JPanel eastPanel = new JPanel();
      eastPanel.setLayout(new BorderLayout(20,20));
      JLabel east = new JLabel("right", JLabel.CENTER);
      eastPanel.add(east);

      JPanel westPanel = new JPanel();
      westPanel.setLayout(new BorderLayout(20,20));
      JLabel west = new JLabel("left I want to center the picture", JLabel.CENTER);
      westPanel.add(west);
      
      
      

			  
      centerPanel.add(eastPanel, BorderLayout.EAST);
      centerPanel.add(westPanel, BorderLayout.WEST);
      centerPanel.add(imgPanel, BorderLayout.CENTER);

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
       if(event.getSource() == pic1Button){
	   // changed the picture to be pic 
	   JPanel newPic = makeCenterPanel("Angel Food Cake.jpg");
	   picPanel.removeAll();
	   picPanel.add(newPic);
	   //update status label
	   statusLabel.setText("Yummy :)");
	   updateUI(); //makes change effective in the GUI
       }else if(event.getSource() ==pic2Button){
	   JPanel newPic = makeCenterPanel("966630_543525631.jpg");
	   picPanel.removeAll();
	   picPanel.add(newPic);
	   statusLabel.setText("Cheesy");
	   updateUI();
       }else if(event.getSource() == pic3Button){
	   JPanel newPic = makeCenterPanel("Ingredients_Healthy_Food.jpg");
	   picPanel.removeAll();
	   picPanel.add(newPic);
	   statusLabel.setText("Colorful");
	   updateUI();
       }
   }




   
 } //end ActionListener
} //end ShowImagePanel
