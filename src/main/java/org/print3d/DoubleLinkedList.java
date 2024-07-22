package org.print3d;

import java.lang.reflect.Array;

@SuppressWarnings("ALL")
public class DoubleLinkedList<T> {
    private Node<T> head;
    private Node<T> tail;

    public boolean isEmpty() {
        return head == null;
    }

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.prev = null;
            this.next = null;
        }
    }

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        if (head == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    public void remove(T data) {
        Node<T> current = head;
        while (current != null) {
            if (current.data.equals(data)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }
                if (current.next != null) {
                    current.next.prev = current.prev;
                } else {
                    tail = current.prev;
                }
                return;
            }
            current = current.next;
        }
    }

    public T[] toArray(T[] array) {
        if (array.length < size()) array = (T[]) Array.newInstance(array.getClass().getComponentType(), size());
        int i = 0;
        for (Node<T> x = head; x != null; x = x.next)
            array[i++] = x.data;
        return array;
    }

    public int size() {
        int size = 0;
        Node<T> current = head;
        while (current != null) {
            size++;
            current = current.next;
        }
        return size;
    }
}