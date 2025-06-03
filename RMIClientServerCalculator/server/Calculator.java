import java.rmi.*;

public interface Calculator extends Remote {
    public String calculate(String request) throws RemoteException;
}
