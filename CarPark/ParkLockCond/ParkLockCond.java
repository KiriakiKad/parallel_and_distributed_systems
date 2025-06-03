import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParkLockCond{

	private int capacity;
	private int spaces;
	private int waitscale;
	private int inscale;

    private Lock lock = new ReentrantLock();
	private Condition bufferFull = lock.newCondition();
	private Condition bufferEmpty = lock.newCondition();

    //prosthiki
    private boolean[] parkingSpots; //false == adeia kai true == piasmeni
    
    public ParkLockCond(int c) {
       capacity = c;
       spaces = capacity;
       waitscale = 10;
       inscale = 5;

       parkingSpots = new boolean[capacity]; 
    }
       
	void arrive () {
		//Car arrival with radom delay
        lock.lock();
		try {
		    //Thread.sleep((int)(Math.random()*waitscale));
            while(spaces == 0){
                System.out.println(Thread.currentThread().getName() + " waiting to park");
                try {
                    bufferEmpty.await();
                } catch (InterruptedException e) {}
            }
            //vriskei to 1o adeio parking
            int pos = findEmpty();
            if(pos != -1){
                parkingSpots[pos] = true; //i sigkekrimeni thesi einai katilimeni

                System.out.println(Thread.currentThread().getName()+" arrival");
                //Car entering
                System.out.println(Thread.currentThread().getName()+"     parking");
                //Decrement capacity
                spaces--;
                System.out.println("free "+ spaces);
            
                printStatus();
            }

            
            //elegxw an iparxei toulaxiston mia keni thesi
            if(spaces == capacity - 1) bufferFull.signalAll();

        }finally{
            lock.unlock();
        }
	}
        
    void depart () {
        lock.lock();
        try{
            while(spaces == capacity){
                System.out.println(Thread.currentThread().getName() + " waiting to leave");
                try {
                    bufferFull.await();
                } catch (InterruptedException e) {}
            }

            //vriskei ti 1h piasmeni thesi
            int pos = findOccupied();
            if(pos != -1){
                parkingSpots[pos] = false;//adeia pleon

                //Car departure
                System.out.println(Thread.currentThread().getName()+"         departure");
                //Increment capacity
                spaces++;
                System.out.println("free "+ spaces);

            }

             
            if(spaces == 1)bufferEmpty.signalAll();
        }finally{
            lock.unlock();
        }
       
    }            
           
    void park() {    
        try {
                Thread.sleep((int)(Math.random()*inscale));
            } catch (InterruptedException e) { }
    }


    //PROSTHIKES

    //vriskei tin 1h adeia thesi
    private int findEmpty(){
        for (int i = 0; i < capacity; i++) {
            if (!parkingSpots[i]) { 
                return i;
            }
        }
        return -1;//den exei adeies theseis
    }

    //vriskei ti 1h piasmeni thesi
    private int findOccupied() {
        for (int i = 0; i < capacity; i++) {
            if (parkingSpots[i]) { 
                return i;
            }
        }
        return -1;
    }

    private void printStatus() {
        System.out.print("Current parking status: ");
        for (int i = 0; i < capacity; i++) {
            if (!parkingSpots[i]) {
                System.out.print("Spot " + (i + 1) + " (Empty) | "); // 1-based index
            } else {
                System.out.print("Spot " + (i + 1) + " (Occupied) | ");
            }
        }
        System.out.println();
    }
}
