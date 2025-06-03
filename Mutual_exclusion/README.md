# Parallel and Distributed Systems - Shared Variable Synchronization Examples

This repository contains 10 Java classes that demonstrate the use of shared variables and mutual exclusion using threads and `ReentrantLock`. These examples focus on various synchronization techniques such as global locking, fine-grained locking, object-based locking, and method-level synchronization to safely handle concurrent access in a multi-threaded environment.

---

## 📁 Folder Structure

### `Mutual_exclusion/`

This folder includes implementations for:

- Global and fine-grained locking strategies
- Synchronization of shared counters using threads
- Safe concurrent updates to arrays with locks
- Thread-safe counter objects with different locking mechanisms
- Use of concurrent collections and synchronization in multi-threaded contexts

---

## 📄 Included Examples

| Class Name                                            | Description                                                                                                              |
|-------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------|
| `SharedCounterGlobalLockExample.java`                 | Uses a global `ReentrantLock` to protect access to all array elements. Demonstrates coarse-grained synchronization with shared counter. |
| `SharedCounterFineGrainedLockExample.java`            | Demonstrates fine-grained locking where each array index has its own lock. Threads safely increment elements concurrently.                    |
| `SharedCounterObjectLockExample.java`                  | Shows how a counter object with a `ReentrantLock` protects concurrent increments in a multi-threaded environment.                              |
| `SharedCounterObjectSynchronizedMethodExample.java`   | Counter is incremented safely using a `synchronized` method. Illustrates method-level synchronization.                                               |
| `SharedCounterGlobalWhileExample.java`                 | Combines a global counter and `while` loop to allocate work to threads, ensuring no overlap with synchronized access.                            |
| `SharedCounterArrayNoSyncExample.java`                 | Demonstrates a race condition when multiple threads update a shared array without any synchronization.                                          |
| `SharedCounterArrayGlobalLockExample.java`             | Threads update array elements using a shared global lock, avoiding race conditions but with reduced concurrency.                                 |
| `SharedCounterArrayFineLockExample.java`               | Each array index is protected with an individual `ReentrantLock`, allowing concurrent updates safely.                                           |
| `SharedCounterGlobalWhileHashMap.java`        | Uses a global counter with synchronized increment and `ConcurrentHashMap` for thread-safe key-value updates. |
| `SharedCounterArrayCopyOnWriteArrayList.java` | Uses `CopyOnWriteArrayList` for safe concurrent updates to an array-like structure with coarse synchronization. |
