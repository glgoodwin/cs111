public class LabTreeOps<T> {
  
  /**
   * comapres the structure of two binary trees. If they have the same 
   * number of nodes and the same shape, it returns true. 
   * The contents of the nodes do not matter.
   * 
   * @param two BianryTrees to be compared
   * @return true if the two input tress have the same shape
   * false otherwise.
   * 
   * 
   */
  public  boolean compareTrees(BinaryTree<T> t1, BinaryTree<T> t2){
    if (t1.isEmpty() && t2.isEmpty()) //if both empty, then similar
      return true; 
    if ((t1.isEmpty() && !t2.isEmpty()) || //if *only one* is empty, they are not similar
        (!t1.isEmpty() && t2.isEmpty()))
      return false;
    
    boolean leftComparison = compareTrees(t1.getLeft(), t2.getLeft());
    boolean rightComparison = compareTrees(t1.getRight(), t2.getRight());
    return leftComparison && rightComparison ;
  }
  
  /**
   * It counts the number of times the second param, obj,
   * appears on the input Bianry Tree.
   * @return The number of occurences of obj in the tree.
   */
  public int countOccurrences(BinaryTree<T> t, T obj) {
    if (t.isEmpty()) return 0;
    if (t.getRootElement().equals(obj)) 
      return 1 + countOccurrences(t.getLeft(), obj) + 
      countOccurrences(t.getRight(), obj);
    else //root not an "occurence"
      return countOccurrences(t.getLeft(), obj) + 
      countOccurrences(t.getRight(), obj);
  }
  
  /**
   * It creates a new tree where every occurence of the oldObj is 
   * replaced by the newObj.
   * Notice that this is a new Binary Tree, with the exact 
   * same structure as the original one. 
   * The two trees share the common nodes.
   * @param the initial tree
   * @param the object to be replaced in the newly created tree
   * @param the object to replace it with.
   * @return the newly created tree that contains newObjs, instead
   * of oldObjs.
   * Notice: The equals() on T is employed to decide equality of T objects.
   * Also, notice how we need to pass "LinkedBianryTree"s as params, as
   * oppposed to just BinaryTrees. The reason is that here, we do 
   * create a new BinaryTree, so we need to be specific about 
   * the implementation we want to use. In other words, one cannot
   * instanciate an Interface, like BinaryTree.!
   */
  public LinkedBinaryTree<T> replaceOccurrences(LinkedBinaryTree<T> t, 
                                                T oldObj, T newObj) {
    if (t.isEmpty()) return t;
    //tree is not empty
    if (t.getRootElement().equals(oldObj))
      return new LinkedBinaryTree<T>(newObj, 
                                     replaceOccurrences(t.getLeft(), oldObj, newObj),
                                     replaceOccurrences(t.getRight(), oldObj, newObj));
    else //element is not equal to oldObj
      return new LinkedBinaryTree<T>(t.getRootElement(), 
                                     replaceOccurrences(t.getLeft(), oldObj, newObj),
                                     replaceOccurrences(t.getRight(), oldObj, newObj));
  }
  
  /**
   * makeHeightTree()
   * Creates and returns a Binary Tree, with the same structure
   * (size and shape) as the input tree. In each node, it contains the height of
   * the corresponding node in the input tree.
   * NOTICE: the root is assumed to be at height zero, its children at 
   * height 1, etc.
   */
  public LinkedBinaryTree<Integer> makeHeightTree(LinkedBinaryTree<T> t) {
    if (t.isEmpty()) return new LinkedBinaryTree<Integer>();
    //t is not empty
      return new LinkedBinaryTree<Integer>(t.height(),
                  makeHeightTree(t.getLeft()),
                  makeHeightTree(t.getRight()));
  }
  
  /** returns a new tree that is the same structure
    * (size and shape) as t, in which the value in each
    * node is the level of the
    * corresponding node in the input tree 
    * Notice: The root is at level 1, its children at level 2 etc...
    * CHECK THIS, as it is the same basically, as the one above!!
    */
  public LinkedBinaryTree<Integer> makeLevelTree(BinaryTree<T> t){
    return levelTree(t, 0);
  }
  //helper method to be used in the makeLevelTree()
  private LinkedBinaryTree<Integer> levelTree(BinaryTree<T> t, int h) {
    if (t.isEmpty()) return new LinkedBinaryTree<Integer>();
    else return new LinkedBinaryTree<Integer>(h + 1,
                                              levelTree(t.getLeft(), h + 1),
                                              levelTree(t.getRight(), h + 1));
  }
  
  public static void main(String[] args) {
    //create testing trees
    LinkedBinaryTree<String> t0 = new LinkedBinaryTree<String>();
    LinkedBinaryTree<String> t3 = new LinkedBinaryTree<String>("hello");
    LinkedBinaryTree<String> t4 = new LinkedBinaryTree<String>("day");
    
    LinkedBinaryTree<String> t1 = new LinkedBinaryTree<String>("good", t3, new LinkedBinaryTree<String>());
    LinkedBinaryTree<String> t2 = new LinkedBinaryTree<String>("morning", t4, new LinkedBinaryTree<String>());
    
    LinkedBinaryTree<String> t5 = new LinkedBinaryTree<String>("hello", t1, t2);
    LinkedBinaryTree<String> t6 = new LinkedBinaryTree<String>("morning", new LinkedBinaryTree<String>(), t4);
    
    //print testing trees out
    System.out.println("t0: " + t0);
    System.out.println("t1: " + t1);
    System.out.println("t2: " + t2);
    System.out.println("t3: " + t3);
    System.out.println("t4: " + t4);
    System.out.println("t5: " + t5);
    System.out.println("t6: " + t6);
    
    LabTreeOps<String> op = new LabTreeOps<String>();
    //Testing compareTrees()
    //System.out.println("t3 compared to t4: " + op.compareTrees(t3, t4));
    //System.out.println("t1 compared to t2: " + compareTrees(t1, t2));
    //System.out.println("t2 compared to t1: " + compareTrees(t2, t1));
    //System.out.println("t1 compared to t4: " + compareTrees(t1, t4));
    //System.out.println("t6 compared to t2: " + compareTrees(t6, t2));
    //System.out.println("t5 compared to t5: " + compareTrees(t5, t5));
    
    //Testing replaceOccurences()
    //System.out.println("t3 replaceOccurrences of bright with xxx --> " + op.replaceOccurrences(t3, "bright", "xxx"));
    //System.out.println("t3 replaceOccurrences of bri with xxx --> " + op.replaceOccurrences(t3, "bri", "xxx"));
    //System.out.println("t5 replaceOccurrences of bright with xxx --> " + op.replaceOccurrences(t5, "bright", "xxx"));
    
    //Testing countOccurences()
    //System.out.println("t3 countOccurrences of bright --> " + op.countOccurrences(t3, "bright"));
    //System.out.println("t3 countOccurrences of bri --> " + op.countOccurrences(t3, "bri"));
    //System.out.println("t6 countOccurrences of morning --> " + op.countOccurrences(t6, "morning"));
    //System.out.println("t5 countOccurrences of hello --> " + op.countOccurrences(t5, "hello"));
    
    //Testing makeHeightTree()
    //System.out.println("t1 makeHeightTree --> " + op.makeHeightTree(t1));
    //System.out.println("t2 makeHeightTree --> " + op.makeHeightTree(t2));
    //System.out.println("t3 makeHeightTree --> " + op.makeHeightTree(t3));
    //System.out.println("t4 makeHeightTree --> " + op.makeHeightTree(t4));
    //System.out.println("t5 makeHeightTree --> " + op.makeHeightTree(t5));
    //System.out.println("t6 makeHeightTree --> " + op.makeHeightTree(t6));
    
    //Testing makeLevelTree()
    System.out.println("t1 makeLevelTree --> " + op.makeLevelTree(t1));
    System.out.println("t2 makeLevelTree --> " + op.makeLevelTree(t2));
    System.out.println("t3 makeLevelTree --> " + op.makeLevelTree(t3));
    System.out.println("t4 makeLevelTree --> " + op.makeLevelTree(t4));
    System.out.println("t5 makeLevelTree --> " + op.makeLevelTree(t5));
  }
  
  
}
