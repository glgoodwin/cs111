import java.util.*;
import java.lang.Object.*;

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
     int rank1 = team1.getSeed();
     int rank2 = team2.getSeed();
     double s1 = Math.random();
     System.out.println(s1);
     double s2 = Math.random();
     System.out.println(s2);
     
     if(rank1 < rank2){
	 double one = (s1*10)+1;
	 score1 = (int)one;//gives a slight advantage to the person with a higher seed
	 double two = (s2*10); 
	 score2 = (int)two;
	 if(score1 == score2){
	     score1 = score1+1; //gives the game to the higher seed if there is a tie
     }
     else if(rank2 < rank1){
	 double r = (s2*10)+1;
	 score2 = (int)r;
	 double t = (s1*10);    
	 score1 = (int)t;
	 if(score1 == score2){
	     score2 = score2+1;
	 }}}}
	
}
