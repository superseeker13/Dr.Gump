package Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 *
 * @author sjw
 * @author Edward Conn
 * @param <T>
 */
public class AList<T> implements ListWithListIterator<T>{
    int numberOfEntries = 0;
    final int DEFAULT_CAPACITY = 25;
    final int MAX_CAPACITY = 500;
    int capacity = DEFAULT_CAPACITY;
    T[] list;
    
    /**
     * Return 0 if using 0-based indexing, 1 if using 1-based indexing.
     *
     * @return 0 or 1, if 0 or 1-indexed
     */
    public static int indexStartsAt() {
        return 0;
    }

    public AList() {
        this.list = (T[]) new Object[DEFAULT_CAPACITY];
    }

    public T[] getList() {
        return list;
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
            if (o.equals(LLIt.next())){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator(numberOfEntries);
    }

    @Override
    public Object[] toArray() {
        return list;
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
        assert size() >= 0;
        if(!isListFull()){
            list[size()] = e;
        }else if(capacity >= MAX_CAPACITY){
            return false;
        }else if( capacity * 2 <= MAX_CAPACITY){
           capacity *= 2;
        }else{
            capacity = MAX_CAPACITY;
            T[] tempArray = list;
            list = (T[]) new Object[capacity];
            System.arraycopy(tempArray, 0, list, 0, tempArray.length );
        }
        numberOfEntries++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        LinkedListIterator<T> currentIT = (LinkedListIterator<T>) getIterator();
        if (currentIT.hasNext()) {
            if(o.equals(currentIT.next())){
                remove(currentIT.getIndex());
                numberOfEntries--;
                return true;
            }
        }
            throw new NoSuchElementException();
    }

    public boolean isListFull(){
        return numberOfEntries >= capacity;
    }
    /*
    OPTIONAL: EXTRA CREDIT (+1)
     */
    @Override
    public boolean containsAll(Collection<?> c) {
        return c.stream().noneMatch((element) -> (!contains (element)));
    }

    /*
    OTPIONAL: EXTRA CREDIT (+1)
     */
    @Override
    public boolean addAll(Collection<? extends T> c) {
        for (T element : c) {
            add(element);
            numberOfEntries++;
        }
       return true;
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        for (T element : c) {
            add(index,element);
            numberOfEntries++;
        }
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
       for(int i = size(); i > 0; i--){
           remove(i - 1);
       }
        numberOfEntries = 0;
    }

    @Override
    public T get(int index) {
        return list[index];
    }

    @Override
    public T set(int index, T element) {
        T oldValue = list[index];
        list[index] = element;
        return oldValue;
    }

    @Override
    public void add(int index, T element) {
            if(index == size() && index < capacity){
                list[index] = element; 
            }else if(index >= 0 && index < size()){
                list[index] = element;
                //TODO: move list
            }
    }

    @Override
    public T remove(int index) {
        if(index >= 0 && index < size()){
            T value = list[index];
            for(int i = index; i < size(); i++){
                list[i] = list[i+1];
            }
            numberOfEntries--;
            return value;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int indexOf(Object o) {
        LinkedListIterator<T> currentIt = (LinkedListIterator<T>) getIterator();
        while (currentIt.hasNext() && !(currentIt.next()).equals(o)) {
            currentIt.next();
        }
        return currentIt.getIndex();
    }

    /*
    YOU DO NOT NEED TO IMPLEMENT THIS METHOD
     */
    @Override
    public int lastIndexOf(Object o) {
        LinkedListIterator<T> currentIt = (LinkedListIterator<T>) getIterator();
        boolean found = false;
        int index;
        while (currentIt.hasNext()) {
            currentIt.next();
            if((currentIt.next()).equals(o)){
                index = currentIt.getIndex();
                found = true;
            }
        }
        if(found){
            return currentIt.getIndex();
        }
        throw new NoSuchElementException();
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
    
    public T typeTCaster(Object e){
        return (T) e;
        }

    
    public int numberOf(T e){
            int result = 0;
            Iterator it = AList.this.getIterator();
            while(it.hasNext()){
                if(it.next().equals(e)){
                    result++;
                }
            }
            return result;
        }
    
    private class LinkedListIterator<T> implements ListIterator<T> {

        private boolean setOrRemoveLegal = false;
        private int index = 0;
        int numberOfEntries;

        private LinkedListIterator(){
        }
        
        LinkedListIterator(int numberOfEntries){
            this.numberOfEntries = numberOfEntries;
        }

        @Override
        public boolean hasNext() {
            return index < numberOfEntries;
        }

        @Override
        public T next() {
            if (hasNext()) {
                T data = (T) AList.this.list[index];
                setOrRemoveLegal = true;
                index++;
                return data;
            }
            throw new NoSuchElementException();
        }

        @Override
        public boolean hasPrevious() {
            return (index > 0);
        }

        @Override
        public T previous() {
            if (hasPrevious()) {
                T data = (T) AList.this.list[index - 1];
                index--;
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
                AList.this.remove(index - 1);
                numberOfEntries--;
                if(index > 0){
                    index--;
                }
            }else{
                throw new NoSuchElementException("The list is empty or next was not called.\n");
            }
        }

        @Override
        public void set(T e) {
            if (setOrRemoveLegal) {
                setOrRemoveLegal = false;
                AList.this.set(index, AList.this.typeTCaster(e));
            } else {
                throw new NoSuchElementException("Next has not been called.\n");
            }

        }

        @Override
        public void add(T e) {
            AList.this.add(index, AList.this.typeTCaster(e));
            numberOfEntries++;
        }

        public int getIndex() {
            return index;
        }
        
        private T getDataAt(int index) {
            T data = null;
            if (index >= 0 && index < numberOfEntries) {
                for (int i = 0; i < index; i++) {
                    data = (T) list[i];
                }
                return data;
            }
            throw new NoSuchElementException();
        }
    }
}