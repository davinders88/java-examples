package com.david.multithreading;
 
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BasicThreadExample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		
		//mainAndChildThreadsHW();
	//	mainAndChildThreadsUsingThreadPool_Runnable();
		//mainAndChildThreadsUsingThreadPool_Callable();
		mainAndChildThreadsUsingThreadPool_CompleatableFuture();
	//	compleatableFuture_UsingLatchToWaitForTasksToComplelet();
	}
	
	private static void compleatableFuture_UsingLatchToWaitForTasksToComplelet() throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(1);
		CountDownLatch cdl = new CountDownLatch(2);
		CompletableFuture.supplyAsync(() -> {
			System.out.println("compfuture async task : " + Thread.currentThread().getName());
			try {
				Thread.sleep(5000);
				throw new InterruptedException();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				cdl.countDown();
			}
			return "Child thread COMP_FUTURE : " + Thread.currentThread().getName();
		},pool).thenAccept((t) -> {
			try {
				Thread.sleep(2500);
				cdl.countDown();
			} catch (InterruptedException e) {	}
			System.out.println(t + " Then accept Called");
		});
		
		System.out.println("Parent thread : " + Thread.currentThread().getName());
		cdl.await(10, TimeUnit.SECONDS);
		pool.shutdown();
		System.out.println("=================================================================");
		
	}
	
	private static void mainAndChildThreadsUsingThreadPool_CompleatableFuture() throws InterruptedException {
		ExecutorService pool = Executors.newFixedThreadPool(1);
		
		CompletableFuture.supplyAsync(() -> {
			System.out.println("compfuture async task : " + Thread.currentThread().getName());
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return "Child thread COMP_FUTURE : " + Thread.currentThread().getName();
		},pool).thenAccept((t) -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {	}
			System.out.println(t + " Then accept Called");
		});
		
		System.out.println("Parent thread : " + Thread.currentThread().getName());
		pool.shutdown();
		pool.awaitTermination(1, TimeUnit.SECONDS);
		System.out.println("=================================================================");
		
	}

	/**
	 * Callable returns Future<ReturnType>
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	private static void mainAndChildThreadsUsingThreadPool_Callable() throws InterruptedException, ExecutionException {
		ExecutorService pool = Executors.newFixedThreadPool(1);
//		Callable<String> callable = new Callable<String>() {
//			@Override
//			public String call() throws Exception {
//				Thread.sleep(5000);
//				return "Child thread callable : " + Thread.currentThread().getName();
//			}
//		};
		Future<String> future = pool.submit(()->{
			Thread.sleep(500);
			return "Child thread callable : " + Thread.currentThread().getName();
		});
		
		System.out.println(future.get());
		
		System.out.println("Parent thread : " + Thread.currentThread().getName());
		
		pool.shutdown();
		System.out.println("=================================================================");
		
	}

	/**
	 * Runnable does not return anything
	 */
	private static void mainAndChildThreadsUsingThreadPool_Runnable() {
		ExecutorService pool = null;
		pool = Executors.newFixedThreadPool(1);
		pool.submit(() -> {
					System.out.println("mainAndChildThreadsUsingThreadPool_Runnable : Child thread runnable: " + Thread.currentThread().getName());
		});
		
		System.out.println("mainAndChildThreadsUsingThreadPool_Runnable Parent thread : " + Thread.currentThread().getName());

		pool.shutdown();
		
		System.out.println("=================================================================");
	}

	private static void mainAndChildThreadsHW() {
		new Thread(
				() -> {
					System.out.println("mainAndChildThreadsHW : Child thread : " + Thread.currentThread().getName());
				}
		).start();
		
		System.out.println("mainAndChildThreadsHW : Parent thread : " + Thread.currentThread().getName());
		
		System.out.println("=================================================================");
	}
}
