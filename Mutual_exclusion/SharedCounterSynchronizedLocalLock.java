/*
 * Demonstrates incorrect synchronization using a local lock object per thread.
 * Each thread synchronizes on its own lock object, so no mutual exclusion occurs.
 * This will lead to race conditions on the shared array.
 */
public class SharedCounterSynchronizedLocalLock {

    public static void main(String[] args) {
        int end = 1000;
        int[] array = new int[end];
        int numThreads = 4;

        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end, array);
            threads[i].start();
        }

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
            // This will likely report errors because no real synchronization occurs
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
        Object localLock = new Object();  // Local lock, useless for mutual exclusion

        public CounterThread(int end, int[] array) {
            this.end = end;
            this.array = array;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++) {
                    // Synchronization on local lock means each thread locks a different object
                    synchronized (localLock) {
                        array[i]++;
                    }
                }
            }
        }
    }
}
