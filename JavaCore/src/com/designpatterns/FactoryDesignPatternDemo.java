package com.designpatterns;

public class FactoryDesignPatternDemo {
	
	public static void main(String[] args) {
		
		System.out.println(ShapeFactory.getShape("RECT"));
		System.out.println(ShapeFactory.getShape("SQUA"));
	}
}

class ShapeFactory {
	
	public static Shape getShape(String type) {
		Shape shape = null;
		if ("RECT".equals(type)) {
			shape = new Rectangle();
		} else if ("SQUA".equals(type)) {
			shape = new Square();
		} 
		return shape;
	}
}

abstract class Shape {
	
	protected String shape;
	

	@Override
	public String toString() {
		return shape;
	}
	
}

class Rectangle extends Shape {

	public Rectangle() {
		shape = "Rectangle";
	}
}

class Square extends Shape {
	public Square() {
		shape = "Square";
	}
}
