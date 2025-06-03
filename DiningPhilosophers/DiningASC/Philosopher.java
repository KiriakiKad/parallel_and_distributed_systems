class Philosopher extends Thread {
  private int identity;          // Philosopher ID
  private Fork first;            // First fork to pick up
  private Fork second;           // Second fork to pick up
  private int scale;             // Sleep scale (ms)
  
  Philosopher(int id, Fork f, Fork s, int sleeptime) {
      identity = id;
      first = f;  // The first fork to pick up
      second = s; // The second fork to pick up
      scale = sleeptime;
  }

  public void run() {
      while (true) {
          // Thinking
          System.out.println("Philosopher " + identity + " is thinking");
          delay(scale);

          // Hungry
          System.out.println("Philosopher " + identity + " is hungry");

          // Acquire forks in order
          first.get();
          System.out.println("Philosopher " + identity + " got first fork");

          second.get();
          System.out.println("Philosopher " + identity + " got second fork and is eating");

          // Eating
          delay(scale);

          // Release forks
          System.out.println("Philosopher " + identity + " is releasing forks");
          second.put();
          first.put();
      }
  }

  public void delay(int scale) {
      try {
          sleep((int) (Math.random() * scale));  // Sleep randomly up to scale
      } catch (InterruptedException e) { }
  }
}