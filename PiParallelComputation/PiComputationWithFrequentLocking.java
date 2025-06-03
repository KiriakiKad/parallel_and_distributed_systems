import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
   Execution time results for PiComputationWithFrequentLocking:
   ------------------------------------------------------------
   | numSteps          | Execution Time (ms)      | Notes              |
   ------------------------------------------------------------
   | 1,000             |           17 ms          |         -          |
   | 10,000            |           25 ms          |         ~1.4x      |
   | 1,000,000         |           71 ms          |         ~2.8x      |
   | 10,000,000        |          347 ms          |         ~4.9x      |
   | 100,000,000       |         3004 ms          |         ~8.7x      |
   | 1,000,000,000     |        29899 ms          |        ~10x        |
   ------------------------------------------------------------

   Note: In this implementation, we lock/unlock on every single iteration of the loop.
   This causes excessive synchronization overhead, nullifying the benefits of parallelism.
   A correct implementation (see PiComputationWithLock) uses local accumulation inside 
   each thread and updates the shared variable only once, minimizing locking.

   SPEEDUP MEASUREMENT:
   T(N,1)  = 2669 ms (sequential)
   T(N,P) = 29899 ms (parallel with bad locking)

   S(N,P) = 2669 / 29899 ≈ 0.089

   For 8 processors, speedup << 8. This means performance is worse than sequential,
   due to poor synchronization design.
*/

public class PiComputationWithFrequentLocking {
    public static double sum = 0.0;
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        long numSteps = 1_000_000_000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        double step = 1.0 / (double) numSteps;

        long startTime = System.currentTimeMillis();

        PiWorkerWithFrequentLocking[] threads = new PiWorkerWithFrequentLocking[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new PiWorkerWithFrequentLocking(i, numThreads, numSteps, step);
            threads[i].start();
        }

        // Wait for threads to complete
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double pi = sum * step;

        long endTime = System.currentTimeMillis();

        System.out.printf("Parallel program with frequent locking, %d steps\n", numSteps);
        System.out.printf("Computed pi = %22.20f\n", pi);
        System.out.printf("Difference from Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("Time to compute = %d ms\n", (endTime - startTime));
    }
}

class PiWorkerWithFrequentLocking extends Thread {
    private int threadId;
    private long start;
    private long end;
    private double step;

    public PiWorkerWithFrequentLocking(int id, int numThreads, long numSteps, double step) {
        this.threadId = id;
        this.step = step;

        long chunk = numSteps / numThreads;
        this.start = threadId * chunk;
        this.end = (threadId == numThreads - 1) ? numSteps : start + chunk;
    }

    @Override
    public void run() {
        for (long i = start; i < end; ++i) {
            double x = ((double) i + 0.5) * step;
            double localValue = 4.0 / (1.0 + x * x);

            // BAD PRACTICE: lock/unlock on every iteration
            PiComputationWithFrequentLocking.lock.lock();
            try {
                PiComputationWithFrequentLocking.sum += localValue;
            } finally {
                PiComputationWithFrequentLocking.lock.unlock();
            }
        }
    }
}
