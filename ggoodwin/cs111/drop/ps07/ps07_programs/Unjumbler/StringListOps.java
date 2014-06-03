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

public class StringListOps extends StringList {
	
  // Operations built on top of the StringList class
  
  public static int length (StringList L) {
    // Returns the number of elements in list L.
    if (isEmpty(L)) {
      return 0;
    } else {
      return 1 + length(tail(L));
    }
  }
  
  public static String min (String s1, String s2) {
    if (s1.compareTo(s2) < 0) {
      return s1;
    } else {
      return s2;
    }
  }
  
  public static String max (String s1, String s2) {
    if (s1.compareTo(s2) > 0) {
      return s1;
    } else {
      return s2;
    }
  }
  
  public static String least (StringList L) {
    // Returns the least string (by alphabetic ordering) number in L. 
    // (signals an exception if L is empty)
    if (isEmpty(L)) {
      throw new RuntimeException("StringList.min: emptyList");
    } else if (isEmpty(tail(L))) {
      return head(L);
    } else {
      return min(head(L), least(tail(L)));
    }
  }
  
  public static String greatest (StringList L) {
    // Returns the greatest string (by alphabetic ordering) in L,
    // or the empty string if L is empty. 
    if (isEmpty(L)) {
      return "";
    } else {
      return max(head(L), greatest(tail(L)));
    }
  }
  
  // List combination operations
  
  public static StringList append(StringList L1, StringList L2) {
    // Returns a list whose elements are those of L1 followed by those of L2.
    if (isEmpty(L1)) {
      return L2;
    } else {
      return prepend (head(L1), append(tail(L1), L2));
    }
  }
  
  public static StringList postpend(StringList L, String s) {
    // Returns a list whose elements are those of L followed by s.
    return append(L, prepend(s, empty()));
  }
  
  public static StringList reverse(StringList L) {
    if (isEmpty(L)) {
      return L;
    } else {
      return postpend(reverse(tail(L)), head(L));
    }
  }
  
}

