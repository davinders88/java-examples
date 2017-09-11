package com.david.multithreading;
 
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ProducerConsumer {
	private static Queue<Integer> taskList = new ConcurrentLinkedQueue<>();

	public static void main(String[] args) {
		Thread prod = new Thread(new Producer(taskList));
		Thread consumer = new Thread(new Consumer(taskList));
		prod.start();
		consumer.start();
	}
}

class Producer implements Runnable {
	private Queue<Integer> taskList;

	public Producer(Queue<Integer> taskList) {
		this.taskList = taskList;
	}

	@Override
	public void run() {
		int count = 0;
		while (count < 100) {
			while (taskList.size() == 20) {
				try {
					synchronized (taskList) {
						taskList.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			taskList.add(count);
			System.out.println("Number produced : " + count);
			count++;
			synchronized (taskList) {
				taskList.notify();
			}
		}
	}
}

class Consumer implements Runnable {
	private Queue<Integer> taskList;

	public Consumer(Queue<Integer> taskList) {
		this.taskList = taskList;
	}

	@Override
	public void run() {
		int count = 0;
		while (count < 100) {
			while (taskList.isEmpty()) {
				try {
					synchronized (taskList) {
						taskList.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Number consumed " + taskList.poll());
			count++;
			synchronized (taskList) {
				taskList.notify();
			}
		}
	}
}