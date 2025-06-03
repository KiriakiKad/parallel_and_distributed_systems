import java.util.concurrent.*;

// Class demonstrating the use of a shared counter and a thread-safe ConcurrentHashMap
// Threads increment values in the map at positions indicated by the shared counter
public class SharedCounterConcurrentHashMap {

    public static void main(String[] args) {

        SharedCounter counter = new SharedCounter();  // Shared counter with synchronized access
        int numThreads = 4;                           // Number of threads
        SharedData sharedData = new SharedData();    // Shared data containing the concurrent map

        CounterThread[] threads = new CounterThread[numThreads];
        
        // Start all threads
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new CounterThread(i, sharedData, counter);
            threads[i].start();
        }
        
        // Wait for all threads to finish
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // Interrupted exception handling (optional)
            }
        } 
        
        check_map(sharedData);  // Verify the map contents after threads complete
    }
    
    // Method to check if all map entries have the expected value (1)
    public static void check_map(SharedData sData) {
        int errors = 0;
        System.out.println("Checking...");
        
        // Iterate over all expected keys and verify values
        for (int i = 0; i < sData.end; i++) {
            int val = sData.map.getOrDefault(i, 0);
            if (val != 1) {
                errors++;
                System.out.printf("%d: %d should be 1\n", i, val);
            }
        }
        System.out.println(errors + " errors.");
    }

    // Thread class that increments map entries using the shared counter index
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
            // Each thread repeatedly obtains a unique index from the shared counter
            // Then atomically updates the value in the concurrent map at that index
            while ((index = count.getAndInc()) < data.end) {
                // Use compute method to atomically update the value at key 'index'
                data.map.compute(index, (key, value) -> (value == null) ? 1 : value + 1);
            }
        }              
    }    
}

// SharedData class encapsulating the concurrent map and upper bound for indices
class SharedData {
    int end; 
    ConcurrentHashMap<Integer, Integer> map; // Thread-safe map supporting concurrent updates

    public SharedData() {
        this.end = 1000;
        this.map = new ConcurrentHashMap<>(); // Initialize ConcurrentHashMap for safe multi-threading
    }
}

// Shared counter class providing atomic get-and-increment operation using synchronized block
class SharedCounter {
    int n; 
    Object lock = new Object(); // Lock object for synchronization
    
    public SharedCounter() {
        this.n = 0;
    }
    
    // Atomically returns current value and increments it for the next call
    public int getAndInc() {
        synchronized (lock) {
            return n++;
        }
    }
}
