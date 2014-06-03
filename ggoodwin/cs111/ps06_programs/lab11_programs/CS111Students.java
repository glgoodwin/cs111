import java.io.*;
import java.util.*;
public class CS111Students{
     //instance variables
     Student [] csStudents;
     int number;
     //constructor
     public CS111Students(String filename){
          Scanner reader = new Scanner(PicOps.getFolder()+ filename);
          csStudents = new Student[100];
       for(int i =0;reader.hasNextLine();i++){
            String line = reader.nextLine();
            Student s1 = new Student(line);
            csStudents[i] = s1;
       }
     }
       public Student[] getLabGroup(){
            Scanner reader2 = new Scanner(System.in);
            System.out.println("Enter lab group: ");
            String group = reader2.nextLine();
            for(int i=0; i< csStudents.length; i++){
                 if(group.equals(csStudents[i].getLab())){
                    System.out.println(csStudents[1]);
                 }}}
       public static void main(String [] args) throws IOException{
          
            CS111Students bob = new CS111Students("data.txt");
            bob.getLabGroup();
       }
       }
            
                      
                      
                      
                 
            
                                          
     
