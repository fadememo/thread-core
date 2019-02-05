package com.bfxy.thread.core.cas;

import java.util.concurrent.atomic.AtomicReference;

public class UseAtomicReference2 {
	// 普通引用
	private static Person person;
	// 原子性引用
	private static AtomicReference<Person> aRperson;

	public static void main(String[] args) throws InterruptedException {
	    person = new Person("Tom", 18);
	    aRperson = new AtomicReference<Person>(person);

	    System.out.println("Atomic Person is " + aRperson.get().toString());

	    Thread t1 = new Thread(new Task1());
	    Thread t2 = new Thread(new Task2());

	    t1.start();
	    t2.start();
	    t1.join();
	    t2.join();
	    
	    Thread.sleep(500);
	    System.out.println("Now Atomic Person is " + aRperson.get().toString());
	}

	static class Task1 implements Runnable {  
	    public void run() {
	    	System.err.println("ret = " + 
	    				// C A S 原子操作
	    				aRperson.compareAndSet(  //10ms 
	    					aRperson.get(), 
	    					new Person("Tom", aRperson.get().getAge() + 1)
	    			));
	        System.out.println("Thread1 Atomic References "
	                + aRperson.get().toString());
	    }
	}

	static class Task2 implements Runnable {
	    public void run() {
	    	System.err.println("ret = " + 
	    			aRperson.compareAndSet(		//8ms
	    					aRperson.get(), 
	    					new Person("Tom", aRperson.get().getAge() + 2)
	    			));
	        System.out.println("Thread2 Atomic References "
	                + aRperson.get().toString());
	    }
	}
}
