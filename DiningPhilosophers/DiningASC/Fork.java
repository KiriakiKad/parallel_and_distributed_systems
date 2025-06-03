class Fork {

  private int identity;           // Fork ID
  private Lock lock = new ReentrantLock();  // Lock to control access to fork

  Fork(int id)
    {identity = id;}

  void get() {
  	lock.lock();   // Acquire the lock on this fork
  }

  void put() {
       lock.unlock();  // Release the lock on this fork
  }
}
