/* Matrix Addition A = B + C                       */

class MatrixAddition
{
  public static void main(String args[])
  {
    int size = 30;
    int numThreads = 5;

    
    double[][] a = new double[size][size];
    double[][] b = new double[size][size];
    double[][] c = new double[size][size];
    for(int i = 0; i < size; i++) 
      for(int j = 0; j < size; j++) { 
        a[i][j] = 0.1;
		    b[i][j] = 0.3;
        c[i][j] = 0.5;
      }   
    

    // get current time 
		long start = System.currentTimeMillis();

		// create threads
		MatrixAddThread threads[] = new MatrixAddThread [numThreads];

		for(int i = 0; i < numThreads; i++) 
		{
			threads[i] = new MatrixAddThread(i, numThreads, a, b, c, size);
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
    for(int i = 0; i < size; i++) { 
      for(int j = 0; j < size; j++) 
        System.out.print(a[i][j]+" "); 
      System.out.println(); 
    } */   
  }
}

class MatrixAddThread extends Thread{

  private double [][] Atable;
  private double [][] Btable;
  private double [][] Ctable;
  private int myStart;
  private int myStop;
  private int  mySize;

  public MatrixAddThread(int myId, int numThreads, double[][] Aarray, double[][] Barray, double[][] Carray , int size){
    Atable = Aarray;
    Btable = Barray;
    Ctable = Carray;
    mySize = size;

    myStart = myId * (size / numThreads);
    myStop = myStart + (size / numThreads);
    if (myId == (numThreads - 1)) myStop = size;
 }

 public void run(){

  for(int i = myStart; i<myStop; i++){
      for(int j = 0; j<mySize; j++){
          Atable[i][j] = Btable[i][j] + Ctable[i][j];
      }
  }
}
}
