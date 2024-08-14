package org.print3d.DataStructures;

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

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int actualIndex = (head + index) % capacity;
        return elements[actualIndex];
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(get(i));
        }
        sb.append("]");
        return sb.toString();
    }
}