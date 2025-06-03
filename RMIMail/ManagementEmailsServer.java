import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

// Server class to host the ManagementEmails remote object
public class ManagementEmailsServer
{
    private static final String HOST = "localhost";  // Hostname for RMI server
    private static final int PORT = Registry.REGISTRY_PORT; // Default RMI registry port (1099)

    public static void main(String[] args) throws Exception
    {
        // Set system property to specify the RMI server hostname/IP
        // Important especially if server is not localhost
        System.setProperty("java.rmi.server.hostname", HOST);

        // Create the remote object implementation instance
        ManagementEmailsImpl robj = new ManagementEmailsImpl();

        Registry registry = LocateRegistry.getRegistry(PORT);

        // Bind the remote object with a name in the RMI registry so clients can look it up
        String rmiObjectName = "ManagementEmails";
        registry.rebind(rmiObjectName, robj);
        System.out.println("Remote object bound to registry with name: " + rmiObjectName);

        // Keep the server running until user presses Enter
        System.out.println("Server is running. Press <Enter> to shut down.");
        System.in.read();

        // Cleanup: unbind the remote object and unexport it
        registry.unbind(rmiObjectName);
        UnicastRemoteObject.unexportObject(robj, true);
        System.out.println("Remote object unbound and server stopped.");
    }
}
