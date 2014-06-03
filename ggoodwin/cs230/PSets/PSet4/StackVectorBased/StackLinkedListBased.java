import java.util.*;

public class StackLinkedListBased<E> implements StackInterface<E> {

  // Assume the top of stack is at position 0
  private LinkedList<E> items;

  public StackLinkedListBased() {
    items = new LinkedList<E>();
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  public void push(E newItem) {
    items.add(0, newItem);
  }

  public E pop() throws StackException {
    if (!isEmpty())
      return items.remove(0);
    else {
      throw new StackException("Stack Exception on pop: stack empty");
    }
  }

  public E peek() throws StackException {
    if (!isEmpty())
      return items.get(0);
    else {
      throw new StackException("Stack Exception on peek: stack empty");
    }
  }

}
