import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class: SharedCounterWithSharedDataLock
 * This example uses a shared data object containing the array and size.
 * Threads increment array elements protected by a lock.
 * Final values expected: array[i] == numThreads * i
 */
public class SharedCounterWithSharedDataLock {

    public static void main(String[] args) {

        SharedData sharedData = new SharedData();
        Lock lock = new ReentrantLock();

        int numThreads = 4;
        CounterThread[] threads = new CounterThread[numThreads];

        // Start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData, lock);
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

        checkArray(sharedData, numThreads);
    }

    /**
     * Check correctness of the array values.
     */
    static void checkArray(SharedData sData, int numThreads) {
        int errors = 0;

        System.out.println("Checking...");

        for (int i = 0; i < sData.end; i++) {
            if (sData.array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, sData.array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    /**
     * Thread that increments shared array elements using a lock.
     */
    static class CounterThread extends Thread {
        int threadId;
        SharedData data;
        Lock lock;

        public CounterThread(int threadId, SharedData sData, Lock lock) {
            this.threadId = threadId;
            this.data = sData;
            this.lock = lock;
        }

        public void run() {
            for (int i = 0; i < data.end; i++) {
                for (int j = 0; j < i; j++) {
                    lock.lock();
                    try {
                        data.array[i]++;
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }
    }
}

/**
 * Shared data structure containing the array and its size.
 */
class SharedData {
    int end;
    int[] array;

    public SharedData() {
        this.end = 1000;
        this.array = new int[end];
    }
}
