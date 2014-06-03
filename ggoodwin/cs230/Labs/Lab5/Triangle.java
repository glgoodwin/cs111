//Gabe Goodwin
// Lab 5 Inheritance
//10/5/2010

public class Triangle  extends Shape{
    //instance variables
    private double side1;
    private double side2;
    private double side3;
    private String name;

    //constructor
    public Triangle(double side1, double side2, double side3){
	super("triangle");
	this.side1 = side1;
	this.side2 = side2;
	this.side3 = side3;
	
    }

    // instance methods
    public double getSide1(){
	return side1;
    }
    public void setSide1(int side1){
	this.side1 = side1;
    }

    public double area(){
	double perim = side1 + side2 + side3;
	double p = perim/2;
	double area =(Math.sqrt(p*(p-side1)*(p-side2)*(p-side3)));
	return area;
    }

    public double perimeter(){
	double perim = side1 + side2 +side3;
	return perim;
    }


    public static void main(String[]args){
	Triangle joe = new Triangle(4,4,4);
	System.out.println(joe.getSide1());
	System.out.println(joe.perimeter());
	System.out.println(joe.area());
    }
	
	









}