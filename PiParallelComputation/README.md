# PiParallelComputation

This folder contains three different implementations of the mathematical constant π (pi) calculation using numerical integration. The goal is to compare sequential and parallel approaches, as well as different synchronization techniques in multithreaded environments.

## 📁 Files Included

| File Name                              | Description                                                                 |
|----------------------------------------|-----------------------------------------------------------------------------|
| `PiComputationSequential.java`         | Sequential implementation of pi computation using a single thread.         |
| `PiComputationWithLock.java`           | Correct parallel implementation using local sums and a single final `lock`. |
| `PiComputationWithFrequentLocking.java`| Incorrect parallel implementation using `lock` within every loop iteration, demonstrating performance degradation. |

## 🧪 Computation Method

The numerical method used is: ( x = (i + 0.5) * step and step = 1.0 / numSteps.)
```math
π ≈ ∑ (4 / (1 + x²)) * step
math
