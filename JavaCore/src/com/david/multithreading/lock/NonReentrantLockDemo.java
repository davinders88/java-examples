package com.david.multithreading.lock;
 
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Even the same thread cann't re-acquire the lock. 
 * If thread A acquire a lock then again try to acquire it again, it will be blocked
 * @author devgosain
 *
 */
public class NonReentrantLockDemo {

	Semaphore semaphore = new Semaphore(3);

	public static void main(String[] args) {

		NonReentrantLockDemo nRLD = new NonReentrantLockDemo();
		new Thread(() -> {
			nRLD.print(nRLD.semaphore, 1);
		}).start();

		new Thread(() -> {
			nRLD.print(nRLD.semaphore, 2);
		}).start();

		new Thread(() -> {
			nRLD.print(nRLD.semaphore, 3);
		}).start();
		
		new Thread(() -> {
			nRLD.print(nRLD.semaphore, 4);
		}).start();
		
		new Thread(() -> {
			nRLD.print(nRLD.semaphore, 5);
		}).start();
	}

	private void print(Semaphore wLock, int i) {
		boolean acquired = false;
		try {
			acquired = wLock.tryAcquire(1, TimeUnit.SECONDS);
			
			if (acquired) {
				System.out.println("Write Accuired " + i);
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Write done " + i);
				
				//will block infinitely if a thread try to re-acquire the lock it already has
				//print(wLock, i); 
			}
			
		} catch (InterruptedException e1) {
		} finally {
			if (acquired) {
				System.out.println("Releasing " + i);
				wLock.release();
			}
		}

	}
	

}
