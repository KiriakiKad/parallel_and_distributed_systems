public class Diners {

    static final int numphils = 5;
    static final int sleeptime = 1000;
    
    
    public static void main(String[] args) {
     Philosopher[] phil = new Philosopher[numphils];
     Table table = new Table(numphils);

     for (int i = 0; i < numphils; i++) {
         phil[i] = new Philosopher(i, table, sleeptime);
         phil[i].start();
     }
 }

}
