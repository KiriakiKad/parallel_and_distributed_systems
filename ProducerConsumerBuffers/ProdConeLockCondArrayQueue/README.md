# Producer-Consumer Problem Using ArrayBlockingQueue

## Overview

This project demonstrates a classic Producer-Consumer problem implementation using Java's `ArrayBlockingQueue` from the `java.util.concurrent` package.

The `ArrayBlockingQueue` class handles thread synchronization internally by providing automatic locking and condition management for the `put` and `take` operations. Producers block when the queue is full, and consumers block when the queue is empty, ensuring safe concurrent access without explicit lock handling.

## Components

- **Buffer**: Wraps an `ArrayBlockingQueue<Integer>` and provides `put` and `get` methods for inserting and removing elements. It also prints the current state of the queue.
- **Producer**: A thread that produces data and inserts it into the buffer with random delays.
- **Consumer**: A thread that consumes data from the buffer with random delays.
- **ProducerConsumer**: The main class that initializes the buffer and starts multiple producer and consumer threads.
