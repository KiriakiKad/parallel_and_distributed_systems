class Philosopher extends Thread {
  private final int id;
  private final Table table;
  private final int scale;

  Philosopher(int id, Table table, int scale) {
      this.id = id;
      this.table = table;
      this.scale = scale;
  }

  public void run() {
      while (true) {
          think();
          table.pickUpForks(id);
          eat();
          table.putDownForks(id);
      }
  }

  private void think() {
      System.out.println("Philosopher " + id + " is thinking.");
      delay();
  }

  private void eat() {
      System.out.println("Philosopher " + id + " is eating.");
      delay();
  }

  private void delay() {
      try {
          sleep((int) (Math.random() * scale));
      } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
      }
  }
}