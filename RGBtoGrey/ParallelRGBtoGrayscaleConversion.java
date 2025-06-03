import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/*
 * Performance Measurement Table (RGB to Grayscale)
 * ==============================================================================
 * Image                Size         Execution Time (ms) per Thread Count
 *                                  1 Thread   2 Threads   4 Threads   8 Threads
 * ------------------------------------------------------------------------------
 * 1house.jpg     		1.2 MB       267 ms    175 ms     166 ms     196 ms
 * 2aerial.jpg    		1.7 MB       614 ms    476 ms     420 ms     412 ms
 * 3tiger.jpg     		2.0 MB       1599 ms   955 ms     835 ms     874 ms
 * 4food.jpg      		2.6 MB       1552 ms   925 ms     886 ms     908 ms
 * 5landscape.jpg       2.9 MB       825 ms    784 ms     685 ms     642 ms
 * 6berries.jpg         3.9 MB       1074 ms   1396 ms    2128 ms    1956 ms
 * 7lake.jpg            6.1 MB       3130 ms   2495 ms    2347 ms    2111 ms
 * =============================================================================
 */


public class ParallelRGBtoGrayscaleConversion {
   public static void main(String args[]) {

		int numThreads = 8;//1 2 4 8
		
		String fileNameR = null;
		String fileNameW = null;
		
		//Input and Output files using command line arguments
		if (args.length != 2) {
			System.out.println("Usage: java RGBtoGrayScale <file to read > <file to write>");
			System.exit(1);
		}
		fileNameR = args[0];
		fileNameW = args[1];
		
				
		//Reading Input file to an image
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(fileNameR));
		} catch (IOException e) {}

        //Start timing
		long start = System.currentTimeMillis(); 
		
		//Coefficinets of R G B to GrayScale
		double redCoefficient = 0.299;
		double greenCoefficient = 0.587;
		double blueCoefficient = 0.114;
      
		
		int size = img.getHeight();

		// create threads
		RGBThread threads[] = new RGBThread [numThreads];

		for(int i = 0; i < numThreads; i++) 
		{
			threads[i] = new RGBThread(i, numThreads, size, img, redCoefficient, greenCoefficient, blueCoefficient);
			threads[i].start();
		}

		for(int i = 0; i < numThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {}
		}
	
	    //Stop timing
	    long elapsedTimeMillis = System.currentTimeMillis()-start;
       
		//Saving the modified image to Output file
		try {
		  File file = new File(fileNameW);
		  ImageIO.write(img, "jpg", file);
		} catch (IOException e) {}
      
		System.out.println("Done...");
		System.out.println("time in ms = "+ elapsedTimeMillis);
   }
}

class RGBThread extends Thread{

	private int myStart;
	private int myStop;

	private BufferedImage img;
	private double redCoefficient;
	private double greenCoefficient;
	private double blueCoefficient;
	

	public RGBThread(int myId, int numThreads, int size, BufferedImage image, double redCoefficient, double greenCoefficient, double blueCoefficient){

		img = image;
		this.redCoefficient = redCoefficient;
		this.greenCoefficient = greenCoefficient;
		this.blueCoefficient = blueCoefficient;

		myStart = myId * (size / numThreads);
		myStop = myStart + (size / numThreads);
		if (myId == (numThreads - 1)) myStop = size;
	}
	
	public void run(){
		      
		for (int y = myStart; y < myStop; y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				//Retrieving contents of a pixel
				int pixel = img.getRGB(x,y);
				//Creating a Color object from pixel value
				Color color = new Color(pixel, true);
				//Retrieving the R G B values, 8 bits per r,g,b
				//Calculating GrayScale
				int red = (int) (color.getRed() * redCoefficient);
				int green = (int) (color.getGreen() * greenCoefficient);
				int blue = (int) (color.getBlue() * blueCoefficient);
				//Creating new Color object
				color = new Color(red+green+blue, 
				                  red+green+blue, 
				                  red+green+blue);
				//Setting new Color object to the image
				img.setRGB(x, y, color.getRGB());
			}
		}
	}
}