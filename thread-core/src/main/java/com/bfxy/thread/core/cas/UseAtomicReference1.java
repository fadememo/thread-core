package com.bfxy.thread.core.cas;

public class UseAtomicReference1 {

	private static Person person;
	
	public static void main(String[] args) throws InterruptedException {
	    person = new Person("Tom", 18);

	    System.out.println("Person is " + person.toString());

	    Thread t1 = new Thread(new Task1());
	    Thread t2 = new Thread(new Task2());

	    t1.start();
	    t2.start();
	    t1.join();
	    t2.join();
	    
	    Thread.sleep(100);
	    System.out.println("Now Person is " + person.toString());
	}

	static class Task1 implements Runnable {
	    public void run() {
	        person.setAge(19);
	        person.setName("Tom1");

	        System.out.println("Thread1 Values "
	                + person.toString());
	    }
	}

	static class Task2 implements Runnable {
	    public void run() {
	        person.setAge(20);
	        person.setName("Tom2");

	        System.out.println("Thread2 Values "
	                + person.toString());
	    }
	}
}
