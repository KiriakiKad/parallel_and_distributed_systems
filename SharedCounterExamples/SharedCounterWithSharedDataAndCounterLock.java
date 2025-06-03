import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class: SharedCounterWithSharedDataAndCounterLock
 * Threads use a SharedCounter with getAndInc() method to get unique indices.
 * Each thread increments array[index] once.
 * SharedCounter uses one lock for atomic getAndInc.
 */
public class SharedCounterWithSharedDataAndCounterLock {

    public static void main(String[] args) {

        SharedCounter counter = new SharedCounter();
        SharedData sharedData = new SharedData();
        Lock lock = new ReentrantLock();

        int numThreads = 4;
        CounterThread[] threads = new CounterThread[numThreads];

        // Start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData, counter, lock);
            threads[i].start();
        }

        // Join threads
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Ignore
            }
        }

        checkArray(sharedData);
    }

    /**
     * Check that every element of array has value 1.
     */
    static void checkArray(SharedData sData) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < sData.end; i++) {
            if (sData.array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, sData.array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    /**
     * Thread that increments array elements using unique indices from SharedCounter.
     */
    static class CounterThread extends Thread {
        int threadId;
        SharedData data;
        Lock lock;
        SharedCounter counter;

        public CounterThread(int threadId, SharedData sData, SharedCounter counter, Lock lock) {
            this.threadId = threadId;
            this.data = sData;
            this.counter = counter;
            this.lock = lock;
        }

        public void run() {
            int index;
            // getAndInc() uses a single lock for atomic get and increment, so no race condition.
            while ((index = counter.getAndInc()) < data.end) {
                data.array[index]++;
            }
        }
    }
}

/**
 * Shared data class holding the array and its size.
 */
class SharedData {
    int end;
    int[] array;

    public SharedData() {
        this.end = 1000;
        this.array = new int[end];
    }
}

/**
 * Shared counter class with atomic getAndInc using a Lock.
 */
class SharedCounter {
    int n;
    Lock lock = new ReentrantLock();

    public SharedCounter() {
        this.n = 0;
    }

    public int getAndInc() {
        lock.lock();
        try {
            return n++;
        } finally {
            lock.unlock();
        }
    }
}
