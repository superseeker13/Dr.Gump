package Tree;

import Lists.AList;
import java.io.Serializable;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public class GeneralNode<T> implements Serializable {

    private T data;
    private AList<GeneralNode<T>> children;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public AList<GeneralNode<T>> getChildren() {
        return children;
    }

    void setChildren(AList<GeneralNode<T>> children) {
        this.children = children;
    }

    public GeneralNode() {
        data = null;
        children = new AList(7);
    }

    public GeneralNode(T data) {
        this.data = data;
        children = new AList(7);
    }

    public GeneralNode(T data, AList<GeneralNode<T>> children) {
        this.data = data;
        children = this.children;
    }

    public boolean addChild(T data) {
        GeneralNode node = new GeneralNode(data);
        return children.add(node);
    }
}
