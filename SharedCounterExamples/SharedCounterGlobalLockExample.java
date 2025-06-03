import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class: SharedCounterGlobalLockExample
 * Simple global static array example.
 * Multiple threads increment each array element i times, with locking to prevent race conditions.
 * Expected final value: array[i] = numThreads * i
 */
public class SharedCounterGlobalLockExample {

    static int end = 1000;
    static int[] array = new int[end];
    static Lock lock = new ReentrantLock();
    static int numThreads = 4;

    public static void main(String[] args) {

        CounterThread[] threads = new CounterThread[numThreads];

        // Start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }

        // Wait for completion
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        checkArray();
    }

    /**
     * Check correctness of array values.
     */
    static void checkArray() {
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
     * Thread increments elements safely with a lock.
     */
    static class CounterThread extends Thread {

        public CounterThread() {
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
