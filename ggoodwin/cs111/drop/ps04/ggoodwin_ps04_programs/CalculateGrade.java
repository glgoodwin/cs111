/*Gabrielle Goodwin
 * CS 111 PS 4
 * Task 4
 * Calculate Grade
 */
public class CalculateGrade{
  public static void main (String [] args){
  
     double ps1 = 100.00;
     double ps2 = 90.00;
     double ps3 = 80.00;
     double ps4 = 70.00;
     double ps5 = 60.00;
     double ps6 = 90.00;
     double ps7 = 90.00;
     double ps8 = 80.00;
     double ps9 = 100.00;
     double ps10 = 95.00;
     double exam1 = 80.00;
     double exam2 = 80.00;
     double examFinal = 70.00;
     
     CalculateGrade grader = new CalculateGrade();
     double score = grader.determineScore(ps1,ps2,ps3,ps4, 
                             ps5,ps6,ps7,ps8,ps9,
                             ps10, exam1, exam2, examFinal);
     String grade = grader.getLetterGrade(score);
     System.out.print( "your score of " + score + " earns you a letter grade of" + grade + ".");
  }
  
/* Given scores (between 0.0 and 100.0) on ten problem sets and three exams,
 * the determineScore method calculates and returns a final score in the course.
 * The ten homework assignments contribute 0.40 to the final score.
 * The first exam contributes 0.15 to the final score.
 * The second exam contributes 0.25 to the final score.
 * The final exam contributes 0.20 to the final score.
 */
public double determineScore(double ps1, double ps2, double ps3, double ps4, 
                             double ps5, double ps6, double ps7, double ps8, double ps9,
                             double ps10, double exam1, double exam2, double examFinal){
     double Ps = (((ps1 + ps2 + ps3 + ps4 + 
                             ps5 + ps6 + ps7 + ps8 + ps9 +
                             ps10)/10)*.4);
     double Ex1 = exam1 * .15;
     double Ex2 = exam2 * .25;
     double Examfinal = examFinal * .20;
     double score = (Ps + Ex1 + Ex2 + Examfinal);
       return score;
     
     
     }

  
public String getLetterGrade(double score)
      /*  scoreToGrade takes in a floating point number (e.g. 84.5) and
     converts it, using the cs111 grade guidelines, into a letter grade,
     (e.g. "B").
    */
  {
   if (score>= 93.3) 
     return "A";
   else if ((score >=90.0) && (score<93.3)) 
     return "A-";
   else if ((score >=86.66) && (score<90.0))
       return "B+";
   else if ((score >=83.33) && (score<86.66))
       return "B";
   else if ((score >=80.0) && (score<83.33))
       return "B-";
   else if ((score >=76.66) && (score<80.00))
       return "C+";
   else if ((score >=73.33) && (score<76.66))
       return "C";
   else if ((score >=70.00) && (score<73.33))
       return "C-";
   else if ((score >=65.00) && (score<70.00))
       return "D";
   else
       return "F";
}
}
