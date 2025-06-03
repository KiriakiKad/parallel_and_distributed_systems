import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * SharedCounterGlobalWhileExample
 *
 * This program demonstrates a shared counter and array increment
 * using a single ReentrantLock for synchronization.
 *
 * Multiple threads increment array elements at unique indices.
 * The lock is held during both the index check and increment to
 * avoid race conditions.
 */
public class SharedCounterGlobalWhileExample {

    static int end = 10000;

    // The counter and the array share the same lock for synchronization.
    static int counter = 0;
    static int[] array = new int[end];
    static Lock lock = new ReentrantLock();

    static int numThreads = 4;

    public static void main(String[] args) {

        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Interrupted, ignore for simplicity
            }
        }

        checkArray();
    }

    /**
     * Checks that each element in the array was incremented exactly once.
     */
    static void checkArray() {
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

    static class CounterThread extends Thread {

        public void run() {
            /*
             * Without locking the entire while loop, a race condition occurs:
             * 1) A thread checks if counter < end and proceeds,
             * 2) Before acquiring the lock, another thread increments the counter,
             * 3) The first thread then accesses an invalid index (counter >= end).
             * 
             * Solution: acquire the lock before checking the counter condition.
             */
            while (true) {
                lock.lock();
                try {
                    if (counter >= end)
                        break;

                    array[counter]++;
                    counter++;
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
