package Tree;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public interface TreeInterface<T> {
    public T getRootData();
    public int getHeight();
    public int getNumberOfNodes();
    public boolean isEmpty();
    public void clear();
}
