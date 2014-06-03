/**Gabe Goodwin
   StockGUI.java
   CS230 Final Project


   This class will display the GUI created by the StockGUI class**/
//This class does not yet worke

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;


public class ShowStockGui{


    public static void main(String[] args){
	JFrame frame = new JFrame("Test");
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	StockGUI gui = new StockGUI();
       	//frame.getContentPane().addImpl(gui, "Test", 4);
	//the previous instructionis not compiling
	frame.pack();
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setVisible(true);
	frame.setDefaultLookAndFeelDecorated(true);
	frame.setSize(800,500);
    }






}