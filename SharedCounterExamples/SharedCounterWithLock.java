import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class: SharedCounterWithLock
 * This example shows a global shared array updated by multiple threads.
 * Each thread increments array[i] i times under a ReentrantLock to avoid race conditions.
 * The final expected value at array[i] is numThreads * i.
 */
public class SharedCounterWithLock {

    public static void main(String[] args) {

        int end = 1000;
        int[] array = new int[end];
        Lock lock = new ReentrantLock();

        int numThreads = 4;
        CounterThread[] threads = new CounterThread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end, array, lock);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Interrupted, ignore
            }
        }

        checkArray(end, array, numThreads);
    }

    /**
     * Check correctness of array increments.
     * Expected array[i] = numThreads * i
     */
    static void checkArray(int end, int[] array, int numThreads) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            if (array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    /**
     * Thread class that increments array elements safely using a Lock.
     */
    static class CounterThread extends Thread {
        int end;
        int[] array;
        Lock lock;

        public CounterThread(int end, int[] array, Lock lock) {
            this.end = end;
            this.array = array;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++) {
                    lock.lock();
                    try {
                        array[i]++;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }
}
