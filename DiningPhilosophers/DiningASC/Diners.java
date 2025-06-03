public class Diners {

     static final int numphils = 5;     // Number of philosophers
     static final int sleeptime = 1;    // Sleep time scale for thinking/eating
     
     public static void main(String[] args) {
         Philosopher[] phil = new Philosopher[numphils];
         Fork[] fork = new Fork[numphils];
 
         // Create forks
         for (int i = 0; i < numphils; ++i) {
             fork[i] = new Fork(i);
         }
 
         // Create philosophers
         for (int i = 0; i < numphils; ++i) {
             if (i == numphils - 1) {
                 // Last philosopher picks up right fork first then left
                 phil[i] = new Philosopher(i, fork[(i + 1) % numphils], fork[i], sleeptime);
             } else {
                 // Others pick up left fork first then right
                 phil[i] = new Philosopher(i, fork[i], fork[(i + 1) % numphils], sleeptime);
             }
             phil[i].start();
        }
    }
}