/**Gabe Goodwin
   StockGUI.java
   CS230 Final Project


   This class will display the GUI created by the StockGUI class**/

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;


public class ShowStockGui{

  private static void createAndShowGUI() throws IOException {
    
     // Enable window decorations. 
    JFrame.setDefaultLookAndFeelDecorated(true); 

    //Create and set up the window.
    JFrame frame = new JFrame("Stock GUI");
    
    frame.setSize(1220, 800);
    
    // Specify that the close button exits application. 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    StockGUI stocks = new StockGUI();
    stocks.init(); 
    
    // frame.getContentPane().add(contents, BorderLayout.CENTER);
    frame.add(stocks);

    // Display the window.
    //frame.pack();
    frame.setVisible(true);
  }


    public static void main(String[] args){
// 	JFrame frame = new JFrame("Test");
// 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// 	StockGUI gui = new StockGUI();


// 	frame.pack();
// 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 	frame.setVisible(true);
// 	frame.setDefaultLookAndFeelDecorated(true);
// 	frame.setSize(800,500);

	javax.swing.SwingUtilities.invokeLater(new Runnable(){
		public void run(){
		    try{
		    createAndShowGUI();
		    }
		    catch (IOException e){
			System.out.println("IOException");
		    }
		}
	    });

	
	
    }






}