package com.designpatterns;

public class AbstractFactoryDemo {
	public static void main(String[] args) {
		Factory factory = new EconomyFactory();
		System.out.println(factory.createFourWheeler());
		System.out.println(factory.createTwoWheeler());
		
		factory = new LuxuryFactory();
		System.out.println(factory.createFourWheeler());
		System.out.println(factory.createTwoWheeler());
	}
}

interface Factory {
	public TwoWheelerVahicle createTwoWheeler();
	public FourWheelerVahicle createFourWheeler();
}

class EconomyFactory implements Factory {

	@Override
	public TwoWheelerVahicle createTwoWheeler() {
		return new Splender();
	}

	@Override
	public FourWheelerVahicle createFourWheeler() {
		return new ALTO();
	}
}

class LuxuryFactory implements Factory {

	@Override
	public TwoWheelerVahicle createTwoWheeler() {
		return new HarleyDavidson();
	}

	@Override
	public FourWheelerVahicle createFourWheeler() {
		return new BMW();
	}
}


class Splender extends TwoWheelerVahicle {
	public Splender() {
		name = "Splender";
	}
}

class HarleyDavidson extends TwoWheelerVahicle {
	public HarleyDavidson() {
		name = "HarleyDavidson";
	}
}

class ALTO extends FourWheelerVahicle {
	public ALTO() {
		name = "ALTO";
	}
}

class BMW extends FourWheelerVahicle {
	public BMW() {
		name = "BMW";
	}
}

abstract class TwoWheelerVahicle {
	protected int noOfWheels = 2;
	protected String name;
	@Override
	public String toString() {
		return "Product [name=" + name + "]";
	}
}

abstract class FourWheelerVahicle {
	protected int noOfWheels = 4;
	protected String name;
	@Override
	public String toString() {
		return "Product [name=" + name + "]";
	}
}

