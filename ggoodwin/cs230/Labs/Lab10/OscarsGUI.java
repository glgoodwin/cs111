/** A GUI-based Oscar awards application.  Displays Oscar-winning
    film title,director, best actor and best actress for a given year.
    New info can be added, existing entries can be deleted, entries
    can be retrieved based on the year, and the data can be printed 
    to the standard output screen.  

    MODIFICATIONS   nov07  sohie converted to java swing, added color and images
                           and formatting within GUI.
*/

import java.awt.*; // Import Java's original AbstractWindowToolkit
import java.awt.event.*; // Import events from Java's original AbstractWindowToolkit
import javax.swing.*; // Import events Java's Swing GUI classes
import javax.swing.event.*; // Import events Java's Swing GUI classes (such as ChangeListener)

public class OscarsGUI extends JApplet implements ActionListener {
  
  // new class to store and retrieve information about the Oscar Awards
    
  // instance variables needed for the GUI
  private JButton addButton, removeButton, quitButton, retrieveButton, printButton, dirFreqButton;
  private JTextField[] inputText;
  private JLabel[] awardLabels, outputText;
  private Table<OscarInfo> oscarTable;
  private JLabel infoLabel;


  /** Constructor for the OscarGUI class
   *  creates the GUI components and the initial (empty) Table. It also adds two 
   *  entries to the table
   */
  public OscarsGUI() {
    //The Labels
    awardLabels = new JLabel[5];
    outputText = new JLabel[5];
    
    //The TextFields
    inputText = new JTextField[5];
    
    String [] labelStrings = {"  Oscar Year        ", "  Best Picture      ", "  Best Director     ", 
			      "  Best Actor        ", "  Best Actress      "};
    for (int i = 0; i < 5; i++) {
      awardLabels[i] = new JLabel(labelStrings[i]);
      awardLabels[i].setFont(new Font("Verdana",Font.PLAIN,16));
      awardLabels[i].setForeground(new Color(0,56,186));// azure blue
      inputText[i] = new JTextField(" ", 30);
      inputText[i].setBackground(new Color(200,255,175));
      inputText[i].setFont(new Font("Verdana",Font.PLAIN,14));
      outputText[i] = new JLabel("   ",JLabel.LEADING);
      outputText[i].setFont(new Font("Verdana",Font.PLAIN,14));
      outputText[i].setForeground(new Color(79,79,47)); // forest green
    }
    // create an initial table with Oscar entries for 2004-2008
    oscarTable = new Table<OscarInfo>();
    populateOscarsTable(oscarTable);
  }

  public void init () {
    System.out.println("Welcome to the Oscars!");
    // create the graphical user interface for the program
    
    // create a Panel of Buttons with Label
    JPanel buttonsAndLabelPanel = makeTextAndButtonPanel();
    // create textboxes, labels and image 
    JPanel textBoxesPanel = makeTextBoxesPanel();
    JPanel imagePanel = makeImagePanel();

    // add components to the Applet
    this.add(textBoxesPanel, BorderLayout.CENTER);
    this.add(buttonsAndLabelPanel,BorderLayout.SOUTH);
    this.add(imagePanel, BorderLayout.WEST);
  }

  /**
   *   makeImagePanel creates a panel with the Oscar jpg image
   *   on a white background
   *
   *  @return The JPanel panel that contains the Oscar jpg image
   *   with white background
   *
   */
  private JPanel makeImagePanel() {
    JPanel iPanel = new JPanel();
    ImageIcon oscar = new ImageIcon("oscar.jpg","oscar statue");
    iPanel.add(new JLabel(" ", oscar, JLabel.CENTER));
    iPanel.setBackground(Color.white);
    return iPanel;
  }

  /**
   *   makeTextBoxesPanel makes the TextFields and their
   *    labels.  There are 5 rows, each with a label,
   *    a textfield, and another label.  For example,
   *       Best Picture  [TextField for Best Picture]  Best Picture Retrieval Label
   *   
   *   @return JPanel panel that contains the 5 rows of 
   *    TextFields and their corresponding Labels.
   */
  private JPanel makeTextBoxesPanel() {
    // create a grid of TextFields and their Labels
    JPanel[] textPanels = new JPanel[5];
    JPanel gridPanel = new JPanel();
    gridPanel.setLayout(new GridLayout(5, 1));
    gridPanel.setBackground(Color.white);
    for (int i = 0; i < 5; i++) {
      textPanels[i] = new JPanel();
      textPanels[i].setBackground(Color.white);
      textPanels[i].setLayout(new GridLayout(1,3));
      textPanels[i].add(awardLabels[i]);
      textPanels[i].add(inputText[i]);
      textPanels[i].add(outputText[i]);
      gridPanel.add(textPanels[i]);
    }
    return gridPanel;
  }

  /**
   *  makeTextAndButtonPanel creates the panel with
   *  the buttons (6) and the JLabel that serves as
   *  an information source for the user.
   *
   *  @return The JPanel panel that contains all 6 buttons
   *        and a JLabel feedback area
   *
   *  Note that infoLabel uses HTML code for formatting the text below
   */
  private JPanel makeTextAndButtonPanel() {
    JPanel both = new JPanel(new BorderLayout());
    both.setBackground(Color.white);
    both.add(makeButtonPanel(),BorderLayout.CENTER);
    infoLabel = new JLabel("<html><B>         Welcome to the cs230 oscars              </b></html> ", JLabel.CENTER);
    both.add(infoLabel,BorderLayout.SOUTH);
    return both;
  }
  
  /**
   *  makeButtonPanel creates the panel with
   *  the buttons (6) 
   *
   * @return The JPanel panel containing the 6 buttons
   */
  private JPanel makeButtonPanel() {
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(Color.white);
    buttonPanel.setLayout(new GridLayout(2,3, 10, 10));
    addButton = new JButton("Add Oscar awards");
    addButton.setForeground(Color.blue);
    addButton.setFont(new Font("Verdana", Font.PLAIN, 14));
    addButton.addActionListener(this);
    buttonPanel.add(addButton);
    
    removeButton = new JButton("Remove Oscar Awards");
    removeButton.setForeground(Color.blue);
    removeButton.setFont(new Font("Verdana", Font.PLAIN, 14));
    removeButton.addActionListener(this);
    buttonPanel.add(removeButton);

    retrieveButton = new JButton("Retrieve Oscar Awards");
    retrieveButton.setForeground(Color.blue);
    retrieveButton.setFont(new Font("Verdana", Font.PLAIN, 14));
    retrieveButton.addActionListener(this);
    buttonPanel.add(retrieveButton);

    printButton = new JButton("Print All");
    printButton.setForeground(Color.blue);
    printButton.setFont(new Font("Verdana", Font.PLAIN, 14));
    printButton.addActionListener(this);
    buttonPanel.add(printButton);

    dirFreqButton = new JButton("Directors/Award Count");
    dirFreqButton.setForeground(Color.blue);
    dirFreqButton.setFont(new Font("Verdana", Font.PLAIN, 14));
    dirFreqButton.addActionListener(this);
    buttonPanel.add(dirFreqButton);

    quitButton = new JButton("Quit");
    quitButton.setForeground(Color.blue);
    quitButton.setFont(new Font("Verdana", Font.PLAIN, 14));
    quitButton.addActionListener(this);
    buttonPanel.add(quitButton);

    return buttonPanel;
  }
  
  /**
   *  populateOscarsTable places some initial entries into the table,
   *   keyed on the year of the award.
   *
   * @param Table t The table containing the Oscar awards information, in OscarInfo format
   *
   */
  private void populateOscarsTable(Table<OscarInfo> t) {
    OscarInfo oscar2010 = new OscarInfo("The Hurt Locker","Kathryn Bigelow", "Jeff Bridges", "Sandra Bullock");  
    OscarInfo oscar2009 = new OscarInfo("Slumdog Millionaire","Danny Boyle", "Sean Penn", "Kate Winslet");  
    t.insert(new TableEntry<OscarInfo>("2010", oscar2010));
    t.insert(new TableEntry<OscarInfo>("2009", oscar2009));

  }
	
  /**
   * addOscarYear adds Oscar award information for a new year to the oscarTable
   *  and prints a message indicating so in the JLabel infoLabel
   *
   */
  public void addOscarYear () {
      String year =  inputText[0].getText();
      String picture = inputText[1].getText();
      String director = inputText[2].getText();
      String actor = inputText[3].getText();
      String actress = inputText[4].getText();
      infoLabel.setText("The information has been added to the database.");

      OscarInfo newOscar =new OscarInfo(picture,director,actor,actress);
      oscarTable.insert(new TableEntry<OscarInfo>(year,newOscar));
  }
	
  /**
   * removeOscarYear removes the table entry for the requested Oscar year
   *  and prints a message indicating so in the JLabel infoLabel
   *
   */
  public void removeOscarYear () {
      String year = inputText[0].getText();
      oscarTable.delete(year);
      infoLabel.setText("The year has been removed from the database.");

  }


  /**
   * retrieveOscarYear prints the information about Oscar awards for a single year
   *  and prints a message indicating so in the JLabel infoLabel
   *
   */
  public void retrieveOscarYear () {
      String year = inputText[0].getText().trim();
      OscarInfo a =  oscarTable.search(year);

      outputText[0].setText(year);
      outputText[1].setText(a.getPicture());
      outputText[2].setText(a.getDirector());
      outputText[3].setText(a.getActor());
      outputText[4].setText(a.getActress());
      
      infoLabel.setText("Oscar awards for the year " + year + " have been retrieved from the database");

      
      
 

  }
	
  /**
   * printOscarTable prints the contents of the full oscarTable
   *  and prints a message indicating so in the JLabel infoLabel
   *
   */
  public void printOscarTable () {
      System.out.println(oscarTable.toString());

  }
   
	
  /**
   * printDirFreq prints out (to the standard output screen, not
   *  to the GUI) the name of  each director with the num of awards (s)he has received
   *  in the period starYear until endYear
   *
   *  @param String startYear  Start of the period, e.g. 1950
   *  @param String endYear  End of the period, e.g. 2006
   *
   */
 public void printDirFreq(String startYear, String endYear) {

    // Uses a Table to store entries, where the key is a String for the name of 
    // the director and the entry is an Integer

    Table<Integer> freqTable = new Table<Integer>(); // table will hold the freq info
    //search the table for every consecutive key
    // from startYear to endYear.
    int yearIntStart = Integer.parseInt(startYear);
    int yearIntEnd = Integer.parseInt(endYear); 
    int yearInt = yearIntStart;
    String yearStr;

    while (yearInt <= yearIntEnd){
      yearStr = Integer.toString(yearInt);
      OscarInfo oi = (OscarInfo) oscarTable.search(yearStr);
      if (oi != null) {
	String dir = oi.getDirector();
	addDirector(dir, freqTable);
      }
      yearInt++;
    }
    //print out the freq table
    System.out.println();
    System.out.println(freqTable);
    System.out.println();
 }
      
  
  /**
   * addDirector  adds director to the freq table with freq=0, 
   *  if (s)he is not there yet.  If dir is in the freq table already, 
   *  increment his/her counter by 1.
   *
   *  @param String dir  the director's name e.g. Martin Scorsese
   *  @param Table freqTable  the table containing the frequency data
   *
   */
  private void addDirector(String dir, Table<Integer> freqTable) {
    Integer fInfo = (Integer)freqTable.search(dir);
    if (fInfo != null) { //This director already exists in the Frequency table
      int newFreq = fInfo.intValue() + 1;
      freqTable.insert(new TableEntry<Integer>(dir, new Integer(newFreq)));
    }
    else { // this director is not in the Freq table yet
      freqTable.insert(new TableEntry<Integer>(dir, new Integer(1)));
    }
  }
 
  /** 
  *
  * This applet's action method is called whenever one of the two buttons   
  *   is pressed or when the user enters a letter in the letterbox and
  *   hits return
  *  Can test for a button press using one of two techniques:
  *   (1) (e.getSource() == <name of instance variable holding button>)
  *   (2) (e.getActionCommand().equals(<string that is label of button>))
  *  Here, we use approach (1) for the buttons
  *  since each button is an instance variable
  *
  * @param event  ActionEvent that the GUI is listening for
  *
  */
  
  public void actionPerformed (ActionEvent event) {
    // provides special handling when the user presses a Button on the GUI
    Object source = event.getSource();
    if (source.equals(addButton)) 
      addOscarYear();
    else if (source.equals(removeButton))
      removeOscarYear();
    else if (source.equals(retrieveButton))
      retrieveOscarYear();
    else if (source.equals(printButton))
      printOscarTable();
    else if (source.equals(dirFreqButton))
      printDirFreq("1900", "2010"); 
    else if (source.equals(quitButton))
      System.exit(0);
  }  // actionPerformed


  // ----------------------------------------------------------------------------
  /**
   *
   *  Creates the applet and shows the GUI, 
   *  Standard boilerplate class methods for 
   *  wrapping applet in an application JFrame. 
   *
  */
  private static void createAndShowGUI() {
    
    // Enable window decorations. 
    JFrame.setDefaultLookAndFeelDecorated(true); 

    //Create and set up the window.
    JFrame frame = new JFrame("CS230 Oscars");
    
    frame.setSize(700, 250);
    
    // Specify that the close button exits application. 
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    OscarsGUI applet = new OscarsGUI();
    applet.init(); 
    
    // frame.getContentPane().add(contents, BorderLayout.CENTER);
    frame.add(applet, BorderLayout.CENTER);

    //Display the window.
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