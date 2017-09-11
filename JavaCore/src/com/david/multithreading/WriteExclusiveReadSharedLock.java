package com.david.multithreading;
 
/**
 * Exclusive write lock | Shared read lock
 * @author devgosain
 * 
 */
public class WriteExclusiveReadSharedLock {
	
	
	public static void main(String[] args) {
		
		MyRWLock lock = new MyRWLock();
		new Thread(new Writer(1, lock)).start();
		new Thread(new Writer(2, lock)).start();
		
		new Thread(new Reader(1, lock)).start();
		new Thread(new Reader(2, lock)).start();
		
	}

}

class MyRWLock {
	
	private int writers;
	private int readers;
	
	public synchronized void writeLock() {
		
		while(writers > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		writers++;
		while (readers > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void unlockWrite() {
		writers = 0;
		this.notifyAll();
	}
	
	public synchronized void readLock() {
		
		while(writers > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		readers ++;
	}
	
	public synchronized void unlockRead() {
		readers--;
		this.notifyAll();
	}
}

class Reader implements  Runnable {
	private int i;
	private MyRWLock lock;
	public Reader(int j, MyRWLock lock) {
		this.i=j;
		this.lock = lock;
	}
	public void run() {
		lock.readLock();
		System.out.println("Trying to read " + i);
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Done reading " + i);
		lock.unlockRead();
	}
}

class Writer implements  Runnable {
	private int i;
	private MyRWLock lock;
	public Writer(int j, MyRWLock lock) {
		this.i=j;
		this.lock = lock;
	}
	public void run() {
		lock.writeLock();
		System.out.println("Trying to write : " + i);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Done writing : " + i);
		lock.unlockWrite();
	}
}
