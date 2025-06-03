import java.rmi.*;
import java.rmi.registry.*;
import java.io.*;

public class CalculatorClient {

    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT; // default RMI port 1099
    private static final String EXIT = "!";

    public static void main(String[] args) {
        try {
            // Connect to the RMI registry on the specified host and port
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);

            // Look up the remote object using its name in the registry
            String rmiObjectName = "MyCalculator";
            Calculator ref = (Calculator) registry.lookup(rmiObjectName);

            // Read user input from console
            BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
            String request;

            System.out.print("Enter operation (e.g., + 4 5 or ! to quit): ");
            request = user.readLine();

            // Send requests until user types "!"
            while (!request.equals(EXIT)) {
                // Call the remote method on the server
                String reply = ref.calculate(request);

                // Display server's response
                System.out.println("Message received from server: " + reply);

                // Prompt for next input
                System.out.print("Enter operation: ");
                request = user.readLine();
            }

            System.out.println("Client terminated.");

        } catch (RemoteException re) {
            System.out.println("Remote Exception");
            re.printStackTrace();
        } catch (Exception e) {
            System.out.println("Other Exception");
            e.printStackTrace();
        }
    }
}
