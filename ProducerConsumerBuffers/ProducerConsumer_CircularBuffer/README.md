# Producer-Consumer Problem Using Circular Buffer

## Overview

This project implements the classic Producer-Consumer problem using a custom Circular Buffer with synchronized methods. The buffer is implemented as a fixed-size circular array that supports concurrent insertion (by producers) and removal (by consumers) of items.

The implementation ensures mutual exclusion and coordination using Java's intrinsic locks (`synchronized`), `wait()`, and `notifyAll()` mechanisms to handle the conditions of buffer full and empty states.

## Components

- **Buffer**: A circular buffer that holds integers. It uses an array internally with `front`, `back`, and `counter` variables to keep track of positions and the current number of elements.
- **Producer**: A thread that produces integers and inserts them into the buffer, waiting if the buffer is full.
- **Consumer**: A thread that consumes integers from the buffer, waiting if the buffer is empty.
- **ProducerConsumer**: The main class which creates a buffer and starts producer and consumer threads.

## How It Works

- The buffer uses a circular array to efficiently reuse space when items are removed.
- When the buffer is full, producers wait until consumers consume items.
- When the buffer is empty, consumers wait until producers produce new items.
- Both producers and consumers notify each other when the buffer state changes, enabling smooth synchronization.
