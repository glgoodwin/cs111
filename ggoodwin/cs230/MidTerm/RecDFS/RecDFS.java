/** Gabe Goodwin
    MidTerm 2
    REcursive Stack Search
    Changed the searchGraph Method*/
import java.util.*;

public class RecDFS{

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

  public static void recDFS (LinkedList<GraphNode> graph, String start, 
				  String goal) {
      String current = start;
      String last = null;
      LinkedList<String> visited = new LinkedList<String>();
      visited.add(start);
      LinkedList<String> neighbors = new LinkedList<String>();
      while(! current.equals(goal)){
	  neighbors = getNeighborList(current,graph);
	  removeVisitedNeighbors(neighbors,visited);
	  if(neighbors.isEmpty()){
	      if(last.equals(null)){
		  System.out.println("There is no path");
	      }else{
		 recDFS(graph,last,goal);
	      }}
	     else{
		 last = current;
		 current = neighbors.get(0);
		 recDFS(graph,current,goal);
		 System.out.println(current);
	     }
      }}
  public static void main (String[] args) {
    LinkedList<GraphNode> graph = makeGraph();
    recDFS(graph, "P", "Z");
  }
      
}
