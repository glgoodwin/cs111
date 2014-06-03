/**  CS230 Tree Lab
 *   Fall 2010
 *   LabTreeOps.java
 *
 */

public class LabTreeOps {
  /*  printPreOrder, printPostOrder and printInOrder are
      included from lecture notes, as a helpful way to print
      out the contents of a tree
  */

  /**
   * printPreOrder prints the contents of the tree in this
   *  order: root, left, right
   *
   *  @param Tree t the tree to be printed
   *
   */
  public static void printPreOrder(Tree t) {
    if (!isLeaf(t)){
      System.out.println(value(t));
      if (left(t) != null)
	printPreOrder(left(t));
      if (right(t) != null)
	printPreOrder(right(t));
    }
  }
  /**
   * printPostOrder prints the contents of the tree in this
   *  order: left, right, root
   *
   *  @param Tree t the tree to be printed
   *
   */
  public static void printPostOrder(Tree t) {
    if (!isLeaf(t)){
      if (left(t) != null)
	printPostOrder(left(t));
      if (right(t) != null)
	printPostOrder(right(t));
      System.out.println(value(t));
    }
  }

  /**
   * printInOrder prints the contents of the tree in this
   *  order: left, root, right
   *
   *  @param Tree t the tree to be printed
   */
  public static void printInOrder(Tree t) {
    if (!isLeaf(t)){
      if (left(t) != null)
	printInOrder(left(t));
      System.out.println(value(t));
      if (right(t) != null)
	printInOrder(right(t));
    }
  }
  
  // local abbreviations for class methods defined in the Tree class
  public static Tree leaf () {
    return Tree.leaf();
  }
  public static boolean isLeaf (Tree t) {
    return Tree.isLeaf(t);
  }
  public static Tree node (Object val, Tree lt, Tree rt) {
	return Tree.node(val, lt, rt);
  }
  public static Object value (Tree t) {
    return Tree.value(t);
  }
  public static Tree left (Tree t) {
    return Tree.left(t);
  }
  public static Tree right (Tree t) {
    return Tree.right(t);
  }
  public static void setValue (Tree t, Object newValue) {
    Tree.setValue(t, newValue);
  }
  public static void setLeft (Tree t, Tree newLeft) {
    Tree.setLeft(t, newLeft);
  }
  public static void setRight (Tree t, Tree newRight) {
    Tree.setRight(t, newRight);
  }

    public static boolean compareTrees(Tree t1, Tree t2){
	boolean answer;
	if(t1.isLeaf() || t2.isLeaf()){
	    answer = false;
	}else{
	    if(!t1.left().isLeaf()&& !t2.left().isLeaf()){//when both the left are nodes
		    //recurse on the left side
		System.out.println("left is not a leaf, recursing");
		    answer= compareTrees(t1.left(),t2.left());
			}else{
		    if(t1.left().isLeaf() && t2.left().isLeaf()){
			System.out.println("Left is leaf, checking right");
			//when both are leaves try the right side
			if(!t1.right().isLeaf() && !t2.right().isLeaf()){
			    System.out.println("Right is not a leaf, recursing");

			    //both right sides are nodes, recurse on right
			    answer= compareTrees(t1.right(),t2.right());
			}else{
			    if(t1.right().isLeaf()&&t2.right().isLeaf()){
				System.out.println("right is a leaf,returning true");
				answer= true;
				//both left and right side are empty, but tree is equal
			    }else{
				System.out.println("trees not equal");
				answer= false;}}}
		    else{answer = false;}}}
	return answer; 
}

    public void replaceOccurences(Tree t, String oldString, String newString){
	if(t.(String)value().equals(oldString)){
	       setValue(newString);
	   }
	   if(!t.left().isLeaf()){
	       replaceOccurences(t.left(),oldString,newString);
	   }
	   if(!t.right().isLeaf()){
	   replaceOccurences(t.right(),oldString,newString);
	   }
    }


    public static void main(String args[]){
	Tree L = leaf();
	Tree t1 = node("john",L,L);
	Tree t2 = node("paul",L,node("george",L,L));
	
	Tree T = leaf();
	
	System.out.println(compareTrees(t1,t2));
	System.out.println(compareTrees(t2,t2));
	
		       




    }
		   
		
}
    
  

