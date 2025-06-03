# Dining Philosophers - Ascending Fork Locking Solution

This implementation avoids deadlocks by enforcing an ordering rule for picking up forks:

- Each philosopher always picks up the fork with the smaller ID first, then the larger one.
- This prevents circular wait, which is a necessary condition for deadlocks.

Forks are implemented using `ReentrantLock` to ensure mutual exclusion.

## How it works

- Philosophers numbered 0 to 3 pick up their left fork first, then right.
- The last philosopher picks up the right fork first, then left.
- This ascending order of fork acquisition breaks the circular wait condition.

## Running the code

Compile and run the `Diners.java` file inside this folder to see the solution in action.

---

Refer to the source code comments for detailed explanations.
