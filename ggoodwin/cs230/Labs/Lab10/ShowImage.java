/*
  ShowImage class
  from www.java2s.com/Code/Java/2D-Graphics-GUI
http://www.java2s.com/Code/Java/2D-Graphics-GUI/DisplayimagesupportedbyImageIO.htm
*/    

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.*;

public class ShowImage extends JPanel {

  private BufferedImage image;

  public ShowImage(String filename) {
    try {
      image = ImageIO.read(new File(filename));
    } catch (IOException ie) {
      ie.printStackTrace();
    }
  }

  public void paint(Graphics g) {
    // drawImage(image, x, y, width, height, bgColor, observer);
    // The width and height allow the images to be resized, so 
    // that they all display the same size, even though they are all different
    //  sizes.
    g.drawImage(image, 0, 0, 400, 400, Color.blue, null);  
  }

 public static void main(String args[]) throws Exception {
    JFrame frame = new JFrame("ShowImage.java");
    JPanel panel = new ShowImage(args[0]);
    frame.getContentPane().add(panel);
    frame.setSize(300, 300);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
  }
}
