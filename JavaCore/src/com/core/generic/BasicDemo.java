package com.core.generic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BasicDemo {
	
	public static void main(String[] args) throws CloneNotSupportedException {
		
		//can not add to list with extend bound
		List<? extends Integer> list = Arrays.asList(1, 2, 3);
		//list.add(3); //error as compiler is not sure if the list is of integers or long or other type
		Integer num =  list.get(0);
		System.out.println(num);
		
		//can add with super bound
		List<? super Integer> list1 = Arrays.asList(1, 2, 3);
		//list1.add(1);// add is allowed but giving exception at run time.
		//list1.add(1);
		System.out.println(list1);
		
		List<Object> listOfObjects = new ArrayList<>();
		listOfObjects.add(new BasicDemo());
		listOfObjects.add("test");
		System.out.println(listOfObjects);
		
		Test t = new Test();
		Test t1 = (Test) t.clone();
		System.out.println(t == t1);
		System.out.println(t.getVal().equals(t1.getVal()));
	}

}

class Test implements Cloneable {
	private String val = "Davinder";
	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}
	
}
