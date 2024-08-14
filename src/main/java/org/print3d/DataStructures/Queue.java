package org.print3d.DataStructures;

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Queue<T> implements Iterable<T> {
    private DoubleLinkedList<T> list;

    public Queue() {
        list = new DoubleLinkedList<>();
    }

    public void offer(T item) {
        list.add(item);
    }

    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T item = peek();
        list.remove(item);
        return item;
    }

    public T remove() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return poll();
    }

    public T peek() {
        if (isEmpty()) {
            return null;
        }
        T[] array = (T[]) new Object[1];
        return list.toArray(array)[0];
    }

    public T element() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return peek();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private T[] array = (T[]) list.toArray((T[]) new Object[list.size()]);
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < array.length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return array[currentIndex++];
            }
        };
    }
}