//Gabe Goodwin
// Lab 5 Inheritance
//10/5/2010

public class Rect extends Shape{
    //instance variables
    private double base;
    private double height;
    private String name;

    //constructor
    public Rect(double base, double height){
	super("rectangle");
	this.base = base;
	this.height = height;
	
    }

    // instance methods
    public double getBase(){
	return base;
    }
    public void setBase(int base){
	this.base = base;
    }

    public double area(){	
	double area = base*height;
	return area;
    }

    public double perimeter(){
	double perim = (2*base)+(2*height);
	return perim;
    }


    public static void main(String[]args){
	Rect joe = new Rect(2,4);
	System.out.println(joe.getBase());
	System.out.println(joe.perimeter());
	System.out.println(joe.area());
    }
	
	









}