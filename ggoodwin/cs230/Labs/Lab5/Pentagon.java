//Gabe Goodwin
// Lab 5 Inheritance
//10/5/2010

public class Pentagon extends Shape{
    //instance variables
    private double side;
    private String name;

    //constructor
    public Pentagon(double side){
	super("rectangle");
	this.side = side;
	
    }

    // instance methods
    public double getSide(){
	return side;
    }
    public void setSide(int side){
	this.side = side;
    }

    public double area(){
	double pi = Math.PI;
	double help = 1/(Math.tan(pi/5));
	double area = (5/4)*(side*side)*help;
	return area;
    }

    public double perimeter(){
	double perim = (5*side);
	return perim;
    }


    public static void main(String[]args){
	Pentagon joe = new Pentagon(2);
	System.out.println(joe.getSide());
	System.out.println(joe.perimeter());
	System.out.println(joe.area());
    }
	
	









}