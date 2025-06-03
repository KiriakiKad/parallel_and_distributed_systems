# Producer-Consumer Problem Using Semaphores with Multi-slot Buffer

## Overview

This project implements the Producer-Consumer problem using semaphores to coordinate multiple producers and consumers sharing a multi-slot (bounded) buffer.

It extends the classical semaphore solution by managing a buffer that can hold multiple items simultaneously, using semaphores for synchronization and mutual exclusion to prevent race conditions.

## Components

- **Buffer**: A circular array buffer storing integer data items. It uses semaphores to track available slots and filled slots, and a mutex semaphore to protect critical sections.
- **Producer**: Threads that produce data items and insert them into the buffer. Producers wait when the buffer is full.
- **Consumer**: Threads that consume data items from the buffer. Consumers wait when the buffer is empty.
- **ProducerConsumer**: The main class which initializes the buffer, starts producer and consumer threads, and controls parameters such as the number of threads and buffer size.

## Synchronization Mechanism

- **Semaphores used:**
  - `emptySlots`: Counts the number of free slots in the buffer. Producers acquire this before producing.
  - `fullSlots`: Counts the number of occupied slots. Consumers acquire this before consuming.
  - `mutex`: Binary semaphore ensuring mutual exclusion while accessing or modifying the buffer.

- Producers block when the buffer is full (no empty slots).
- Consumers block when the buffer is empty (no full slots).
- Access to the buffer is protected by the mutex semaphore to prevent concurrent modifications.
