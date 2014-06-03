public class Tree<E> implements TreeInterface<E> {
 	
  // code to implement a binary tree of nodes that contain objects of type E
	
  // instance variables
  private E value;
  private Tree<E> left, right;
  private boolean isLeaf;
	
  // constructors
  public Tree () {
    isLeaf = true;
  }

  public Tree (E value, Tree<E> lt, Tree<E> rt) {
    this.value = value;
    this.left = lt;
    this.right = rt;
    isLeaf = false;
  }
	
  // instance methods 

  public boolean isLeaf () {
    // returns true if the tree is a leaf (i.e. empty tree)
    return isLeaf;
  }

  public E getValue () {
    // returns the object stored in the root node of the tree
    if (isLeaf)
      throw new RuntimeException("Attempt to get value of an empty tree");
    else
      return value;
  }
	
  public Tree<E> getLeft () {
    // returns the left subtree of the tree
    if (isLeaf)
      throw new RuntimeException("Attempt to get left subtree of an empty tree");
    else
      return left;
  }
	
  public Tree<E> getRight () {
    // returns the right subtree of the tree
    if (isLeaf)
      throw new RuntimeException("Attempt to get right subtree of an empty tree");
    else
      return right;
  }
	
  public void setValue (E newValue) {
    // sets the object in the root node of the tree to a new value
    if (isLeaf)
      throw new RuntimeException("Attempt to set value of an empty tree");
    else
      value = newValue;
  }
	
  public void setLeft (Tree<E> newLeft) {
    // changes the left subtree to a new tree
    if (isLeaf)
      throw new RuntimeException("Attempt to set left subtree of an empty tree");
    else
      left= newLeft;
  }
	
  public void setRight (Tree<E> newRight) {
    // changes the right subtree to a new tree
    if (isLeaf)
      throw new RuntimeException("Attempt to set right subtree of an empty tree");
    else
      right = newRight;
  }
	
  public String toString () {
    // returns a String representation of the contents of a Tree
    if (this.isLeaf())
      return ".";
    else 
      return "(" + this.getLeft().toString() + " " + this.getValue() + " " + 
	this.getRight().toString() + ")";
  }


  // class methods for syntactic sugar

  public static <E> Tree<E> leaf () {
    // returns an empty tree (i.e. leaf)
    return new Tree<E>();
  }

  public static <E> boolean isLeaf (Tree<E> t) {
    // returns true if the tree is a leaf (i.e. empty tree)
    return t.isLeaf;
  }
        
  public static <E> Tree<E> node (E val, Tree<E> lt, Tree<E> rt) {
    // creates a new tree with the object "val" in the root node and
    // with the left subtree "lt" and right subtree "rt"
    return new Tree<E>(val, lt, rt);
  }
    
  public static <E> E getValue (Tree<E> t) {
    // returns the object stored in the root node of the tree
    return t.getValue();
  }
        
  public static <E> Tree<E> getLeft (Tree<E> t) {
    // returns the left subtree of the tree t
    return t.getLeft();
  }
        
  public static <E> Tree<E> getRight (Tree<E> t) {
    // returns the right subtree of the tree t
    return t.getRight();
  }

  public static <E> void setValue (Tree<E> t, E newValue) {
    // sets the object in the root node of the tree to a new value
    t.setValue(newValue);
  }
        
  public static <E> void setLeft (Tree<E> t, Tree<E> newLeft) {
    // changes the left subtree to a new tree
    t.setLeft(newLeft);
  }
        
  public static <E> void setRight (Tree<E> t, Tree<E> newRight) {
    // changes the right subtree to a new tree
    t.setRight(newRight);
  }

  public static void classStyle() {
    Tree<String> T = Tree.<String>leaf();
         System.out.println("T is now: " + T);
    T = node("A", Tree.<String>leaf(), Tree.<String>leaf());
         System.out.println("T is now: " + T);
    Tree<String> tl = node("B", Tree.<String>leaf(), Tree.<String>leaf());
    Tree<String> tr = node("C", Tree.<String>leaf(), Tree.<String>leaf());
    setLeft(T, tl);
    setRight(T, tr);
         System.out.println("T is now: " + T);
    setValue(getLeft(T), "?"); 
         System.out.println("T is now: " + T);
    setRight(T, getRight(getRight(T)) );
         System.out.println("T is now: " + T);
  }

  public static void instanceStyle() {
    Tree<String> T = new Tree<String>();
         System.out.println("T is now: " + T);
    T = new Tree<String>("A", new Tree<String>(), new Tree<String>());
         System.out.println("T is now: " + T);
    Tree<String> tl = new Tree<String>("B", new Tree<String>(), new Tree<String>());
    Tree<String> tr = new Tree<String>("C", new Tree<String>(), new Tree<String>());
    T.setLeft(tl);
    T.setRight(tr);
         System.out.println("T is now: " + T);
    T.getLeft().setValue("?");
         System.out.println("T is now: " + T);
    T.setRight(T.getRight().getRight());
         System.out.println("T is now: " + T);
  }
  
	
  // Tree methods are tested
  public static void main(String[] args) {
    System.out.println("Using instance methods...");
    instanceStyle();
    System.out.println();
    System.out.println("Using class methods...");
    classStyle();
  }

}
