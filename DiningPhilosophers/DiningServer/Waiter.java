class Waiter {
    private int eatingPhilosophers = 0;

    // Only allow up to 4 philosophers to attempt to pick forks simultaneously to avoid deadlock
    public synchronized void requestPermission(int philosopherId) throws InterruptedException {
        while (eatingPhilosophers >= 4) {
            wait(); // Wait if 4 philosophers are already eating or picking forks
        }
        eatingPhilosophers++;
    }

    // Release permission after eating so others can try
    public synchronized void releasePermission() {
        eatingPhilosophers--;
        notifyAll(); // Notify waiting philosophers
    }
}