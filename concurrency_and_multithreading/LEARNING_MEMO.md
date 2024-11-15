## ModCount, Fail-Fast, Fail-Safe mechanism

- The HashMap maintains an internal counter called modCount, which is incremented every time the map undergoes a structural modification (adding or removing keys).
- Fail-Fast Behavior: Iterator checks modCount , if it has changed,  iterator throws ConcurrentModificationException


### HashMap vs SynchronizedMap vs ConcurrentHashMap

- HashMap:  NOT thread-safe,  Fail-Fast
- SynchronizedMap:  thread-safe,  Fail-Fast
- ConcurrentHashMap:  thread-safe,  Fail-Safe

#### When to Use SynchronizedMap
- Simple Synchronization Needs: If your synchronization needs are simple and the performance impact is negligible, synchronizedMap might be sufficient.
- Legacy Systems: When working within legacy codebases that already use synchronizedMap, and the cost of refactoring is high.
- Controlled Access Patterns: In situations where you can guarantee that modifications won't occur during iteration, or you can effectively manage synchronization externally.

### Starvation
By adding Thread.sleep(50) in indefinite loop will reduce starvation risk

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


