/*
 * Encapsulates shared counter and array.
 * Uses a private lock object for synchronization.
 * Threads increment array elements one by one using a while loop.
 */
public class SharedCounterSynchronizedWithWhile {

    public static void main(String[] args) {
        int end = 10000;
        int[] array = new int[end];
        int numThreads = 4;

        SharedCounter counter = new SharedCounter(end, array);
        CounterThread[] threads = new CounterThread[numThreads];

        // Start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(counter);
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

        checkArray(end, array);
    }

    static void checkArray(int end, int[] array) {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            // Each element should be incremented exactly once
            if (array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {
        SharedCounter counter;

        public CounterThread(SharedCounter counter) {
            this.counter = counter;
        }

        public void run() {
            // Each thread repeatedly calls getAndInc() until all indexes processed
            counter.getAndInc();
        }
    }

    static class SharedCounter {
        private int end;
        private int[] array;
        private Object lock = new Object();
        private int count = 0;

        public SharedCounter(int end, int[] array) {
            this.end = end;
            this.array = array;
        }

        public void getAndInc() {
            while (true) {
                synchronized (lock) {
                    if (count >= end)
                        break;
                    array[count]++;
                    count++;
                }
            }
        }
    }
}
