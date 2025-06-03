/*
 * MultiplesThreadedSequential.java
 *
 * This is a variation of the previous program.
 * It creates and executes threads one at a time — each thread starts, finishes, and then the next begins.
 *
 * As a result, the output is clean and ordered (no interleaving between threads).
 *
 * How to Compile and Run:
 * javac MultiplesThreadedSequential.java
 * java MultiplesThreadedSequential
 */
/*
In this version, each thread executes sequentially.
Its output completes before the next thread starts,
so the output follows a logical order.
The key addition is the ThreadExecute() method,
which creates a thread and waits for it to finish before continuing.
*/

public class  MultiplesThreadedSequential {

    public static void main(String[] args) {

        // Define number of threads
        int numThreads = 10;
        
        // Execute each thread one at a time
        for(int i = 0; i < numThreads; i++){
            System.out.println("In main: create and start thread " + i);
            ThreadExecute(i); // call method to execute thread
        }

        System.out.println("In main: threads all done");
    }

    public static void ThreadExecute(int num){
        MyThread thread = new MyThread(num);
        thread.start();
        {
            try {
                // wait for the thread to finish
                thread.join();
            }
            catch (InterruptedException e) {
            }
        }
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

