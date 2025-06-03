class Philosopher extends Thread {
    private int id;
    private Fork leftFork;
    private Fork rightFork;
    private Waiter waiter;

    public Philosopher(int id, Fork leftFork, Fork rightFork, Waiter waiter) {
        this.id = id;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.waiter = waiter;
    }

    public void run() {
        try {
            while (true) {
                think();

                waiter.requestPermission(id); // Request permission from waiter before picking forks

                leftFork.pickUp(id);  // Pick left fork
                rightFork.pickUp(id); // Pick right fork

                eat();

                leftFork.putDown(id);  // Put down left fork
                rightFork.putDown(id); // Put down right fork

                waiter.releasePermission(); // Inform waiter that eating is finished
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Simulate thinking
    private void think() throws InterruptedException {
        synchronized (this) {
            System.out.println("Philosopher " + id + " is thinking.");
            wait(500); // Pause for thinking time
        }
    }

    // Simulate eating
    private void eat() throws InterruptedException {
        synchronized (this) {
            System.out.println("Philosopher " + id + " is eating.");
            wait(500); // Pause for eating time
        }
    }
}