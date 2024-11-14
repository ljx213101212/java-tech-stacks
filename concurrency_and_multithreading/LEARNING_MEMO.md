## ModCount, Fail-Fast, Fail-Safe mechanism

- The HashMap maintains an internal counter called modCount, which is incremented every time the map undergoes a structural modification (adding or removing keys).
- Fail-Fast Behavior: Iterator checks modCount , if it has changed,  iterator throws ConcurrentModificationException


### HashMap vs synchronizedMap vs ConcurrentHashMap

HashMap:  NOT thread-safe,  Fail-Fast
SynchronizedMap:  thread-safe,  Fail-Fast
ConcurrentHashMap:  thread-safe,  Fail-Safe

#### when to Use SynchronizedMap
- Simple Synchronization Needs: If your synchronization needs are simple and the performance impact is negligible, synchronizedMap might be sufficient.
- Legacy Systems: When working within legacy codebases that already use synchronizedMap, and the cost of refactoring is high.
- Controlled Access Patterns: In situations where you can guarantee that modifications won't occur during iteration, or you can effectively manage synchronization externally.

### Starvation
By adding Thread.sleep(50) in indefinite loop will reduce starvation risk