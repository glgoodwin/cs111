public class StackVectorBasedTest {  // implementation of a program that uses a Stack to determine whether a   // string has balanced open and closed brackets	  public static boolean open_bracket (char c) {    // returns true if c is an open bracket    return (c == '(') || (c == '{') || (c == '[') || (c == '<');   }	  public static boolean close_bracket (char c) {    // returns true if c is a close bracket    return (c == ')') || (c == '}') || (c == ']') || (c == '>');   }	  public static char matching_bracket (char c) {    // returns the closing bracket that matches the input open bracket     if (c == '(') return ')';    else if (c == '{') return '}';    else if (c == '[') return ']';     else return '>';  }	  public static boolean isBalanced (String S) {    // returns true if the string S has balanced open and closed brackets    StackVectorBased<Character> stk = new StackVectorBased<Character>();    int i = 0;    boolean valid = true;    char nextC, top;    while (valid && (i < S.length())) {      // get the next character in the string      nextC = S.charAt(i);      if (open_bracket(nextC))      // push open brackets onto the stack	stk.push(new Character(nextC));       else if (close_bracket(nextC)) {	// check whether the matching open bracket is on top of stack	if (stk.isEmpty()) 	  valid = false;	else {	  top = stk.pop().charValue();	  if (nextC != matching_bracket(top)) 	    valid = false;	}      }      i++;    }    return (valid && stk.isEmpty());  }  // prints the contents of a Stack from top to bottom, assuming that   // the Objects on the Stack have the toString() method    public static <E> void printStack (StackVectorBased<E> stk) {    // create a temporary Stack to hold contents of stk    StackVectorBased<E> tempStack = new StackVectorBased<E>();     System.out.println("Contents of Stack from top to bottom: ");    while (!stk.isEmpty()) {      E element = stk.pop();      System.out.print(element.toString() + " ");      tempStack.push(element);    }    System.out.println();    // restore contents of stk    while(!tempStack.isEmpty())       stk.push(tempStack.pop());  }  public static void main (String[] args) {    System.out.println("[{(<>)}]: " + isBalanced("[{(<>)}]"));     System.out.println("[{): " + isBalanced("[{)"));     // testing printStack    StackVectorBased<String> stk = new StackVectorBased<String>();    stk.push("one");    stk.push("two");    stk.push("three");    printStack(stk);  }}					