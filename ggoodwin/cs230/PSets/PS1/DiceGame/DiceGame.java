public class DiceGame {

  // FILE NAME: DiceGame.java
  // WRITTEN BY:
  // WHEN:
  
  // PURPOSE: Simulates a Dice Poker game played between the computer and
  // user. This class definition contains a main() method that assumes 
  // that the user enters a name in the command line, for example: 
  //        java PlayDice Dave


/**********************************/
  private void printArray (int[] arr) {
  
    // prints the contents of the input array. 
    for (int i = 0; i < arr.length; i++)
      System.out.print(arr[i] + " ");
  }


/**********************************/
	
  private int[] throw5Dice(int [] dArray) {
  
	//Throws the dice 5 times
	// fills the input array with five random integers between 1 and 6,
	// simulating a rolling of 5 dice
	// returns the updated input array
         
     for (int i = 0; i < 5; i++)
        dArray[i] = (int)(Math.random() * 6) + 1;
     
     printArray(dArray);
     System.out.print(": ");
     return dArray;
  
  }	


/**********************************/

  private void accumulateValues(int[] input, int[] diceResults) {
  
  // Counts how many distinct values appear in the input array
  // and stores each count onto the diceResults array.
  // CONDITION: diceResults should have enough length to accomodate the values
  // found in the input array. 
  
  }
  
/**********************************/
    
  public int getRank (int[] input) {
  
    // given an input array storing five dice values, determines the rank
    // (i.e. an integer between 0 and 6) of the set of values
    
    // first determine how many of each dice value are stored in input
	// by calling the accumulateValues() method
    int[] DiceResults = new int[7];
	accumulateValues(input, DiceResults);
	
	// Then, determine the rank based on the accumulated values
    return 0;
  }
  

/**********************************/
  public int playOneRound() {
  // returns 0 if computer wins the round
  // returns 1 if player wins the round
  // returns 2 if a tie
  
	// declare the array that will hold the 5 dice
	int[] DiceArray = new int[5];

	// First simulate the computer's play in the round & find its rank
  
	//then simulate the player's play in the round & find its rank
  
	// finally determine the winner based on their ranks and return the correct integer
	return 0;
  }
  

/**********************************/
  public void playDiceGame (String name, int numRounds) {
    // simulates the playing of numRounds of the Dice Poker game between 
    // HAL and the input name, and prints the winner at the end
   
    System.out.println("Hello " + name + ". I'm completely operational and all my circuits are functioning perfectly.");
	System.out.println("Would you like to play a game of Dice Poker? I play very well.");
 
    // declare variables for storing the score of all the rounds

    // for each round of the game:
       // play the round, determine the winner of the round
		int winner = playOneRound();
		
    // After all rounds played, determine the final winner of the game and print the results
  }


/**********************************/

  public static void main (String args[]) {

	// Create an instance of a new game
	DiceGame game = new DiceGame();


	// and then play the game if they entered a player's name
    if (args.length > 0)
      game.playDiceGame(args[0], 5);
    else
      System.out.println("Please enter a name in the command line");
    // when you have working code, increment the number of rounds of play
    
  }

}
