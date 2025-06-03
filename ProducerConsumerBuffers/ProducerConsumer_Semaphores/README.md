# Producer-Consumer Problem Using Semaphores

## Overview

This project implements the classic Producer-Consumer problem using semaphores for synchronization and mutual exclusion.

Instead of using intrinsic locks (`synchronized`), it uses Java's `Semaphore` class to manage concurrent access to a bounded buffer. Semaphores control the number of permits available for producers and consumers, ensuring proper coordination between threads.

## Components

- **Buffer**: A fixed-size circular buffer (array) that stores integers. Access to the buffer is controlled via semaphores to ensure thread safety.
- **Producer**: A thread that produces data items and inserts them into the buffer. It acquires the necessary semaphore permits before inserting to avoid overflow.
- **Consumer**: A thread that consumes data items from the buffer. It acquires semaphore permits to avoid underflow.
- **ProducerConsumer**: The main class that initializes the buffer and starts producer and consumer threads.

## How It Works

- **Semaphores**:
  - `emptySlots`: Counts the number of empty slots available in the buffer. Producers must acquire this semaphore before inserting data.
  - `fullSlots`: Counts the number of filled slots in the buffer. Consumers must acquire this semaphore before removing data.
  - `mutex`: A binary semaphore (mutex) to protect the critical section where the buffer is modified.
  
- Producers wait if the buffer is full (i.e., no empty slots).
- Consumers wait if the buffer is empty (i.e., no full slots).
- The `mutex` semaphore guarantees that only one thread accesses the buffer at a time, preventing race conditions.

