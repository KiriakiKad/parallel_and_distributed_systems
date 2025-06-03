import java.util.concurrent.CopyOnWriteArrayList;

// Demonstrates the use of CopyOnWriteArrayList for thread-safe concurrent updates
// Multiple threads increment values of the list according to a pattern
public class SharedCounterCopyOnWriteArrayList {

    static int end = 1000;

    // CopyOnWriteArrayList allows safe concurrent access, but at the cost of copying on writes
    static CopyOnWriteArrayList<Integer> array = new CopyOnWriteArrayList<>();

    static int numThreads = 4;

    public static void main(String[] args) {
        // Initialize list with 1000 zeros
        for (int i = 0; i < end; i++) {
            array.add(0);
        }

        CounterThread[] threads = new CounterThread[numThreads];
        
        // Start all threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread();
            threads[i].start();
        }
    
        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Handle interruption if needed
            }
        } 

        check_array(); // Verify results after concurrent modifications
    }
     
    // Verify that each element has the expected value: numThreads * i
    static void check_array() {
        int errors = 0;
        System.out.println("Checking...");

        for (int i = 0; i < end; i++) {
            int val = array.get(i);
            if (val != numThreads * i) {
                errors++;
                System.out.printf("%d: %d should be %d\n", i, val, numThreads * i);
            }
        }
        System.out.println(errors + " errors.");
    }

    // Thread that increments elements of the list according to the pattern
    static class CounterThread extends Thread {
        public CounterThread() {}

        public void run() {
            for (int i = 0; i < end; i++) {
                // Synchronize on the list to safely read/update elements (CopyOnWriteArrayList is thread-safe for reads,
                // but concurrent modifications like this still require synchronization to avoid lost updates)
                synchronized (array) {  
                    for (int j = 0; j < i; j++) {
                        // Increment element at index i by 1, repeated i times
                        array.set(i, array.get(i) + 1);
                    }
                }
            } 
        }            	
    }
}
