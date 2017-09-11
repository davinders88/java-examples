package com.david.multithreading;
 
/**
 * @author devgosain
 *
 */
public class PrintOddEvenSynchronization {
	public static void main(String[] args) {
		int start = 1;
		Printer printer = new Printer();
		Thread odd = new Thread(new OddEvenTask(start, true, printer));
		Thread even = new Thread(new OddEvenTask(start + 1, false, printer));
		odd.start();
		even.start();
	}
}

class OddEvenTask implements Runnable {
	private Printer printer;
	private boolean isOdd;
	private int currentValue;
	private int max = 10;
	
	public OddEvenTask(int currentValue, boolean isOdd, Printer printer) {
		this.printer = printer;
		this.isOdd = isOdd;
		this.currentValue = currentValue;
	}

	@Override
	public void run() {
		while (currentValue <= max) {
			if (isOdd) {
				printer.printOdd(currentValue);
			} else {
				printer.printEven(currentValue);
			}
			currentValue += 2;
		}
	}
}

class Printer {
	private boolean isOdd = true;
	public synchronized void printOdd(int currentValue) {
		try {
		while (!isOdd) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Odd : " + currentValue);
		isOdd = false;
		notify();
		} catch (Exception e) {
			System.out.println("Exception occured");
		}
	}
	
	public synchronized void printEven(int currentValue) {
		try {
		while (isOdd) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("even : " + currentValue);
		isOdd = true;
		notify();
		} catch(Exception e) {
			System.out.println("Exception occured");
		}
	}
}
