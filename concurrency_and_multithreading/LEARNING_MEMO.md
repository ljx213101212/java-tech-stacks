## ModCount, Fail-Fast, Fail-Safe mechanism

- The HashMap maintains an internal counter called modCount, which is incremented every time the map undergoes a structural modification (adding or removing keys).
- Fail-Fast Behavior: Iterator checks modCount , if it has changed,  iterator throws ConcurrentModificationException


### HashMap vs SynchronizedMap vs ConcurrentHashMap vs HashTable

- HashMap:  NOT thread-safe,  Fail-Fast
- SynchronizedMap:  thread-safe,  Fail-Fast
- ConcurrentHashMap:  thread-safe,  Fail-Safe
- HashTable: thread-safe, Fail-Fast, Legacy Java 1.0 (obsoleted, please use HashMap or ConcurrentHashMap)

#### When to Use SynchronizedMap
- Simple Synchronization Needs: If your synchronization needs are simple and the performance impact is negligible, synchronizedMap might be sufficient.
- Legacy Systems: When working within legacy codebases that already use synchronizedMap, and the cost of refactoring is high.
- Controlled Access Patterns: In situations where you can guarantee that modifications won't occur during iteration, or you can effectively manage synchronization externally.

### Starvation
By adding Thread.sleep(50) in indefinite loop will reduce starvation risk and CPU usage.

### adding "synchronized" modifier 
- To make the function scope thread-safe

### spurious wakeups
https://en.wikipedia.org/wiki/Spurious_wakeup
- It could be caused by the JVM or operating system signaling the waiting thread accidentally.
- Solution: always check the condition in a while loop, not an if statement.

### notify() vs signal()
- notify()
  - synchronized keyword and intrinsic locks ("monitor" lock).
  - eg.wait()
  - lacks fine-grained control, notify only one thread
  - ease of use, but not for complex scenario
- signal()
  - explicit locks (ReentrantLock and Condition objects)
  - eg. lock.lock()
  - better control , multiple conditions
  - complex to use and easy to make mistakes, but provide flexibility

### Memory Barrier
- Memory barriers are instructions that ensure memory operations are not reordered or cached in a way that would violate the expectations of a multithreaded program.
- They ensure visibility and ordering guarantees for memory operations.
- In Java, memory barriers are enforced using constructs like volatile and synchronized, which help avoid problems like stale values, out-of-order reads/writes, and race conditions.

### Thread scheduler
- start()
  - register the thread with the thread scheduler and begin its execution.
  - moves the thread from the NEW state to the READY state, making it eligible for execution by the thread scheduler.
  - internally invokes the run() method, which contains the code that the thread will execute. However, it also performs additional work like allocating system resources and registering the thread with the thread scheduler.

- run()
  - contains the code to be executed by the thread.
  - However, directly calling run() does not start a new thread or register it with the thread scheduler. Instead, it simply executes the run() method in the current thread, just like a normal method call.

- parallel execution/parallelism (in multi-core processors)
  - on a quad-core processor, different threads of the same process can be assigned to different cores, allowing for parallel execution.

### Concurrency vs Parallelism
- Parallelism is when multiple threads run simultaneously on different CPU cores, fully utilizing the available cores.
- Concurrency is when threads make progress independently, but not necessarily at the same time. On a single-core processor, threads may be time-sliced, meaning they take turns executing.

### How to reuse Thread instead new Thread everytime?
- use thread pool to manage worker threads, such as ExecutorService in task5