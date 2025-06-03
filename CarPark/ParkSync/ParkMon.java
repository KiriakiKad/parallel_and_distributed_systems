
public class ParkMon{

	private int capacity;
	private int spaces;
	private int waitscale;
	private int inscale;

    private boolean bufferEmpty = true;
	private boolean bufferFull = false;
    
    public ParkMon(int c) {
       capacity = c;
       spaces = capacity;
       waitscale = 10;
       inscale = 5;
    }
       
	public synchronized void arrive () {

        while (bufferFull) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
		System.out.println(Thread.currentThread().getName()+" arrival");
		//Car entering
		System.out.println(Thread.currentThread().getName()+"     parking");
		//Decrement capacity
		spaces--;
		System.out.println("free "+ spaces);
        bufferEmpty = false;

        if (spaces == 0) {
            bufferFull = true;
            System.out.println("The parking is full");
        }

        if (spaces == capacity - 1) {
            notifyAll();
        }
	}
        
    public synchronized void depart () {
        while (bufferEmpty) {
			try {
				wait();
			} catch (InterruptedException e) {}
		}
        //Car departure
        System.out.println(Thread.currentThread().getName()+"         departure");
        //Increment capacity
        spaces++;
        System.out.println("free "+ spaces);
        bufferFull = false;

        if (spaces == capacity) {
            bufferEmpty = true;
            System.out.println("The parking is empty");
        }

        if (spaces == 1) {
            notifyAll();
        }
    }            
           
    void park() {    
        try {
                Thread.sleep((int)(Math.random()*inscale));
            } catch (InterruptedException e) { }
    }
}
