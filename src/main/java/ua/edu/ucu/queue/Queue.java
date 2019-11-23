package ua.edu.ucu.queue;

public class Queue {
    private ImmutableLinkedList array;

    public Queue() {
        array = new ImmutableLinkedList();
    }

    public Queue(Object e) {
        array = new ImmutableLinkedList(e);
    }

    public Object peek() {
        return array.getFirst();
    }

    public Object dequeue() {
        Object elem = array.getFirst();
        array = array.removeFirst();
        return elem;
    }

    public void enqueue(Object e) {
        array = array.addLast(e);
    }

    @Override
    public String toString() {
        return array.toString();
    }

    public Object[] toArray() {
        return array.toArray();
    }
}
