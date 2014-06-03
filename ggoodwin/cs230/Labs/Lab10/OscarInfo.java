/** CS230 
 *  OscarInfo.java
 *
 */
public class OscarInfo {
	
  // new class to store information related to a movie that won an Oscar
  
  private String bestPicture;
  private String bestDirector;
  private String bestActor;
  private String bestActress;
	
  // constructor
  
  public OscarInfo (String picture, String director, String actor, String actress) {
    bestPicture = picture;
    bestDirector = director;
    bestActor = actor;
    bestActress = actress;
  }
	
  // instance methods
  
  public String getPicture () {
    return bestPicture;
  }
  
  public String getDirector () {
    return bestDirector;
  }
  
  public String getActor () {
    return bestActor;
  }
  
  public String getActress () {
    return bestActress;
  }
	
  public String toString () {
    String S = "Best Picture: " + bestPicture + "\nBest Director: " + bestDirector;
    return S + "\nBest Actor: " + bestActor + "\nBest Actress: " + bestActress;
  }
  
}
	
	
