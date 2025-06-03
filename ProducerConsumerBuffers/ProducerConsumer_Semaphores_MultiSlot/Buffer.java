import java.util.concurrent.Semaphore;
public class Buffer
{
	private int[] contents;
	private int size;
	private int front, back;

	private Semaphore bufferFull = new Semaphore(0);
	private Semaphore bufferEmpty; 

	// Constructor
	public Buffer(int s) {
	this.size = s;
	contents = new int[size];
	for (int i=0; i<size; i++)
		contents[i] = 0;
		this.front = 0;
		this.back = size-1;	
		this.bufferEmpty = new Semaphore(size);
	}

	// Put an item into buffer
	public void put(int data) {
		try {
			bufferEmpty.acquire();
		} catch (InterruptedException e) { }
	
		
		back = (back + 1) % size;
		contents[back] = data;
		
		System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Loc " + back);
		if (bufferFull.availablePermits() == size - 1) System.out.println("The buffer is full");
		bufferFull.release(); 
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferFull.acquire();
		} catch (InterruptedException e) { }

		data = contents[front];
		System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Loc " + front);
        front = (front + 1) % size;
			
		if (bufferFull.availablePermits() == 0) System.out.println("The buffer is empty");	
		bufferEmpty.release();
		return data;
	}
}

	
			
	
