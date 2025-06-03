public class Consumer extends Thread {
    private Buffer buff;
    private int scale;

    // Constructor initializes buffer and sleep scale for consumer
    public Consumer(Buffer b, int s) {
        this.buff = b;
        this.scale = s;
    }

    // Consumer runs indefinitely consuming items with random delays
    public void run() {
        int value;
        while (true) {
            try {
                sleep((int)(Math.random() * scale));
            } catch (InterruptedException e) { }
            value = buff.get();
        }
    }
}
