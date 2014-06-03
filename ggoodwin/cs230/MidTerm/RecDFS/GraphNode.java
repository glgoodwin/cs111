import java.util.*;

public class GraphNode {

  // new class to store information about the label and neighbors
  // of a node of a graph to be searched
  
  // instance variables

  private String label;
  private LinkedList<String> neighbors;

  // constructor
  
  public GraphNode (String name, LinkedList<String> neighborList) {
    label = name;
    neighbors = neighborList;
  }

  // instance methods
  
  public String getLabel () {
    return label;
  }

  public LinkedList<String> getNeighbors () {
    return neighbors;
  }

  public String toString () {
    return label;
  }

}
