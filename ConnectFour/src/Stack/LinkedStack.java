package Stack;
import java.util.EmptyStackException;

/**
 * @author Edward Conn
 * @param <T>
 */
public class LinkedStack<T> implements StackInterface<T>{
    private Node topNode;
    
    public LinkedStack(){
        topNode = null;
    }
    
    @Override
    public void push(T newItem) {
        Node newNode = new Node(newItem);
        newNode.setNext(topNode);
        topNode = newNode;
    }

    @Override
    public T peek() {
        if(!isEmpty()){
            return (T) topNode.getData();
        }else{
            throw new EmptyStackException();
        }
    }

    @Override
    public T pop() {
        T top = peek();
        assert !isEmpty();
        topNode = topNode.getNext();
        return top;

    }

    @Override
    public boolean isEmpty() {
        return topNode == null;
    }

    @Override
    public void clear() {
        topNode = null;
    }
    
    
    private class Node<T>{
        private T data;
        private Node<T> next;

        private Node(T data){
             this.data = data;
             this.next = null;
        }
        
        public void setData(T data){
            this.data = data;
        }
        
        public T getData(){
            return data;
        }
        
        public void setNext(Node next) {
            this.next = next;
        }

        public Node getNext() {
            return this.next;
        }
    }
}

