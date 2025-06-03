import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
public class Buffer
{
	//private int[] contents;
	private int content;
	private int size;
	//private int front, back;
	//private int counter = 0;
	private boolean flag = false;//pleon apla xreiazetai mia boolean metavliti gia na kseroume an iparxei stoixeio ston buffer i oxi 
	private Lock lock = new ReentrantLock();
	private Condition bufferFull = lock.newCondition();
	private Condition bufferEmpty = lock.newCondition();

	// Constructor
	public Buffer(int s) {
		this.size = s;
		content = 0;
	}

	// Put an item into buffer
	public void put(int data) {

		lock.lock();
			try {
				while (flag) {
				System.out.println("The buffer is full");
				try {
					bufferFull.await();
				} catch (InterruptedException e) { }
			}

			content = data;
			flag = true;
			System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Count = 1" );
			//buffer was empty
			if (flag) bufferEmpty.signalAll();
		} finally {
			lock.unlock() ;
		}
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;

		lock.lock();
		try {
			while (!flag) {
				System.out.println("The buffer is empty");
				try {
					bufferEmpty.await();
				} catch (InterruptedException e) { }
			}

			data = content;
			System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Count = 0");

			flag = false;

			if (!flag) bufferFull.signalAll();
		} finally {
			lock.unlock() ;
		}
		return data;
	}
}

	
			
	
