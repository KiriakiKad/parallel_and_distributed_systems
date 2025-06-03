# Dining Philosophers - Odd and Even Fork Picking Solution

This solution prevents deadlocks by alternating the order in which philosophers pick up forks based on whether their ID is odd or even:

- Even-numbered philosophers pick up their left fork first, then the right fork.
- Odd-numbered philosophers pick up their right fork first, then the left fork.

By alternating the fork acquisition order, the circular wait condition that causes deadlocks is avoided.

Forks are implemented using `ReentrantLock` to ensure thread safety.

## How it works

- Philosophers try to acquire forks in an order depending on their ID parity.
- This breaks the circular waiting pattern.
- Random delays simulate thinking and eating times.

## Running the code

Compile and run the `Diners.java` file in this folder to test the solution.

---

Refer to the source code comments for detailed explanations.
