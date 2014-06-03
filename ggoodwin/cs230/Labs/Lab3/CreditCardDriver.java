/**
 * FILE NAME: CreditCardDriver.java
 * AUTHOR: Java Foundations 
 * DATE: 
 * COMMENTS: CS230: Creating Objects Lab
 * A driver program for the CreditCard.java program
 *
 * MODIFICATION HISTORY:
 * Made it javadoc compliant.
 * Defined the helper static methods, which simplify main() and 
 * can possibly be used in other progarm(s).
 */

import java.util.Scanner;

public class CreditCardDriver {
  
  
    private static void askAndMakeAPayment(CreditCard c) {
    String ans = "y";
    Scanner scan = new Scanner(System.in);
    if (c.getBalance() > 0) {
      System.out.print("Would you like to make a payment (y/n)? ");
      ans = scan.next();
      if (ans.equalsIgnoreCase("y")) {
        System.out.print("Enter the payment amount: ");
        c.makePayment(scan.nextDouble());
      }
      //Raise the rate if the card holder misses a payment
      else {
        System.out.println("No payment made this month. Raising rate by 2%.");
        c.raiseRate(2);
      }
    } 
  }
  
  private static String askAndMakeAPurchase(CreditCard c) {
    String ans = "y";
    Scanner scan = new Scanner(System.in);
    System.out.print("Would you like to make a purchase (y/n)? ");
        ans = scan.next();
        if (ans.equalsIgnoreCase("y")) {
          System.out.print("Enter the purchase amount: ");
          c.makePurchase(scan.nextDouble());
        }
        return ans;
  }
  
  public static void main(String[] args) {
    String ans = "y";
    String userName = "";
    Scanner scan = new Scanner(System.in);
    System.out.print("We are happy to issue a Credit Card for you! What is your name? ");
    userName = scan.nextLine();
    CreditCard myCard = new CreditCard(userName, 490);
    System.out.println(myCard);
    
    
    //Simulate 3 months of purchases and payments
    for (int month = 1; month <= 3; month++) {
      //Let user make as many purchases per month as he/she likes
      do {
	  System.out.println("It is now month "+month+":");
       ans = askAndMakeAPurchase(myCard);
      } while(ans.equalsIgnoreCase("y"));
      
      //Allow user to make a payment if the balance is greater than 0
      askAndMakeAPayment(myCard);
      
      //Recalculate balance at the end of each month
      myCard.calculateBalance();
      
      System.out.println("\nEnd of month " + month + ":");
      System.out.println(myCard);
    }
  }
}
   
 
