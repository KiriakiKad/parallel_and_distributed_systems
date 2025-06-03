/*
 * CustomThreadsExample.java
 *
 * This Java program demonstrates how to create and run two different threads using two separate classes. 
 * Each class extends `Thread` and overrides the `run()` method independently.
 *
 * Key Concepts:
 * - Creating and starting threads with `Thread` subclasses
 * - Overriding the `run()` method
 * - Using `start()` and `join()` for thread execution control
 * - Multithreading basics in Java
 *
 * How to Compile and Run:
 * javac CustomThreadsExample.java
 * java CustomThreadsExample
 *
 * Sample Output:
 * In main: create and start a thread
 * Hello from thread Thread-0 (MyThread1)
 * Hello from thread Thread-1 (MyThread2)
 * In main: threads are done
 *
 * (Note: Output order may vary depending on thread scheduling.)
 */

public class CustomThreadsExample {
    public static void main(String[] args) {
    
        System.out.println("In main: create and start a thread ");
        
        // Create a new thread using class MyThread1
        MyThread1 Thread1 = new MyThread1();
        
        // Create another thread using class MyThread2
        MyThread2 Thread2 = new MyThread2();

        // Start the first thread
        Thread1.start();  

        // Start the second thread
        Thread2.start(); 

        try {
            // Wait for the first thread to finish
            Thread1.join(); 

            // Wait for the second thread to finish
            Thread2.join(); 
        }
        catch (InterruptedException e) {
        }

        System.out.println("In main: threads are done");
    }
}

/* Class containing the code that will be executed by the first thread */
class MyThread1 extends Thread {

    /* Code that runs when the thread starts */
    public void run() {
    
        // Print a message from the thread along with its name
        System.out.println("Hello from thread " + Thread.currentThread().getName() + " (MyThread1)");
    } 
}

/* Class containing the code that will be executed by the second thread */
class MyThread2 extends Thread {

    /* Code that runs when the thread starts */
    public void run() {
    
        // Print a message from the thread along with its name
        System.out.println("Hello from thread " + Thread.currentThread().getName() + " (MyThread2)");
    } 
}
