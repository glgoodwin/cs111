/** FILE NAME: LabOps.java
 * AUTHOR: CS111 Staff
 *
 *
 * COMMENTS: You should write your class methods for this lab here.
 * Notice that this class extends StringListOps, which extends StringList.
 * Therefore, all methods defined in these two classes are inhereted here.
 * 
 * In particular:
 * The following methods are inherited from StringList:
 * public static StringList empty();
 * public static boolean isEmpty (StringList L);
 * public static StringList prepend (String s, StringList L);
 * public static String head (StringList L);
 * public static StringList tail (StringList L);
 * public static boolean equals (StringList L1, StringList L2);
 * public static String toString (StringList L);
 * public static String fromString (String s);
 * 
 * The following methods are inherited from StringList:
 * public static int length (StringList L);
 * public static StringList append (StringList L1, StringList L2);
 * public static StringList postpend (StringList L, String s);
 * public static StringList reverse (StringList L);
 * 
 * All the above methods can be used in this file without the explicit 
 * "StringList." or "StringListOps." prefixes.
 * For example, you can write "head(L)" rather than "StringList.head(L)",
 * and "length(L)" rather than "StringListOps.length(L)".
 * 
 * MODIFICATION HISTORY:
*/    

public class LabOps extends StringListOps {
 // Write your methods here:
     public static StringList mapPluralize(StringList L){
          if(isEmpty(L)){
          return L;
          }else{
            String plural = head(L) + "s";
            StringList pluralize = prepend(plural, mapPluralize(tail(L)));
            return pluralize;
     }
                         }
     public static StringList mapUpperCase(StringList L){
          if(isEmpty(L)){
               return L;
          }else{
               String upPlural = head(L);
               StringList upperPlural = prepend(upPlural.toUpperCase(),mapUpperCase(tail(L)));
               return upperPlural;
          }
     }
     
     public static boolean isMember(String s, StringList L){  
          if(isEmpty(L)){
               return false;
          }else if (s.equals(head(L))){
          return true;
     }else{
          isMember(s,tail(L));
                   }
              return false; }
     public static StringList explode(String s){
          if (s.equals("")){
               return empty();
          }else{
               String letter = first(s);
               StringList explosion = prepend(letter, explode(butFirst(s)));
                                              return explosion;
                                              }
                    }
     public static String implode(StringList L){
          if(isEmpty(L)){
           String empty = " ";
               return empty;
          }else{
               String boom = head(L) + implode(tail(L));
               return boom;
          }
     }
     public static StringList filterMatches(String s, StringList L){
          if (isEmpty(L)){
               return L;
          }else if(s.indexOf(head(L))){//cant get indexOf to work
               String match = indexOf(s, head(L));
               StringList  = prepend(k, filtermatches(s,tail(L)));
               return p;
          }else{
               filterMatches(s,tail(L));
     }
     }                             
          
               
               

 
  
   public static void main (String [] args) {
     //testing the f=given auxiliary methods
  System.out.println("first(\"cs111\") = \"" + first("cs111") + "\"");
  System.out.println("butFirst(\"cs111\") = \"" + butFirst("cs111") + "\"");
  System.out.println("fromString(\"[I,do,not,like,green,eggs,and,ham]\") = "
                      + fromString("[I,do,not,like,green,eggs,and,ham]"));  
  
  // Add your testing code here: 
StringList A =  fromString("[dog,cat,mouse,horse,goose]");
StringList B = fromString("[harry,ron,hermione]");
StringList C = fromString("[green,red,blue,yellow]"); 
StringList D = fromString("[]");
System.out.println("Testing mapPluralize: ");
System.out.println(mapPluralize(A));
System.out.println(mapUpperCase(B));
System.out.println(isMember("magenta",C));
System.out.println(implode(B));
System.out.println(explode("dog"));
      
 }
 //---------------------------------------------------------------
 // The following auxiliary methods are helpful for defining explode()
 
 // Given a string s, returns a one-character string containing
 // the first character of s. 
 public static String first (String s) {
  return new Character(s.charAt(0)).toString();
 }

 
 // Given a string s, returns the substring of s that contains
 // all characters except the first. 
 public static String butFirst (String s) {
  return s.substring(1,s.length());
 }

 
}

