package Tree;

import Lists.LList;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public class BinaryNode<T> {

    private T data;
    private BinaryNode<T> left;
    private BinaryNode<T> right;

    public BinaryNode(T root) {
        this.data = root;
    }

    public BinaryNode(T root, BinaryNode<T> left, BinaryNode<T> right) {
        this.data = root;
        this.left = left;
        this.right = right;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public int getNumberOfNodes() {
        int leftNumber = 0;
        int rightNumber = 0;

        if (left != null) {
            leftNumber = left.getNumberOfNodes();
        }
        if (right != null) {
            rightNumber = right.getNumberOfNodes();
        }

        return 1 + leftNumber + rightNumber;
    }

    public LList getLeftChildren() {
        LList<T> treeList = new LList<>();
        BinaryNode<T> currentNode = left;
        while(currentNode != null)
        {
            treeList.add((T) currentNode);
        }        
        return treeList;
    }
}
