import java.rmi.registry.*;
import java.rmi.server.*;

public class CalculatorServer {

    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT; // Default RMI port 1099

    public static void main(String[] args) throws Exception {

        // Set the hostname property for the RMI server
        System.setProperty("java.rmi.server.hostname", HOST);

        // Create remote object implementation
        Calculator robj = new CalculatorImpl();

        // Create and start the RMI registry on the specified port
        Registry registry = LocateRegistry.createRegistry(PORT);

        // Bind the remote object to a name in the registry
        String rmiObjectName = "MyCalculator";
        registry.rebind(rmiObjectName, robj);
        System.out.println("Remote object bounded.");

        // Wait for user input to terminate the server
        System.out.println("Press <Enter> for exit.");
        System.in.read();

        // Unbind and unexport the remote object from the RMI system
        UnicastRemoteObject.unexportObject(robj, true);
        registry.unbind(rmiObjectName);
        System.out.println("Remote object unbounded.");
    }
}
