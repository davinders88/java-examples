package com.david.java8;

/**
 * @FunctionalInterface, default methods and static methods in interface.
 * INTERFACE.super.METHOD syntax demo
 * @author devgosain
 *
 */
public class FunctionalInterfacesDemo {

	public static void main(String[] args) {
		
		//demo of lambda use, functional interface
		new FunctionalInterfacesDemo().getGoal(() -> {
			System.out.println("Dad's goal");
		});
		
		//demo for implementing multiple functional interfaces with default method diamond problem scenario
		//understanding INTERFACE.super.METHOD syntax
		new Child().hasMoney();
		
	}

	private void getGoal(Dad dad) {
		dad.hasGoal();
	}
	
}



class  Child implements Dad, Mom {

	@Override
	public void hasGoal() {
		System.out.println("Child's goal");
	}

	//<TerfaceName>.super.methodeName : here super signifies which  default method for parent interface to call.
	// Suppose an interface in extending/implementing two other interface, the both interfaces has same default method. In this case
	// we need to override the default method. If from this overridden default method we want to call the default method
	// of one of the super interfaces we need to use super keyword with interface name on left side of super and method name
	//on right side of super as shown in below example TestFunc.super.testdefault();
	@Override
	public void hasMoney() {
		Dad.super.hasMoney();
		Dad.hasHouse();
		Mom.super.hasMoney();
		Mom.hasHouse();
	}

} 


//keyword abstract is optional in interface and in method declaration 
//static and default methods of interface are not forced to implement in implementation class until there is  diamond problem
@FunctionalInterface
abstract interface Mom {
	public abstract void hasGoal();
	
	default void hasMoney() {
		System.out.println("Mom's money");
	}
	
	static void hasHouse() {
		 System.out.println("Mom's house");
	}
}

@FunctionalInterface
abstract interface Dad {
	public abstract void hasGoal();
	
	 default void hasMoney() {
		 System.out.println("Dad's money");
	 }
	 
	 static void hasHouse() {
		 System.out.println("Dad's house");
	}
}
