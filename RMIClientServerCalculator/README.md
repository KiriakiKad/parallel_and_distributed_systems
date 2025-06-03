RMI Client-Server Calculator
============================

This project implements a distributed calculator system using Java RMI (Remote Method Invocation). 
It allows a client to remotely perform arithmetic operations (addition, subtraction, multiplication, division) 
on a server.

Project Structure:
------------------
RMIClientServerCalculator/
1 client/
  - Calculator.java           ( Remote interface)
  - CalculatorClient.java     ( Client that uses the remote calculator)
2 server/
  - Calculator.java           ( Remote interface (same as client))
  - CalculatorImpl.java       ( Implementation of Calculator interface)
  - CalculatorServer.java     ( Server that registers the remote object)
  - DataStructure.java        ( Class holding calculation data)

How to Run:
-----------
1. Compile all `.java` files:

   From the root directory of the project, run:
   javac client/*.java server/*.java

2. Start the RMI Registry:

   Open a terminal in the root project directory and run:
   rmiregistry

   (Leave this terminal open. If port 1099 is in use, another registry may already be running.)

3. Run the Server:

   In a new terminal window:
   java server.CalculatorServer

4. Run the Client:

   In another terminal window:
   java client.CalculatorClient

Notes:
------
- Make sure all terminals are opened in the project root directory so relative paths resolve correctly.
- Tested using Java 17.
