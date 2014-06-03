/** Gabrielle Goodwin
    11/15/2010
    Midterm 3
    Problem 1
**/
import java.util.*;
import java.lang.Object.*;

public class ExamTreeOps extends Tree {
    //instance variables
    
    //part a
    public static boolean fuji(Tree<Integer> t){
	boolean result = true;
	if(t.isLeaf()){
	    result = true;
	}
	if(!t.getLeft().isLeaf()){
	    if(t.getLeft().getValue()>t.getValue()){
		result = fuji(t.getLeft());
	    }else{
		result= false;
	    }
	}

        if(!t.getRight().isLeaf()){
	    if(t.getRight().getValue()>t.getValue()){
		result = fuji(t.getRight());
	    }else{
		result = false;
	    }
	   
	}return result;
    }
    //part b
    public static <E> Tree<E> addComplete (Tree<E> t, E newValue){
	//need to use breadth first search for this problem
	//downloaded Queue.java to use Queues

	Queue<Tree<E>> trees = new Queue<Tree<E>>();
	Tree<E> copy = copyTree(t);
	trees.enQ(copy);
	Tree<E> answer;
	answer = trees.deQ();
	while((!answer.getLeft().isLeaf())&&(!answer.getRight().isLeaf())){
	    trees.enQ(answer.getLeft());
	    trees.enQ(answer.getRight());
	    answer = trees.deQ();
	}
	    if(answer.getLeft().isLeaf()){
		answer.setLeft(new Tree(newValue, new Tree(), new Tree()));
	    }else{
		answer.setRight(new Tree(newValue, new Tree(), new Tree()));
	    }
	    System.out.println(t);
	    return copy;
    }

    public static <E> Tree<E> copyTree(Tree<E> t){
	if(t.isLeaf()){
	       return t;
	   }else{
	    return new Tree(t.getValue(), copyTree(t.getLeft()),copyTree(t.getRight()));
	}}

    //part d
    public static <E> void preOrder (Tree<E> t){
	Stack<E> nodes = new Stack<E>();
	Stack<Tree<E>> visited = treeLeft(t, nodes);
	Tree<E> v = visited.peek();
	while(!visited.isEmpty()){
	    v = visited.pop();
	    Stack<Tree<E>> n  = treeLeft(v, nodes);
	    while(!n.isEmpty()){
		treeRight(n.pop(),nodes);
	    }}
	System.out.println(nodes);	
    }
    //helper method for preOrder
    public static <E> Stack<Tree<E>> treeLeft(Tree<E> t, Stack<E> nodes){//returns a stack of tree objects
	Stack<Tree<E>> visited = new Stack<Tree<E>>();
	while(!t.isLeaf()){
	    nodes.push(t.getValue());
	    visited.push(t.getRight());
	    t = t.getLeft();
	}
	return visited;
    }
    //helper method for preOrder
    public static <E> void  treeRight(Tree<E> t, Stack<E> nodes){
	while(!t.isLeaf()){
	    nodes.push(t.getValue());
	    t = t.getRight();
	}
    }

    //part c
    public static <E> int width (Tree<E> t){
	//this method is not working correctly. I keep getting an empty stack exception, when that should be handled by my while loop.
	Stack<Tree<E>> Q1 = new Stack<Tree<E>>();
	Stack<Tree<E>> Q2 = new Stack<Tree<E>>();
	Q1.push(t);
	int height = height(t);
        int  maxLevel = 0;
	int i = 0;
	while(i < height){
	int curLevel = 0;
	while(!Q1.isEmpty()){//Q1 should be empty after the last row of the tree.
	    Q2.clear();
	    Tree x = Q1.pop();
	    if(!x.getLeft().isLeaf()){
		curLevel ++;
		Q2.push(x.getLeft());
	    }
	    if(!x.getRight().isLeaf()){
		curLevel++;
		Q2.push(x.getRight());
	    }
	    if(x.getLeft().isLeaf() && x.getRight().isLeaf()){
		curLevel = curLevel;
	    }		
	    if(curLevel > maxLevel){
	    maxLevel = curLevel;
	    }
	}//when Q1 is empty
	    Q1 = Q2;//set contents of q1 to q2
	    i= i++;//increment level to start loop over
	}

		return maxLevel;
    }


    


    //part e
    public static <E> boolean isComplete (Tree<E> t){
	//this method is not working. Returns a false when there should be true
	if(isFull(t)){
	    return true;
	}
	// if the tree is not full
	int nodes = countNodes(t);
	int h = height(t);
	int full =0;
	int i = 0;
	while(i<(h-1)){
	    int j = (int)Math.pow(2,i);
	    full = full +j;
	    i++;
	    System.out.println(full);
	}
	if(nodes <(full+1)){
	    return false; //aren't enough nodes for it to be complete
	}
	//if those two fail, need to do Breadth First Search
	`//this is the part that isn't working
	Queue<Tree<E>> trees = new Queue<Tree<E>>();
	trees.enQ(t);
	Tree<E> answer;
	answer = trees.deQ();
	while((!answer.getLeft().isLeaf())&&(!answer.getRight().isLeaf())){
	    trees.enQ(answer.getLeft());
	    trees.enQ(answer.getRight());
	    answer = trees.deQ();
	}
	if(answer.getLeft().isLeaf()&& !answer.getRight().isLeaf()){
	    return false;}//left is empty but right isnt
	else{ //if both are leaves,or left is not leaf and right is, have to keep checking to make sure rest of the nodes dont have any children
	    while(!trees.empty()){// trees with children should have been deQ'd if this is a full tree
		Tree k = trees.deQ();
		if(!k.getLeft().isLeaf() || k.getLeft().isLeaf()){
		    return false;
		}
	    }
	    return true;  	
	}
    }
    


    public static <E> boolean  isFull(Tree<E> t){
	int h = height(t);
	int nodes = countNodes(t);
	int full =0;
	int i = 0;
	while(i<h){
	    int j = (int)Math.pow(2,i);
	    full = full +j;
	    i++;
	}
	if(nodes == full){
	    return true;
	}else{
	    return false;
	}}



    public static <E> int height(Tree<E> t){
	if (isLeaf(t))	
	    return 0;
	else	
	    return 1 + Math.max(height(t.getLeft()), height(t.getRight()));
    }

    public static <E> int countNodes(Tree<E> t){
	if (t.isLeaf())	
    return 0;
	else	
	    return 1 + (countNodes(t.getLeft()) + (countNodes(t.getRight())));

    }
	

		
    public static void main(String[] args){
	Tree<Integer> leaf = new Tree();
	Tree<Integer> a = new Tree(6,leaf,leaf);
	Tree<Integer> b = new Tree(10,leaf,leaf);
	Tree<Integer> c = new Tree(4,a,leaf);
	Tree<Integer> d = new Tree(5,leaf,b);
	Tree<Integer> e = new Tree(2,c,d);
	Tree<Integer> f = new Tree(3,leaf,leaf);

	Tree<Integer> g = new Tree(1,e,f);

	Tree<Integer> h = new Tree(12,leaf,leaf);
	Tree<Integer> i = new Tree(13,leaf,leaf);
	Tree<Integer> j = new Tree(9,leaf,leaf);
	Tree<Integer> k = new Tree(8,h,leaf);
	Tree<Integer> l = new Tree(11,i,j);

	Tree<Integer> m = new Tree(7,k,l);


	Tree<String> n = new Tree("sue", leaf,leaf);
	Tree<String> o = new Tree("joe", leaf, leaf);
	Tree<String> p = new Tree("jan", n, o);
	Tree<String> q = new Tree("dan", leaf, leaf);
	Tree<String> r = new Tree("bob", q, leaf);

	Tree<String> s = new Tree("ann", p, r);

	System.out.println(fuji(g));
	System.out.println(fuji(m));

	System.out.println(s);
       	System.out.println(addComplete(s,"chloe"));
	
	preOrder(g);
	preOrder(m);
	preOrder(s);

	System.out.println(isComplete(g));
	System.out.println(isComplete(m));
	System.out.println(isComplete(s));

	System.out.println(width(g));
	System.out.println(width(m));
	System.out.println(width(s));
    }}//close ExamTreeOps
    