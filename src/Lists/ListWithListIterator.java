package Lists;

import java.util.List;
import java.util.ListIterator;
/**
 *
 * @author sjw
 * @param <T>
 */
public interface ListWithListIterator<T> extends List<T>, Iterable<T> {
    public ListIterator<T> getIterator();
}
