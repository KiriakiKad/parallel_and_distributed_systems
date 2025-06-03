import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Fork {

    private int identity;
    private Lock lock = new ReentrantLock();

    Fork(int id) {
        identity = id;
    }

    // Acquire the fork (lock)
    void get() {
        lock.lock();
    }

    // Release the fork (unlock)
    void put() {
        lock.unlock();
    }
}