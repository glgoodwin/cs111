import java.util.*;

public class QueueTest {
		
  // code to test the methods in the Queue class
    
  public static boolean isPalindrome (String s) { 
    // determines whether the input string s is a palindrome
    Stack<Character> stk = new Stack<Character>();
    Queue_2<Character> que = new Queue_2<Character>();
    char schar, qchar;
    Character C;
    // step through the string and push all the characters onto the stack
    // while also enQueuing the characters onto the queue
    for (int i = 0; i < s.length(); i++) {
      C = new Character(s.charAt(i));
      stk.push(C);
      que.enQ(C);
    }
    while (!que.empty()) {
      // compare characters at top of stack and front of queue
      schar = stk.pop().charValue();
      qchar = que.deQ().charValue();
      if (schar != qchar) 
	return false;
    }
    return true;
  }

  public static void playJosephus (int n, int m) {
    // simulates the solution of the Josephus problem for N people 
    // and M passes, using a Queue
    Queue_2<Integer> Q = new Queue_2<Integer>();
    int i, remove;
    for (i = 1; i <= n; i++)    // create a queue of n integers
      Q.enQ(new Integer(i));
    while (Q.size() > 1) {    // continue until one integer is left
      for (i = 0; i < m; i++)    // pass over m integers
	Q.enQ(Q.deQ());
      // remove next integer
      System.out.println("Player " + Q.deQ() + " removed");
    }   
    // last integer that remains is the "winner"
    System.out.println("Player " + Q.front() + " wins");
  }
  
  public static void main (String[] args) {
    String S = "ablewasiereisawelba";
    System.out.println("is String " + S + " a palindrome?");
    System.out.println(isPalindrome(S));
    S = "spiderman";
    System.out.println("is String " + S + " a palindrome?");
    System.out.println(isPalindrome(S));

    System.out.println("playJosephus(6, 3)");
    playJosephus(6, 3);
    System.out.println("playJosephus(20, 13)");
    playJosephus(20, 13);

    System.out.println("playJosephus(180, 2)");
    playJosephus(180, 2);

    // Queue exercise
    Queue_1<String> Q1 = new Queue_1<String>();
    Q1.enQ("**"); System.out.println(Q1);
    Q1.enQ("??"); System.out.println(Q1);
    Q1.enQ("zz"); System.out.println(Q1);
    S = Q1.deQ(); System.out.println(Q1);
    Q1.enQ(Q1.deQ()); System.out.println(Q1);
    Q1.enQ(Q1.front()); System.out.println(Q1);
    Q1.enQ("$$"); System.out.println(Q1);
    S = Q1.deQ(); System.out.println(Q1);
    // print size and contents of queue
    System.out.println("contents of final queue of size " + Q1.size());
    System.out.println(Q1);
    Q1.clear();
    System.out.println("Queue is clear, size = " + Q1.size());
    // S = Q1.deQ();
    // S = Q1.front();

    // Queue exercise
    Queue_2<String> Q2 = new Queue_2<String>();
    Q2.enQ("**"); System.out.println(Q2);
    Q2.enQ("??"); System.out.println(Q2);
    Q2.enQ("zz"); System.out.println(Q2);
    S = Q2.deQ(); System.out.println(Q2);
    Q2.enQ(Q2.deQ()); System.out.println(Q2);
    Q2.enQ(Q2.front()); System.out.println(Q2);
    Q2.enQ("$$"); System.out.println(Q2);
    S = Q2.deQ(); System.out.println(Q2);
    // print size and contents of queue
    System.out.println("contents of final queue of size " + Q2.size());
    System.out.println(Q2);
    Q2.clear();
    System.out.println("Queue is clear, size = " + Q2.size());
    // S = Q2.deQ();
    // S = Q2.front();
  }

}
	
