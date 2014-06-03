/* Gabrielle Goodwin
 * Take Home Exam 2
 * Task 3
 * Powersets
 */ 

// Powerset is the class in which you should write your class methods 
// for the Powerset problem. 
//
// Since this class extends PS7_Solutions, it inherits class methods from all 
// of StringList, StringListOps, LabOps, and PS7_Solutions.  Such methods can 
// be used without the explicit "StringList.", "StringListOps.", "LabOps.",
// or "PS7_Solutions." prefixes. For example, you can write "head(L)" rather 
// than "StringList.head(L)", "length(L)" rather than
// "StringListOps.length(L)", "isMember(s,L)" rather than
// "LabOps.isMember(s,L)", and "removeDuplicates(L)" rather than
// "PS7_Solutions.removeDuplicates(L)".
//
// The following methods are inherited from StringList:
//    public static StringList empty();
//    public static boolean isEmpty (StringList L);
//    public static StringList prepend (String s, StringList L);
//    public static String head (StringList L);
//    public static StringList tail (StringList L);
//    public static boolean equals (StringList L1, StringList L2);
//    public static String toString (StringList L);
//    public static String fromString (String s);
//
// The following methods are inherited from StringListOps:
//    public static int length (StringList L);
//    public static StringList append (StringList L1, StringList L2); 
//    public static StringList postpend (StringList L, String s);
//    public static StringList reverse (StringList L);
//
// The following methods are inherited from LabOps:
//    public static StringList mapPluralize (StringList L) 
//    public static StringList mapUpperCase (StringList L) 
//    public static boolean isMember (String s, StringList L) 
//    public static StringList filterMatches (String s, StringList L)
//    public static String implode (StringList L) 
//    public static StringList explode (String s) 
//    public static StringList insert (String s, StringList L)
//    public static StringList insertionSort (StringList L)
//    public static String first (String s);
//    public static String butFirst (String s);
//
// The following methods are inherited from PS7_Solutions:
//    public static StringList remove (String s, StringList L);
//    public static StringList removeDuplicates (StringList L);
//    public static StringList mapConcat (String s, StringList L);
//    public static StringList insertions (String s1, String s2);
//    public static StringList insertionsList (String s, StringList L);
//    public static StringList permutations (String s);

public class Powerset extends PS7_Solutions {

  // In this class, we model character sets as strings 
  // that do not contain duplicate characters.
  // 
  // For example, the character set {'a', 'b', 'c'} is 
  // modeled as "abc" and the character set 
  // {'c', 'o', 'm', 'p', 'u', 't', 'e', 'r'} is modeled as "computer". 

  // The powerset of a set is the set of all subsets of a set. 
  // Representing character sets as strings, we can represent
  // the powerset of a character set S as a list of strings, each of whose
  // elements represents a subset of S. The powerset list of strings should 
  // contain no duplicates, and the order of elements in list is irrelevant. 
  // For instance, here are some of the ways to represent the powerset of "abc":
  //
  //    ["", a, b, c, ab, ac, bc, abc]
  //    [abc, bc, ac, ab, c, b, a, ""]
  //    ["", c, b, bc, a, ac, ab, abc]
  //    [bc, a, ac, abc, "", c, ab, b]
  // 
  // By convention, the strings in our string lists are displayed
  // without the double-quote delimiters. However, an exception is
  // made for the empty string and strings that begin/end with 
  // so-called "whitespace", because the structure of such strings
  // would be difficult to determine without the explicit double quotes.
  // ---------------------------------------------------------------------


  // ------------------------------- powerset ----------------------------
  public static StringList powerset (String chars) {//gives back the powersets of the inputted String
    StringList Q = (permutations3(permutations2(chars)));//gives back all possibilities for the inputted String
    String q = " ";//creates an empty string object
     return postpend((removeDuplicates(makeAlphebetical(Q))),q);}// sorts the strings and adds an empty string to the list,not recursive

  public static StringList permutations2(String s){//creates all available permutations of a string without changing order of letters
    if(s.isEmpty()){
      return empty();
    }else{
      StringList L = permutations(s);
      return append(L, permutations2(butFirst(s)));//recursive
    }}
  public static StringList permutations3(StringList L){// creates permutations of permutation 2 so that all possible variations of the letters are made
  if(isEmpty(L)){
    return L;
  }else{
    StringList R = permutations2(head(L));
    return append(R, permutations3(tail(L)));//recursive
                  }
  }
  public static StringList makeAlphebetical(StringList L){// puts all the Strings from Permutations3 in alphabetical order so that duplicates can be removed
    if(isEmpty(L)){
      return L;
    }else{
      String T= implode(insertionSort(explode(head(L))));
      return prepend(T, makeAlphebetical(tail(L)));//recursive
    }
  }

  // ------------------------------- Testing ---------------------------
  public static void main (String [] args) {
    if (args.length != 1) {
      System.out.println("Please include a single command line argument " 
                           + "in the Dr. Java interactions pane when "
                           + "executing this application.\n"
                           + "For example:\n"
                           + "\tjava Powerset abcd");
    } else {
      System.out.println(powerset(args[0]));
    }
  }
 
}
    
    
    
