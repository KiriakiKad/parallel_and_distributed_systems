/*
 * MultiplesThreaded.java
 *
 * This Java program creates 10 threads.
 * Each thread calculates and prints the first 20 multiples of its own number (from 1 to 10).
 * For example, thread for number 3 will print:
 *   1*3=3
 *   2*3=6
 *   ...
 * 
 * Since threads run concurrently, the output may appear interleaved due to thread scheduling.
 *
 * How to Compile and Run:
 * javac MultiplesThreaded.java
 * java MultiplesThreaded
 */

/*
As observed, the output of the threads is not in a logical sequence but rather mixed up.
This happens due to thread scheduling and the non-deterministic execution of threads.
Scheduling determines which thread runs at any moment, and the OS can decide
which thread will be active at any given time.
Therefore, threads can be interrupted and resumed out of order.
Also, non-deterministic execution means there’s no fixed sequence
in which threads execute their routines.
*/
public class MultiplesThreaded {
    public static void main(String[] args) {

        // Define number of threads
        int numThreads = 10;
        
        // Create an array to hold the thread objects
        Thread[] thread = new Thread[numThreads];
    
        // Create and start the threads
        for (int i = 0; i < numThreads; ++i) {
            System.out.println("In main: create and start thread " + i);
            
            // Create new thread using class MyThread
            thread[i] = new MyThread(i + 1);
            
            // Start the thread
            thread[i].start();
        }
        
        // Wait for all threads to finish before continuing
        for (int i = 0; i < numThreads; ++i) {
            try {
                // join() causes main thread to wait for each thread to finish
                thread[i].join();
            }
            catch (InterruptedException e) {
            }
        }

        // Print when all threads have finished
        System.out.println("In main: threads all done");
    }
}

// Class containing the code executed by each thread
class MyThread extends Thread {

    private int number; // number used to compute the multiples

    public MyThread(int number){
        this.number = number;
    }

    // Code to be executed when the thread starts
    public void run() {
        // Print the first 20 multiples of 'number'
        for(int i = 1; i <= 20; i++){
            int mult = i * number;
            System.out.println(i + "*" + number + "=" + mult);
        }
        System.out.println();
    } 
}


