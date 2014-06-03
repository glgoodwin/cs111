//Gabrielle Goodwin
//Lab 5

public class Tip{ //determines the total bill with tip 
     public double convert (double dollars)
     { double totalBill = dollars * 1.2;
          return totalBill;
     }
     public static void main (String [] args)
     {    double bill= 100.00; 
          Tip tip = new Tip();
          double totalBill = tip.convert(bill);
          System.out.print( "your total bill is " + bill + "." + "With tip is " + totalBill + ".");
                           }
               }