/*
 * Uses a static global lock object for synchronization.
 * Multiple threads increment a shared counter and update a shared array.
 * Synchronization uses this global lock to ensure mutual exclusion.
 */
public class SharedCounterSynchronizedGlobalWhile {

    public static void main(String[] args) {
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;

        CounterThread[] threads = new CounterThread[numThreads];

        // Start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end, array);
            threads[i].start();
        }

        // Join threads
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
        static Object globalLock = new Object();
        static int count = 0;

        public CounterThread(int end, int[] array) {
            this.end = end;
            this.array = array;
        }

        public void run() {
            while (true) {
                int i;

                synchronized (globalLock) {
                    if (count >= end) break;
                    i = count++;
                }

                // Increment the array element i times
                for (int j = 0; j < i; j++) {
                    synchronized (globalLock) {
                        array[i]++;
                    }
                }
            }
        }
    }
}
