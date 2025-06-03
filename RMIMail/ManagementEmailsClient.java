import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

// Client class to interact with the remote ManagementEmails service
public class ManagementEmailsClient
{
    private static final String HOST = "localhost";
    private static final int PORT = Registry.REGISTRY_PORT; // default 1099

    public static void main(String[] args)
    {
        try
        {
            // Locate the RMI registry running on the server
            Registry registry = LocateRegistry.getRegistry(HOST, PORT);

            // Lookup the remote object by its name
            String rmiObjectName = "ManagementEmails";
            ManagementEmails ref = (ManagementEmails) registry.lookup(rmiObjectName);

            // Invoke remote method to get email for "Panos"
            String emailPanos = ref.getEmail("Panos");
            System.out.println("The email of Panos is " + emailPanos);

            // Invoke remote method to add/update email for "George"
            boolean putResult = ref.putEmail("George", "george@yahoo.com");
            System.out.println("Put George email result: " + (putResult ? "Success" : "Failure"));

            // Invoke remote method to remove email for "John"
            boolean removeResult = ref.removeEmail("John");
            System.out.println("Remove John email result: " + (removeResult ? "Success" : "Failure"));

            // Check email of George after addition
            String emailGeorge = ref.getEmail("George");
            System.out.println("The email of George is " + emailGeorge);

            // Check email of John after removal
            String emailJohn = ref.getEmail("John");
            System.out.println("The email of John is " + (emailJohn != null ? emailJohn : "Not found"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
