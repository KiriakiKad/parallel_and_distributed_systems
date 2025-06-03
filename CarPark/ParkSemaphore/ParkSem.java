import java.util.concurrent.Semaphore;

public class ParkSem {

    private int capacity;  // Maximum number of parking spots
    private int spaces;    // Current number of free spots
    private int waitscale; // Scale factor for arrival time delay
    private int inscale;   // Scale factor for parking duration

    // Semaphores for synchronization
    private Semaphore mutex = new Semaphore(1);         // Ensures mutual exclusion
    private Semaphore bufferFull = new Semaphore(0);    // Tracks occupied spots
    private Semaphore bufferEmpty;                      // Tracks available spots

    public ParkSem(int c) {
        capacity = c;
        spaces = capacity;
        waitscale = 10;
        inscale = 5;
        bufferEmpty = new Semaphore(c); // Initially, all spots are available
    }

    void arrive() {
        // Simulate car arrival after a random delay
        try {
            // Thread.sleep((int)(Math.random()*waitscale)); // Optional arrival delay
            bufferEmpty.acquire(); // Wait if no empty spots
        } catch (InterruptedException e) {}

        try {
            mutex.acquire(); // Enter critical section
        } catch (InterruptedException e) {}

        System.out.println(Thread.currentThread().getName() + " arrival");
        System.out.println(Thread.currentThread().getName() + "     parking");

        // Decrement available spaces
        spaces--;
        System.out.println("free " + spaces);
        if (spaces == 0) System.out.println("No free space");

        mutex.release();     // Exit critical section
        bufferFull.release(); // Signal that a spot is now occupied
    }

    void depart() {
        try {
            bufferFull.acquire(); // Wait until there is at least one car parked
        } catch (InterruptedException e) {}

        try {
            mutex.acquire(); // Enter critical section
        } catch (InterruptedException e) {}

        System.out.println(Thread.currentThread().getName() + "         departure");

        // Increment available spaces
        spaces++;
        System.out.println("free " + spaces);
        if (spaces == capacity) System.out.println("All spaces are free");

        mutex.release();     // Exit critical section
        bufferEmpty.release(); // Signal that a spot has been freed
    }

    void park() {
        // Simulate time spent parked
        try {
            Thread.sleep((int)(Math.random() * inscale));
        } catch (InterruptedException e) {}
    }
}
