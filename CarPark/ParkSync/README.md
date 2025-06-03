# 🚗 Car Parking Simulation using Java Monitors (`synchronized`, `wait`, `notifyAll`)

This Java program simulates a car parking system where multiple car threads attempt to park in a lot with limited capacity. The solution uses **Java's built-in monitor mechanism** (`synchronized`, `wait`, `notifyAll`) to safely manage shared state.

---

## 🧠 Overview

- The parking lot has a fixed capacity (e.g., 4 spots).
- Multiple `Car` threads arrive randomly, try to park, and leave after a short delay.
- Synchronization is achieved using:
  - Java's `synchronized` methods.
  - `wait()` and `notifyAll()` for condition signaling.
