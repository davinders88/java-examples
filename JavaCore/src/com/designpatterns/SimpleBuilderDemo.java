package com.designpatterns;

public class SimpleBuilderDemo {
	
	public static void main(String[] args) {
		SimpleBuilder sb = new SimpleBuilder();
		sb.buildAddress("Cyber City").buildAge(12).buildName("Davinder Singh").buildComp("MMT");
		System.out.println(sb);
		
	}

}

class SimpleBuilder {

	private String name;
	private int age;
	private String address;
	private String comp;

	public SimpleBuilder buildComp(String comp) {
		this.comp = comp;
		return this;
	}

	public SimpleBuilder buildAddress(String address) {
		this.address = address;
		return this;
	}

	public SimpleBuilder buildAge(int age) {
		this.age = age;
		return this;
	}

	public SimpleBuilder buildName(String name) {
		this.name = name;
		return this;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getAddress() {
		return address;
	}

	public String getComp() {
		return comp;
	}

	@Override
	public String toString() {
		return "SimpleBuilder [name=" + name + ", age=" + age + ", address=" + address + ", comp=" + comp + "]";
	}
}
