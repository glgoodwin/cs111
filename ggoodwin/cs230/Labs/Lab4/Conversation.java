/**  Conversation.java
  *
  *  Sohie CS230
  *   Demonstrates use of the Scanner class
  *    to facilitate a conversation.
  */

import java.util.Random;
import java.util.Scanner;

public class Conversation {
    public static void main (String [] args) {
	Random rand = new Random();
	
	String [] thingsToSay = {"Really?  Tell me more...",
				 "I can't believe that.  Are you sure that is true?",
				 "I was just thinking the same thing.",
			     "You are such a liar!",
				 " "};
	
	String utterance;
	Scanner scan = new Scanner(System.in); // read from the keyboard
	
	System.out.println("~~Let's have a conversation:");
	utterance = scan.nextLine(); // read in user input
	
	while (!(utterance.equals("bye"))) {
	    int r = rand.nextInt(5); //0-4
	    if (r==4) { // make a new string
		thingsToSay[4] = " " + utterance + " is a silly thing to say to me.";
	    }
	    System.out.println("~~"+thingsToSay[r]);
	    utterance = scan.nextLine();
	} // until the user types bye

	// done with conversation
	System.out.println("~~Goodbye! Let's talk again real soon.");
    }// main
    
}// Conversation
