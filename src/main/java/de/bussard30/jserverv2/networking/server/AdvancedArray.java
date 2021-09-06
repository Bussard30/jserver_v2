package de.bussard30.jserverv2.networking.server;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public class AdvancedArray<T> implements Iterable<T> {
    private final T[] array;
    private final int length;
    private final AtomicInteger offset = new AtomicInteger();
    private volatile boolean concurrentModification;

    public AdvancedArray(Class<T> c, int length) {

        offset.set(0);
        @SuppressWarnings("unchecked") final T[] array = (T[]) Array.newInstance(c, length);
        this.array = array;
        this.length = length;
    }

    public void put(T object, int index) {
        if (!concurrentModification) {
            if (index >= length) {
                index = index % length;
                //throw new ArrayIndexOutOfBoundsException("Index [" + index + "] is out of bounds.");
            }
            if ((index + offset.get()) >= length) {
                array[(index + offset.get()) - length] = object;
            } else {
                array[index + offset.get()] = object;
            }
        } else
            throw new ConcurrentModificationException();
    }

    public T get(int index) {
        if (!concurrentModification) {
            if ((index + offset.get()) >= length) {
                return array[(index + offset.get()) - length];
            } else {
                return array[index + offset.get()];
            }
        } else
            throw new ConcurrentModificationException();
    }

    public void removeFirst() {
        if (!concurrentModification) {
            array[offset.get()] = null;
            if (offset.addAndGet(1) >= length)
                offset.set(offset.get() - length);
        } else
            throw new ConcurrentModificationException();
    }

    public int getLength() {
        return length;
    }

    public int getOffset() {
        return offset.get();
    }

    public AtomicInteger getAtomicOffset() {
        return offset;
    }

    public boolean concurrentModification() {
        return concurrentModification;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private final int l = length;
            private int currentIndex = offset.get();

            @Override
            public boolean hasNext() {
                if (currentIndex == (offset.get() + l)) {
                    concurrentModification = false;
                    return false;
                } else
                    return true;

            }

            @Override
            public T next() {
                concurrentModification = true;
                T object;
                if (currentIndex >= length) {
                    object = get(currentIndex - length);
                } else {
                    object = get(currentIndex);
                }
                currentIndex++;

                return object;
            }
        };
    }
}
