package com.core.enu;

/**
 * 
 * @author devgosain
 *
 */
public class DemoEnum {

	public static void main(String[] args) {
		System.out.println(Month.JAN);
		Month jan = Month.valueOf("JAN");
		System.out.println(jan.getVal());
		
		Month[] months = Month.values();
		for (Month month : months) {
			System.out.println((month.ordinal() + 1) + month.getVal());
		}
	}
}

enum Month {
	
	JAN("jan") {
		@Override
		public String toString() {
			return "Custom : " + getVal();	
		}
	}, FEB("feb"), MAR("mar"), APR("apr"), MAY("may"), JUN("jun"), JUL("jul"),
	AUG("aug"), SEP("sep"), OCT("oct"), NOV("nov"), DEC("dec");
	
	//public error
	private Month (String s) {
		this.val = s;
	};
	
	@Override
	public String toString() {
		return "Default : " + getVal();	
	}
	
	private String val;

	public String getVal() {
		return val;
	}
}