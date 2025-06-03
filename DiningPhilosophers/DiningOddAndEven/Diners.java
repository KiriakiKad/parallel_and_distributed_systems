public class Diners {

    static final int numphils = 5;
    static final int sleeptime = 1000;
    
    public static void main(String[] args) {
        Philosopher[] phil = new Philosopher[numphils];
        Fork[] fork = new Fork[numphils];

        // Initialize forks
        for (int i = 0; i < numphils; ++i)
            fork[i] = new Fork(i);

        // Create philosophers and assign forks
        for (int i = 0; i < numphils; ++i) {
            // Even philosophers pick left fork first, odd pick right fork first
            if (i % 2 == 0) {
                phil[i] = new Philosopher(i, sleeptime, fork[i], fork[(i + 1) % numphils]);  // Left -> Right
            } else {
                phil[i] = new Philosopher(i, sleeptime, fork[(i + 1) % numphils], fork[i]);  // Right -> Left
            }
            phil[i].start();
        }
    }
}