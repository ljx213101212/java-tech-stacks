package com.ljx213101212.concurrency_and_multithreading.task1;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadSafeMap <K, V> implements Map<K, V> {

    private final Map<K, V> internalMap;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public ThreadSafeMap() {
        this.internalMap = new HashMap<>();
    }

    @Override
    public int size() {
        rwLock.readLock().lock();
        try {
            return internalMap.size();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public boolean isEmpty() {
        rwLock.readLock().lock();
        try {
            return internalMap.isEmpty();
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        rwLock.readLock().lock();
        try {
            return internalMap.containsKey(key);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        rwLock.readLock().lock();
        try {
            return internalMap.containsValue(value);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public V get(Object key) {
        rwLock.readLock().lock();
        try {
            return internalMap.get(key);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public V put(K key, V value) {
        rwLock.writeLock().lock();
        try {
            return internalMap.put(key, value);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public V remove(Object key) {
        rwLock.writeLock().lock();
        try {
            return internalMap.remove(key);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        rwLock.writeLock().lock();
        try {
            internalMap.putAll(m);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void clear() {
        rwLock.writeLock().lock();
        try {
            internalMap.clear();
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public Set<K> keySet() {
        rwLock.readLock().lock();
        try {
            return new HashSet<>(internalMap.keySet());
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public Collection<V> values() {
        rwLock.readLock().lock();
        try {
            return new ArrayList<>(internalMap.values());
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        rwLock.readLock().lock();
        try {
            return new HashSet<>(internalMap.entrySet());
        } finally {
            rwLock.readLock().unlock();
        }
    }
}
