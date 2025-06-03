import java.util.concurrent.locks.ReentrantLock;

/**
 * FineGrainedLockingCounterArray simulates multiple threads concurrently updating
 * elements of an array using fine-grained locking. Each thread performs a nested
 * loop update for each index, and a separate lock is used per index.
 *
 * This example demonstrates how fine-grained locking allows multiple threads
 * to operate safely on different elements of a shared array concurrently.
 *
 */
public class FineGrainedLockingCounterArray {

    static final int end = 1000; // Size of the shared array
    static final int[] array = new int[end]; // Shared array
    static final int numThreads = 4; // Number of threads to spawn
    static final ReentrantLock[] locks = new ReentrantLock[end]; // One lock per array element

    public static void main(String[] args) {
        // Initialize one ReentrantLock for each element of the array
        for (int i = 0; i < end; i++) {
            locks[i] = new ReentrantLock();
        }

        CounterThread[] threads = new CounterThread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }

        // Wait for all threads to complete
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Ignored for simplicity
            }
        }

        checkArray();
    }

    /**
     * Verifies the final values in the shared array.
     * Each array[i] should be equal to numThreads * i
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
     * Each CounterThread loops through the entire array and,
     * for each index i, performs i increments to array[i].
     * Each array[i] is protected by its own lock to ensure thread-safety.
     */
    static class CounterThread extends Thread {

        public void run() {
            for (int i = 0; i < end; i++) {
                locks[i].lock(); // Lock only the specific array index (fine-grained)
                try {
                    for (int j = 0; j < i; j++) {
                        array[i]++;
                    }
                } finally {
                    locks[i].unlock(); // Always unlock in a finally block
                }
            }
        }
    }
}
