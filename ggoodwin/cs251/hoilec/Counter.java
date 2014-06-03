public class Counter {

  // Instance variables:
  private int count; 
  private int id; 

  // Class variables: 
  private static int numCounters = 0;
  private static IList<Counter> allCounters = IList.<Counter>empty();

  // Constructor method: 
  public Counter () {
    numCounters++; // We've made one more counter
    allCounters = IList.prepend(this, allCounters); 
        // Include this counter in list of all counters
    // Here and below, can punt "this."
    this.id = numCounters; // Initialize this counter's id
    this.count = 0; // Initialize this counter's count to 0
    displayCounters(); // Display state of all counters
  }

  // Instance methods: 
  public void inc () {
    this.count++; // Increment this counter's count
    displayCounters(); // Display state of all counters
  }

  public String toString () {
    return "[" + this.id + " of " + numCounters + ":" + this.count + "]";
  }

  // Class methods: 
  public static void displayCounters() {
    IList<Counter> counters = allCounters; 
    while (! IList.isEmpty(counters)) {
      System.out.print(IList.head(counters) + "; ");
      counters = IList.tail(counters);
    }
    System.out.println();
  }

  // Testing code for the Counter class
  public static void main (String [] args) {
    Counter a = new Counter();
    a.inc();
    Counter b = new Counter();
    a.inc(); 
    b.inc(); 
    a.inc();
  }

}

