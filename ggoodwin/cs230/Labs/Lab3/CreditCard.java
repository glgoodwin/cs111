/*Gabe Goodwin
  Sept. 21 2010
  Lab 3 Credit Card
*/


public class CreditCard{
    // Static Vaiables
    private static double  baseRate = 5.5;
    private static int lastAccountNumber = 1234567;

    //Instance Variables
    public String accountHolder;
    public int accountNumber;
    public int creditScore;
    public double rate;
    public double balance;
    public int creditLimit;


    //Constructor
    /**
       Makes a new credit card account using the specified information and the
       table given.
       @param accountHolder  The name of the credit card holder
       @param creditScore    The credit score of the card holder
    */
       
    public CreditCard(String accountHolder,int creditScore){
	accountNumber = lastAccountNumber+1;
	lastAccountNumber = lastAccountNumber+1 ;   
	this.accountHolder= accountHolder; 
	this.creditScore = creditScore;
	balance = 0;
	if(creditScore<300){
	    rate =(baseRate+(baseRate*.1));
	    creditLimit = 1000;
	}else if(creditScore<500){
	    rate = (baseRate+(baseRate*.07));
	    creditLimit = 3000;
	}else if(creditScore<700){
	    rate = (baseRate+(baseRate*.04));
	    creditLimit = 7000;
	}else{
	    rate = (baseRate+(baseRate*.01));
	    creditLimit = 15000;
	}
    }

    public String toString(){
	String result = "";
	result += "Account Holder: " +getAccountHolder()+ "\n";
	result += "Account Number: " +getAccountNumber()+"\n";
	result += "Credit Score: " +getCreditScore()+ "\n";
	result += "Rate: " +getRate()+ "\n";
      	result += "Balance: " +getBalance()+ "\n";
      	result += "Credit Limit: " +getCreditLimit()+ "\n";
      	result += "Base Rate: " +getBaserate()+ "\n";
      	result += "Last Account Number: " +getLastAccountNumber()+ "\n";
	return result;
    }
    //Instance Methods
    public String getAccountHolder(){
	return accountHolder;
    }

    public int getAccountNumber(){
	return accountNumber;
    }

    public int getCreditScore(){
	return creditScore;
    }

    public double getRate(){
	return rate;
    }

    public double getBalance(){
	return balance;
    }

    public int getCreditLimit(){
	return creditLimit;
    }

    public void setCreditScore(int creditScore){
	this.creditScore= creditScore;
    }

    //Class Methods
    public static double getBaserate(){
	return baseRate;
    }

    public static int getLastAccountNumber(){
	return lastAccountNumber;
    }

    //methods
    public void makePurchase(double amount){
	balance = balance+amount;
	if(balance>creditLimit){
	    System.out.println("Your transaction has been denied");
	} }

    public void makePayment(doubel payment){
    	if(payment>=balance){
	    balance = 0;
	    System.out.println("You have paid off your credit card! Hooray!");
	}else if(payment<(balance+(balance*.1)){
		     balance = balance - payment;
		     rate = rate+(rate*.01);
		     System.out.println("Your credit rate has been raised by 1% because of your low payment");
		 }
		 if(balance = 0){
		     creditScore = creditScore +10;
		     setCreditScore(creditScore);
		    	if(creditScore<300){
	    rate =(baseRate+(baseRate*.1));
	    creditLimit = 1000;
	}else if(creditScore<500){
	    rate = (baseRate+(baseRate*.07));
	    creditLimit = 3000;
	}else if(creditScore<700){
	    rate = (baseRate+(baseRate*.04));
	    creditLimit = 7000;
	}else{
	    rate = (baseRate+(baseRate*.01));
	    creditLimit = 15000;
			}// Working on changing the credit rate. ..
		     
	
    }
    
    public static void main(String[]args){
	CreditCard Gabe = new CreditCard("Gabe",600);
	System.out.println(Gabe);
	Gabe.makePurchase(200);
	Gabe.makePurchase(10000);





    }
	



}