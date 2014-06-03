/** 
* FILE NAME: TicTacToePanel.java
* WHO: CS230 Staff
* WHAT: Sets up the Panel that contains the TicTacToe game.
* It communicates with the TicTacToe.java class where 
* the functionality of the game resides.
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToePanel extends JPanel implements ActionListener {
  //instance vars
  private ImageIcon xImg, oImg; //these two images will be used in a couple
  // of diff methods,so make them instance vars, and create them only once.
  private ImageIcon tieImg;
  
  private TicTacToe game;

  // fixes the serializable class TicTacToePanel to run in eclipse
  private static final long serialVersionUID = 1L;
  
  //add more instance vars, as you need them.
  
  //constructor. Notice how it takes an instance of the game as input!
  public TicTacToePanel(TicTacToe g) {
    xImg = createImageIcon("images/X.jpg",
                                   "a pretty but meaningless splat");
    oImg = createImageIcon("images/O.jpg",
                                     "a pretty but meaningless splat");
    tieImg = createImageIcon("images/Tie.jpg",
                                     "a pretty but meaningless splat");
    //add more code here
  }
  


  /** 
   * Creates and returns an ImageIcon out of an image file.
   * @param path The path to the imagefile relevant to the current file.
   * @param description A short description to the image.
   * @return ImageIcon An ImageIcon, or null if the path was invalid. 
   */
  private static ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = TicTacToe.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
  
  
  
  
} //end TicTacToePanel
