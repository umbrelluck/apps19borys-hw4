package ua.edu.ucu.queue;

public class Stack {
    private ImmutableLinkedList array;

    public Stack() {
        array = new ImmutableLinkedList();
    }

    public Stack(Object e) {
        array = new ImmutableLinkedList(e);
    }

    public Object peek() {
        return array.getLast();
    }

    public Object pop() {
        Object elem = array.getLast();
        array = array.removeLast();
        return elem;
    }

    public void push(Object e) {
        array = array.addLast(e);
    }

    public String toString() {
        return array.toString();
    }

    public Object[] toArray() {
        return array.toArray();
    }
}
