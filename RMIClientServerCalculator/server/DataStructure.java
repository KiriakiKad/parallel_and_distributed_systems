// This class represents the structure of a mathematical operation request
public class DataStructure {

    private String op;   // The operator (e.g., "+", "-", "*", "/")
    private int numA;    // First operand
    private int numB;    // Second operand

    // Constructor that initializes the operator and operands
    public DataStructure(String operator, int numberA, int numberB) {
        op = operator;
        numA = numberA;
        numB = numberB;
    }

    // Getter for the operator
    public String getOp() {
        return op;
    }

    // Getter for the first operand
    public int getNumA() {
        return numA;
    }

    // Getter for the second operand
    public int getNumB() {
        return numB;
    }

    // Returns the result of addition
    public int addition() {
        return numA + numB;
    }

    // Returns the result of subtraction
    public int substraction() {
        return numA - numB;
    }

    // Returns the result of multiplication
    public int multiplication() {
        return numA * numB;
    }

    // Returns the result of division (note: assumes numB ≠ 0)
    public int division() {
        return numA / numB;
    }
}
