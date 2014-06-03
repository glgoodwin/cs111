// A class to represent one item in a list
public class ListNode<E> {

    // Simplify things by using public instance variables
    public E value;
    public ListNode<E> next;
    
    public ListNode(E value, ListNode<E> next) {
        this.value = value;
        this.next = next;
    }
}
