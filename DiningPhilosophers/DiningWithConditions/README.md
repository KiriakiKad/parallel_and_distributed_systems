# Dining Philosophers - Solution Using Locks and Conditions

This solution uses Java's `ReentrantLock` and `Condition` objects to coordinate philosophers.

## How it works

- A shared `Table` object manages the state of each philosopher (`THINKING`, `HUNGRY`, `EATING`).
- Each philosopher tries to pick up forks by requesting the Table to check if neighbors are eating.
- If neighbors are eating, the philosopher waits on its own `Condition`.
- When a philosopher finishes eating, it signals neighbors so they can try to eat.
- This approach prevents deadlocks by avoiding circular wait and ensuring mutual exclusion.

## Key Concepts

- Fine-grained locking using a `Lock` per shared resource (the Table).
- Use of `Condition` variables to suspend/resume philosophers safely.
- States tracked per philosopher to coordinate access.

## Running the code

Compile and run the `Diners.java` file in this folder.

---

Refer to inline comments for detailed implementation notes.
