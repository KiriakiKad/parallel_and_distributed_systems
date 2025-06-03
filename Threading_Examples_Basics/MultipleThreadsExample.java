/*
 * MultipleThreadsExample.java
 *
 * This Java program extends the previous example by creating 10 threads from each of two different classes. 
 * Each thread prints a message when it starts running.
 *
 * Key Concepts:
 * - Creating multiple threads from different classes
 * - Using arrays to manage and control threads
 * - Controlling thread execution with `start()` and `join()`
 *
 * How to Compile and Run:
 * javac MultipleThreadsExample.java
 * java MultipleThreadsExample
 *
 * Sample Output (partial):
 * In main: create and start a thread
 * Hello from thread Thread-0 (MyThread1)
 * Hello from thread Thread-1 (MyThread1)
 * Hello from thread Thread-2 (MyThread1)
 * ...
 * Hello from thread Thread-19 (MyThread2)
 * In main: threads are done
 *
 * (Note: Output order may vary depending on thread scheduling.)
 */

public class MultipleThreadsExample { 
    public static void main(String[] args) {
    
        System.out.println("In main: create and start a thread ");
        
        // Create an array of 10 threads using class MyThread1
        MyThread1[] Thread1 = new MyThread1[10];
        
        // Create an array of 10 threads using class MyThread2
        MyThread2[] Thread2 = new MyThread2[10];

        // Start the 10 MyThread1 threads
        for(int i = 0; i < 10; i++) {
            Thread1[i] = new MyThread1();
            Thread1[i].start();
        }

        // Start the 10 MyThread2 threads
        for(int i = 0; i < 10; i++) {
            Thread2[i] = new MyThread2();
            Thread2[i].start();
        }

        try {
            // Wait for all 10 MyThread1 threads to finish
            for(int i = 0; i < 10; i++) {
                Thread1[i].join();
            }

            // Wait for all 10 MyThread2 threads to finish
            for(int i = 0; i < 10; i++) {
                Thread2[i].join();
            }
        }
        catch (InterruptedException e) {
        }

        System.out.println("In main: threads are done");
    }
}

/* Class containing the code that will be executed by the first set of threads */
class MyThread1 extends Thread {

    /* Code that runs when the thread starts */
    public void run() {
        // Print a message from the thread along with its name
        System.out.println("Hello from thread " + Thread.currentThread().getName() + " (MyThread1)");
    } 
}

/* Class containing the code that will be executed by the second set of threads */
class MyThread2 extends Thread {

    /* Code that runs when the thread starts */
    public void run() {
        // Print a message from the thread along with its name
        System.out.println("Hello from thread " + Thread.currentThread().getName() + " (MyThread2)");
    } 
}
