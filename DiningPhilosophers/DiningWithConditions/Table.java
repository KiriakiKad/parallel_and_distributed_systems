import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Table {

    private Lock lock = new ReentrantLock();
    // Array of conditions, one for each philosopher
    private Condition[] conditions;
    // Array representing the state of each philosopher
    private State[] states;

    // Possible states for a philosopher
    enum State { THINKING, HUNGRY, EATING }

    // Constructor initializes conditions and states for all philosophers
    public Table(int numPhilosophers) {
        conditions = new Condition[numPhilosophers];  // Condition array for each philosopher
        states = new State[numPhilosophers];  // State array for each philosopher

        // Initialize all philosophers as THINKING and create their conditions
        for (int i = 0; i < numPhilosophers; i++) {
            conditions[i] = lock.newCondition();  // Create a new condition for each philosopher
            states[i] = State.THINKING;  // Initial state: thinking
        }
    }

    // Method for a philosopher to pick up forks (attempt to eat)
    public void pickUpForks(int id) {
        lock.lock();  // Acquire lock to protect shared data
        try {
            states[id] = State.HUNGRY;  // Philosopher becomes hungry
            test(id);  // Check if philosopher can start eating

            // Wait while philosopher is not eating
            while (states[id] != State.EATING) {
                conditions[id].await();  // Wait until notified to eat
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();  // Restore interrupted status
        } finally {
            lock.unlock();  // Release lock
        }
    }

    // Method for a philosopher to put down forks (stop eating)
    public void putDownForks(int id) {
        lock.lock();  // Acquire lock for synchronization
        try {
            states[id] = State.THINKING;  // Philosopher returns to thinking
            test((id + 4) % 5); // Notify left neighbor to try eating
            test((id + 1) % 5); // Notify right neighbor to try eating
        } finally {
            lock.unlock();  // Release lock
        }
    }

    // Check if a philosopher can start eating:
    // Philosopher can eat if hungry and neighbors are not eating
    private void test(int id) {
        if (states[id] == State.HUNGRY &&
            states[(id + 4) % 5] != State.EATING &&  // Left neighbor not eating
            states[(id + 1) % 5] != State.EATING) {  // Right neighbor not eating
            
            states[id] = State.EATING;  // Philosopher starts eating
            conditions[id].signal();  // Notify philosopher to proceed
        }
    }
}