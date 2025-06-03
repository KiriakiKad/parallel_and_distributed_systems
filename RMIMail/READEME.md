# RMImail - Java RMI Email Management System

This project demonstrates a simple use of Java RMI (Remote Method Invocation) to manage user emails remotely.

## 📁 Contents

- `ManagementEmails.java` — Remote interface defining email management methods.
- `ManagementEmailsImpl.java` — Implementation of the remote object.
- `ManagementEmailsServer.java` — RMI server that registers the remote object.
- `ManagementEmailsClient.java` — RMI client that interacts with the remote service.

## ⚙️ Functionality

The system allows remote clients to:

- Add or update an email for a user (`putEmail`)
- Retrieve the email of a user (`getEmail`)
- Remove a user’s email (`removeEmail`)

Each method returns a success or failure message depending on the outcome.
