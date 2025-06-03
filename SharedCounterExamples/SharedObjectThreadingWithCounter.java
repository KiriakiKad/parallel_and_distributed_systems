public class SharedObjectThreadingWithCounter {

    public static void main(String[] args) {
        SharedCounter counter = new SharedCounter(); // Shared counter used to assign unique indices
        int numThreads = 4;
        SharedData sharedData = new SharedData(); // Shared object holding the array

        CounterThread[] threads = new CounterThread[numThreads];

        // Create and start each thread
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData, counter);
            threads[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Verify the result
        checkArray(sharedData);
    }

    // Verifies that every array element was incremented exactly once
    static void checkArray(SharedData sData) {
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

    // Thread class using a shared counter to divide work between threads
    static class CounterThread extends Thread {
        int threadId;
        SharedData data;
        SharedCounter counter;

        public CounterThread(int threadId, SharedData sData, SharedCounter counter) {
            this.threadId = threadId;
            this.data = sData;
            this.counter = counter;
        }

        public void run() {
            while (true) {
                // Get current index to process
                int index = counter.get();
                if (index >= data.end) {
                    break;
                }
                data.array[index]++; // Update shared array
                counter.inc();       // Move to next index
            }
        }
    }
}

// Class that holds shared data (array and its size)
class SharedData {
    int end;
    int[] array;

    public SharedData() {
        this.end = 1000;
        this.array = new int[end];
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
