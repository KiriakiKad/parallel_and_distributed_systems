/*
 * Synchronizes on a shared data object containing the array.
 * Each thread increments array elements inside a synchronized block locking on SharedData.
 */
public class SharedDataSynchronizedGlobal {

    public static void main(String[] args) {
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        checkArray(sharedData, numThreads);
    }

    static void checkArray(SharedData sData, int numThreads) {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < sData.end; i++) {
            // Each element should be incremented numThreads * i times
            if (sData.array[i] != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, sData.array[i], numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    static class CounterThread extends Thread {
        int threadId;
        SharedData data;

        public CounterThread(int threadId, SharedData sData) {
            this.threadId = threadId;
            this.data = sData;
        }

        public void run() {
            /*
             * Synchronize on shared data object to prevent race conditions
             * when updating array elements.
             */
            for (int i = 0; i < data.end; i++) {
                for (int j = 0; j < i; j++) {
                    synchronized (data) {
                        data.array[i]++;
                    }
                }
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
