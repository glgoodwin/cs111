/** 
 *  FILENAME:  Card.java
 *  Defines a class, Card, to store information about a single playing card.
 *
 */
public class Card {
  //WRITTEN by Ellen
  //MODIFICATION HISTORY:  Edited to conform to javadoc
  // Replaced greater() with the compareTo()
  // Added testing code.
  private String value;
  private String suit;
  
  /**
   * Constructs a Card, given its value (like "Ace", "2", "Jack", "Queen",
   *   "King", etc)
   * and its suit (like "hearts", "clubs", "diamonds" and "spades").
   */	
  public Card (String value, String suit) {	
    this.value = value;
    this.suit = suit; 
  }
  /**
   * Access and returns the value of this Card object.
   *
   *@return	A string representing the value of the card.
   */
  public String value () {
    return this.value;
  }
  
  /**
   * Access and returns the suit of this Card object.
   *
   *@return	A string representing the suit of the card.
   */
  public String suit () {
    return this.suit;
  }
  
  /**
   * Compares two cards. The comparison is based on the values of the 
   *  two compared cards.  "Ace" has the value of 1, while "Jack", "Queen",
   *  and "King" have the value of 11, 12, and 13 respectively.
   * The result is a negative integer if this Card object is smaller than 
   *  the argument card.
   * The result is a positive integer if this Card object  is greater than 
   *  the argument card. 
   * The result is zero if the Cards  are equal; 
   * @return	The value 0 if the argument is a  Card equal to this  Card;
   * a value less than 0 if the argument is a Card greater than this card; 
   * and a value greater than 0 if the argument is a  Card  smaller than 
   * this  Card.
   */
  public int compareTo (Card inCard) {
    String value1 = this.value;
    String value2 = inCard.value;
    int intVal1, intVal2;
    if (value1.equals("Ace"))
      intVal1 = 1;
    else if (value1.equals("Jack"))
      intVal1 = 11;
    else if (value1.equals("Queen"))
      intVal1 = 12;
    else if (value1.equals("King"))
      intVal1 = 13;
    else
      intVal1 = Integer.parseInt(value1);
    if (value2.equals("Ace"))
      intVal2 = 1;
    else if (value2.equals("Jack"))
      intVal2 = 11;
    else if (value2.equals("Queen"))
      intVal2 = 12;
    else if (value2.equals("King"))
      intVal2 = 13;
    else
      intVal2 = Integer.parseInt(value2);
    //create two Integer objects out of intVal1 and intVal2, so you can use the
    // compareTo() method on Integers.
    Integer intObj1 = new Integer(intVal1);
    Integer intObj2 = new Integer(intVal2);
    return (intObj1.compareTo (intObj2));
  }
  
  /**
   * Returns a string representation of this Card object
   */
  public String toString() {
    String rep = this.value() +" of " +  this.suit();
    return rep;
  }
  
  /**
   * Main method so we can add testing code.
   *
   */
  public static void main (String[] args) {
    Card c1 = new Card("Ace","hearts");
    //System.out.println(c1);
    //Card c2 = new Card("2","hearts");
    //System.out.println(c2);	
    //int res = c2.compareTo(c1);
    //System.out.println(res);
    //res = c1.compareTo(c2);
    //System.out.println(res);
    Card c3 = new Card("Ace","clubs");
    System.out.println(c1.compareTo(c3));
    System.out.println(c3.compareTo(c1));
    
  }
  
}
	
