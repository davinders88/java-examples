package com.big.size.data;

public class Demo {
	public static void main(String[] args) {
		System.out.println(new Demo().testCatchFinally());
	}
	public static void main(String e) {
		System.out.println(new Demo().testCatchFinally());
	}
	
	@SuppressWarnings("finally")
	public int testCatchFinally() {
		
		try {
			int i = 5/0;
		} catch (Exception e) {
			return 6;
		} finally {
			return 0;
		}
		
	}
}
