public class Team {
	
  // FILE NAME: Team.java
  // WRITTEN BY:
  // WHEN:

  // PURPOSE: new class used to store information about a single team
	
  private String name;        // name of the team
  private int seed;	      // seed number for the team
	
  // constructors
	
  public Team () {
    name = " ";
    seed = 0;
  }

  public Team (String name, int seed) {
    this.name = name;
    this.seed = seed;
  }
	
  // instance methods to access the instance variables
  
  public String getName () {
    return name;
  }
  
  public int getSeed () {
    return seed;
  }
	
  public String toString () {
    // returns a String representation of the contents of a Team object
    return seed + " " + name;
  }


    public static void main(String[] args){
	Team america = new Team("USA",2);
	Team sAmerica = new Team("Brazil",4);
	Game bob = new Game(america,sAmerica);
	bob.playGame();
	System.out.println(bob);
}
}