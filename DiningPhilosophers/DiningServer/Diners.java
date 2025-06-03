public class Diners {
    static final int numPhils = 5;

    public static void main(String[] args) {
        Philosopher[] philosophers = new Philosopher[numPhils];
        Fork[] forks = new Fork[numPhils];
        Waiter waiter = new Waiter(); // The waiter controls access to forks to avoid deadlock

        // Initialize forks
        for (int i = 0; i < numPhils; i++) {
            forks[i] = new Fork();
        }

        // Initialize philosophers and assign forks and waiter
        for (int i = 0; i < numPhils; i++) {
            philosophers[i] = new Philosopher(i, forks[i], forks[(i + 1) % numPhils], waiter);
            philosophers[i].start();
        }
    }
}