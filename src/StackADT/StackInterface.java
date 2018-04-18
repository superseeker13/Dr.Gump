package StackADT;
/**
 *
 * @author Edward Conn
 * @param <T>
 */
public interface StackInterface<T>{
    public void push(T newItem);
    public T peek();
    public T pop();
    public boolean isEmpty(); 
    public void clear(); 
}
