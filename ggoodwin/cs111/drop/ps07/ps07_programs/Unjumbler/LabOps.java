//
// Since this class extends StringListOps, it inherits class methods 
// from both StringList and StringListOps. Such methods can be used
// without the explicit "StringList." or "StringListOps." prefixes.
// For example, you can write "head(L)" rather than "StringList.head(L)",
// and "length(L)" rather than "StringListOps.length(L)".
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

public class LabOps extends StringListOps {
    
  public static void main (String [] args) {
    System.out.println("first(\"cs111\") = \"" + first("cs111") + "\"");
    System.out.println("butFirst(\"cs111\") = \"" + butFirst("cs111") + "\"");
    System.out.println("fromString(\"[I,do,not,like,green,eggs,and,ham]\") = "
                         + fromString("[I,do,not,like,green,eggs,and,ham]"));
                      
    // Add your testing code here: 
    System.out.println("mapPluralize(fromString(\"[dog,cat,goat]\"))) = "
                         + mapPluralize(fromString("[dog,cat,goat]")));
    System.out.println("mapUpperCase(fromString(\"[I,do,not,like,green,eggs,and,ham]\")) = "
                         + mapUpperCase(fromString("[I,do,not,like,green,eggs,and,ham]")));
    System.out.println("isMember(\"egg\", fromString(\"[I,do,not,like,green,eggs,and,ham]\")) = "
                         + isMember("egg", fromString("[I,do,not,like,green,eggs,and,ham]")));
    System.out.println("isMember(\"eggs\", fromString(\"[I,do,not,like,green,eggs,and,ham]\")) = "
                         + isMember("eggs", fromString("[I,do,not,like,green,eggs,and,ham]")));
    System.out.println("filterMatches(\"com\", fromString(\"[computer,program,incomparable,intercom,Java]\")) = "
                         + filterMatches("com", fromString("[computer,program,incomparable,intercom,Java]")));
    System.out.println("implode(fromString(\"[dog,cat,goat]\"))) = "
                         + implode(fromString("[dog,cat,goat]")));
    System.out.println("explode(\"computer\")) = "
                         + explode("computer"));
    System.out.println("insert(\"dog\", fromString(\"[ant,bat,cat,goat,lion]\"))) = "
                         + insert("dog", fromString("[ant,bat,cat,goat,lion]")));
    System.out.println("insert(\"aardvark\", fromString(\"[ant,bat,cat,goat,lion]\"))) = "
                         + insert("aardvark", fromString("[ant,bat,cat,goat,lion]")));
    System.out.println("insert(\"tiger\", fromString(\"[ant,bat,cat,goat,lion]\"))) = "
                         + insert("tiger", fromString("[ant,bat,cat,goat,lion]")));
    System.out.println("insert(\"cat\", fromString(\"[ant,bat,cat,goat,lion]\"))) = "
                         + insert("cat", fromString("[ant,bat,cat,goat,lion]")));
    System.out.println("insertionSort(fromString(\"[I,do,not,like,green,eggs,and,ham]\")) = "
                         + insertionSort(fromString("[I,do,not,like,green,eggs,and,ham]")));
    
  } // main()
 
  //---------------------------------------------------------------
  // Write your methods here:
  
  public static StringList mapPluralize (StringList L) {
    if (isEmpty(L)) {
      return L;
    } else {
      return prepend(head(L) + "s", mapPluralize(tail(L)));
    }
  } 

  public static StringList mapUpperCase (StringList L) {
    if (isEmpty(L)) {
      return L;
    } else {
      return prepend(head(L).toUpperCase(), mapUpperCase(tail(L)));
    }
  } 

  public static boolean isMember (String s, StringList L)  {
    if (isEmpty(L)) {
      return false;
    } else if (s.equals(head(L))) {
      return true;
    } else {
      return isMember(s, tail(L));
    }
  } 

  public static StringList filterMatches (String s, StringList L) {
    if (isEmpty(L)) {
      return L;
    } else if (head(L).indexOf(s) != -1) {
      return prepend(head(L), filterMatches(s, tail(L)));
    } else {
      return filterMatches(s, tail(L));
    }
  }

  public static String implode (StringList L) {
    if (isEmpty(L)) {
      return "";
    } else {
      return head(L) + (implode(tail(L)));
    }
  }

  public static StringList explode (String s) {
    return s.equals("")? empty() : prepend(first(s), explode(butFirst(s)));
/*
          if (s.equals("")) {
               return empty();
          } else {
               return prepend(first(s), explode(butFirst(s)));
          }
*/
  }
  
  // insert s into the correct position in an alphbetically sorted list
  public static StringList insert (String s, StringList L) {
    if (isEmpty(L) || (s.compareTo(head(L)) <= 0)){
      return prepend(s,L);
    } else {
      return prepend(head(L), insert(s,tail(L)));
    }
  }

  public static StringList insertionSort (StringList L) {
    if (isEmpty(L)) {
      return L;
    } else {
      return insert(head(L), insertionSort(tail(L)));
    }
  }
 
  //---------------------------------------------------------------
  // The following auxiliary methods are helpful for explode
 
  // Given a string s, returns a one-character string containing
  // the first character of s. 
  public static String first (String s) {
    return new Character(s.charAt(0)).toString();
  }
 
  // Given a string s, returns the substring of s that contains
  // all characters except the first. 
  public static String butFirst (String s)  {
    return s.substring(1,s.length());
  }
} // class LabOps

