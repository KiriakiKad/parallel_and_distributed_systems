// This program creates 4 threads that increment elements in a shared array using a shared counter.
// The counter determines the index each thread should update. 
// However, since there is no synchronization, race conditions may still occur.

public class SharedCounterNoSync {

    public static void main(String[] args) {

        int end = 10000;
        int[] array = new int[end];
        SharedCounter counter = new SharedCounter();
        int numThreads = 4;

        CounterThread[] threads = new CounterThread[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(end, array, counter);
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
        checkArray(end, array);
    }

    // Verifies that each element in the array has been incremented exactly once
    static void checkArray(int end, int[] array) {
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

    // Thread that uses the shared counter to determine the next index to increment
    static class CounterThread extends Thread {
        int end;
        int[] array;
        SharedCounter counter;

        public CounterThread(int end, int[] array, SharedCounter counter) {
            this.end = end;
            this.array = array;
            this.counter = counter;
        }

        public void run() {
            while (true) {
                int index = counter.get();
                if (index >= end) {
                    break;
                }
                array[index]++;
                counter.inc(); // Increment the shared counter
            }
        }
    }
}

// Simple shared counter class (not thread-safe)
class SharedCounter {
    int n;

    public SharedCounter() {
        this.n = 0;
    }

    public int get() {
        return n;
    }

    public void inc() {
        n = n + 1;
    }
}
