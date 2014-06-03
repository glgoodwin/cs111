/**Gabe Goodwin
   StockGUI.java
   CS230 Final Project


   This class will create a GUI to display the information collected by the StockInfo Class. My goal for this GUI is to allow the user to access important stock information in one place 
**/
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.awt.image.*;
import java.io.*;
import java.lang.*;
import javax.imageio.*;
import java.lang.Object.*;
import java.net.URL;

public class StockGUI extends JApplet implements ActionListener{

    private JPanel UL, centerPanel, UR, P11, P12, P21, P22, sigDate1, sigDate2, LL, northPanel, centerPanel2, centerPanel3, centerPanel4, newsPanel, imgPanel, eastPanel, half1, half2, chartPanel,LR ;
    private JLabel  stockText1,  priceText1, textPrice1, signalText1, stockText2,  priceText2, textPrice2, signalText2,textDate1,  stockDate1,  priceDate1, signalDate1,textDate2, stockDate2,  priceDate2, signalDate2, charts, textIDate2, sigLabel1, sigLabel2, sigLabel3, sigLabel4, newSigLabel;
    private JRadioButton d1, d5, m3, m6, y1, y2, y5, max, stock1, stock2;
    private JEditorPane newsPane;
    private String priceString1, priceString2, dateString1, dateString2, buy, sell;
    private JTextField textStock1, textStock2, textIDate1;
    private String text, text2, date1;
    private JPanel centerPanel5;
    private URL url;
    private Image image;
    private StockQuote q, q2, q3, q4;
    private StockInfo stocks, stocks2;
    private JFrame f;
    private JEditorPane editor;
    private ButtonGroup graphs, news;
    private JRadioButton[] buttonArray;

    public StockGUI() throws IOException{
	makeULPanel();
	makeURPanel();
	makeLLPanel("http://chart.finance.yahoo.com/z?s=GOOG&t=1y&q=&l=&z=l&p=s&a=v&p=s&lang=en-US&region=US");	
      	makeLRPanel("http://finance.yahoo.com/q/h?s=GOOG&t=2010-12-18");
	//	makeLRPanel(
    }





    /** Calls upon the helper methods to put the GUI together**/
    public void init(){
	//	wholeGUI= new JPanel();
       setLayout(new BorderLayout());
       half1 = new JPanel(new GridLayout(1,2));
       half2 = new JPanel(new GridLayout(1,2));
       half1.add(UL);
       half1.add(UR);
       add(half1,BorderLayout.NORTH);
       add(LL,BorderLayout.CENTER);
       add(LR, BorderLayout.EAST);

		  


    }





    /**Creates the Upper Left Panel for the GUI that will display the current Day's sTock information for the given stock(s).**/
    public JPanel makeULPanel(){
	UL = new JPanel();
	UL.setLayout(new GridLayout(2,1));
	//sets the two halves of the first panel
	UL.add(stock1TodayPanel());
	UL.add(stock2TodayPanel());
	
	return UL;
    }
    

/**Creates the top and bottom halfs of the UL panel. Keeps track of information for the Stocks. Takes a string representing whether the signal is buy or sell**/ 
    public JPanel stock1TodayPanel(){
	//initialize 2 strings that i will need later
	buy = new String("BUY!");
	sell = new String("SELL!");
       
	P11 = new JPanel();
	P11.setLayout(new BorderLayout(2,2));

	//creates the components for top half of ULPanel
	stockText1 = new JLabel("Enter a stock Symbol: ");
	textStock1 = new JTextField();//text area for first stock Symbol
       	textStock1.addActionListener(this);

	priceText1 = new JLabel("Closing Price: ");
	priceString1 = "$$$.$$";
	textPrice1 = new JLabel(priceString1);
	
	signalText1 = new JLabel("Signal: ");
	sigLabel1 = new JLabel("XXX");//will actually call on get signal form StockInfo class

	centerPanel = new JPanel();
	centerPanel.setLayout(new GridLayout(2,3));
	

	//adds components to Panel
	centerPanel.add(stockText1);
       	centerPanel.add(priceText1);
       	centerPanel.add(signalText1);
	centerPanel.add(textStock1);
      	centerPanel.add(textPrice1);
      	centerPanel.add(sigLabel1);
	P11.add(centerPanel,BorderLayout.CENTER);




	return P11;
    }


    
    public JPanel stock2TodayPanel(){
       
	P12 = new JPanel();
	P12.setLayout(new BorderLayout(2,2));

	//creates the components for top half of ULPanel
	stockText2 = new JLabel("Enter a Stock Symbol: ");
	textStock2 = new JTextField();//text area for first stock Symbol
       	textStock2.addActionListener(this);
	
	priceText2 = new JLabel("Closing Price: ");
	priceString2 = "$$$.$$";
	textPrice2 = new JLabel(priceString2);

	signalText2 = new JLabel("Signal: ");
	sigLabel2 = new JLabel("XXX");

	centerPanel2 = new JPanel();
	centerPanel2.setLayout(new GridLayout(2,3));
	

	//adds components to Panel
	centerPanel2.add(stockText2);
       	centerPanel2.add(priceText2);
       	centerPanel2.add(signalText2);
	centerPanel2.add(textStock2);
      	centerPanel2.add(textPrice2);
      	centerPanel2.add(sigLabel2);
	P12.add(centerPanel2,BorderLayout.CENTER);




	return P12;
    }

    /** This method creates and return a JPanel to be shown on the GUI. Will display historic informatin for the inputted stock(s)**/
    public JPanel makeURPanel(){
	UR = new JPanel(new BorderLayout());
	JPanel temp = new JPanel(); 
	temp.setLayout(new GridLayout(2,1));
	//sets the two halves of the first panel
	temp.add(stock1DatePanel());
	temp.add(stock2DatePanel());
	
	JPanel southURPanel = new JPanel();
	//	southURPanel.setLayout(new GridLayout(2,1));
	JLabel whichStock = new JLabel("Check news for: "); 

	 news = new ButtonGroup();
        stock1 = new JRadioButton("Stock1");
	stock2 = new JRadioButton("Stock2");
	news.add(stock1);
	news.add(stock2);

	stock1.addActionListener(new ButtonListener());
	stock2.addActionListener(new ButtonListener());
	
	
	southURPanel.add(whichStock);
	southURPanel.add(stock1);
	southURPanel.add(stock2);

	UR.add(temp, BorderLayout.CENTER);
	UR.add(southURPanel, BorderLayout.SOUTH);
	return UR;
    }


    public JPanel stock1DatePanel(){
       
	P21 = new JPanel();
	P21.setLayout(new BorderLayout(2,2));

	//creates the components for top half of ULPanel
	stockDate1 = new JLabel("Enter a Date: ");
	textIDate1 = new JTextField("YYYY-MM-DD");//text area for first stock Symbol
       	textIDate1.addActionListener(this);

	priceDate1 = new JLabel("Closing Price on Date: ");
	dateString1 = "$$$.$$";
	textDate1 = new JLabel(dateString1);

	signalDate1 = new JLabel("Signal on Date: ");
	sigLabel3 = new JLabel("XXX");

	centerPanel3 = new JPanel();
	centerPanel3.setLayout(new GridLayout(2,3));
	

	//adds components to Panel
	centerPanel3.add(stockDate1);
       	centerPanel3.add(priceDate1);
       	centerPanel3.add(signalDate1);
	centerPanel3.add(textIDate1);
      	centerPanel3.add(textDate1);
      	centerPanel3.add(sigLabel3);
	P21.add(centerPanel3,BorderLayout.CENTER);




	return P21;

    }
    public JPanel stock2DatePanel(){
       
	P22 = new JPanel();
	P22.setLayout(new BorderLayout(2,2));

	//creates the components for top half of ULPanel
	stockDate2 = new JLabel("Date: ");
	textIDate2 = new JLabel("       ");//text area for first stock Symbol


	priceDate2 = new JLabel("Closing Price on Date: ");
	dateString2 = "$$$.$$";
	textDate2 = new JLabel(dateString2);

	signalDate2 = new JLabel("Signal on Date: ");
	sigLabel4 = new JLabel("XXX");

	centerPanel4 = new JPanel();
	centerPanel4.setLayout(new GridLayout(2,3));
	

	//adds components to Panel
	centerPanel4.add(stockDate2);
       	centerPanel4.add(priceDate2);
       	centerPanel4.add(signalDate2);
	centerPanel4.add(textIDate2);
      	centerPanel4.add(textDate2);
      	centerPanel4.add(sigLabel4);
	P22.add(centerPanel4,BorderLayout.CENTER);




	return P22;

    }

    /** This Method will create and return a JPanel to be shown on the GUI. WIll display grpahs**/
    public JPanel makeLLPanel(String imageURL){
	LL = new JPanel(new BorderLayout());
	northPanel = new JPanel();
	//these Radio Buttons are grouped above the graph
	buttonArray = new JRadioButton[8];
        graphs = new ButtonGroup();
        d1 = new JRadioButton("1d");
	d5 = new JRadioButton("5d");
	m3 = new JRadioButton("3m");
	m6 = new JRadioButton("6m");
	y1 = new JRadioButton("1y");
	y2 = new JRadioButton("2y");
	y5 = new JRadioButton("5y");
	max = new JRadioButton("max");

	
 
	graphs.add(d1);	
	graphs.add(d5);	
	graphs.add(m3);	
	graphs.add(m6);	
	graphs.add(y1);	
	graphs.add(y2);	
	graphs.add(y5);	
	graphs.add(max);	



 	d1.addActionListener(new ButtonListener());
 	d5.addActionListener(new ButtonListener());
 	m3.addActionListener(new ButtonListener());
 	m6.addActionListener(new ButtonListener());
 	y1.addActionListener(new ButtonListener());
 	y2.addActionListener(new ButtonListener());
 	y5.addActionListener(new ButtonListener());
 	max.addActionListener(new ButtonListener());


       	northPanel.add(d1);
	northPanel.add(d5);
	northPanel.add(m3);
	northPanel.add(m6);
	northPanel.add(y1);
	northPanel.add(y2);
	northPanel.add(y5);
	northPanel.add(max);
	LL.add(northPanel, BorderLayout.NORTH);


	centerPanel5 = makeChartPanel(imageURL);
	LL.add(centerPanel5);
	
	return LL;

	}
    public JPanel makeChartPanel(String imageURL){
	BufferedImage newImage;
	try{
	    url = new URL(imageURL);
	    image = ImageIO.read(url);
	   
	}catch (IOException e){
	}
    
	chartPanel = new JPanel();
	charts = new JLabel(new ImageIcon(image));
	chartPanel.add(charts);
	
	return chartPanel;
    }



    /** This method will create and return a JPanel to be shown on the GUI. Will show news**/
    public JFrame makeLRPanel(String url)throws IOException{
	LR = new JPanel();
	String gabe = url;
	editor = new JEditorPane();	
	URL myURL = new URL(gabe);	
	editor.setPage(myURL);	
	editor.setEditable(false);	
	// stuff to add the editor into another panel goes here         
       	JScrollPane pane = new JScrollPane(editor);
	JScrollPane scroller =new JScrollPane(
	    editor,
	    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	scroller.setPreferredSize(new Dimension(400,525));
	LR.add(scroller);
       	JFrame f = new JFrame("woohoo");	
       	f.getContentPane().add(pane);		
      	f.setVisible(true);

	return f;

 }

    public void actionPerformed(ActionEvent event){

	Object source = event.getSource();

	if(source == textStock1){
	    System.out.println("You have hit enter");
	    //user input first stock symbol
	    text = textStock1.getText();
	    System.out.println(text);
	    stocks = new StockInfo(text);
	    q = stocks.getQuote(stocks.lastDate());
	    double price = q.getPrice(); //need to update GUI with this info
	    System.out.println(price);
	 
	    //uncheck radio button
	    // graphs.checked = false;
	  
	    textPrice1.setText(Double.toString(price));
	    textPrice1.updateUI();
	    	    

	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=1y&q=&l=&z=l&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	    int s = stocks.returnSignal(); //update buy/sell signal with this info
	    //update signal
	    System.out.println(Integer.toString(s));
	    if(s == 1){
		sigLabel1.setText(buy);
		sigLabel1.setForeground(Color.green);
	    }else if( s == -1){
		sigLabel1.setText(sell);
		sigLabel1.setForeground(Color.red);
	    }
	    sigLabel1.updateUI();
	    
	    try{
	    //update the news Headlines
		editor.removeAll();
		URL myURL = new URL("http://finance.yahoo.com/q/h?s="+text+"&t="+stocks.lastDate().toString());
		editor.setPage(myURL);
		editor.updateUI();
	    }
	    catch(IOException e){
		System.out.println("Invalid");
	    }
	    
	    

	    
		}
	
	else if(source == textStock2){
	    //user input for the second stock symbol
	    	    System.out.println("You have hit enter");
	    //user input first stock symbol
	    text2 = textStock2.getText();
	    System.out.println(text);
	    stocks2 = new StockInfo(text2);
	    q2 = stocks2.getQuote(stocks.lastDate());
	    double price = q2.getPrice(); //need to update GUI with this info
	    System.out.println(price);
	  
	  
	    textPrice2.setText(Double.toString(price));
	    textPrice2.updateUI();
	    	    

	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=1y&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	    int s = stocks2.returnSignal(); //update buy/sell signal with this info
	    if(s == 1){
			sigLabel2.setText(buy);
		sigLabel2.setForeground(Color.green);
		    }else if( s == -1){
			sigLabel2.setText(sell);
		sigLabel2.setForeground(Color.red);
		    }
		    sigLabel2.updateUI();
	}

	else if(source == textIDate1){
	    //user input for the date that they want to look up
	    	    System.out.println("You have hit enter");
	    //user input first stock symbol
	    date1 = textIDate1.getText();
	    System.out.println(date1);
	    CS230Date newDate = StockInfo.stringToDate(date1);
	    System.out.println(newDate);

	    q3 = stocks.getQuote(newDate);
	    System.out.println(q3);
	    double price3 = q3.getPrice(); //need to update GUI with this info
	    q4 = stocks2.getQuote(newDate);
	    System.out.println(q4);
	    double price4 = q4.getPrice();
	   
	    int s1 = stocks.getSignal(newDate);// update buy/sell signal with this info	
	    if(s1 == 1){
		sigLabel3.setText(buy);
		sigLabel3.setForeground(Color.green);
	    }else if( s1 == -1){
		sigLabel3.setText(sell);
		sigLabel3.setForeground(Color.red);
	    }
	    sigLabel3.updateUI();
	   
	    int s2 = stocks2.getSignal(newDate);
	    if(s2 == 1){
		sigLabel4.setText(buy);
		sigLabel4.setForeground(Color.green);
	    }else if( s2 == -1){
		sigLabel4.setText(sell);
		sigLabel4.setForeground(Color.red);
	    }
	    sigLabel4.updateUI();


	    
	    textIDate2.setText(date1);
	    textDate1.setText(Double.toString(price3));
	    textDate2.setText(Double.toString(price4));
	    textIDate2.updateUI();
	    textDate1.updateUI();
	    textDate2.updateUI();

	  
	    try{
		editor.removeAll();
		URL myURL = new URL("http://finance.yahoo.com/q/h?s="+text+"&t="+date1);
		editor.setPage(myURL);
		editor.updateUI();
	    }
	    catch(IOException e){
		System.out.println("Invalid");
	    }
	  
	    
	    	    
	}
	    
    }
    private class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent event){
	Object source = event.getSource();
							  
       	if(source == d1){
	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=1d&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();
	}
	else if(source == d5){
	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=5d&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	}

	else if(source == m3){
	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=3m&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	}

	else if(source == m6){
	    	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=6m&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	}

	else if(source == y1){
	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=1y&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	}

	else if(source == y2){

	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=2y&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();
	}

	else if(source == y5){
	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=5y&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();


	}

	else if(source == max){
	    JPanel newChartPanel = makeChartPanel("http://chart.finance.yahoo.com/z?s="+text+"&t=max&q=l&l=on&z=l&c="+text2+"&p=s&a=v&p=s&lang=en-US&region=US");
	    centerPanel5.removeAll();
	    centerPanel5.add(newChartPanel);
	    centerPanel5.updateUI();

	}
	else if(source == stock1){
	    //update the news Headlines
	    try{
		editor.removeAll();
		URL myURL = new URL("http://finance.yahoo.com/q/h?s="+text+"&t="+date1);
		editor.setPage(myURL);
		editor.updateUI();
	    }
	    catch(IOException e){
		System.out.println("Invalid");
	    }

	}

	else if(source == stock2){
	    //update the news Headlines
	    try{
		editor.removeAll();
		URL myURL = new URL("http://finance.yahoo.com/q/h?s="+text2+"&t="+date1);
		editor.setPage(myURL);
		editor.updateUI();
	    }
	    catch(IOException e){
		System.out.println("Invalid");
	    }


	}
	}
    }





    


}//ends StockGUI class








