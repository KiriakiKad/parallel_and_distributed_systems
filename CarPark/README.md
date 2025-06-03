# Car Parking Simulation - Parallel and Distributed Systems

This project contains several implementations of a car parking simulation, demonstrating different synchronization mechanisms in Java for managing concurrent access to a limited resource (parking spaces).

---

## Overview

- The simulation models a parking lot with a fixed number of spaces (`capacity`).
- Multiple `Car` threads attempt to arrive, park, and depart repeatedly.
- The synchronization problem ensures cars only park when there is a free spot, and prevents race conditions in updating shared state.
- Each implementation uses a different concurrency control mechanism to solve the problem:

  1. **ParkLockCond** — Uses `java.util.concurrent.locks.Lock` and `Condition` variables.
  2. **ParkSemaphore** — Uses Java `Semaphore`s.
  3. **ParkSync** — Uses Java intrinsic monitors with `synchronized`, `wait()`, and `notifyAll()`.

---

## Implementations

### 1. ParkLockCond (Lock and Condition)

- Utilizes `ReentrantLock` and two `Condition` variables (`bufferFull`, `bufferEmpty`).
- Threads wait on conditions if the parking lot is full or empty.
- Provides fine-grained control of locking and signaling.

### 2. ParkSem (Semaphore-based)

- Uses semaphores to control access and track free and occupied spots.
- Semaphores `bufferEmpty` and `bufferFull` represent available and occupied slots.
- A mutex semaphore ensures atomic updates of shared state.

### 3. ParkMon (Java Monitors)

- Uses Java’s built-in monitor mechanism (`synchronized` methods).
- Employs `wait()` and `notifyAll()` to handle thread waiting and signaling.
- Simpler, classical approach to synchronization.

---

## Common Features

- All implementations feature:
  - A fixed capacity parking lot.
  - Multiple car threads (`20` in the example).
  - Cars looping through arrive → park → depart cycles.
  - Printing of arrival, parking, departure events and free space count.
  - Proper synchronization to avoid race conditions and incorrect states.
  - Randomized delays to simulate real-life parking times and arrivals.


