//Gabe Goodwin

public class Factorials{
     
     public static void main (String[] args){
          double n = 3;
          
          Factorials billy = new Factorials();
          
          double answer = billy.computeFactorial(n);
          
          System.out.println("the factorial of " + n + " is " + answer +".");
          
          
     }
     public double computeFactorial(double n){
          if(n==0){
               return 1;
          }else{
          return n*(computeFactorial(n-1));
     }
}
}
                                  
    
             
   


                         
               
                         
                       
                         
                      