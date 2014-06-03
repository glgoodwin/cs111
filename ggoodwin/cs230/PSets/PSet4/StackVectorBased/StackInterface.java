public interface StackInterface<E> {

  /** Determines whether stack is empty.
   * Precondition: None.
   * Postcondition: Returns true if the stack is empty; otherwise returns false. */
  public boolean isEmpty();

  /** Adds an item to the top of a stack.
   * Precondition: newItem is the item to be added.
   * Postcondition: If insertion is successful, newItem is on top of the stack. */
  public void push(E newItem);

  /** Removes the top of a stack.
   * Precondition: None.
   * Postcondition: If the stack is not empty, the item that was added most
   *     recently is removed from the stack and returned.
   * Exception: Throws StackException if the stack is empty. */
  public E pop() throws StackException;

  /** Retrieves the top of a stack.
   * Precondition: None.
   * Postcondition: If the stack is not empty, the item that was added most
   * 	recently is returned. The stack is unchanged. 
   * Exception: Throws StackException if the stack is empty. */ 
  public E peek() throws StackException;


}
