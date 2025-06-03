import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

// Implementation of the remote interface ManagementEmails
public class ManagementEmailsImpl extends UnicastRemoteObject implements ManagementEmails
{
    // HashMap to store name-email pairs in-memory
    private HashMap<String, String> storeEmails;

    // Constructor initializes the email store with some data
    public ManagementEmailsImpl() throws RemoteException
    {
        storeEmails = new HashMap<String, String>();
        storeEmails.put("Panos", "panosm@uom.gr");
        storeEmails.put("John", "johnf@gmail.com");
    }

    // Returns the email associated with the given name or null if not found
    public String getEmail(String name) throws RemoteException
    {
        return storeEmails.get(name);
    }

    // Adds or updates the email for a given name
    // Always returns true because put always succeeds (replaces or adds)
    public boolean putEmail(String name, String email) throws RemoteException
    {
        storeEmails.put(name, email);
        return true;
    }

    // Removes the email for the given name
    // Returns true if an email was removed, false if no such name existed
    public boolean removeEmail(String name) throws RemoteException
    {
        if (storeEmails.containsKey(name)) {
            storeEmails.remove(name);
            return true;
        } else {
            return false;
        }
    }
}
