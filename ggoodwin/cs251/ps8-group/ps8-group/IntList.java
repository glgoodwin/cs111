// Immutable linked lists of integers. 

public class IntList {
  
  // Instance variables
  
  private int head;
  private IntList tail;
  private boolean empty;
  
  // Constructor method:
  
  private IntList () {
    this.empty = true;
  }
  
  private IntList (int head, IntList tail) {
    this.head = head;
    this.tail = tail;
    this.empty = false;
  }
  
  // Instance methods: 
  
  private boolean isEmpty() {
    return empty;
  }
  
  private IntList prepend(int n) {
    return new IntList (n, this);
  }
  
  private int head () {
    if (empty) {
      throw new RuntimeException("Attempt to get the head of an empty IntList");
    } else {
      return head;
    }
  }
  
  private IntList tail() {
    if (empty) {
      throw new RuntimeException("Attempt to get the tail of an empty IntList");
    } else {
      return tail;
    }
  }
  
  public String toString () {
    if (empty) {
      return "[]";
    } else {
      StringBuffer sb = new StringBuffer();
      sb.append("[");
      sb.append(head);
      IntList toDo = tail;
      while (! toDo.empty) {
	sb.append(",");
	sb.append(toDo.head);
	toDo = toDo.tail;
      }
      sb.append("]");
      return sb.toString();
    }
  }
  
  // Class Methods: 
  
  public static IntList empty() {
    return new IntList();
  }
  
  public static boolean isEmpty(IntList L) {
    return L.empty;
  }
  
  public static IntList prepend(int n, IntList L) {
    return new IntList(n, L);
  }
  
  public static int head(IntList L) {
    if (L.empty) {
      throw new RuntimeException("Attempt to get the head of an empty IntList");
    } else {
      return L.head;
    }
  }
  
  public static IntList tail(IntList L) {
    if (L.empty) {
      throw new RuntimeException("Attempt to get the tail of an empty IntList");
    } else {
      return L.tail;
    }
  }
  
  public static IntList arrayToIntList(int [] a) {
    IntList result = empty();
    for (int i = a.length - 1; i >=0; i--) {
      result = prepend(a[i], result);
    }
    return result;
  }
  
  public static String toString (IntList L) {
    return L.toString();
  }
  
  public static boolean equals (IntList L1, IntList L2) {
    if (isEmpty(L1) && isEmpty(L2)) {
      return true;
    } else if (isEmpty(L1) || isEmpty(L2)) {
      return false;
    } else {
      return ((head(L1) == head(L2)) && (equals(tail(L1),tail(L2))));
    }
  }
  
  public static IntList fromString (String s) {
    if (s.charAt(0) != '[') {
      throw new RuntimeException("IntList.fromString: string does not begin with '[': "
				 + "\"" + s + "\"");
    } else if ((s.charAt(s.length() - 1)) != ']') {
      throw new RuntimeException("IntList.fromString: string does not end with ']': "
				 + "\"" + s + "\"");
    } else if (isAllWhitespace(s.substring(1,s.length()))) {
      return IntList.empty();
    } else { 			
      return fromStringHelp (s, 1);
    }
  }
  
  private static IntList fromStringHelp (String s, int lo) {
    int commaIndex = s.indexOf(',', lo);
    if (commaIndex == -1) { // last element
      return prepend(toInt(s, lo, s.length() - 2), empty());
    } else {
      return prepend(toInt(s, lo, commaIndex - 1), fromStringHelp(s, commaIndex+1));
    }
  }
  
  // Extract an int from the substring in s between lo and hi (inclusive).
  // Strip any leading/trailing whitespace. 
  private static int toInt (String s, int lo, int hi) {
    while (isWhitespace(s.charAt(lo))) {
      lo++;
    }
    while (isWhitespace(s.charAt(hi))) {
      hi--;
    }
    return Integer.parseInt(s.substring(lo,hi+1));
  }
  
  private static boolean isAllWhitespace (String s) {
    for (int k = 0; k < s.length() -1; k++) {
      if (!isWhitespace(s.charAt(k))) {
	return false;
      }
    }
    return true;
  }
  
  private static boolean isWhitespace (char c) {
    return (c == ' ') || (c == '\t') || (c == '\n') || (c == '\r');
  }
  
}



