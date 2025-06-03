# Producer-Consumer Problem: Single Producer Single Consumer (SPSC) with Single-Item Buffer

## Overview

This project implements the classic Producer-Consumer problem with **one producer** and **one consumer** sharing a single-item buffer. It demonstrates synchronization using Java’s intrinsic locks (`synchronized` keyword), `wait()`, and `notifyAll()` methods to coordinate the producer and consumer threads.

## Components

- **Buffer**: A buffer that can hold only a single integer item at a time. It maintains flags to indicate whether it is empty or full, and uses synchronized methods to enforce correct access.
- **Producer**: A thread that produces integer data items and puts them into the buffer. It waits if the buffer is full.
- **Consumer**: A thread that consumes data items from the buffer. It waits if the buffer is empty.
- **ProducerConsumer**: The main class which initializes the buffer, starts one producer thread and one consumer thread, and sets parameters such as the number of iterations and thread delays.

## Synchronization Mechanism

- The buffer uses `synchronized` methods `put()` and `get()` to ensure mutual exclusion.
- The producer waits when the buffer is full.
- The consumer waits when the buffer is empty.
- `wait()` and `notifyAll()` are used for thread communication and to wake threads when the buffer state changes.
