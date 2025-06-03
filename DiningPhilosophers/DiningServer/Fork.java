class Fork {
    private boolean isTaken = false;

    // Synchronized method to pick up the fork
    public synchronized void pickUp(int philosopherId) throws InterruptedException {
        while (isTaken) {
            wait(); // Wait if fork is already taken
        }
        isTaken = true;
        System.out.println("Philosopher " + philosopherId + " picked up a fork.");
    }

    // Synchronized method to put down the fork
    public synchronized void putDown(int philosopherId) {
        isTaken = false;
        System.out.println("Philosopher " + philosopherId + " put down a fork.");
        notifyAll(); // Notify waiting philosophers that fork is available
    }
}