// Immutable linked lists in Java, parameterized by the type <T> of their element

public class IList<T> { 

    
  // Instance variables
    
  private T head;
  private IList<T> tail;
  private boolean empty;
  
  // Constructor method:
  // Make these private so that only static methods of this class can construct instances. 
  private IList () { 
    this.empty = true;
  }
  
  private IList (T head, IList<T> tail) {
    this.head = head;
    this.tail = tail;
    this.empty = false;
  }
  
  // Instance methods: 
  // Make these private so that only static methods of this class can invoke them.
  
  private boolean isEmpty() {
    return empty;
  }
  
  private IList<T> prepend(T elt) {
    return new IList<T> (elt, this);
  }
  
  private T head () {
    if (empty) {
      throw new RuntimeException("Attempt to get the head of an empty IList");
    } else {
      return head;
    }
  }
  
  private IList<T> tail() {
    if (empty) {
      throw new RuntimeException("Attempt to get the tail of an empty IList");
    } else {
      return tail;
    }
  }

  public String toString () {
    if (empty) {
      return "()";
    } else {
      StringBuffer sb = new StringBuffer();
      sb.append("(");
      sb.append(head);
      IList<T> toDo = tail;
      while (! toDo.empty) {
	sb.append(",");
	sb.append(toDo.head);
	toDo = toDo.tail;
      }
      sb.append(")");
      return sb.toString();
    }
  }

  public boolean equals (Object x) {
    if (x instanceof IList) {
      IList L = (IList) x; 
      if (empty) {
	return L.isEmpty();
      } else if (L.isEmpty()) {
	return false;
      } else {
	return this.head().equals(L.head())
	       && this.tail().equals(L.tail());
      }
    } else {
      return false; 
    }
  }

  // Class Methods:
  
  public static <T> void prettyPrint1 (IList<T> L) {
    // Only expand outermost list to multiple lines.
    // Useful for printing list of lists. 
    ppIndent1(L);
  }
  
  private static <T> void ppIndent1 (IList<T> L) {
    if (isEmpty(L)) {
      System.out.print("()");
    } else {
      System.out.print("(");
      for (IList<T> elts = L; ! isEmpty(elts); L = tail(elts)) {
	System.out.print(head(elts).toString());
	if (isEmpty(tail(elts))) {
	  System.out.println();
	} else {
	  System.out.println(",");
	}
	System.out.print(" ");
      }
      System.out.println(")");
    }
  }
  
  
  public static <T> IList<T> empty() {
    return new IList<T>();
  }
  
  public static <T> boolean isEmpty(IList<T> L) {
    return L.empty;
  }
  
  public static <T> IList<T> prepend(T elt, IList<T> L) {
    return new IList<T>(elt, L);
  }
  
  public static <T> T head(IList<T> L) {
    return L.head();
  }
  
  public static <T> IList<T> tail(IList<T> L) {
    return L.tail();
  }

  public static <T> String toString (IList<T> L) {
    return L.toString();
  }

  public static <T> boolean equals (IList<T> L1, IList<T> L2) {
    return L1.equals(L2);
  }

  /* 
  public static IList<String> fromString (String s) {
    return fromVector(VectorOps.fromStringHelper(s,'(',')')); 
  }
  */

  // Other helpful operations on the IList class
  
  public static <T> int length (IList<T> L) {
    // Returns the number of elements in list L.
    if (isEmpty(L)) {
      return 0;
    } else {
      return 1 + length(tail(L));
    }
  }
  
  public static <T> T nth (int n, IList<T> L) {
    // Returns the element at (1-based) index n in L. 
    if (n == 1) {
      return head(L);
    } else {
      return nth(n-1, tail(L));
    }
  }
  
  public static <T> IList<T> lastNode (IList<T> L) {
    // Returns the last node of L. Raises an exception if L is empty.
    if (isEmpty(L)) {
      throw new RuntimeException("can't take lastNode() of an empty list");
    } else {
      while (! isEmpty(tail(L))) {
	L = tail(L);
      }
      // Invariant: isEmpty(tail(L))
      return L;
    }
  }
  
  public static <T> IList<T> append(IList<T> L1, IList<T> L2) {
    // Returns a list whose elements are those of L1 followed by those of L2.
    if (isEmpty(L1)) {
      return L2;
    } else {
      return prepend (head(L1), append(tail(L1), L2));
    }
  }

  public static <T> IList<T> postpend(IList<T> L, T elt) {
    // Returns a list whose elements are those of L followed by elt.
    return append(L, prepend(elt, IList.<T>empty()));
    // Note: Need IList.<T>empty() to explicitly invoke 
    // static empty method from IList class at type <T>.
    // Without this explicit info, Java cannot reconstruct <T>.
  }

  public static <T> IList<T> reverse (IList<T> L) {
    // Returns a new IList that is the reverse of L
    IList<T> rev = empty();
    while (! isEmpty(L)) {
      rev = prepend(head(L), rev);
      L = tail(L);
    }
    return rev;
  }

  // [lyn, 02/25/07] Having trouble making this type check
  public static <T> IList<IList<T>> tails (IList<T> L) {
    // Returns the successive tails of L
    if (isEmpty(L)) {
      return prepend(L, IList.<IList<T>>empty()); // return a singleton of the empty list. 
      // Note: Need IList.<IList<T>>empty() to explicitly invoke 
      // static empty method from IList class at type IList<T>.
      // Without this explicit info, Java cannot reconstruct IList<T>.
    } else {
      return prepend(L, tails(tail(L)));
    }
  }

  public static <T> IList<T> copy (IList<T> L) {
    // Returns a copy of L with all new nodes
    if (isEmpty(L)) {
      return empty();
    } else {
      return prepend(head(L), copy(tail(L)));
    }
  }

  /*
  public static void main (String [] args) {
    if (args.length == 2 && args[0].equals("fromString")) {
      System.out.println(fromString(args[1]));
    } else if (args.length == 2 && args[0].equals("displayIterator")) {
      IterableOps.display(fromString(args[1]));
    } else {
      System.out.println("unrecognized option");
    }

  } 
  */ 
    
}



