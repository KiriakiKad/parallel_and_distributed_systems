# 🚗 Car Parking Simulation with ReentrantLock and Condition

This Java project simulates a multi-threaded car parking system using **`ReentrantLock`** and **`Condition`** variables to safely coordinate cars trying to enter and exit a parking lot with limited capacity.

---

## 🧠 Overview

- A parking lot has a fixed number of parking spaces (`capacity`).
- Multiple cars (threads) arrive randomly and try to park.
- If no space is available, cars wait until a spot is freed.
- Cars park for a short time, then depart.
- The system ensures correct synchronization using `Lock` and `Condition` variables.
