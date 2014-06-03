/** 
* FILE NAME: ShowImageGUI.java
* CS230 Staff
* Updated by Sohie Fall 2010 with window decoration and default window size
*/
import javax.swing.JFrame;

public class ShowImageGUI {

  public static void main (String[] args) {
    // creates and shows a Frame 
    JFrame frame = new JFrame("CS230: Show Image GUI");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // image now supplied in ShowImagePanel.java
    // ShowImage si  = new ShowImage(args[0]);
    
    ShowImagePanel panel = new ShowImagePanel();
    frame.getContentPane().add(panel);
    
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setDefaultLookAndFeelDecorated(true);
    frame.setSize(800,500); // sets initial height and width of GUI
    
  }
}
