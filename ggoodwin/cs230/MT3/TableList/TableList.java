/** 
Gabrielle Goodwin
MidTerm3 
Problem 2
Table List
**/


public class TableList<ListNode<TableEntry<V>>>{
    //I can't seem to get this code to compile :( 
    //instance variables
    private ListNode<TableEntry<V>> front;
    private int size;

    //constructor
    public TableList(){
	front = null;
	size = 0;
    }
    
    public  boolean isEmpty(){//O(n)
	if(front.value() = null)
	    return true;
	else return false;
    }//end isEmpty


    public void clear(){//O(n)
	front.setValue(null);
	size = 0;
    }//end Clear


    public <V> void insert(TableEntry<V> newEntry){//O(n)
	ListNode<TableEntry<V>> g = front;
	
	if(!contains(newEntry)){//if contains is true, value is already inserted
	    while(g.value != null){
		g = g.next(); //goes to end of the list
	    }
	    g.value.key = newEntry.key();
	    g.setValue(newEntry.value());
	}
	    size = size +1;

	
    }//end Insert
    
    public <V> boolean contains(TableEntry<V> newEntry){//helper method for insert
	ListNode<TableEntry<V>> g = front;
	boolean result = false;// initialize to false, will only change if an entry with the same key is found
	while(g != null){
	    if(g.value.key() = newEntry.key()){
		g.setValue(newEntry.value());
		result = true;
		break;
		    }else{
		g = g.next();
	    }return result;}
    }

    public <V> void delete(String key){//O(n)
       	ListNode<TableEntry<V>> g = front;
	while(!g.isEmpty()){
	    if(g.value.key() = key){//when the key to delete is found
		size = size -1;
		while(g.value !=null){//need to shift all of the values down one
		   ListNode<TableEntry<V>> h = g.next();
		    g.value.key() = h.value.key(); 
		    g.setValue(h.value());
		    g = g.next();
		}
	    }else{//leave as is and move on to next value
		g = g.next();
	    }
	}
    }//end delete


    public<V> V search(String key){ //O(n)
	ListNode<TableEntry<V>> g = front;
	while(g.value != null){
	    if(g.value.key() = key){
		return g;
		    }else{
		g = g.next();
	    }}

    }//end search


    public int size(){ //O(1)
	return size;
    }//end size


    public String toString(){ //O(n)
      	ListNode<TableEntry<V>> g = front;
	while(g != null){
	    g.toString();
	}
    }//end toString





    

}//end TableList
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