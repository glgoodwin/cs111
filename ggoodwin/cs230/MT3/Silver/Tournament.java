import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Tournament extends JApplet 
  implements ActionListener {
  
  // FILE NAME: Tournament.java
  // WRITTEN BY:
  // WHEN:

  // PURPOSE: new class to store information about a complete tournament, 
  // create a GUI to display the progress of the tournament, and simulate 
  // the playing of the tournament games
    
  private Team[] teams;               // array of 16 initial teams
  private Tree<Team> gameTree;        // game tree for the full tournament
  private int nextLevel;              // next level of games in the game tree 
                                                     // to be played 
  private JPanel panelArray[], buttonPanel;   // Panels for the GUI display
  private JLabel[] labelArray;                // Labels for the GUI display
  private JLabel tournamentStatusLabel;       // status of the tournament
  private JButton startButton, nextButton, stopButton;   // control buttons

  // constructor
  
  public Tournament (Team[] teams) {
    this.teams = teams;
    gameTree = makeInitTree(1);
    nextLevel = 4;
    makeLabels();
    makeButtonPanel();
    makeDisplayPanels();
  }

  public void init () {
    // adds the five team Panels to the Applet display
    setLayout(new GridLayout(1, 5, 10, 10));
    for (int i = 0; i < 5; i++)
      add(panelArray[i]);
  }
    
  private Tree<Team> makeInitTree (int nodeLabel) {
    // recursive method to create and return an initial game tree with 16 teams 
    // at the bottom level and empty Team objects at all of the higher levels
    if (nodeLabel > 15)   // if at the bottom level of the tree, create a node 
                                        // containing a real team
      return node(teams[nodeLabel-16], new Tree<Team>(), new Tree<Team>());
    else   	     // otherwise create a node with an empty Team object
      return node(new Team(), 
		  makeInitTree(2*nodeLabel),
		  makeInitTree(2*nodeLabel + 1));
  }
	
  private void makeLabels () {
    // creates an array to store the 31 Labels that appear on the GUI display 
    // (only indices 1 to 31 of the array are used, corresponding to the node
    // labels of the game tree)
    labelArray = new JLabel[32];
    for (int i = 1; i < 32; i++) {
      labelArray[i] = new JLabel();
      labelArray[i].setOpaque(true);
      labelArray[i].setBackground(Color.white);
      if (i > 15)    // Labels for the initial 16 teams
	labelArray[i].setText(teams[i-16].toString());
      else           // Labels for the outcome of games to be played
	labelArray[i].setText(" ");
    }
    tournamentStatusLabel = new JLabel("New Tournament");
  }
	
  private void makeButtonPanel () {
    // create Panel of three buttons for starting a new game, playing
    // the next level of the game, and stopping the program
    buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
    startButton = new JButton("start new tournament");
    startButton.setForeground(Color.green);
    startButton.addActionListener(this);
    nextButton = new JButton("play next level");
    nextButton.setForeground(Color.yellow);
    nextButton.addActionListener(this);
    stopButton = new JButton("end tournament");
    stopButton.setForeground(Color.red);
    stopButton.addActionListener(this);
    buttonPanel.add(startButton);
    buttonPanel.add(nextButton);
    buttonPanel.add(stopButton);
  }

  private void makeDisplayPanels () {
    // creates a Panel for each of the five levels of the tournament
    panelArray = new JPanel[5];
    int i;
    for (i = 0; i < 5; i++)      // initialize the Panels
      panelArray[i] = new JPanel();
    // create Panel for the 16 initial teams
    panelArray[0].setLayout(new GridLayout(16, 1, 5, 5));
    for (i = 16; i < 32; i++)
      panelArray[0].add(labelArray[i]);
    // create Panel for the 8 teams at level 4 of the game tree
    panelArray[1].setLayout(new GridLayout(8, 1, 5, 5));
    for (i = 8; i < 16; i++)
      panelArray[1].add(labelArray[i]);
    // create Panel for the 4 teams at level 3 of the game tree
    panelArray[2].setLayout(new GridLayout(4, 1, 5, 5));
    for (i = 4; i < 8; i++)
      panelArray[2].add(labelArray[i]);
    // create Panel for the 2 teams at level 2 of the game tree
    panelArray[3].setLayout(new GridLayout(2, 1, 5, 5));
    panelArray[3].add(labelArray[2]);
    panelArray[3].add(labelArray[3]);
    // create Panel to display the final winning team, tournamentStatusLabel
    // and buttonPanel
    panelArray[4].setLayout(new GridLayout(5, 1, 5, 5));
    panelArray[4].add(new JLabel());   // top two grid cells are blank
    panelArray[4].add(new JLabel());
    panelArray[4].add(labelArray[1]);
    panelArray[4].add(tournamentStatusLabel);
    panelArray[4].add(buttonPanel);
  }
  
  private void startNewTournament () {
    // initializes the GUI display to start a new tournament with only the 16 
    // initial teams, and recreates the initial game tree
    gameTree = makeInitTree(1);
    nextLevel = 4;
    for (int i = 1; i < 32; i++) 
      if (i > 15)
	labelArray[i].setText(teams[i-16].toString());
      else
	labelArray[i].setText(" ");
    tournamentStatusLabel.setText("New Tournament");
  }

  private void playNextLevel () {
    // simulates playing of the games at the next level of the tournament
    playLevel(gameTree, 1, 1);
  }

  private void playLevel (Tree<Team> t, int level, int label) {
    // plays all the games that yield the winning teams to be placed at the
    // nextLevel of the game tree
    //if (!isLeaf(t)) {
      if (level == nextLevel) {          // play the next game
	Team team1 = getValue(getLeft(t));
	Team team2 = getValue(getRight(t));
	Game newGame = new Game(team1, team2);
	newGame.playGame();
	int score1 = newGame.getScore1();
	int score2 = newGame.getScore2();
	String S = labelArray[2*label].getText();
	S = S + "   " + score1;
	labelArray[2*label].setText(S);
	S = labelArray[2*label+1].getText();
	S = S + "   " + score2;
	labelArray[2*label+1].setText(S);
	if (score1 > score2) {
	    setValue(t, team1);
	  if (team1.getSeed() < team2.getSeed())
	    labelArray[label].setText(team1.toString());
	  else          // outcome is an upset
	    labelArray[label].setText(team1.toString() + "  *****"); 
	} else {
	  setValue(t, team2);
	  if (team2.getSeed() < team1.getSeed())
	    labelArray[label].setText(team2.toString());
	  else          // outcome is an upset
	    labelArray[label].setText(team2.toString() + "  *****");
	}  //Modification for MT 3
	if(level == 1){
	    System.out.println(silver(t));}
    } else {
	  playLevel(getLeft(t), level + 1, 2*label);
	  playLevel(getRight(t), level + 1, 2*label + 1);
    }
  }
	
  public void actionPerformed (ActionEvent event) {
    // handles the events generated by Button clicks
    Object source = event.getSource();   
    if (source.equals(startButton))
      startNewTournament();
    else if (source.equals(nextButton)) {
      if (nextLevel > 0) {
	    playNextLevel();
	    nextLevel--;
      }
      if (nextLevel == 0)
	    tournamentStatusLabel.setText("Tournament over!");
    } 
    else if (source.equals(stopButton))
      System.exit(0);
  }
    //Modification for MT3 also modified Play Level
    public static LinkedList<Team> silver (Tree<Team> t){
	LinkedList<Team> second = new LinkedList<Team>();
	while(!t.getLeft().isLeaf() && !t.getRight().isLeaf()){
	    if(t.getValue().equals(t.getLeft().getValue())){
		second.add(t.getLeft().getValue());
		t = t.getLeft();
	    }else if(t.getValue().equals(t.getRight().getValue())){
		second.add(t.getLeft().getValue());
		t = t.getRight();
	    }}
	return second;
    }
    
	
  // local abbreviations for the Tree class methods

  public static <V> boolean isLeaf (Tree<V> t) {  
    return Tree.isLeaf(t);  
  }  
  public static <V> Tree<V> node (V val, Tree<V> lt, Tree<V> rt) {  
    return Tree.node(val, lt, rt);  
  }      
  public static <V> V getValue (Tree<V> t) {  
    return Tree.getValue(t);  
  }  
  public static <V> Tree<V> getLeft (Tree<V> t) {  
    return Tree.getLeft(t);  
  }      
  public static <V> Tree<V> getRight (Tree<V> t) {  
    return Tree.getRight(t);  
  }
  public static <V> void setValue (Tree<V> T, V newValue) {
    Tree.setValue(T, newValue);
  }
  public static <V> void setLeft (Tree<V> T, Tree<V> newLeft) {
    Tree.setLeft(T, newLeft);
  }
  public static <V> void setRight (Tree<V> T, Tree<V> newRight) {
    Tree.setRight(T, newRight);
  }
}
		
		
