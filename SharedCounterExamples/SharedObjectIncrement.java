// Updated version of the first code from ex1_2_Global
// This code demonstrates how to use shared objects between threads
// Each thread performs operations on a shared array without synchronization

public class SharedObjectIncrement {

    public static void main(String[] args) {
        // Shared data between threads
        SharedData sharedData = new SharedData();
        int numThreads = 4;

        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {}
        }

        check_array(sharedData, numThreads);
    }

    // Check the contents of the array
    static void check_array(SharedData sData, int numThreads) {
        int errors = 0;
        System.out.println("Checking...");
        for (int i = 0; i < sData.end; i++) {
            if (sData.array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, sData.array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    // Thread that performs non-synchronized increments on a shared array
    static class CounterThread extends Thread {
        int threadId;
        SharedData data;

        public CounterThread(int threadId, SharedData sData) {
            this.threadId = threadId;
            this.data = sData;
        }

        public void run() {
            for (int i = 0; i < data.end; i++) {
                for (int j = 0; j < i; j++) {
                    data.array[i]++;
                }
            }
        }
    }
}

// Class that holds shared data for threads
class SharedData {
    int end;
    int[] array;

    public SharedData() {
        this.end = 1000;
        this.array = new int[end];
    }
}
