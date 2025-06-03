public class Buffer
{
	private int content;
	private boolean bufferEmpty = true;
	private boolean bufferFull = false;
	private int size;

	// Constructor
	public Buffer(int s) {
		this.size = s;
		content = 0; 
	}

	// Put an item into buffer
	public synchronized void put(int data)
	{
		while (bufferFull == true) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}

		content = data;
		System.out.println("Item " + data + " added in loc " + ". Count = 1");
		bufferEmpty = false;

		
		bufferFull = true;
		System.out.println("The buffer is full");
		
		//buffer was empty
		if (bufferFull == true) notifyAll();
	}

	// Get an item from bufffer
	public synchronized int get()
	{
		while (bufferEmpty == true) {
			try {
				wait();
			}
			catch (InterruptedException e) {}
		}

		int data = content;
		System.out.println("Item " + data + " removed from loc " + ". Count = 0");	
		
		bufferFull = false;
		
		
		bufferEmpty = true;
		System.out.println("The buffer is empty");
	
		//buffer was full
		if (bufferEmpty == true) notifyAll();
		return data;
	}
}

	
			
	
