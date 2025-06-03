import java.util.concurrent.locks.ReentrantLock;

/**
 *FineGrainedLockingExample
 * 
 * This class demonstrates the use of fine-grained locking in a multithreaded context.
 * 
 * Instead of using a global lock for the entire array, a separate ReentrantLock is used for each index.
 * This allows for better concurrency, as threads can operate on different indices in parallel.
 */
public class FineGrainedLockingExample {

    public static void main(String[] args) {

        int numThreads = 4;

        // Shared data structures: a counter and an array with per-index locks
        SharedData sharedData = new SharedData();
        SharedCounter counter = new SharedCounter();

        // Create and launch threads
        CounterThread[] threads = new CounterThread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData, counter);
            threads[i].start();
        }

        // Wait for all threads to complete
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Check for correctness: all elements should be exactly 1
        checkArray(sharedData);
    }

    /**
     * Validates that all elements of the shared array were incremented exactly once.
     */
    private static void checkArray(SharedData sData) {
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
     * CounterThread
     * 
     * Each thread gets the next index from the shared counter,
     * then locks the specific position in the array to safely increment it.
     */
    static class CounterThread extends Thread {
        int threadId;
        SharedData data;
        SharedCounter count;

        public CounterThread(int threadId, SharedData sData, SharedCounter counter) {
            this.threadId = threadId;
            this.data = sData;
            this.count = counter;
        }

        @Override
        public void run() {
            int index;

            // Threads continue fetching and updating array indices until they exceed bounds
            while ((index = count.getAndInc()) < data.end) {
                // Fine-grained locking: lock only the array index we're modifying
                data.locks[index].lock();
                try {
                    data.array[index]++;
                } finally {
                    data.locks[index].unlock();
                }
            }
        }
    }

    /**
     * SharedData
     * 
     * Holds the shared array and one lock per index for fine-grained synchronization.
     */
    static class SharedData {
        int end = 1000;
        int[] array = new int[end];
        ReentrantLock[] locks = new ReentrantLock[end];

        public SharedData() {
            for (int i = 0; i < end; i++) {
                locks[i] = new ReentrantLock();
            }
        }
    }

    /**
     * SharedCounter
     * 
     * A thread-safe counter using a single ReentrantLock to provide atomic get-and-increment.
     */
    static class SharedCounter {
        private int n = 0;
        private final ReentrantLock lock = new ReentrantLock();

        public int getAndInc() {
            lock.lock();
            try {
                return n++;
            } finally {
                lock.unlock();
            }
        }
    }
}
