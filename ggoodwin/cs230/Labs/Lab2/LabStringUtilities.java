/*Gabrielle Goodwin
  CS 230 Lab 2
  9/14/2010
  Strings
*/

public class LabStringUtilities{
    
    public static String printPhrase(String phrase){
	//	System.out.println(phrase);
	if(phrase.length()==0){
	    return phrase;
	}else{
	    System.out.println(phrase.charAt(0));
	    return printPhrase(phrase.substring(1,(phrase.length())));
	}
	}

    public static boolean isPalindrome(String phrase)
    {  if( phrase.length()==1){
	    return true;
	    
	}else if( phrase.charAt(0)==phrase.charAt(phrase.length()-1)){
	   return isPalindrome(phrase.substring(1,(phrase.length()-1)));
       
       }else{
	   return false;
       }	
    }
    
    public static String removeVowels(String phrase)
    {
	
	
    }
    
    

    public static void main(String[]args){
	System.out.println( isPalindrome("racecar"));

    }
}

 