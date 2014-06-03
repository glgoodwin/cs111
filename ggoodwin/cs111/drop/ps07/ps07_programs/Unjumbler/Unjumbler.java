/* Gabrielle Goodwin
 * cs 111 ps 7
 * 4/5/10
 */ 
 


// Unjumbler is the class in which you should write your class methods 
// for the Unjumbler problem. 

// Since this class extends LabOps, it inherits class methods from all 
// of StringList, StringListOps, and LabOps.  Such methods can be used
// without the explicit "StringList.", "StringListOps.", or "LabOps."
// prefixes. 
// For example, you can write "head(L)" rather than
// "StringList.head(L)", "length(L)" rather than
// "StringListOps.length(L)", and  "isMember(s,L)" rather than
// "LabOps.isMember(s,L)". 

// The following methods are inherited from StringList:
//    public static StringList empty();
//    public static boolean isEmpty (StringList L);
//    public static StringList prepend (String s, StringList L);
//    public static String head (StringList L);
//    public static StringList tail (StringList L);
//    public static boolean equals (StringList L1, StringList L2);
//    public static String toString (StringList L);
//    public static String fromString (String s);

// The following methods are inherited from StringListOps:
//    public static int length (StringList L);
//    public static StringList append (StringList L1, StringList L2); 
//    public static StringList postpend (StringList L, String s);
//    public static StringList reverse (StringList L);

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





public class Unjumbler extends LabOps {

  public static void main (String [] args) {
    // Uncomment the following line to test how a correctly
    // working version of your program operate: 
     UnjumblerAnswers.main(args);
    
    /****Transcript, java interactions included*****
   1.  StringList c = remove("I", fromString("[I,know,that,I,said,that,I,did]"));
       System.out.println(c);
           [know,that,said,that,did]
           
   3.  StringList c = mapConcat("com", fromString("[puter,plain,municate,pile]"));
                             System.out.println(c);
           [computer,complain,communicate,compile]
   
   5.  StringList c = insertionsList("*", fromString("[I,am,Sam]"));
                             System.out.println(c);
           [*I,I*,*am,a*m,am*,*Sam,S*am,Sa*m,Sam*]  
           
   6.  StringList c = permutations("bcd");
                             System.out.println(c);
           [bcd,cbd,cdb,bdc,dbc,dcb]                  
   
   7.  StringList c = filterWords(fromString("[tra,rta,rat,tar,atr,art]"));
                             System.out.println(c);
                  Constructing dictionary from file dicts/dict8.bin.
                  This may take a little while...
                  Done! Dictionary constructed with 22641 words.
                  [rat,tar,art]           
   
   8.   StringList c = unjumble("argle");
                             System.out.println(c);
                   Constructing dictionary from file dicts/dict8.bin.
                   This may take a little while...
                   Done! Dictionary constructed with 22641 words.
                   [glare,large,lager,regal]
                   
   9.      > java Unjumbler sbso
           Constructing dictionary from file dicts/dict8.bin.
           This may take a little while...
           Done! Dictionary constructed with 22641 words.
           [sobs,boss]
           Constructing dictionary from file dicts/dict8.bin.
           This may take a little while...
           Done! Dictionary constructed with 22641 words.
           > java Unjumbler sbso
           [sobs,boss]
           > java Unjumbler argle
           [glare,large,lager,regal]
           > java Unjumbler olleh
           [hello]
           > java Unjumbler udhw
           []
           >               
           */
     unjumble(args[0]); //allows the code to be typed into the interactions pane
                        
  } 
  

      
    
  
        /************************************************************/
        /*   The following two contract methods are given to you.   */
        /************************************************************/
        
        /* Returns a list containing each string in L exactly
         * once. The order of the elements in the returned list should
         * be the relative order of the first occurrence of each
         * element in L.
         *
         * There will be a detailed description of this method in the
         * forthcoming solutions.
         */
        public static StringList removeDuplicates (StringList L)
        {
                if (isEmpty(L))
                        return L;
                else 
                        return prepend(head(L),
                                       remove(head(L),
                                              removeDuplicates(tail(L))));
        }
        
        /* Given two strings s1 and s2, where s2 has n characters,
         * returns a list of n + 1 strings that result from inserting
         * s1 at all possible positions within s2, from left to right.
         *
         * There will be a detailed description of this method in the
         * forthcoming solutions.
         */
        public static StringList insertions (String s1, String s2) 
        {
                if (s2.equals(""))
                        return prepend(s1, empty());
                else
                        return prepend(s1 + s2,
                                       mapConcat(first(s2),
                                                 insertions(s1,
                                                            butFirst(s2))));
        }
        
        /************************************************************/
        /*   You must replace the call in the body of each of the   */
        /*   following 7 stubs with your own working code.  You can */
        /*   use the stubs for debugging other methods, of course,  */
        /*   but in the end you must provide your own definitions.  */
        /************************************************************/
        
        /* Returns a new list in which all occurrences of s in L have
         * been removed. The other strings in the list should have the
         * same relative order in the resulting list as in the given
         * list.
         */
        public static StringList remove(String s, StringList L)
        { if(isEmpty(L)){
          return L;
        }else if(s.equals(head(L))){
          StringList l = tail(L);
          return remove(s,l); //recurses on the rest of the stringlist
        }else{
          StringList l = tail(L);
          return prepend(head(L), remove(s,l)); //does not remove word 
                                                //if it is not meant to be
        }
        }
        
          
          
          /* Given a list L with n strings, returns a new list with n
         * strings in which the ith string of the resulting list is
         * the result of concatenating s to the ith element of L.
         */
        public static StringList mapConcat (String s, StringList L)
        {
          if(isEmpty(L)){
            return L;
          }else{
            String k= s + head(L);
            StringList l = tail(L);
           return prepend(k,mapConcat(s,l)); //recursive case, puts list together
            
        }
        }
        
        /* Returns a list that contains all the strings that result
         * from inserting s at all possible positions in all the
         * strings of L.
         */
        public static StringList insertionsList (String s, StringList L)
        {
          if(isEmpty(L)){
            return L;
          }else{
            StringList l = tail(L);
            StringList m = insertions(s,head(L));
            return append(m,insertionsList(s,l));//recursive case, makes new list with insertions
        }
        }
        
        /* Returns a list of all permutations of the string s. A
         * permutation of a string s is any string that is formed by
         * reordering the letters in the string s (without duplicating
         * or deleting any letters). For a string with n distinct
         * characters, there are exactly n! (i.e., "n factorial")
         * permutations. If some characters in s are repeated, there
         * are still n! permutations, but the permutations contain
         * duplicates. The elements in the list returned by
         * permutations may be in any order.
         */
        public static StringList permutations (String s){ 
          if(butFirst(s).length()==0){ 
           return prepend(first(s),empty());//returns last letter of the word
          }else{
            return  insertionsList(first(s),permutations(butFirst(s)));
                  // recursive case, makes all permutation
                                     
         }
        }
   
        
        /* Returns a list of all strings in L that are English
         * words. The resulting strings should be in the same relative
         * order as in L.
         */
        public static StringList filterWords (StringList L)
        {
          if(isEmpty(L)){
            return L;
          }else if(!isWord(head(L))){
            String s = head(L);
            return remove(s,filterWords(tail(L)));//takes out word if not in dictionary
          }else{
            StringList l = tail(L);
            String s = head(L);
          return prepend(head(L), remove(s,filterWords(tail(L))));
                  //leaves word in list if in dictionary
          }
        }
            
                  
        
        
        /* Returns a list of all the permutations of s that are
         * English words (as determined by the default
         * dictionary). The order of elements in the resulting list
         * does not matter, but each word in the resulting list should
         * be listed only once.
         */
        public static StringList unjumble (String s)
        { return removeDuplicates(filterWords(permutations(s)));
          //puts all the neccesary recursive cases together to make unjumbler
          
        } 

 
  
 
  // ------------------------------------------------------------
  // DICTIONARIES
  
  // You are provided with a method
  //
  //    private static boolean isWord (String s);
  //
  // that determines whether the string s is an English word. 
  // You do not need to understand any of the details how this is
  // done, except to know that it is performed by testing if s
  // appears in a certain word list file.  So the test is only as
  // good as the word list file it uses. 
  
  // The name of the file in which the dictionary resides is a
  // class variable.  By default, it is "dicts/dict8.bin", but you
  // can change it to be one of the other choices below.  All of
  // the following dictionaries are derived from  the freely
  // available Linux word list. Files with more words take longer
  // to load. Note that the Linux word list is missing some English
  // words. 
  
  private static String dir = "dicts" + java.io.File.separatorChar;  // directory
  // private static String dictFile = "dict-small.bin";  // "foo", "bar", "baz" 
  // private static String dictFile = "dict5.bin"; // Words up to 5 letters (5525 words)
  // private static String dictFile = "dict6.bin"; // Words up to 6 letters (10488 words)
  // private static String dictFile = "dict7.bin"; // Words up to 7 letters (16618 words)
  private static String dictFile = "dict8.bin"; // Words up to 8 letters (22641 words)
  // private static String dictFile = "dict.bin"; // Whole linux word list (45425 words)
  
  // For efficiency's sake, the dictionary itself is stored in a
  // class variable.  This variable initially contains no
  // dictionary.  It is intialized the first time that isWord() is
  // called. 
  private static Dict dict = null;
  
  // You should not need to add any other class variables!  The
  // ones above suffice.   
  private static boolean isWord (String s) {
    if (dict == null) {         // If no dictionary yet
      dict = Dict.fromFile(dir + dictFile);
    }
    return dict.isWord(s);
  }
}
