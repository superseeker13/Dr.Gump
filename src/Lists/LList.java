package Lists;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author sjw
 * @author Edward Conn
 * @author Micheal Schultz
 * @param <T>
 */
public class LList<T> implements ListWithListIterator<T>, Serializable {

    private Node firstNode;
    private Node lastNode;
    private int numberOfEntries = 0;

    /**
     * Return 0 if using 0-based indexing, 1 if using 1-based indexing.
     *
     * @return 0 or 1, if 0 or 1-indexed
     */
    public static int indexStartsAt() {
        return 0;
    }

    @Override
    public ListIterator<T> getIterator() {
        return (LinkedListIterator<T>) iterator();
    }

    @Override
    public int size() {
        return numberOfEntries;
    }

    @Override
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    @Override
    public boolean contains(Object o) {
        LinkedListIterator LLIt = (LinkedListIterator) getIterator();
        while (LLIt.hasNext()) {
            if (o.equals(LLIt.next())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public Object[] toArray() {
        Node tempNode = firstNode;
        Object[] objArray = new Object[numberOfEntries];
        int counter = 0;
        do {
            objArray[counter] = tempNode.getData();
            counter++;
        } while (tempNode.next != null);

        return objArray;
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean add(T e) {
        assert numberOfEntries >= 0;
        if (numberOfEntries > 0) {
            Node newNode = new Node(e);
            lastNode.setNext(newNode);
            newNode.setPrevious(lastNode);
            lastNode = newNode;
            lastNode.setNext(null);
        } else {
            Node node = new Node(e);
            firstNode = lastNode = node;
            firstNode.setNext(lastNode);
            lastNode.setPrevious(firstNode);
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        LinkedListIterator<T> currentIT = (LinkedListIterator<T>) getIterator();
        if (currentIT.hasNext()) {
            if (o.equals(currentIT.next())) {
                remove(currentIT.getIndex());
                numberOfEntries--;
                return true;
            }
        }
        throw new NoSuchElementException();
    }

    /*
    OPTIONAL: EXTRA CREDIT (+1)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().map((element) -> contains(element)).noneMatch((check) -> (!check));
    }

    /*
    OTPIONAL: EXTRA CREDIT (+1)
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        c.stream().map((element) -> {
            add(element);
            return element;
        }).forEachOrdered((_item) -> {
            numberOfEntries++;
        });
        return true;
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        c.stream().map((element) -> {
            add(index, element);
            return element;
        }).forEachOrdered((_item) -> {
            numberOfEntries++;
        });
        return true;
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void clear() {
        firstNode = null;
        lastNode = null;
        numberOfEntries = 0;
    }

    @Override
    public T get(int index) {
        LinkedListIterator<T> currentIT = (LinkedListIterator<T>) getIterator();
        Node n = currentIT.getNodeAt(index);
        assert n != null;
        return n.getData();
    }

    @Override
    public T set(int index, T element) {
        LinkedListIterator<T> currentIT = (LinkedListIterator<T>) getIterator();
        Node n = currentIT.getNodeAt(index);
        assert n != null;
        T oldValue = n.getData();
        n.setData(element);
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        LinkedListIterator<T> currentIT = (LinkedListIterator<T>) getIterator();
        Node newNode = new Node(element);
        Node nextNode = currentIT.getNodeAt(index);
        Node previousNode = nextNode.previous;

        if (index >= numberOfEntries) {
            this.add(element);
        } else {
            newNode.setPrevious(previousNode);
            newNode.setNext(nextNode);
            nextNode.setPrevious(newNode);
            previousNode.setNext(newNode);
            numberOfEntries++;
        }
    }

    @Override
    public T remove(int index) {
        LinkedListIterator<T> currentIT = (LinkedListIterator<T>) getIterator();
        if (index > 0 && index <= numberOfEntries) {
            Node removeNode = currentIT.getNodeAt(index - 1);
            if (removeNode.next != null) {
                Node nex = removeNode.next;
                nex.setPrevious(removeNode.previous);
            }
            if (removeNode.previous != null) {
                Node prev = removeNode.previous;
                prev.setNext(removeNode.next);
            }
            T data = removeNode.getData();
            removeNode.previous = null;
            removeNode.next = null;
            numberOfEntries--;
            return data;
        } else if (index == 0) {
            firstNode = firstNode.next;
            firstNode.setPrevious(null);
        }
        throw new NoSuchElementException();
    }

    @Override
    @SuppressWarnings("empty-statement")
    public int indexOf(Object o) {
        LinkedListIterator<T> currentIt = (LinkedListIterator<T>) getIterator();
        while (currentIt.hasNext() && !(currentIt.next()).equals(o));
        return currentIt.getIndex();
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public ListIterator<T> listIterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public T typeTCaster(Object e) {
        return (T) e;
    }

    private class Node implements Serializable{

        private T data;
        private Node next;
        private Node previous;

        public Node(T data) {
            this.data = data;
            next = null;
            previous = null;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public boolean hasNext() {
            return next != null;
        }

        public boolean hasPrevious() {
            return previous != null;
        }

        public Node next() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }

        public Node previous() {
            return previous;
        }

        public void setPrevious(Node previous) {
            this.previous = previous;
        }
    }

    private class LinkedListIterator<T> implements ListIterator<T> {

        Node nextNode;
        private boolean setOrRemoveLegal = false;
        private int index = 0;

        LinkedListIterator() {
            nextNode = firstNode;
        }

        @Override
        public boolean hasNext() {
            return index < numberOfEntries;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = (T) nextNode.getData();
                setOrRemoveLegal = true;
                nextNode = nextNode.next;
                index++;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return (nextNode.previous != null);
        }

        @Override
        public T previous() {
            if (nextNode.hasPrevious()) {
                assert index >= 0;
                index--;
                T data = (T) nextNode.previous;
                nextNode = nextNode.previous;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public int nextIndex() {
            return index + 1;
        }

        @Override
        public int previousIndex() {
            return index - 1;
        }

        @Override
        public void remove() {
            if (setOrRemoveLegal) {
                setOrRemoveLegal = false;
                LList.this.remove(index);
            } else {
                throw new NoSuchElementException("The list is empty or next was not called.\n");
            }
        }

        @Override
        public void set(T e) {
            if (setOrRemoveLegal) {
                setOrRemoveLegal = false;
                LList.this.set(index, LList.this.typeTCaster(e));
            } else {
                throw new NoSuchElementException("Next has not been called.\n");
            }

        }

        @Override
        public void add(T e) {
            LList.this.add(index, LList.this.typeTCaster(e));
        }

        public int getIndex() {
            return index;
        }

        private Node getNodeAt(int index) {
            if (index >= 0 && index < numberOfEntries) {
                Node current = firstNode;
                for (int i = 0; i < index; i++) {
                    current = current.next();
                }
                return current;
            }
            throw new NoSuchElementException();
        }
    }
}
