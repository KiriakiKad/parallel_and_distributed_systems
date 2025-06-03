/*
 * Demonstrates synchronization on a shared array object.
 * Multiple threads increment elements of a shared array.
 * Synchronization is done by locking on the shared array itself.
 * 
 * This is a typical way to use synchronized for mutual exclusion.
 */
public class SharedCounterSynchronizedGlobal {

    public static void main(String[] args) {
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;

        CounterThread[] threads = new CounterThread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end, array);
            threads[i].start();
        }

        // Wait for threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        checkArray(end, array, numThreads);
    }

    static void checkArray(int end, int[] array, int numThreads) {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            // Each element should be incremented numThreads * i times
            if (array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {
        int end;
        int[] array;

        public CounterThread(int end, int[] array) {
            this.end = end;
            this.array = array;
        }

        public void run() {
            /*
             * For each i, increment array[i] i times.
             * Synchronize on the shared array to prevent race conditions.
             */
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++) {
                    synchronized (array) {
                        array[i]++;
                    }
                }
            }
        }
    }
}
