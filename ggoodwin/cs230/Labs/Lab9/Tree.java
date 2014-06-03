/** CS230
 *  Tree lab
 *   Tree.java
 */
 public class Tree {
 	
 // code to implement a binary tree of nodes that contain Objects
	
  // instance variables
	
  private Object value;
  private Tree left, right;
  private boolean isLeaf;
	
  // constructors
	
  public Tree () {
    isLeaf = true;
  }
		
  public Tree (Object value, Tree left, Tree right) {
    this.value = value;
    this.left = left;
    this.right = right;
    isLeaf = false;
  }
	
  // instance methods 
	
  public boolean isLeaf () {
    // returns true if the tree is a leaf (i.e. empty tree)
    return isLeaf;
  }
	
  public Object value () {
    // returns the Object stored in the root node of the tree
    if (isLeaf)
      throw new RuntimeException("Attempt to get value of an empty tree");
    else
      return value;
  }
	
  public Tree left () {
    // returns the left subtree of the tree
    if (isLeaf)
      throw new RuntimeException("Attempt to get left of an empty tree");
    else
      return left;
  }
	
  public Tree right () {
    // returns the right subtree of the tree
    if (isLeaf)
      throw new RuntimeException("Attempt to get right of an empty tree");
    else
      return right;
  }
	
  public void setValue (Object newValue) {
    // sets the Object in the root node of the tree to a new value
    if (isLeaf)
      throw new RuntimeException("Attempt to set value of an empty tree");
    else
      value = newValue;
  }
	
  public void setLeft (Tree newLeft) {
    // changes the left subtree to a new tree
    if (isLeaf)
      throw new RuntimeException("Attempt to set left of an empty tree");
    else
      left = newLeft;
  }
	
  public void setRight (Tree newRight) {
    // changes the right subtree to a new tree
    if (isLeaf)
      throw new RuntimeException("Attempt to set right of an empty tree");
    else
      right = newRight;
  }
	
  public String toString () {
    // returns a String representation of the contents of a Tree
    if (this.isLeaf())
      return "X";
    else 
      return "(" + this.left().toString() + " " + this.value() + " " + 
	this.right().toString() + ")";
  }

  // class methods
  
  public static Tree leaf () {
    // returns an empty tree (i.e. leaf)
    return new Tree();
  }
	
  public static boolean isLeaf (Tree t) {
    // returns true if the tree is a leaf (i.e. empty tree)
    return t.isLeaf;
  }
	
  public static Tree node (Object val, Tree lt, Tree rt) {
    // creates a new tree with the Object "val" in the root node and
    // with the left subtree "lt" and right subtree "rt"
    return new Tree(val, lt, rt);
  }
    
  public static Object value (Tree t) {
    // returns the Object stored in the root node of the tree
    return t.value();
  }
	
  public static Tree left (Tree t) {
    // returns the left subtree of the tree t
    return t.left();
  }
	
  public static Tree right (Tree t) {
    // returns the right subtree of the tree t
    return t.right();
  }

  public static void setValue (Tree t, Object newValue) {
    // sets the Object in the root node of the tree to a new value
    t.setValue(newValue);
  }
	
  public static void setLeft (Tree t, Tree newLeft) {
    // changes the left subtree to a new tree
    t.setLeft(newLeft);
  }
	
  public static void setRight (Tree t, Tree newRight) {
    // changes the right subtree to a new tree
    t.setRight(newRight);
  }
	
}
