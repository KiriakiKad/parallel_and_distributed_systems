import java.util.concurrent.Semaphore;
public class Buffer
{ 
	private int content; 
	private int size;

	private Semaphore bufferFull = new Semaphore(0);
	private Semaphore bufferEmpty; 

	// Constructor
	public Buffer(int s) {
	this.size = s;
	content = 0;	
		this.bufferEmpty = new Semaphore(size);
	}

	// Put an item into buffer
	public void put(int data) {
		try {
			bufferEmpty.acquire(); 
		} catch (InterruptedException e) { }
		
		content = data;

		System.out.println("Prod " + Thread.currentThread().getName() + " No "+ data + " Count = 1" );
		System.out.println("The buffer is full");

		bufferFull.release(); 
	}

	// Get an item from bufffer
	public int get() {
		int data = 0;
		try {
			bufferFull.acquire();
		} catch (InterruptedException e) { }

		data = content;
		System.out.println("  Cons " + Thread.currentThread().getName() + " No "+ data + " Count = 0" );

		System.out.println("The buffer is empty");	
			
		bufferEmpty.release();
		return data;
	}
}

	
			
	
