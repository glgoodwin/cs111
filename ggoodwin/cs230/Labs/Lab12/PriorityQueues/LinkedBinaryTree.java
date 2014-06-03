/*******************************************************************
  *  LinkedBinaryTree.java       Java Foundations
  *
  *  Implements a binary tree using a linked representation.
  * MODIFICATION HISTORY:
  * Stella S08:
  * -- made it javadoc
  * -- made it implement Iterable 
  * -- changed the implementation of toString() to use parens.
  * -- added a main() for testing
  */

import java.util.Iterator;
//import javafoundations.*;
//import exceptions.*;

public class LinkedBinaryTree<T> implements BinaryTree<T>, Iterable<T> {
  protected BTNode<T> root;
  
  /**
   *  Creates an empty binary tree.
   */
  public LinkedBinaryTree() {
    root = null;
  }
  
  /**
   *  Creates a binary tree with the specified element as its root.
   */
  public LinkedBinaryTree (T element) {
    root = new BTNode<T>(element);
  }
  
  /**
   *  Creates a binary tree with the two specified subtrees.
   */
  public LinkedBinaryTree (T element, LinkedBinaryTree<T> left,
                           LinkedBinaryTree<T> right) {
    root = new BTNode<T>(element);
    root.setLeft(left.root);
    root.setRight(right.root);
  }
  
  /**
  *  Returns the element stored in the root of the tree. Throws an
  *  EmptyCollectionException if the tree is empty.
  * 
  * @return The element in the root of this binary tree.
  */
  public T getRootElement() {
    if (root == null)
      throw new EmptyCollectionException ("Get root operation "
                                            + "failed. The tree is empty.");
    return root.getElement();
  }
  
  /**
   *  Returns the left subtree of the root of this tree.
   * @return The left sub-tree of this binary tree.
   */
  public LinkedBinaryTree<T> getLeft() {
    if (root == null)
      throw new EmptyCollectionException ("Get left operation "
                                            + "failed. The tree is empty.");
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getLeft();
    
    return result;
  }
  
  /**
   *  Returns the right subtree of the root of this tree.
   * @return The right sub-tree of this binary tree.
   */
  public LinkedBinaryTree<T> getRight() {
    if (root == null)
      throw new EmptyCollectionException ("Get left operation "
                                            + "failed. The tree is empty.");
    LinkedBinaryTree<T> result = new LinkedBinaryTree<T>();
    result.root = root.getRight();
    
    return result;
  }
  
  /**
   * Returns true if the target element was found in the tree, false otherwise.
   * @param the object to look for in the binary tree.
   * @return true if the target element was found in the tree, false otherwise.
   */
  public boolean contains (T target)  {
    try {
      find(target);
    }
    catch(ElementNotFoundException Enfe) {
      return false;
    }
    return true;
  }
  
  /**
   *  Returns the element in this binary tree that matches the
   *  specified target. Throws a ElementNotFoundException if the
   *  target is not found.
   * @param The object to look for in this bianry tree
   * @return the element of this tree that matches the target.
   */
  public T find (T target) {
    BTNode<T> node = null;
    
    if (root != null) node = root.find(target);
    if (node == null)
      throw new ElementNotFoundException("Find operation failed. "
                                           + "No such element in tree.");
    return node.getElement();
  }
  
  /**
   * returns true if this is an empty tree, false otherwise.
   */
  public boolean isEmpty() {
    return size() == 0;
  }
  
  /**
   *  Returns the number of elements in this binary tree.
   * @return the number of elements in this binary tree
   */
  public int size() {
    int result = 0;  
    if (root != null) result = root.count();
    return result;
  }
  
  /**
   * Returns a String object representing this Binary Tree.
   * This string is an in-order representation.
   * This method does not work well, since it does not show the
   * null elements. So, one cannot re-produce a tree by its
   * string represenation.
   */
 /* public String toString() {
    String result = "";
    Iterator<T> iter = iterator();
    while (iter.hasNext())
      result += iter.next() + "\n";
    return result;
  }
  */
  
  /**
   * Returns a String object representing this Binary Tree.
   * This string is an in-order representation.
   * It uses parens to show the structure of the tree.
   */
  public String toString(){
    String s = "";
    if (root == null) return s;
    else {
      s = "(";
      s = s + getLeft().toString();
      s = s + root.getElement();
      s = s + getRight().toString();
      s = s + ")";
    }
      return s;
  }
    /**
   *  Populates and returns an iterator containing the elements in
   *  this binary tree using a preorder traversal.
   */
  public Iterator<T> preorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.preorder (iter);
    
    return iter;
  }
  
  /**
   *  Populates and returns an iterator containing the elements in
   *  this binary tree using an inorder traversal.
   */
  public Iterator<T> inorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.inorder (iter);
    
    return iter;
  }
  
    /**
   *  Populates and returns an iterator containing the elements in
   *  this binary tree using a postorder traversal.
   */
  public Iterator<T> postorder() {
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null)
      root.postorder (iter);
    return iter;
  }
  
  /**
   *  Populates and returns an iterator containing the elements in
   *  this binary tree using a levelorder traversal.
   */
  public Iterator<T> levelorder() {
    LinkedQueue<BTNode<T>> queue = new LinkedQueue<BTNode<T>>();
    ArrayIterator<T> iter = new ArrayIterator<T>();
    
    if (root != null) {
      queue.enqueue(root);
      while (!queue.isEmpty()) {
        BTNode<T> current = queue.dequeue();
        
        iter.add (current.getElement());
        
        if (current.getLeft() != null)
          queue.enqueue(current.getLeft());
        if (current.getRight() != null)
          queue.enqueue(current.getRight());
      }
    }
    return iter;
  }
  
  /**
   *  Satisfies the Iterable interface using an inorder traversal.
   */
  public Iterator<T> iterator() {
    return inorder();
  }
  
  
  
  
  //These are not mentioned in the BinaryTree Interface. 
  //Is this the right place to add them??
  
  public int height() {
    if (root == null)
      return 0;
      return root.height();
  }
  
  /*
   * Spins the tree in a way that the left sub-tree of every node
   * becomes its right sub-tree.
   * It modifies the initial tree.
   */
  public void spin() {
    if(root != null)
      root.spin();
  }
  

  //alternative implementation of spin()
  /*public void spin() {
    if (root == null) return; //do not do anything
    //the tree is not empty
    BTNode<T> temp = this.root.getLeft();
    this.root.setLeft(this.root.getRight());
    this.root.setRight(temp);
    this.getLeft().spin();
    this.getRight().spin();
  }
  */
  public static void main(String[] args) {
    LinkedBinaryTree<String> t0 = new LinkedBinaryTree<String>();
    LinkedBinaryTree<String> t3 = new LinkedBinaryTree<String>("bright");
    LinkedBinaryTree<String> t4 = new LinkedBinaryTree<String>("day");
    
    LinkedBinaryTree<String> t1 = new LinkedBinaryTree<String>("good", t3, new LinkedBinaryTree<String>());
    LinkedBinaryTree<String> t2 = new LinkedBinaryTree<String>("morning", t4, new LinkedBinaryTree<String>());
  
    LinkedBinaryTree<String> t5 = new LinkedBinaryTree<String>("hello", t1, t2);
    
    System.out.println("t0: " + t0);
    System.out.println("t3: " + t3);
    System.out.println("t1: " + t1);
    System.out.println("t5: " + t5);
    //System.out.println("before spinning:\n " + t5);
    //t5.spin();
    //System.out.println("after spinning:\n " + t5);
  }
}

