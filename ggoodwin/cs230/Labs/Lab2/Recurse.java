/* Gabrielle Goodwin
   Lab 2   CS 230
   9/14/2010
   Recurse.java
*/

public class Recurse{

    public static int power(int base, int exp){
	if(exp == 0){
	    return 1;

	}else{
	    return  base *(power(base,(exp-1)));  
	    
	}}
    public static int morePower(int base, int exp){
	if(exp == 0){
	    return 1;
	}else if(exp%2==0){
	    return  base* (morePower(base,exp/2));
	}else{
	   return base* base* (morePower(base,exp/2));
	  }

    }

    
    public static void main(String[]args){	
	System.out.println(morePower(2,5));
    }}

   