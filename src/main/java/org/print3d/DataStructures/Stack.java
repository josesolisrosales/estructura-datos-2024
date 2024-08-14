package org.print3d.DataStructures;

import java.lang.reflect.Array;
import java.util.EmptyStackException;
import java.util.Iterator;

public class Stack<T> implements Iterable<T> {
    private DoubleLinkedList<T> list;

    public Stack() {
        list = new DoubleLinkedList<>();
    }

    public void push(T item) {
        list.add(item);
    }

    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T item = peek();
        list.remove(item);
        return item;
    }

    public T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        T[] array = toArray((T[]) new Object[1]);
        return array[array.length - 1];
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int size() {
        return list.size();
    }

    public T[] toArray() {
        return toArray((T[]) new Object[size()]);
    }

    public T[] toArray(T[] a) {
        if (a.length < size()) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size());
        }
        T[] result = list.toArray(a);
        // Reverse the array to maintain stack order (last in, first out)
        for (int i = 0; i < result.length / 2; i++) {
            T temp = result[i];
            result[i] = result[result.length - 1 - i];
            result[result.length - 1 - i] = temp;
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private T[] array = toArray();
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < array.length;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new EmptyStackException();
                }
                return array[currentIndex++];
            }
        };
    }
}