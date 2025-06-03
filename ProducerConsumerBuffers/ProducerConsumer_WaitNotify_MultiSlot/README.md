# Producer-Consumer Problem: Multi-slot Buffer with Wait/Notify Synchronization

## Overview

This project implements the classic Producer-Consumer problem with multiple slots in the buffer, using Java's `wait()` and `notifyAll()` methods for synchronization. The buffer is a bounded circular queue allowing concurrent producers and consumers to put and take items safely.

## Components

- **Buffer**: A circular buffer (array) of configurable size that holds multiple integer items. It uses intrinsic locking (`synchronized` methods) along with `wait()` and `notifyAll()` to coordinate access.
- **Producer**: A thread that generates integer items and inserts them into the buffer. If the buffer is full, the producer waits.
- **Consumer**: A thread that removes items from the buffer. If the buffer is empty, the consumer waits.
- **ProducerConsumer**: The main class that initializes the buffer and launches multiple producers and consumers with configurable parameters such as number of iterations and delays.

## Synchronization Details

- Both `put()` and `get()` methods in the buffer are `synchronized`.
- If the buffer is full, producers call `wait()` until notified.
- If the buffer is empty, consumers call `wait()` until notified.
- `notifyAll()` wakes up waiting threads when the buffer state changes, ensuring smooth coordination.
- The buffer uses `front` and `back` indices and a `counter` to manage the circular queue logic.
