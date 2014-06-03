// Immutable linked lists of strings

public class StringList {
 
  // Instance variables
 
  private String head;
  private StringList tail;

  private static StringList theEmptyList = new StringList();
 
  // Constructor method:
 
  protected StringList () {
    this.tail = null;
  }
  
  private StringList (String head, StringList tail) {
    this.head = head;
    this.tail = tail;
    // [lyn, 10/15/07] Don't allow a null tail -- it breaks abstraction
    if (tail == null) {
      throw new RuntimeException("IntList not allowed to have a null tail!");
    }
  }
 
  // Instance methods: 
 
  private boolean isEmpty() {
    return (this == theEmptyList) || (tail == null); 
    // Must test for tail == null in case user has called nullary constructor directly. 
  }
 
  private StringList prepend(String s) {
    return new StringList (s, this);
  }
 
  private String head () {
    if (isEmpty()) {
      throw new RuntimeException("Attempt to get the head of an empty String list");
    } else {
      return head;
    }
  }
 
  private StringList tail() {
    if (isEmpty()) {
      throw new RuntimeException("Attempt to get the tail of an empty String list");
    } else {
      return tail;
    }
  }
 
  public String toString () {
    if (isEmpty()) {
      return "[]";
    } else {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      sb.append(head);
      StringList toDo = tail;
      while (! toDo.isEmpty()) {
 sb.append(",");
 sb.append(toDo.head);
 toDo = toDo.tail;
      }
      sb.append("]");
      return sb.toString();
    }
  }
 
  // Class Methods: 
 
  public static StringList empty() {
    return new StringList();
  }
 
  public static boolean isEmpty(StringList L) {
    return L.isEmpty();
  }
 
  public static StringList prepend(String n, StringList L) {
    return new StringList(n, L);
  }
 
  public static String head(StringList L) {
    return L.head();
  }
 
  public static StringList tail(StringList L) {
    return L.tail();
  }
 
  public static StringList arrayToStringList(String [ ] a) {
    StringList result = empty();
    for (int i = a.length - 1; i >=0; i--) {
      result = prepend(a[i], result);
    }
    return result;
  }
 
  public static String toString (StringList L) {
    return L.toString();
  }
 
  public static boolean equals (StringList L1, StringList L2) {
    if (isEmpty(L1) && isEmpty(L2)) {
      return true;
    } else if (isEmpty(L1) || isEmpty(L2)) {
      return false;
    } else {
      return ((head(L1).equals(head(L2))) && (equals(tail(L1),tail(L2))));
    }
  }
 
  public static StringList fromString (String s) {
    if (s.charAt(0) != '[') {
      throw new RuntimeException("StringList.fromString: string does not begin with '[': "
     + "\"" + s + "\"");
    } else if ((s.charAt(s.length() - 1)) != ']') {
      throw new RuntimeException("StringList.fromString: string does not end with ']': "
     + "\"" + s + "\"");
    } else {
      return fromStringHelp (s, 1);
    }
  }
 
  private static StringList fromStringHelp (String s, int lo) {
    int commaIndex = s.indexOf(',', lo);
    if (commaIndex == -1) { // last element or empty list
      int hi = s.length()-1; // index of ']';
      if (lo >= hi) {
 return empty();
      } else {
 return prepend(s.substring(lo,hi), empty());
      }
    } else {
      return prepend(s.substring(lo,commaIndex), fromStringHelp(s, commaIndex+1));
    }
  }
 
  private static void testFromString (String s) {
    System.out.println("fromString(\"" + s + "\") = " + fromString(s)); 
  }
 
  public static void main (String [] args) {
    testFromString("[a,b,c]");
    testFromString("[a,bc,def,ghij,klmno]");
    testFromString("[ a ,  bc  ,  def  ,    ghij    ,     klmno     ]");
    testFromString("[]");
    testFromString("[ ]");
    testFromString("[  ]");
  }
 
}



