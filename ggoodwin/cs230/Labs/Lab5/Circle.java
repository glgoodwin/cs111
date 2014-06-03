//Gabe Goodwin
// Lab 5 Inheritance
//10/5/2010

public class Circle extends Shape{
    //instance variables
    private double radius;
    private String name;

    //constructor
    public Circle(double radius){
	super("circle");
	this.radius = radius;
	
    }

    // instance methods
    public double  getRadius(){
	return radius;
    }
    public void setRadius(int radius){
	this.radius = radius;
    }

    public double area(){
	double pi = Math.PI;
	double area = (pi*(radius*radius));
	return area;
    }

    public double perimeter(){
	double pi = Math.PI;
	double perim = 2*(pi*radius);
	return perim;
    }


    public static void main(String[]args){
	Circle joe = new Circle(1);
	System.out.println(joe.getRadius());
	System.out.println(joe.perimeter());
	System.out.println(joe.area());
    }
	
	









}