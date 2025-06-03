import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class: SharedCounterWithLockAndWhile
 * This example uses a shared counter protected by a lock.
 * Threads get unique indices from the counter and increment the array once per index.
 * Each array element should be incremented exactly once.
 */
public class SharedCounterWithLockAndWhile {

    public static void main(String[] args) {

        int end = 10000;
        int[] array = new int[end];
        SharedCounter counter = new SharedCounter();
        Lock lock = new ReentrantLock();

        int numThreads = 4;
        CounterThread[] threads = new CounterThread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end, array, counter, lock);
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Interrupted, ignore
            }
        }

        checkArray(end, array);
    }

    /**
     * Check that each element of array has been incremented exactly once.
     */
    static void checkArray(int end, int[] array) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    /**
     * Thread that gets unique index from a shared counter and increments the array at that index.
     */
    static class CounterThread extends Thread {

        int end;
        int[] array;
        SharedCounter counter;
        Lock lock;

        public CounterThread(int end, int[] array, SharedCounter counter, Lock lock) {
            this.end = end;
            this.array = array;
            this.counter = counter;
            this.lock = lock;
        }

        public void run() {
            while (true) {
                int index = counter.get();
                if (index >= end) {
                    break;
                }
                // No need to lock array access because each index is unique per thread
                array[index]++;
                counter.inc();
            }
        }
    }
}

/**
 * SharedCounter class encapsulates an integer counter protected by a Lock
 */
class SharedCounter {
    int n;
    Lock lock = new ReentrantLock();

    public SharedCounter() {
        this.n = 0;
    }

    public int get() {
        lock.lock();
        try {
            return n;
        } finally {
            lock.unlock();
        }
    }

    public void inc() {
        lock.lock();
        try {
            n = n + 1;
        } finally {
            lock.unlock();
        }
    }
}
