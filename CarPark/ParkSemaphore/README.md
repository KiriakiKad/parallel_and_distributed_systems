# 🚗 Car Parking Simulation using Semaphores

This Java project simulates a car parking system using **Semaphores** to control access to a limited number of parking spaces in a thread-safe way.

---

## 🧠 Overview

- A parking lot has a fixed capacity (`capacity`).
- Multiple cars (threads) arrive, attempt to park, wait if full, and depart after a short stay.
- Synchronization is handled using:
  - A **binary semaphore (`mutex`)** for mutual exclusion.
  - A **counting semaphore (`bufferEmpty`)** for available spaces.
  - A **counting semaphore (`bufferFull`)** for cars ready to leave.
