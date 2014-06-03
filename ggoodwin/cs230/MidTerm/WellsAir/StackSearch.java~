import java.util.*;

public class StackSearch {

  // code to implement the search for a path between two nodes of a graph

  public static LinkedList<String> makeList (String S) {
    // creates a LinkedList from the characters of S, with each node of 
    // the list containing a String of one character
    LinkedList<String> L = new LinkedList<String>();
    for (int i = 0; i < S.length(); i++) 
      L.add(S.substring(i, i+1));
    return L;
  }

  public static LinkedList<GraphNode> makeGraph () {
    // creates a LinkedList that stores information about a sample graph
    // to be searched
    LinkedList<GraphNode> graph = new LinkedList<GraphNode>();
    graph.add(new GraphNode("P", makeList("RW")));
    graph.add(new GraphNode("Q", makeList("X")));
    graph.add(new GraphNode("R", makeList("X")));
    graph.add(new GraphNode("S", makeList("T")));
    graph.add(new GraphNode("T", makeList("W")));
    graph.add(new GraphNode("W", makeList("SY")));
    graph.add(new GraphNode("X", makeList("")));
    graph.add(new GraphNode("Y", makeList("RZ")));
    graph.add(new GraphNode("Z", makeList("")));
    return graph;
  }

  public static LinkedList<String> getNeighborList (String label, LinkedList<GraphNode> graph) {
    // given the label for a node of the graph, returns list of neighbors
    GraphNode node;
    for (int i = 0; i < graph.size(); i++) {
      node = graph.get(i);
      if ((node.getLabel()).equals(label))
	return node.getNeighbors();
    }
    return new LinkedList<String>();
  }

  public static boolean isMember (String S, LinkedList<String> L) {
    // returns true if the String S is contained in the list L
    for (int i = 0; i < L.size(); i++)
      if (L.get(i).equals(S))
	return true;
    return false;
  }

  public static void removeVisitedNeighbors (LinkedList<String> neighbors, 
					     LinkedList<String> visited) {
    // removes nodes of the neighbors list whose contents are contained in
    // the visited list
    int i = 0;
    while (i < neighbors.size()) {
      if (isMember(neighbors.get(i), visited))
	neighbors.remove(i);
      else
	i++;
    }
  }

  public static void searchGraph (LinkedList<GraphNode> graph, String start, 
				  String goal) {
    // finds a path in the graph from the start node to the goal node, 
    // using the depth-first search strategy
    String current = start;                  // current node during search
    LinkedList<String> visited = new LinkedList<String>();   // list of visited nodes
    visited.add(start);
    LinkedList<String> neighbors = new LinkedList<String>(); // list of neighboring nodes
    Stack<String> pathStack = new Stack<String>();           // stores the current path
    pathStack.push(start);                         // through the graph
    System.out.print(current + " ");
    while (!current.equals(goal)) {      // while the goal is not reached...
      // get the neighbors of the current node
      neighbors = getNeighborList(current, graph);
      // remove neighbors that have already been visited
      removeVisitedNeighbors(neighbors, visited);
      if (neighbors.isEmpty()) {       // no new nodes can be reached,
	pathStack.pop();                  // so back-up the search to
	current = pathStack.peek();     // a previous node
	System.out.print(current + " ");
      } else {   // proceed to a new node of the graph
	current = neighbors.get(0);
	pathStack.push(current);
	visited.add(current);
	System.out.print(current + " ");
      }
    }
    System.out.println("\n Final path from start node to goal node:");
    System.out.println(pathStack);
    }

  public static void main (String[] args) {
    LinkedList<GraphNode> graph = makeGraph();
    searchGraph(graph, "P", "Z");
  }

}
