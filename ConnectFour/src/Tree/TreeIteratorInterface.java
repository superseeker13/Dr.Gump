package Tree;

import java.util.Iterator;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public interface TreeIteratorInterface<T> {
    public Iterator<T> getInOrderIterator();
}
