/** Defines a class for maintaining an array of School objects
 * Gabe Goodwin
 * cs 230 PSet 2
 * Problem 2
 * Sept 26,2010
 */
public class GradSchools {
	
  // instance variables
  
  private School[] schools;	 // array of School objects
  private int numSchools;	 // number of School objects stored in array
  
  // 3. Code required to complete the assignment:
  /** constructor 
   */ 
    public GradSchools(int size){
      schools = new School[size];
      numSchools = 0;
    }


  // 4. Code required to complete the assignment:
  /** instance method to add a new School object to the schools array
   */
    public void addSchool(String name, int rateWomen, int rateAI, int rateSys, int rateTheory, int rateEffect, int ratePubs){     System.out.println(numSchools);
	if (numSchools<schools.length){
	 schools[numSchools] = new School(name, rateWomen, rateAI, rateSys, rateTheory, rateEffect, ratePubs);
	    numSchools++;
	}else{
	    System.out.println("No space to fit more schools in the array");
		}
    }

	
  /** instance method that prints information about all of the School objects
   * in the schools array
   */
  public void printGradSchools () {
    System.out.println("There are " + numSchools + " schools in the database:");
    for (int i = 0; i < numSchools; i++)
      System.out.println(schools[i]);
    System.out.println();
  }
  
  // 5. Code required to complete the assignment:
  /** instance method that computes overallRating for each School object
   */	
    public void computeRatings(int weightWomen, int weightAI, int weightSys, int weightTheory, int weightEffect, int weightPubs){
	int i =0;
	while(i<numSchools){
	    School a = schools[i];
	    System.out.println(a.getName());
	    a.computeRating(weightWomen, weightAI, weightSys, weightTheory,
			    weightEffect, weightPubs);
	
	    i++;
	}}
 

  // 7. Code required to complete the assignment:
  /** instance method that rank orders the School objects in the schools
   * array, either by a single factor or the overallRating as specified
   * by an input String, and prints the names of the schools in order
   * from highest to lowest rank
   */
    public void rankSchools(String factor){
	if(inputRankValue(factor)==true){
	sortArray(schools);//puts array in decreasing order
	System.out.println("Ranking of schools from highest to lowest using "+ factor);
	int j = 0;
	while(j<numSchools){
	    System.out.println(schools[j].getName());
	
	    j++;

	}}}

    private boolean inputRankValue(String factor){//helper method for RankSchools
	int i = 0;
	while(i<numSchools){        //Puts the corect info into rank Value.
	    if(factor.equals("Women")){
	       School a = schools[i];
	       a.setRankValue(a.getRateWomen());
	       }else if(factor.equals("AI")){
		 School a = schools[i];
	       a.setRankValue(a.getRateAI());
	       }else if(factor.equals("Sys")){
		  School a = schools[i];
	       a.setRankValue(a.getRateSys());
	        }else if(factor.equals("Theory")){
		   School a = schools[i];
	       a.setRankValue(a.getRateTheory());
		 }else if(factor.equals("Effect")){
		  School a = schools[i];
	       a.setRankValue(a.getRateEffect());	
		 }else if(factor.equals("Pubs")){
		  School a = schools[i];
	       a.setRankValue(a.getRatePubs());
		 }else if(factor.equals("Overall")){
		   School a = schools[i];
	       a.setRankValue(a.getOverallRating());
			}else{
	       System.out.println("Invalid input.Please input Women,AI, Sys, Theory, Effect, Pubs, or Overall.");
	      
	       return false;
			}
			i++;
	} return true;
    }
    
      



  // 6. Documentation required to complete the assignment:
  /** instance method that sorts the School objects in the schools array in 
   * order of increasing rankValue
   * Sorting strategy for original sortArray() method:
   */
    //this method goes through the whole array checking each element. It puts them in order by taking the lowest int that it has found so far and putting it in the back of the array. 
  public void sortArray (School[] arrayA) {
    // sorts the integers in arrayA in decreasing order
    int minNum;        // maximum integer
    int maxIndex;      // index of maximum integer
    int i, j;
    for (j = numSchools - 1; j > 0; j--) {
      maxIndex = 0;
      minNum = arrayA[0].getRankValue();
      for (i = 1; i <= j; i++)     //changed the value sign so that it moves the lowest #
	  if (arrayA[i].getRankValue() < minNum) {
	      minNum = arrayA[i].getRankValue();
	  maxIndex = i;
	}
      swap(arrayA, maxIndex, j);
    }   
    
  }

  /** exchanges the contents of arrayA[i] and arrayA[j]
   */
  private void swap (School[] arrayA, int i, int j) {
    // exchanges the contents of locations i and j in arrayA
    School temp = arrayA[i];
    arrayA[i] = arrayA[j];
    arrayA[j] = temp;
  }
  
  /** Provides code for testing the methods defined in the
   * GradSchools class for maintaining an array of School objects
   */
  public static void main (String[] args) {
      GradSchools myGradSchools = new GradSchools(7);//changed this to make it easier to test
    // Disclaimer: The ratings here are somewhat arbitrary
    myGradSchools.addSchool("MIT", 5, 10, 9, 10, 10, 7);
    myGradSchools.addSchool("Stanford", 9, 8, 5, 8, 10, 9);
    myGradSchools.addSchool("CMU", 6, 9, 9, 7, 8, 6);
    myGradSchools.addSchool("UC Berkeley", 4, 6, 8, 9, 9, 9);
    myGradSchools.addSchool("Cornell", 9, 5, 8, 9, 9, 8);
    myGradSchools.addSchool("Univ. of Illinois", 4, 7, 7, 7, 7, 7);
    myGradSchools.addSchool("Univ. of Washington", 7, 5, 7, 8, 8, 8);
    myGradSchools.addSchool("Princeton", 8, 4, 5, 8, 7, 10);
    int weightWomen = 5;
    int weightAI = 5; 
    int weightSys = 2;
    int weightTheory = 0;
    int weightEffect = 5;
    int weightPubs = 4;
    myGradSchools.computeRatings(weightWomen, weightAI, weightSys, weightTheory, weightEffect, weightPubs);
    myGradSchools.printGradSchools();
    // 8. Code required to complete the assignment:
    myGradSchools.rankSchools("Women");
    myGradSchools.rankSchools("FoodQuality");
    myGradSchools.rankSchools("AI");
    myGradSchools.rankSchools("Pubs");
    myGradSchools.rankSchools("Theory");
    myGradSchools.addSchool("Fake",4,5,6,2,8,4);

    
  }

  
}