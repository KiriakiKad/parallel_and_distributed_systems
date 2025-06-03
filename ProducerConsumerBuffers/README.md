# Producer-Consumer Problem - Multiple Synchronization Approaches in Java

This repository contains several Java implementations of the classic **Producer-Consumer** concurrency problem, demonstrating different synchronization techniques and buffer management strategies.

---

## Project Structure

The project includes multiple folders/examples, each implementing the Producer-Consumer problem with a different approach:

### 1. Single-item Buffer using `synchronized`, `wait()`, and `notifyAll()`

- A simple buffer that holds only one item at a time.
- Uses intrinsic Java locks with `synchronized` methods.
- Uses `wait()` and `notifyAll()` for thread coordination.
- Suitable for learning the basics of thread communication.

### 2. Circular Buffer with `synchronized`, `wait()`, and `notifyAll()`

- Implements a bounded buffer with a fixed size as a circular queue.
- Supports multiple items in the buffer.
- Uses intrinsic locks plus `wait()` and `notifyAll()` to handle full and empty conditions.
- Demonstrates more realistic buffer management.

### 3. Buffer using Java `ArrayBlockingQueue`

- Replaces the manual synchronization with Java's built-in `ArrayBlockingQueue`.
- Internally manages locks and blocking behavior.
- Simplifies producer-consumer logic by delegating synchronization.
- Shows the advantage of using Java concurrent utilities.

### 4. (Optional) Semaphore-based Buffer

- Uses `Semaphore` objects to manage access to the buffer slots.
- Shows explicit semaphore usage to control producer and consumer access.
- Useful for understanding semaphore-based synchronization.

---

## Common Features

- Multiple producers and consumers running as separate threads.
- Configurable parameters: buffer size, number of producers/consumers, number of items produced, and delays.
- Console output showing the state of the buffer and actions of producers/consumers.
- Demonstrates handling of race conditions and synchronization correctness.

---

## How to Use

1. Select the folder/example you want to run.
2. Modify parameters such as buffer size, delays, and iterations inside the main class.
3. Compile and run the `ProducerConsumer` main class.
4. Observe console logs to see how producers and consumers interact with the buffer.
5. Compare synchronization methods and their impact on concurrency and performance.

---

## Learning Objectives

- Understand different synchronization techniques in Java (`synchronized`, `wait/notify`, `Semaphore`, concurrent queues).
- Learn how to implement and manage bounded buffers safely in multithreaded environments.
- Compare manual locking with built-in Java concurrency utilities.
- Explore practical solutions to prevent deadlocks, race conditions, and data inconsistency.

---

## Notes

- The examples use Java but the concepts apply to concurrency programming in general.
- Other languages provide similar synchronization primitives or concurrent data structures (e.g., Python's `Queue`).
- Using Java's concurrency utilities like `ArrayBlockingQueue` usually simplifies code and reduces errors.

---

## Recommended Next Steps

- Experiment with varying numbers of producers/consumers and buffer sizes.
- Try to modify or extend the code to add features like priority, multiple buffer types, or timeout handling.
- Study the trade-offs between each synchronization approach regarding complexity and efficiency.
