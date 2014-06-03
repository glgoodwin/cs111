import java.awt.*;
import javax.swing.*;

public class RunTournament {  
	
  // FILE NAME: RunTournament.java
  // WRITTEN BY:
  // WHEN:

  // PURPOSE: creates the initial 16 teams for the European soccer tournament 
  // and provides a main() method to display the GUI to run the tournament
    
  public static Team[] makeTeams () {
    // return an array of 16 initial teams for the tournament
    Team [] teams = new Team [16];
    teams[0] = new Team("Portugal", 1);
    teams[1] = new Team("Greece", 16);
    teams[2] = new Team("Spain", 8);
    teams[3] = new Team("Russia", 9);
    teams[4] = new Team("France", 5);
    teams[5] = new Team("England", 12);
    teams[6] = new Team("Croatia", 4);
    teams[7] = new Team("Switzerland", 13);
    teams[8] = new Team("Sweden", 6);
    teams[9] = new Team("Denmark", 11);
    teams[10] = new Team("Italy", 3);
    teams[11] = new Team("Bulgaria", 14);
    teams[12] = new Team("Czech Republic", 7);
    teams[13] = new Team("Holland", 10);
    teams[14] = new Team("Germany", 2);
    teams[15] = new Team("Latvia", 15);
    return teams;
  }

  // Create the GUI applet and show it.  
  private static void createAndShowGUI() {
    
     // Enable window decorations. 
    JFrame.setDefaultLookAndFeelDecorated(true); 

    //Create and set up the window.
    JFrame frame = new JFrame("European Football Tournament");
    
    frame.setSize(950, 650);
    
    // Specify that the close button exits application. 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Tournament euro = new Tournament(makeTeams());
    euro.init(); 
    
    // frame.getContentPane().add(contents, BorderLayout.CENTER);
    frame.add(euro, BorderLayout.CENTER);

    // Display the window.
    //frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        createAndShowGUI();
      }
    });
  }
  
}
