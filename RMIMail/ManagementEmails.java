import java.rmi.Remote;
import java.rmi.RemoteException;

// Remote interface for managing emails over RMI
public interface ManagementEmails extends Remote
{
    // Get email by name
    String getEmail(String name) throws RemoteException;

    // Add or update email by name, returns true if successful
    boolean putEmail(String name, String email) throws RemoteException;

    // Remove email by name, returns true if the email existed and was removed
    boolean removeEmail(String name) throws RemoteException;
}
