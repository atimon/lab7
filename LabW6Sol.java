/* CS118, Lab Week 6
   An Introduction to Object Oriented Programming
   Sara Kalvala, 2018.11
*/

import java.awt.Color;
import java.util.Scanner;

// Figure is abstract because some methods are abstract, ie there is no code given,
// only the type, which needs to be followed in subclasses

// As Color is protected, we create set and get methods for it, as the only way to change them.

abstract class Figure {
    protected Color color = Color.blue;
    public void setColor(Color c) {color = c;}
    public Color getColor() {return color;}
    public abstract double area();
    public abstract String toString();
    public abstract void print(int format);
}

// We create protected data fields below so that subclasses can access them directly
// (as opposed to private, where subclasses can't see them.)

// We do not *have* to create get/set methods, if we do not want other classes to have
// access to those data fields

class Triangle extends Figure {
    protected int side1;
    protected int side2;
    protected int side3;

    public Triangle(int s1, int s2, int s3) {
      side1 = s1;
      side2 = s2;
      side3 = s3;
    }

    public double area() {return ((side1 * side2)/2);}
    public String toString() {return ("triangle of hypothenuse "+side3);}
    public void print(int format){
        System.out.println("Working");
    }
}

class Rectangle extends Figure {

    protected int height;
    protected int width;

    public Rectangle(int h, int w) {
	height = h; width=w;}

    public double area() {return height * width;}
    public String toString() {return ("rectangle of height "+height);}
    public void print(int format){
        if (format == 1) {
            StdDraw.clear();
            StdDraw.setPenRadius(0.01);
            StdDraw.filledRectangle(0.5, 0.5, (this.width/20f), (this.height/20f));
        }
        else {
            StdDraw.clear();
            StdDraw.setPenRadius(0.01);
            StdDraw.rectangle(0.5, 0.5, (this.width/20f), (this.height/20f));
        }
    }
}


class Square extends Rectangle {

    protected int side;
    public Square(int s){ super(s,s); side = s;}
    public double area() {return side * side;}
    public String toString() {return ("a square of side "+side);}
}

// Because side in Square is protected, not private, we are able to access it directly
// in the subclass below using super.side, even though there is no get method.

class MutableSquare extends Square {
    public MutableSquare(int s){ super(s);}
    public void Resize (int x) {super.side = x;}
}

// Ellipse, Circle and MutableSquare are analogous to the above.

class Ellipse extends Figure {

    protected int axis1;
    protected int axis2;

    public Ellipse(int h, int w) {
	axis1 = h; axis2 = w;}

    public double area() {return Math.PI * axis1 * axis2;}
    public String toString() {return ("ellipse of axis "+ axis1 + " and "+ axis2);}
    public void print(int format){
        if (format ==  1) {
            StdDraw.clear();
            StdDraw.setPenRadius(0.01);
            StdDraw.filledEllipse(0.5, 0.5, (this.axis1/20f), (this.axis2/20f));
        }
        else {
            StdDraw.clear();
            StdDraw.setPenRadius(0.01);
            StdDraw.ellipse(0.5, 0.5, (this.axis1/20f), (this.axis2/20f));
        }
    }
}


class Circle extends Ellipse {

    protected int radius;
    public Circle(int s){ super(s,s); radius = s;}
    public double area() {return Math.PI * Math.pow(radius, 2);}
    public String toString() {return ("a circle of radius "+ radius);}
}


class MutableCircle extends Circle {
    public MutableCircle(int s){ super(s);}
    public void Resize (int x) {super.radius = x;}
}

public class LabW6Sol {

    public static void main(String[] args) {
	Scanner sc = new Scanner(System.in);
	// just initialized it to something to avoid errors;
	Figure fig =  new Circle(25);

	char command = args[0].charAt(0);

	if (command == 'i'){
		System.out.println("Enter shape and sizes or 'X' to exit");
		command = sc.next().charAt(0);
		while (command != 'x') {
		    switch (command) {
		    case 'r': fig = new Rectangle(sc.nextInt(),sc.nextInt()); break;
		    case 's': fig = new Square(sc.nextInt()); break;
            case 'e': fig = new Ellipse(sc.nextInt(),sc.nextInt()); break;
            case 'c': fig = new Circle(sc.nextInt()); break;
            case 't': fig = new Triangle(sc.nextInt(),sc.nextInt(),sc.nextInt()); break;
		    }
		    System.out.println(fig.toString());
            System.out.println("Choose the color of the shape");
            char clr = sc.next().charAt(0);
            switch (clr) {
                case 'b': StdDraw.setPenColor(StdDraw.BLUE); break;
                case 'r': StdDraw.setPenColor(StdDraw.RED); break;
                case 'p': StdDraw.setPenColor(StdDraw.PINK); break;
                case 'y': StdDraw.setPenColor(StdDraw.YELLOW); break;
                case 'w': StdDraw.setPenColor(StdDraw.WHITE); break;
                case 'g': StdDraw.setPenColor(StdDraw.GREEN); break;
                case 'o': StdDraw.setPenColor(StdDraw.ORANGE); break;
                default: StdDraw.setPenColor(StdDraw.BLACK); break;
            }
            System.out.println("Do you want the shape to be filled?");
            int decision = sc.next().charAt(0);
            int flld = (decision == 'y') ? 1 : 2;
            fig.print(flld);
		    System.out.println("Enter shape and sizes or X to exit");
		    command = sc.next().charAt(0);
		}
	}
	else {
	    switch (command){
	    case 'r': fig = new Rectangle(Integer.parseInt(args[1]),Integer.parseInt(args[2]));
		break;
	    case 's': fig = new Square(Integer.parseInt(args[1]));
		break;
	    }
	    System.out.println(fig.toString());
	}
    }


	/* Notice the slight trickery here: because we have a method defined
           in MutableSquare but not in Figure, we allow the same object to have
           two names with different types, so we can see an individual object as
	   a member of a class or a subclass as needed.
	MutableSquare f1 =   new MutableSquare(45);
	Figure f2 = f1;
	System.out.println(f2.toString());
	// Another way to print, allowing formatting of values
	System.out.format("and area %.2f.\n", f2.area());
	f1.Resize(25);
	System.out.println(f2.toString());
	// Preview of how we can use command line arguments
	if (args.length > 0) {
	    System.out.println(args[0]);}
    } */
}
