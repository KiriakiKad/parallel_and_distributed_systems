import java.rmi.*;
import java.rmi.server.*;

public class CalculatorImpl extends UnicastRemoteObject implements Calculator {

    private static final long serialVersionUID = 1;

    // Constructor required for exporting the remote object
    public CalculatorImpl() throws RemoteException {
    }

    // Remote method implementation
    public String calculate(String request) throws RemoteException {
        System.out.println("Received message from client: " + request);

        // Parse the request into a structured format
        DataStructure data = getRequestData(request);

        if (data == null) {
            return "Invalid Input";
        }

        String op = data.getOp();
        int result;
        String reply;

        // Handle each operation type accordingly
        switch (op) {
            case "+":
                result = data.addition();
                reply = buildReplyMessage(result);
                break;
            case "-":
                result = data.substraction();
                reply = buildReplyMessage(result);
                break;
            case "*":
                result = data.multiplication();
                reply = buildReplyMessage(result);
                break;
            case "/":
                if (data.getNumB() == 0) {
                    reply = "Division by zero";
                } else {
                    result = data.division();
                    reply = buildReplyMessage(result);
                }
                break;
            case "!":
                reply = "EXIT!";
                break;
            default:
                reply = "Unknown operator";
                break;
        }

        System.out.println("Send message to client: " + reply);
        return reply;
    }

    // Helper method to build the reply message
    private String buildReplyMessage(int computationResult) {
        return "Result " + computationResult;
    }

    // Helper method to parse and validate the input string
    private DataStructure getRequestData(String request) {
        String[] parts = request.trim().split(" ");

        // Expecting format: <op> <a> <b>, or just "!" to exit
        if (parts.length < 3 && !parts[0].equals("!")) return null;

        String op = parts[0];
        int a = 0, b = 0;

        try {
            if (!op.equals("!")) {
                a = Integer.parseInt(parts[1]);
                b = Integer.parseInt(parts[2]);
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format");
            return null;
        }

        return new DataStructure(op, a, b);
    }
}
