public class TableBST<V> implements TableInterface<V> {
	
  // uses a binary search tree to implement the methods specified 
  // in the Table interface
    
  private int size;
  private Tree<TableEntry<V>> entries;
	
  public TableBST () {
    // constructs an initial empty table 
    size = 0;
    entries = new Tree<TableEntry<V>>();
  }
	
  public boolean isEmpty () {
    // returns true if there are no entries in the table
    return entries.isLeaf();
  }
	
  public void clear () {
    // resets the table to be empty
    size = 0;
    entries = new Tree<TableEntry<V>>();;
  }
	
  public V search (String key) {
    // calls the find() method to search the table for a TableEntry 
    // containing the given key. If an entry with the given key is in the 
    // table, then the value associated with the entry is returned. Otherwise 
    // the value null is returned
    return find(entries, key);
  }
	
  private V find (Tree<TableEntry<V>> t, String key) {
    // if an entry with the given key is stored in the tree t, then the 
    // value associated with this entry is returned. Otherwise null is returned.
    // if tree is empty, then key is not found, so return null immediately
    if (isLeaf(t))
      return null;
    // if key is found, return value of entry associated with this key
    else if (getValue(t).key().equals(key))
      return getValue(t).value();
    // if given key is less than key in the root node, search left subtree
    else if (getValue(t).key().compareTo(key) > 0)
      return find(getLeft(t), key);
    // otherwise given key is greater than the key in root node, so search 
    // the right subtree
    else
      return find(getRight(t), key);
  }
  
  public void insert (TableEntry<V> newEntry) {
    // calls the insertTree() method to insert the TableEntry newEntry 
    // into the table. If a TableEntry with the same key already exists 
    // in the table, this TableEntry will be replaced with newEntry
    entries = insertTree(entries, newEntry);
  }
	
  private Tree<TableEntry<V>> insertTree (Tree<TableEntry<V>> t, TableEntry<V> newEntry) {
    // returns a new Tree with the TableEntry newEntry inserted. If an 
    // entry with the same key already exists in the table, this 
    // TableEntry will be replaced by newEntry.
    // If we have reached a leaf where newEntry should be inserted, 
    // create new Tree containing newEntry and return this new Tree
    if (isLeaf(t)) {
      size++; 	   // one more entry has been added to the tree
      return new Tree<TableEntry<V>>(newEntry, new Tree<TableEntry<V>>(), new Tree<TableEntry<V>>());
      // if the key in newEntry is found, return a tree with newEntry 
      // stored in root node, and left and right subtrees preserved
    } else if (getValue(t).key().equals(newEntry.key()))
      return node(newEntry, getLeft(t), getRight(t));
      // if the key in newEntry is less than the key in root node, then 
      // newEntry should be inserted into the left subtree, so return a 
      // tree with same root node and right subtree, but with left subtree 
      // in which newEntry has been inserted 
    else if (getValue(t).key().compareTo(newEntry.key()) > 0)
       return node(getValue(t), insertTree(getLeft(t), newEntry), getRight(t));
      // otherwise the key in newEntry is greater than the key in root 
      // node, so newEntry should be inserted into the right subtree. In 
      // this case, return a tree with same root node and same left subtree, 
      // but with a right subtree in which newEntry is inserted
    else
      return node(getValue(t), getLeft(t), insertTree(getRight(t), newEntry));
  }
	
  public void delete (String key) {
    // deletes the TableEntry with the given key from the table
    if (search(key) != null) {
      // if TableEntry with the given key already exists in the table, 
      // call deleteTree() method to delete this TableEntry
      entries = deleteTree(entries, key);
      size--;       // update size of table
    }
  }
  
  private Tree<TableEntry<V>> deleteTree (Tree<TableEntry<V>> t, String key) {
    // returns a new Tree in which the TableEntry with given key is deleted. 
    // if the tree is empty, then return an empty tree
    if (isLeaf(t))
      return new Tree<TableEntry<V>>();
    // if TableEntry with given key is found, then delete this node from tree
    else if (getValue(t).key().equals(key)) {
      // if left subtree is empty, then shift right subtree upwards to 
      // the root of this tree (i.e. just return the right subtree)
      if (isLeaf(getLeft(t)))
	return getRight(t);
      // if right subtree is empty, then shift left subtree upwards to 
      // the root of this tree (i.e. just return the left subtree)
      else if (isLeaf(getRight(t)))
	return getLeft(t);
      // if there are left and right subtrees of node to be deleted, 
      // then first find TableEntry in the left subtree that contains 
      // the maximum key (i.e. the String that occurs latest in the 
      // alphabet). Return a new tree that has this new TableEntry at 
      // its root, and has a left subtree in which this maximum entry 
      // has been deleted. The original right subtree is preserved
      else {
	TableEntry<V> prevTableEntry = maxEntry(getLeft(t));
	return node(prevTableEntry, deleteMaxEntry(getLeft(t)), getRight(t));
      }
      // if given key is less than key in the root node, then node to be 
      // deleted is contained in left subtree, so return a tree with 
      // same root node and right subtree, but with left subtree in 
      // which TableEntry with given key has been deleted
    } else if (getValue(t).key().compareTo(key) > 0)
      return node(getValue(t), deleteTree(getLeft(t), key), getRight(t));
    // otherwise given key is greater than key in the root node, so the node 
    // to be deleted is in the right subtree. Return a tree with same root 
    // node and left subtree, but with right subtree in which TableEntry 
    // with given key has been deleted
    else
      return node(getValue(t), getLeft(t), deleteTree(getRight(t), key));
  }
	
  private TableEntry<V> maxEntry (Tree<TableEntry<V>> t) {
    // returns the TableEntry in the Tree t that has the maximum String
    // (i.e. latest in the alphabet)
    if (isLeaf(getRight(t)))
      return getValue(t);
    else
      return maxEntry(getRight(t));
  }
	
  private Tree<TableEntry<V>> deleteMaxEntry (Tree<TableEntry<V>> t) {
    // returns a Tree in which the node with the maximum String is deleted
    // if right subtree is empty, then node with the maximum String key 
    // has been found, so it can effectively be removed by just returning 
    // the left subtree of this node
    if (isLeaf(getRight(t)))
      return getLeft(t);
    // otherwise keep searching down the right subtrees for node with
    // the maximum String key
    else
      return node(getValue(t), getLeft(t), deleteMaxEntry(getRight(t)));
  }
	
  public int size () {
    // returns the number of entries in the table
    return size;
  }
  
  public String toString () {
    // returns a String representation of the contents of a table
    return toString(entries);
  }
    
  private String toString (Tree<TableEntry<V>> t) {
    // uses in-order traversal of the tree t to create a String 
    // representation of its contents
    if (isLeaf(t)) 
      return "";
    else
      return toString(getLeft(t)) + getValue(t) + "\n" + 
	toString(getRight(t));
  }

  // local abbreviations for the Tree class methods
  
  public static <V> boolean isLeaf (Tree<TableEntry<V>> t) {  return Tree.isLeaf(t);  }  
  public static <V> Tree<TableEntry<V>> node (TableEntry<V> val, Tree<TableEntry<V>> lt, Tree<TableEntry<V>> rt) {  return Tree.node(val, lt, rt);  }	
  public static <V> TableEntry<V> getValue (Tree<TableEntry<V>> t) {  return Tree.getValue(t);  }	
  public static <V> Tree<TableEntry<V>> getLeft (Tree<TableEntry<V>> t) {  return Tree.getLeft(t);  }	
  public static <V> Tree<TableEntry<V>> getRight (Tree<TableEntry<V>> t) {  return Tree.getRight(t);  }
  
  // testing the implementation methods
  public static void main(String[] args) {
    TableBST<String> T = new TableBST<String>();
    System.out.println("Should be true and 0: " + T.isEmpty() + " " + T.size());
    T.insert(new TableEntry<String>("A", "Alpha"));
    System.out.println("Should be false and 1: " + T.isEmpty() + " " + T.size());
    System.out.println("Has 4 in : " + T);
    T.insert(new TableEntry<String> ("B", "Beta"));
    T.insert(new TableEntry<String> ("G", "Gamma"));
    T.insert(new TableEntry<String> ("D", "Delta"));
    System.out.println("Should be false and 4: " + T.isEmpty() + " " + T.size());
    System.out.println("Has 4 in : " + T);
    System.out.println("Can you find G? : " + T.search("G"));
    T.delete("A");
    System.out.println("Has no A in it : " + T);
  }
  
}
