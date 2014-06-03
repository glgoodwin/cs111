public class Student{
 //instance variables
    String lastName;
    String firstName;
    String year;
    String lecture;
    String lab;
    
    //Constructors
    public Student(String lName, String fName, String classYear, String lectureSection, String labSection){
         lastName = lName;
         firstName = fName;
         year= classYear;
         lecture = lectureSection;
         lab = labSection;
    }
    public Student(String studentInfo){
         String [] student = studentInfo.split(";");
         lastName = student[0];
         firstName = student[1];
         year = student[2];
         lecture = student[3];
         lab = student[4];
    }
    public String getFirstName(){
         return firstName;
    }
    public String getLastName(){
         return lastName;
    }
    public String getLab(){
         return lab;
    }
    public String getLecture(){
         return lecture;
    }
    public String getYear(){
         return year;
    }
    public String toString(){
         return (firstName +" "+ lastName + " year "+ year + " is in " + lecture + " and "+lab);
    }
    public static void main(String [] args){
         Student s1 = new Student("Alexander", "Wendy", "2012", "lec03","lab01"); 
         Student s2 = new Student("Alexander;Wendy;2011;lec02;lab03;");
         System.out.println(s2.toString());
    
}
}
                            
         
         
         
     
