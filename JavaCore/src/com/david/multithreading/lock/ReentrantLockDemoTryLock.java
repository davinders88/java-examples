package com.david.multithreading.lock;
 
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemoTryLock {
	
	public static void main(String[] args) {
		
		ReentrantLock rRWL = new ReentrantLock();
		ReentrantLockDemoTryLock basicReentrantLock = new ReentrantLockDemoTryLock();
		new Thread(() -> {
			basicReentrantLock.print(rRWL, 1);
		}).start();
		
		new Thread(() -> {
			basicReentrantLock.print(rRWL, 2);
		}).start();
		
		
		new Thread(() -> {
			basicReentrantLock.print(rRWL, 3);
		}).start();
	
	}
	
	private void print(Lock wLock, int i) {
		//if (wLock.tryLock()) {
		try {
			if (wLock.tryLock(500, TimeUnit.MILLISECONDS)) {
				System.out.println("Write Accuired " + i);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Write done " + i);
				wLock.unlock();
			} 
//			else {
//				System.out.println("Could not acquire try lock " + i + ", Trying again");
//				print(wLock, i);
//			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
