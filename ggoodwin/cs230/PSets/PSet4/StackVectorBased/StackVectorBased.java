/** Gabe Goodwin
   cs230 Pset 4
   10/17/2010
   Task 2
*/

import java.util.*;

public class StackVectorBased<E> implements StackInterface<E> {
    /** instance variables */
    private Vector<E> items;

    /** constructor
	creates a new empty vector*/
    public StackVectorBased(){
	items = new Vector();
    }
    /** returns true if the vector is empty*/
    public boolean isEmpty(){
	return items.isEmpty();
    }
    /** add a new item to the end of the vector.*/
    public void push(E newItem){
	items.add(newItem);//adds item to the end of the vector
    }
    /** pops an item off the end of the vector. Last item added will be popped */
    public E pop() throws StackException {
	if(!isEmpty()){//takes last item of vector, since I am adding to the end it will be the last item that was added
	    return items.remove(items.lastIndexOf(items.lastElement()));
	} else{
		throw new StackException("Cannot pop, stack is empty");
	    }
	}
    /** Shows what is on the end of the stack */
    public E peek() throws StackException {
	if (!isEmpty()){
	    return items.lastElement();
	}else{
	    throw new StackException("Cannot peek, stack is empty");
    }
    }
}