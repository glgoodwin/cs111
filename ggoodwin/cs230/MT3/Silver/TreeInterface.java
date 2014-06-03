public interface TreeInterface<E> {

  public boolean isLeaf ();
	
  public E getValue ();
	
  public Tree<E> getLeft ();
	
  public Tree<E> getRight ();
	
  public void setValue (E newValue);
	
  public void setLeft (Tree<E> newLeft);
	
  public void setRight (Tree<E> newRight);

  public String toString ();
	
  
}
