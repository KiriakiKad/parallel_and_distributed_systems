import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Εcoli_BruteForceDNAStringMatch {
    public static void main(String args[]) throws IOException {

        int numThreads = Runtime.getRuntime().availableProcessors();
    
        if (args.length != 2) {
			System.out.println("BruteForceStringMatch  <file name> <pattern>");
			System.exit(1);
        }

        String fileString = new String(Files.readAllBytes(Paths.get(args[0])));//, StandardCharsets.UTF_8);
        char[] text = new char[fileString.length()]; 
        int n = fileString.length();
        for (int i = 0; i < n; i++) { 
            text[i] = fileString.charAt(i); 
        } 
 
        String patternString = new String(args[1]);                                                
        char[] pattern = new char[patternString.length()]; 
        int m = patternString.length(); 
        for (int i = 0; i < m; i++) { 
            pattern[i] = patternString.charAt(i); 
        }

        int matchLength = n - m;
        char[] match = new char[matchLength+1]; 
        for (int i = 0; i <= matchLength; i++) { 
            match[i] = '0'; 
        }
        
        // get current time
		long start = System.currentTimeMillis();

        ECThread threads[] = new ECThread[numThreads];

        		
    
        // thread execution   
		for (int i = 0; i < numThreads; i++) 
		{
			threads[i] = new ECThread(i, numThreads, matchLength, match, pattern, m, text);
			threads[i].start();
		}


        int matchCount = 0;
                     
        for (int i = 0; i < numThreads; i++) {
            try {
                threads[i].join();
                matchCount = matchCount + threads[i].myCount;
            } catch (InterruptedException e) {}
        }

        // get current time and calculate elapsed time
		long elapsedTimeMillis = System.currentTimeMillis()-start;
		System.out.println("time in ms = "+ elapsedTimeMillis);
		
		
        for (int i = 0; i < matchLength; i++) { 
            if (match[i] == '1') System.out.print(i+" ");
        }
        System.out.println();
        System.out.println("Total matches " + matchCount);
        
   }
}

class ECThread extends Thread {


    private char [] match;
    private char [] pattern;
    private char [] text;
    private int m;
	private int myId;
	private int myStart;
	private int myStop;
    public int myCount;

    public ECThread(int i, int numThreads, int matchLength, char[] match, char[] pattern, int m, char[] text) 
    {
        this.match = match;
        this.pattern = pattern;
        this.text = text;
        this.m = m;
        myCount = 0;
        myId = i;
        myStart = myId * (matchLength / numThreads);
		myStop = myStart + (matchLength / numThreads);
		if (myId == (numThreads - 1)) myStop = matchLength;
    }

    public void run(){

        for (int j = myStart; j < myStop; j++) {
			int i;
      		for (i = 0; i < m && pattern[i] == text[i + j]; i++);
				if (i >= m) {
	         		match[j] = '1';
					myCount++;
                }        
        }
    }

    
}
