class Philosopher extends Thread {
    private int identity;
    private Fork left;
    private Fork right;
    private int scale;

    Philosopher(int id, int s, Fork l, Fork r) {
        identity = id;
        scale = s;
        left = l;
        right = r;
    }

    public void run() {
        while (true) {
            // Philosopher is thinking
            System.out.println("Philosopher " + identity + " is thinking.");
            delay(scale);

            // Philosopher is hungry and tries to pick forks
            System.out.println("Philosopher " + identity + " is trying to get fork " + identity);

            // Pick forks in assigned order to avoid deadlock (even/odd)
            right.get();
            System.out.println("Philosopher " + identity + " got fork " + identity);

            left.get();
            System.out.println("Philosopher " + identity + " got fork " + (identity + 1) % 5);

            // Philosopher is eating
            System.out.println("Philosopher " + identity + " is eating.");
            delay(scale);

            // Release left fork
            System.out.println("Philosopher " + identity + " is releasing left fork " + (identity + 1) % 5);
            left.put();

            // Release right fork
            System.out.println("Philosopher " + identity + " is releasing fork " + identity);
            right.put();
        }
    }

    // Helper method to simulate delay (thinking or eating)
    public void delay(int scale) {
        try {
            sleep((int) (Math.random() * scale));
        } catch (InterruptedException e) { }
    }
}