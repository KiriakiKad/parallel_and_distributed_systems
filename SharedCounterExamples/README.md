# Parallel and Distributed Systems - Concurrency Examples

This repository contains a collection of Java examples demonstrating concepts of concurrency, shared variables, and mutual exclusion using locks and threads. The examples are organized into folders based on different approaches and synchronization techniques.

---

## Folder Structure

- `SharedCounterExamples/`  
  Contains Java classes illustrating various concurrency problems and solutions involving shared counters and arrays. These examples demonstrate:
  - No synchronization (race conditions)
  - Synchronization using `synchronized` keyword
  - Synchronization using `Lock` (ReentrantLock)
  - Using busy-wait loops with locks
  - Shared data protection with mutual exclusion

---

## Included Examples in `SharedCounterExamples/`

| Class Name                                       | Description                                                                                                             |
|-------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| `SharedCounterNoSync.java`                        | Demonstrates incrementing a shared counter by multiple threads **without synchronization**, leading to race conditions. |
| `SharedCounterWithLock.java`                      | Shows correct synchronization using a **ReentrantLock** to safely increment a shared counter.                           |
| `SharedCounterGlobalLockExample.java`             | Protects multiple shared variables using a **single global lock**.                                                      |
| `SharedCounterGlobalWhileExample.java`            | Uses a global lock with a **while loop** for controlled incrementing and synchronization.                               |
| `SharedArrayNoSync.java`                          | Shows a race condition when threads update a shared array without synchronization.                                      |
| `SharedCounterWithSharedDataAndCounterLock.java`  | Combines locks on shared counter and shared data to avoid race conditions.                                              |
| `SharedCounterWithSharedDataLock.java`            | Uses locks to synchronize access to shared data structures separately from counters.                                    |
| `SharedObjectIncrement.java`                      | Demonstrates thread-safe incrementing of a counter encapsulated inside an object with synchronization.                  |
| `SharedObjectThreadingWithCounter.java`           | Illustrates threading with shared objects and synchronized methods for safe counter increments.                         |
| `SharedCounterWithLockAndWhile.java`              | Combines the use of a **ReentrantLock** and a **while loop** for safe increments in a multi-threaded environment.       |

