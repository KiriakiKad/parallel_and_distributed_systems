/*
 * Uses separate SharedData and SharedCounter classes.
 * SharedCounter provides synchronized getAndInc() method.
 * Threads fetch index from SharedCounter and increment array elements.
 */
public class SharedCounterWithSharedDataAndSynchronizedWhile {

    public static void main(String[] args) {
        SharedCounter counter = new SharedCounter();
        SharedData sharedData = new SharedData();

        int numThreads = 4;
        CounterThread[] threads = new CounterThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData, counter);
            threads[i].start();
        }

        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        checkArray(sharedData);
    }

    static void checkArray(SharedData sData) {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < sData.end; i++) {
            // Each element should be incremented exactly once
            if (sData.array[i] != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, sData.array[i]);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {
        int threadId;
        SharedData data;
        SharedCounter count;

        public CounterThread(int threadId, SharedData sData, SharedCounter counter) {
            this.threadId = threadId;
            this.data = sData;
            this.count = counter;
        }

        public void run() {
            int index;
            // Threads repeatedly get the next index to update from SharedCounter
            while ((index = count.getAndInc()) < data.end) {
                data.array[index]++;
            }
        }
    }
}

class SharedData {
    int end;
    int[] array;

    public SharedData() {
        this.end = 1000;
        this.array = new int[end];
    }
}

class SharedCounter {
    int n;
    Object lock = new Object();

    public SharedCounter() {
        this.n = 0;
    }

    // Returns current value and increments it atomically inside synchronized block
    public int getAndInc() {
        synchronized (lock) {
            return n++;
        }
    }
}
