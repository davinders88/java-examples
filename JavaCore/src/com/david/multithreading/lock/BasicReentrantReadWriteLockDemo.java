package com.david.multithreading.lock;
 
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

@SuppressWarnings("unused")
public class BasicReentrantReadWriteLockDemo {
	public static void main(String[] args) {
		
		final ReentrantReadWriteLock rRWL = new ReentrantReadWriteLock();
		WriteLock wLock = rRWL.writeLock();
		//ReadLock wLock = rRWL.readLock();
		new Thread(() -> {
			print(wLock, 1);
		}).start();
		
		new Thread(() -> {
			print(wLock, 2);
		}).start();
		
		
		new Thread(() -> {
			print(wLock, 3);
		}).start();
		
		
	}

	private static boolean flag = true;
	private static void print(Lock wLock, int i) {
		wLock.lock();
		System.out.println("Write Accuired " + i);
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Write done " + i);
		
		//to prove re-entrancy
		if (flag) {
			flag = false;
			print(wLock, i);
		}
		
		wLock.unlock();
	}
}
