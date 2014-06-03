// CS111 Spring 2010
import java.util.*;
import java.io.*;

public class CareerAspirations {
 // instance variables
  SecretCareer [] scArray;
  int number;
  
  // constructor
  public CareerAspirations(String filename)
  {// can assume the max size of array is 20
       Scanner reader = new Scanner(PicOps.getFolder()+ filename);
       for(int i =0;reader.hasNextLine();i++){
            String name = reader.nextLine();
            String job = reader.nextLine();
            SecretCareer sc1 = new SecretCareer(name,job); 
            scArray[i]= sc1;
            
       
         
          }
  }
  
  public String toString()
  {//print out a string representation of your array
       for(int i=0;i<scArray.length;i++){
            String careers = scArray[i] + "";
     } 
       return careers;
  }
  
  public static void main (String [] args)throws FileNotFoundException
  {
    CareerAspirations careerA = new CareerAspirations(args[0]);
    System.out.println(careerA);
  }
}