// This program creates 4 threads that increment values in a shared array.
// It does NOT use synchronization, so race conditions are expected.

public class SharedArrayNoSync {

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

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Handle interruption
            }
        }

        // Check the contents of the array
        checkArray(end, array, numThreads);
    }

    // Verifies each element in the array
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

    // Thread that updates the shared array
    static class CounterThread extends Thread {
        int end;
        int[] array;

        public CounterThread(int end, int[] array) {
            this.end = end;
            this.array = array;
        }

        public void run() {
            for (int i = 0; i < end; i++) {
                for (int j = 0; j < i; j++)
                    array[i]++;
            }
        }
    }
}
