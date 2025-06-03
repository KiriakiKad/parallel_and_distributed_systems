/**
 * ParallelPiComputation.java
 * 
 * Computes the value of π using numerical integration with parallel threads.
 * The approach uses the midpoint rule to approximate the integral of 4 / (1 + x²) from 0 to 1.
 * 
 * Execution time results for ParallelPiComputation:
 * ----------------------------------------------------------------------
 * | numSteps        | Execution Time (ms)   | Notes                    |
 * ----------------------------------------------------------------------
 * | 1,000           |           6 ms        |                          |
 * | 10,000          |           8 ms        | ~1.3x                    |
 * | 1,000,000       |          24 ms        | 3x                       |
 * | 10,000,000      |          32 ms        | ~1.3x                    |
 * | 100,000,000     |          93 ms        | ~3x                      |
 * | 1,000,000,000   |         615 ms        | ~6.7x                    |
 * ----------------------------------------------------------------------
 * 
 * Observation:
 * For small values (1K, 10K, 1M), the thread management overhead is greater 
 * than the benefit of parallelism. For larger problems (100M, 1B), we see 
 * substantial improvements in execution time.
 * 
 * SPEEDUP MEASUREMENT:
 * Sequential Time T(N,1) = 2669 ms
 * Parallel Time T(N,P) = 615 ms
 * Speedup S(N,P) = T(N,1) / T(N,P) ≈ 4.4
 * 
 * For 8 cores, we get a speedup of ~4.4, indicating effective parallelism 
 * under heavier computation loads.
 */

public class ParallelPiNoSync{
    public static void main(String[] args) {

        long numSteps = 100_000_000;
        int numThreads = Runtime.getRuntime().availableProcessors(); // Use all available cores

        /*
         * If you wish to parse the number of steps from command line, uncomment below:
         *
         * if (args.length != 1) {
         *     System.out.println("arguments: number_of_steps");
         *     System.exit(1);
         * }
         * try {
         *     numSteps = Long.parseLong(args[0]);
         * } catch (NumberFormatException e) {
         *     System.out.println("argument " + args[0] + " must be a long integer");
         *     System.exit(1);
         * }
         */

        // Start timing
        long startTime = System.currentTimeMillis();

        double step = 1.0 / (double) numSteps;
        PiThread[] threads = new PiThread[numThreads];

        // Launch threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new PiThread(i, numThreads, numSteps, step);
            threads[i].start();
        }

        // Combine partial results from all threads
        double sum = 0.0;
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                sum += threads[i].mySum;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        double pi = sum * step;

        // End timing
        long endTime = System.currentTimeMillis();

        // Output results
        System.out.printf("Parallel computation results with %,d steps and %d threads\n", numSteps, numThreads);
        System.out.printf("Estimated π = %.20f\n", pi);
        System.out.printf("Difference from Math.PI = %.20f\n", Math.abs(pi - Math.PI));
        System.out.printf("Total execution time = %d ms\n", endTime - startTime);
    }
}

/**
 * PiThread:
 * Each thread is responsible for computing a portion of the summation used
 * to estimate π. The total interval [0,1] is divided evenly among all threads.
 */
class PiThread extends Thread {

    private final long myStart;
    private final long myStop;
    public double mySum;
    private final double myStep;

    public PiThread(int id, int numThreads, long numSteps, double step) {
        myStep = step;
        mySum = 0.0;
        long chunk = numSteps / numThreads;
        myStart = id * chunk;
        myStop = (id == numThreads - 1) ? numSteps : myStart + chunk;
    }

    @Override
    public void run() {
        for (long i = myStart; i < myStop; i++) {
            double x = ((double) i + 0.5) * myStep;
            mySum += 4.0 / (1.0 + x * x);
        }
    }
}
