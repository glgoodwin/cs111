/** 
 * FILENAME: Deck.java
 * WHO: CS230 staff
 * PURPOSE:to create, shuffle and deal a deck of cards 
 * Defines a class, Deck, to store information about a full deck of (52) 
 *  playing cards.
 *
 */
import java.util.*;
public class Deck {
  // instance variable
    private Card [] cards; // array of Card objects
  
  /**
   * Constructs a Deck of 52 playing cards.
   */	
  public Deck () {	
    cards = new Card [52];
    for (int i = 0; i < 52; i++) {
      cards[i] = new Card(intToValue((i / 4) + 1), intToSuit(i % 4));
    }
  }

 /**
   *  Converts an integer number into a String that represents a playing card
   *  @param int intVal  the integer representation of a card, e.g. 11
   *  @return String the string representation of a card, .e.g. "Jack"
   */	
  private String intToValue (int intVal) {
    // converts an int value into the String representation of the
    //  Card's value
    //i.e. Ace, "two", "three",... "Jack", "Queen", "King"
    String value = " ";
    switch (intVal) {
    case 1: value = "Ace"; break;
    case 2: value = "2"; break;
    case 3: value = "3"; break;
    case 4: value = "4"; break;
    case 5: value = "5"; break;
    case 6: value = "6"; break;
    case 7: value = "7"; break;
    case 8: value = "8"; break;
    case 9: value = "9"; break;
    case 10: value = "10"; break;
    case 11: value = "Jack"; break;
    case 12: value = "Queen"; break;
    case 13: value = "King"; break;
    }
    return value;
  }
  
/**
 * converts an int value into the String representation of the Card's suit
 *    i.e. "hearts", "diamonds", "clubs", "spades"
 *  @param intVal  the value of a card's suit, in the range 0-3 inclusive
 *  @return String the string representation of the suit, e.g. "hearts"
 */
private String intToSuit (int intVal) {
    String suit = " ";
    switch (intVal) {
    case 0: suit = "hearts"; break;
    case 1: suit = "diamonds"; break;
    case 2: suit = "clubs"; break;
    case 3: suit = "spades"; break;
    }
    return suit;
  }
  
  /**
   * Shuffles this Deck object. Swaps each card in the deck with a 
   * randomly chosen card.
   *
   */
  public void shuffle () {
    // randomizes the order of Cards in a Deck 
    for (int i = 51; i > 0 ; i--) {
      swap(this, i, randomNum(i));
    }
  }
  
    /**
     * returns a random int between 0 and n-1
     *  @param int n One greeater than the upper limit of the range of random numbers
     *  @return int a random number between 0 and n-1
     */
  private int randomNum (int n) {
  
    return (int)(Math.random() * n);
  }
    /**
     *  Exchanges the Cards at locations i and j of the Deck
     *  @input Deck D  the deck of cards
     *  @input int i one card to be swapped
     *  @input int j another card to be swapped
     */
  private void swap (Deck D, int i, int j) {

    Card temp = D.cards[i];
    D.cards[i] = D.cards[j];
    D.cards[j] = temp;
  }
  
  /**
   * Deals this Deck of cards to the two passed arrays.
   * Those arrays can be used later, in the caller class, 
   * to fill in stacks, queues, or other data structures, as needed.
   */
  public void dealCards (Card[] pile1, Card[] pile2) {
    for (int i = 0; i < 51; i = i + 2) {
      pile1[i/2] = cards[i];
      pile2[i/2] = cards[i+1];
    }
  }
  
  /**
   * returns the string representation of this Deck object
   * @return String the string representation of the Deck
   */
  public String toString() {
    String rep = "";
    for (int i=0; i<52; i++) 
      rep= rep + cards[i] + "\n";
    return rep;
  }
  
  /** 
   * Main allows us to test the methods in this Deck class
   */
  public static void main (String[] args) {
    Deck d = new Deck();
    d.shuffle();
    //System.out.println(d);
    
    //Test the dealCards(), with two arrays as inputs
    Card[] p1 = new Card[26];
    Card[] p2 = new Card[26];
    d.dealCards(p1, p2);
    System.out.println("First pile: ");
    for (int i=0; i<26; i++) {
      System.out.println(p1[i]);
    }
    System.out.println("*************************");
    System.out.println("Second pile: ");
    for (int i=0; i<26; i++) {
      System.out.println(p2[i]);
    }
  }
}
