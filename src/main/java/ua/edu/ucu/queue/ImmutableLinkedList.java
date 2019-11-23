package ua.edu.ucu.queue;

public final class ImmutableLinkedList implements ImmutableList {

    private Object value;
    private ImmutableLinkedList next;
    private ImmutableLinkedList previous;

    public ImmutableLinkedList() {
        value = null;
        next = null;
        previous = null;
    }

    public ImmutableLinkedList(Object e) {
        value = e;
        next = null;
        previous = null;
    }

    @Override
    public int size() {
        if (this.value == null) {
            return 0;
        }
        ImmutableLinkedList index = this;
        int i = 0;
        while (index != null) {
            index = index.next;
            i += 1;
        }
        return i;
    }

    public ImmutableLinkedList last() {
        ImmutableLinkedList lastElem = this;
        while (lastElem.next != null) {
            lastElem = lastElem.next;
        }
        return lastElem;
    }

    private ImmutableLinkedList copy() {
        ImmutableLinkedList newElem = new ImmutableLinkedList();
        ImmutableLinkedList newElemIter = newElem;
        ImmutableLinkedList thisIterator = this;
        while (thisIterator != null) {
            if (newElem.value == null) {
                newElem.value = thisIterator.value;
            } else {
                newElemIter.next = new
                        ImmutableLinkedList(thisIterator.value);
                newElemIter.next.previous = newElemIter;
                newElemIter = newElemIter.next;
            }
            thisIterator = thisIterator.next;
        }
        return newElem;
    }

    @Override
    public ImmutableLinkedList add(Object e) {
        ImmutableLinkedList newElem = copy();
        if (newElem.value == null) {
            newElem.value = e;
            return newElem;
        }
        ImmutableLinkedList lastElem = newElem.last();
        lastElem.next = new ImmutableLinkedList(e);
        lastElem.next.previous = lastElem;
        return newElem;
    }

    @Override
    public ImmutableLinkedList add(int index, Object e) {
        if (index > size()) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size()) {
            return add(e);
        }
        ImmutableLinkedList newElem = copy();
        if (index == 0) {
            ImmutableLinkedList fe = new ImmutableLinkedList(e);
            fe.next = newElem;
            newElem.previous = fe;
            newElem = newElem.previous;
        } else {
            ImmutableLinkedList indexElem = newElem;
            int i = 0;
            while (i < index - 1 && indexElem.next != null) {
                i++;
                indexElem = indexElem.next;
            }
            ImmutableLinkedList tail = indexElem.next;
            indexElem.next = new ImmutableLinkedList(e);
            indexElem.next.previous = indexElem;

            indexElem.next.next = tail;
            tail.previous = indexElem.next;
        }
        return newElem;
    }

    @Override
    public ImmutableLinkedList addAll(Object[] c) {
        ImmutableLinkedList newElem = copy();
        for (Object elem : c) {
            newElem = newElem.add(elem);
        }
        return newElem;
    }

    @Override
    public ImmutableLinkedList addAll(int index, Object[] c) {
        ImmutableLinkedList newElem = copy();
        int i = index;
        for (Object elem : c) {
            newElem = newElem.add(i, elem);
            i++;
        }
        return newElem;
    }

    @Override
    public Object get(int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        int i = 0;
        ImmutableLinkedList indElem = this;
        while (i != index) {
            i++;
            indElem = indElem.next;
        }
        return indElem.value;
    }

    private ImmutableLinkedList helperInd(ImmutableLinkedList newElem,
                                          int index) {
        if (index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList indElem = newElem;
        int i = 0;
        while (i != index) {
            i++;
            indElem = indElem.next;
        }
        return indElem;
    }

    @Override
    public ImmutableLinkedList remove(int index) {
        if (index >= size() || index == -1) {
            throw new IndexOutOfBoundsException();
        }
        ImmutableLinkedList newElem = copy();
        if (index == 0) {
            return newElem.next;
        }
        ImmutableLinkedList indElem = helperInd(newElem, index);
        indElem.previous.next = indElem.next;
        if (indElem.next != null) {
            indElem.next.previous = indElem.previous;
        }
        return newElem;
    }

    @Override
    public ImmutableLinkedList set(int index, Object e) {
        ImmutableLinkedList newElem = copy();
        ImmutableLinkedList indElem = helperInd(newElem, index);
        indElem.value = e;
        return newElem;
    }

    @Override
    public int indexOf(Object e) {
        int i = 0;
        ImmutableLinkedList indexof = this;
        while (indexof != null) {
            if (indexof.value.equals(e)) {
                return i;
            }
            i++;
            indexof = indexof.next;
        }
        return -1;
    }

    @Override
    public ImmutableLinkedList clear() {
        return new ImmutableLinkedList();
    }

    @Override
    public boolean isEmpty() {
        return this.value == null;
    }

    @Override
    public Object[] toArray() {
        ImmutableLinkedList iter = this;
        int sz = size();
        if (sz == 0) {
            return new Object[0];
        }
        Object[] array = new Object[size()];
        for (int i = 0; i < sz; i++, iter = iter.next) {
            array[i] = iter.value;
        }
        return array;
    }

    @Override
    public String toString() {
        ImmutableLinkedList iter = this;
        StringBuilder str = new StringBuilder();
        while (iter != null) {
            if (iter.next == null) {
                str.append(iter.value);
            } else {
                str.append(iter.value).append(", ");
            }
            iter = iter.next;
        }
        return str.toString();
    }

    public ImmutableLinkedList addFirst(Object e) {
        return add(0, e);
    }

    public ImmutableLinkedList addLast(Object e) {
        return add(e);
    }

    public Object getFirst() {
        return this.value;
    }

    public Object getLast() {
        return last().value;
    }

    public ImmutableLinkedList removeFirst() {
        return remove(0);
    }

    public ImmutableLinkedList removeLast() {
        return remove(size() - 1);
    }


}