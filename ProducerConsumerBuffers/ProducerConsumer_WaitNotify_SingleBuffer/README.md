# Producer-Consumer Problem: Single-slot Buffer with Wait/Notify Synchronization

## Overview

This project implements the classic Producer-Consumer problem using a single-slot buffer. The synchronization between producer and consumer threads is managed using Java's `wait()` and `notifyAll()` methods. This approach ensures that the producer waits when the buffer is full, and the consumer waits when the buffer is empty.

## Components

- **Buffer**: A single-slot buffer holding one integer item. It uses synchronized methods along with `wait()` and `notifyAll()` to manage safe access.
- **Producer**: A thread that generates integer items and puts them into the buffer. It waits if the buffer is full.
- **Consumer**: A thread that takes items from the buffer. It waits if the buffer is empty.
- **ProducerConsumer**: The main class that initializes the buffer and starts producer and consumer threads with configurable parameters.

## Synchronization Details

- The buffer's `put()` and `get()` methods are synchronized.
- When the buffer is full, the producer thread waits.
- When the buffer is empty, the consumer thread waits.
- `notifyAll()` is called to wake waiting threads after a state change (put or get).
