/** Gabe Goodwin
    10/19/2010
    cs230 lab 6
*/
import java.util.*;
public class War{
    //instance variables
    private Deck cards; //gives us a deck of cards to play with
    private Card[] player;
    private Card[] comp;
    private Stack<Card> you;
    private Stack<Card> computer;
    private Stack<Card> tie;
    private Stack<Card> compWin;
    private Stack<Card> youWin;

    //constructor
    public War(){
        cards = new Deck();
	player = new Card[26];
	comp = new Card[26];
	you = new Stack<Card>();
	computer = new Stack<Card>();
	tie = new Stack<Card>();
	compWin = new Stack<Card>();
	youWin = new Stack<Card>();
	cards.shuffle();
	cards.dealCards(player,comp);// puts the card in two arrays
	you = stackCards(player);//turns the players array of cards into a stack
	computer = stackCards(comp);
    }

    public static  Stack<Card> stackCards(Card[] card){
	//should put cards into two stacks which can then be played with
	Stack<Card> temp = new Stack<Card>();
	for(int i =0;i<card.length;i++){
	Card one = card[i];
	temp.push(one);
	}
	return temp;}
	

	  

    public void playOneRound(){
	Card a = computer.pop();
	Card b = you.pop();
	int isTie = 0;//keeps track of whether there are cards in the tie stack or not
	int compCards = 0;
	int youCards = 0;
	    
	while(!computer.empty() || !you.empty()){
	    System.out.println("*********" +compCards);
	    System.out.println(youCards);
	    System.out.println(a);
	    System.out.println(b);
	if(a.compareTo(b)==0){
	    System.out.println("tie");
	    tie.push(a);
	    tie.push(b);
	    isTie = isTie +2;
	 
	}else if(a.compareTo(b)>0){
	    System.out.println("comp wins");
	    compWin.push(a);
	    compWin.push(b);
	    compCards =compCards +2;
	    if(isTie != 0){
		compCards = compCards + isTie;
		while(isTie!=0){
		compWin.push(tie.pop());
		isTie = isTie-1;
		}
	    }}else if(a.compareTo(b)<0) {
		System.out.println("you win");
	    youWin.push(a);
	    youWin.push(b);
	    youCards = youCards +2;
	     if(isTie != 0){
		youCards = youCards + isTie;
		while(isTie!=0){
        	compWin.push(tie.pop());
		isTie = isTie-1;
	        }
	     }}
       	a = computer.pop();
	b = you.pop();
	}//end while loop, game is done playing need to determine winner
	
	if(compCards > youCards){
	    System.out.println( "The computer has won with a total of " + compCards + " to " +youCards + " cards.");
	} else if(compCards < youCards){
			    System.out.println( "Yay!You have won with a total of " + youCards + " to " +compCards + " cards.");
	    }
	    else{
		 System.out.println("There was a tie");
	    }}


	public static void main(String[] ars){
	    War game = new War();
	    game.playOneRound();

}
}