import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
   Execution time results for PiComputationWithLock:
   ------------------------------------------------------------
   | numSteps          | Execution Time (ms)      | Notes              |
   ------------------------------------------------------------
   | 1,000             |           0 ms           |         -          |
   | 10,000            |          10 ms           |         -          |
   | 1,000,000         |          21 ms           |        ~2x         |
   | 10,000,000        |          47 ms           |       ~2.3x        |
   | 100,000,000       |          88 ms           |       ~1.8x        |
   | 1,000,000,000     |         545 ms           |       ~6.1x        |
   ------------------------------------------------------------

   Note: According to the above measurements, for small values like 1k, 10k, and 1M, 
   the overhead of thread creation and synchronization is much higher than the cost of 
   arithmetic computation. For larger values such as 100M and 1B, we observe significant 
   improvement in execution time (SEQ = 2669 ms vs PAR = 545 ms). 

   Therefore, parallelization pays off as workload increases, while for smaller problems 
   the sequential code is faster.

   SPEEDUP MEASUREMENT:
   T(N,1) = 2669ms
   T(N,P) = 545ms

   S(N,P) = 2669 / 545 ≈ 4.9

   For 8 processors, we achieve a speedup of ~4.9, meaning the program is nearly 
   4.9 times faster – confirming the benefit of parallelization.
*/

public class PiComputationWithLock {

    public static double sum;
    public static Lock lock = new ReentrantLock();

    public static void main(String[] args) {

        long numSteps = 1_000_000_000;
        int numThreads = Runtime.getRuntime().availableProcessors();

        // Optional command-line parsing
        /*
        if (args.length != 1) {
            System.out.println("arguments:  number_of_steps");
            System.exit(1);
        }
        try {
            numSteps = Long.parseLong(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("argument " + args[0] + " must be a long integer");
            System.exit(1);
        }
        */

        sum = 0.0;
        double step = 1.0 / (double) numSteps;

        long startTime = System.currentTimeMillis();

        PiWorkerWithLock[] threads = new PiWorkerWithLock[numThreads];

        // Create and start threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new PiWorkerWithLock(i, numThreads, numSteps, step);
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

        double pi = sum * step;

        long endTime = System.currentTimeMillis();

        System.out.printf("Parallel program results with %d steps\n", numSteps);
        System.out.printf("Computed pi = %22.20f\n", pi);
        System.out.printf("Difference from Math.PI = %22.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("Time to compute = %d ms\n", (endTime - startTime));
    }
}

class PiWorkerWithLock extends Thread {

    private int threadId;
    private long start;
    private long end;
    private double step;
    private double localSum;

    public PiWorkerWithLock(int id, int numThreads, long numSteps, double step) {
        this.threadId = id;
        this.step = step;
        this.localSum = 0.0;

        long chunk = numSteps / numThreads;
        this.start = threadId * chunk;
        this.end = (threadId == numThreads - 1) ? numSteps : start + chunk;
    }

    @Override
    public void run() {
        for (long i = start; i < end; ++i) {
            double x = ((double) i + 0.5) * step;
            localSum += 4.0 / (1.0 + x * x);
        }

        // Critical section protected by lock
        PiComputationWithLock.lock.lock();
        try {
            PiComputationWithLock.sum += localSum;
        } finally {
            PiComputationWithLock.lock.unlock();
        }
    }
}
