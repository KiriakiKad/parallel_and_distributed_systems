# Dining Philosophers - Waiter (Server) Solution

This solution introduces a **Waiter** (or server) to coordinate the philosophers and prevent deadlocks.

## How it works

- A Waiter object controls permission for philosophers to pick up forks.
- Philosophers must request permission from the Waiter before trying to pick up forks.
- The Waiter allows at most 4 philosophers to attempt picking up forks simultaneously (one less than the total).
- This prevents the circular wait condition and thus deadlocks.
- Forks are managed with simple synchronized methods using `wait()` and `notifyAll()`.

## Advantages

- Centralized control simplifies deadlock prevention.
- Fair access to forks ensured by the Waiter.

## Running the code

Compile and run the `Diners.java` file inside this folder.

---

See inline comments for more detailed explanations.
