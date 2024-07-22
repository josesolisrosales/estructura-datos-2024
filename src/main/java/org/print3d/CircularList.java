package org.print3d;

import java.util.ArrayList;
import java.util.List;

public class CircularList<T> {
    private final T[] elements;
    private final int capacity;
    private int size;
    private int head;
    private int tail;

    @SuppressWarnings("unchecked")
    public CircularList(int capacity) {
        this.capacity = capacity;
        this.elements = (T[]) new Object[capacity];
        this.size = 0;
        this.head = 0;
        this.tail = 0;
    }

    public void add(T element) {
        elements[tail] = element;
        tail = (tail + 1) % capacity;

        if (size < capacity) {
            size++;
        } else {
            head = (head + 1) % capacity;
        }
    }

    public List<T> getElements() {
        List<T> result = new ArrayList<>(size);
        int current = head;
        for (int i = 0; i < size; i++) {
            result.add(elements[current]);
            current = (current + 1) % capacity;
        }
        return result;
    }

    public int size() {
        return size;
    }

}