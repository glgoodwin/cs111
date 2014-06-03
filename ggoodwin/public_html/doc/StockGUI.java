/**Gabe Goodwin
   StockGUI.java
   CS230 Final Project


   This class will create a GUI to display the information collected by the StockInfo Class. 
**/
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
 
public class StockGUI{


    public StockGUI(){
	this.init();
    }





    /** Calls upon the helper methods to put the GUI together**/
    public void init(){
	JPanel wholeGUI= new JPanel();
	wholeGUI.setLayout(new GridLayout(2,2));
	wholeGUI.add(makeULPanel());
	//these three methods will be created later to finish the gui
	//add(makeURPanel());
	//whill be almost identical to the UL panel, except that it will have input for a date and show the user historical information
	//add(makeLLPanel());
	//the LL panel will display the graph taken from Yahoo Stocks based on 
	//users input by radio button
	//add(makeLRPanel());
	//will display news, the major part of this project that I need to work out
		  


    }


    /**Creates the Upper Left Panel for the GUI that will display the current Day's sTock information for the given stock(s).**/
    public JPanel makeULPanel(){
	JPanel P1 = new JPanel();
	P1.setLayout(new GridLayout(2,1));

	//sets the two 
	P1.add(stock1TodayPanel());
	P1.add(stock1TodayPanel());
	
	
	
	JTextArea textStock2 = new JTextArea(" ",2,5);//text area for second stock symbol
	return P1;
    }
    

/**Creates the top and bottom halfs of the UL panel. Keeps track of information for the Stocks. Takes a string representing whether the signal is buy or sell**/ 
    //this method does not yet have an action listener. Still trying to figure out just how to make all the methods work together with the GUI
    public JPanel stock1TodayPanel(){
       
	JPanel P11 = new JPanel();
	P11.setLayout(new BorderLayout(2,2));

	//creates the components for top half of ULPanel
	JLabel stockText1 = new JLabel("Enter a stock Symbol: ");
	JTextArea textStock1 = new JTextArea(" ",2,5);//text area for first stock Symbol
	//needs actionListener
	JLabel priceText1 = new JLabel("Closing Price: ");
	JLabel textPrice1 = new JLabel("$$$.$$");
	//needs action Listener
	JLabel signalText1 = new JLabel("Signal: ");
	JPanel sigPanel = makeSigPanel();
	

	//adds components to Panel
	P11.add(stockText1,BorderLayout.NORTH);
	P11.add(priceText1,BorderLayout.NORTH);
	P11.add(signalText1,BorderLayout.NORTH);
	P11.add(textStock1, BorderLayout.CENTER);
	P11.add(textPrice1, BorderLayout.CENTER);
	P11.add(sigPanel, BorderLayout.EAST);

	return P11;
    }

    
    /**This method will set the east part of the panel to the buy or sell signal picture**/
    public JPanel makeSigPanel(){
	JPanel eastPanel = new JPanel();
	JPanel imgPanel = new JPanel();
	
	eastPanel.setLayout(new BorderLayout(20,20));
	eastPanel.add(imgPanel, BorderLayout.CENTER);
	return eastPanel;
    }
	
	
    

    //moved this Method to ShowStockGUI.java
    //    public static void main(String[] args){
// 	StockGUI gui = new StockGUI();
// 	JFrame frame = new JFrame("Test");
// 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

// 	frame.getContentPanel().add(gui);
	
// 	frame.pack();
// 	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// 	frame.setVisible(true);
// 	frame.setDefaultLookAndFeelDecorated(true);
// 	frame.setSize(800,500);
    







}//ends StockGUI class


