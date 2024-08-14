package org.print3d.DataStructures;

import java.util.Iterator;

public class Set<T> implements Iterable<T> {
    private DoubleLinkedList<T> list;

    public Set() {
        list = new DoubleLinkedList<>();
    }

    public boolean add(T element) {
        if (!contains(element)) {
            list.add(element);
            return true;
        }
        return false;
    }

    public boolean remove(T element) {
        if (contains(element)) {
            list.remove(element);
            return true;
        }
        return false;
    }

    public boolean contains(T element) {
        T[] array = (T[]) new Object[list.size()];
        array = list.toArray(array);
        for (T item : array) {
            if (item.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        T[] array = (T[]) new Object[list.size()];
        array = list.toArray(array);
        T[] finalArray = array;
        return new Iterator<T>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < finalArray.length;
            }

            @Override
            public T next() {
                return finalArray[currentIndex++];
            }
        };
    }
}