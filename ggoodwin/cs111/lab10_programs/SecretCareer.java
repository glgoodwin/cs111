// Cs111 Spring 2010

public class SecretCareer
{
  // instance variables
  String name;
  String secretJob;
  
  // constructor
  public SecretCareer(String n, String job)
  {
    name = n;
    secretJob = job;
  }
  
  public String toString()
  {
    String result = "shhhh! "+name+"'s secret career aspiration is to be a "+secretJob+".";
    return result;
  }
  
  public static void main(String[] args)
  {
    //testing
    SecretCareer sc1 = new SecretCareer("Patty","reality tv show star");
    System.out.println(sc1);
  }
    
  
}