public class StringListOps {
 
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
 
 
 // ----------------------------------------------------------
 // Local abbreviations
 
 public static StringList empty() {
  return StringList.empty();
 }
 
 public static boolean isEmpty(StringList L) {
  return StringList.isEmpty(L);
 }
 
 public static StringList prepend(String n, StringList L) {
  return StringList.prepend(n, L);
 }
 
 public static String head(StringList L) {
  return StringList.head(L);
 }
 
 public static StringList tail(StringList L) {
  return StringList.tail(L);
 }
 
 public static StringList fromString (String L) {
  return StringList.fromString(L);
 }
 
 public static boolean equals (StringList L1, StringList L2) {
  return StringList.equals(L1, L2);
 }
 
}

