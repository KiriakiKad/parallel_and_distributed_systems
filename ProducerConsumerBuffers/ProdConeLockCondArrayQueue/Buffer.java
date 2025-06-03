import java.util.concurrent.ArrayBlockingQueue;

/*
 * The ArrayBlockingQueue automatically manages mutual exclusion by providing
 * built-in locks for put and take operations. It controls when the queue is full or empty
 * and synchronizes threads that access the critical section.
 * By default, it operates as a circular queue (FIFO).
 */

public class Buffer
{
    // Underlying queue to hold integer data items
    private ArrayBlockingQueue<Integer> contents;

    // Constructor initializes the queue with fixed size 's'
    public Buffer(int s) {
        contents = new ArrayBlockingQueue<>(s);
    }

    // Inserts an item into the buffer, blocking if full
    public void put(int data) {
        try {
            if (contents.remainingCapacity() == 0) {
                System.out.println("The buffer is full");
            }

            contents.put(data);  // Blocks if the queue is full
            System.out.println("Prod " + Thread.currentThread().getName() + " No " + data + " Count = " + contents.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Retrieves and removes an item from the buffer, blocking if empty
    public int get() {
        int data = 0;
        try {
            if (contents.isEmpty()) {
                System.out.println("The buffer is empty");
            }

            data = contents.take();  // Blocks if the queue is empty
            System.out.println("  Cons " + Thread.currentThread().getName() + " No " + data + " Count = " + contents.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return data;
    }
}