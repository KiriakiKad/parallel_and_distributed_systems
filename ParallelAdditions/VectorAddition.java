/* Vector Addition a = b + c                       */

class VectorAddition
{
  public static void main(String args[])
  {
    int size = 30;
    int numThreads = 5;

    double[] a = new double[size];
    double[] b = new double[size];
    double[] c = new double[size];
    for(int i = 0; i < size; i++) {
        a[i] = 0.0;
		b[i] = 1.0;
        c[i] = 0.5;
    }
	


    // get current time 
		long start = System.currentTimeMillis();

		// create threads
		VectorAddThread threads[] = new VectorAddThread [numThreads];

		for(int i = 0; i < numThreads; i++) 
		{
			threads[i] = new VectorAddThread(i, numThreads, a, b, c, size);
			threads[i].start();
		}

		for(int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}

		// get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		System.out.println("time in ms = "+ elapsedTimeMillis);


    /* for debugging 
    for(int i = 0; i < size; i++) 
        System.out.println(a[i]); */
 }
}
class VectorAddThread extends Thread{

    private double [] Atable;
    private double [] Btable;
    private double [] Ctable;
	private int myStart;
	private int myStop;
    
    public VectorAddThread(int myId, int numThreads, double[] Aarray, double[] Barray, double[] Carray , int size){
        Atable = Aarray;
        Btable = Barray;
        Ctable = Carray;

        myStart = myId * (size / numThreads);
		myStop = myStart + (size / numThreads);
		if (myId == (numThreads - 1)) myStop = size;
    }

    public void run(){

        for(int i = myStart; i<myStop; i++){
            Atable[i] = Btable[i] + Ctable[i];
        }
    }
}