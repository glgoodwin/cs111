public class Game {
	
  // FILE NAME: Game.java
  // WRITTEN BY:
  // WHEN:

  // PURPOSE: new class used to store information about a single game
  // and to simulate the outcome of playing a game
    
  private Team team1;		// two teams competing in the game
  private Team team2;
  private int score1;		// scores for the two teams
  private int score2;
	
  // constructor
	
  public Game (Team team1, Team team2) {
    this.team1 = team1;
    this.team2 = team2;
    score1 = 0;
    score2 = 0;
  }
	
  // instance methods to access the instance variables
  
  public Team getTeam1 () {
    return team1;
  }

  public Team getTeam2 () {
    return team2;
  }

  public int getScore1 () {
    return score1;
  }

  public int getScore2 () {
    return score2;
  }

  public String toString () {
    // returns a String representation of the contents of a Game object
    return team1 + " vs. " + team2 + ": " + score1 + " to " + score2;
  }
	
  public void playGame () {
    // simulates the outcome of playing a single game between two teams
    int seed1 = team1.getSeed();
    int seed2 = team2.getSeed();

    // Randomly generate different scores for the winning team and losing team
    int score_winner = (int)(Math.random() * 6);
    int score_loser = (int)(Math.random() * 6);
    while (score_winner == score_loser)
      score_loser = (int)(Math.random() * 6);
    if (score_loser > score_winner) {  // Swap scores
      int temp = score_loser;
      score_loser = score_winner;
      score_winner = temp;
    }

    // Assign winning score to lower seed with some proportionally large probability,
    // and assign winning socre to higher seed with some proportionally small probability.
    double probabilityTeam1_wins = 1.0 - (((double)(seed1))/(seed1+seed2));
    double probabilityTeam2_wins = 1.0 - (((double)(seed2))/(seed1+seed2));
    if (Math.random() < probabilityTeam1_wins) {  // Team 1 wins
      score1 = score_winner;
      score2 = score_loser;
    } else {  // Team1 loses
      score1 = score_loser;
      score2 = score_winner;
    }
  } 
	
}
