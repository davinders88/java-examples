package com.designpatterns;

public class FactoryMethodDemo {
	public static void main(String[] args) {
		
		ShapeCreator sc = new SquareCreator();
		System.out.println(sc.coloredShape());
		
		ShapeCreator sc1 = new RectangleCreator();
		System.out.println(sc1.coloredShape());
	}
}

abstract class ShapeCreator {
	public abstract Shape create();
	
	public Shape coloredShape() {
		Shape shape = create();
		shape.shape += " : Colored"; // general processing common to subclasses
		return shape;
	}
	
}

class SquareCreator extends ShapeCreator {
	@Override
	public Shape create() {
		return new Square();
	}
	
}

class RectangleCreator extends ShapeCreator {
	@Override
	public Shape create() {
		return new Rectangle();
	}
	
}

