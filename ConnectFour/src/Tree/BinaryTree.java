package Tree;

import Stack.*;
import Lists.LList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public class BinaryTree<T> implements BinaryTreeInterface<T>, TreeIteratorInterface<T> {

    private BinaryNode<T> root;

    public BinaryTree(T data) {
        this.root = new BinaryNode<>(data);
        this.root.setData(data);
    }

    public BinaryTree(T data, BinaryNode<T> left, BinaryNode<T> right) {
        this.root = new BinaryNode<>(data);
        root.setLeft(left);
        root.setRight(right);
    }

    public BinaryTree(T root, BinaryTree<T> left, BinaryTree<T> right) {
        this.root = new BinaryNode<>(root);
        ((BinaryNode) root).setLeft(left.getRoot());
        ((BinaryNode) root).setRight(right.getRoot());
    }

    public BinaryNode<T> getRoot() {
        return root;
    }

    @Override
    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(BinaryNode<T> root) {
        int height = 0;
        if (root != null) {
            height = 1 + Math.max(getHeight(root.getLeft()),
                    getHeight(root.getRight()));
        }
        return height;
    }

    @Override
    public T getRootData() {
        return root.getData();
    }

    @Override
    public int getNumberOfNodes() {
        return root.getNumberOfNodes();
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void clear() {
        this.root = null;
    }

    @Override
    public Iterator<T> getInOrderIterator() {
        return new Iterator<T>() {
            private StackInterface<BinaryNode<T>> nodeStack
                    = new LinkedStack<>();
            private BinaryNode<T> currentNode;

            @Override
            public boolean hasNext() {
                return !nodeStack.isEmpty() || currentNode != null;
            }

            @Override
            public T next() {
                BinaryNode<T> nextNode = null;
                currentNode = root;
                while (currentNode != null) {
                    nodeStack.push(currentNode);
                    currentNode = currentNode.getLeft();
                }

                if (!nodeStack.isEmpty()) {
                    nextNode = nodeStack.pop();
                    assert nextNode != null;
                    currentNode = nextNode.getRight();
                } else {
                    throw new NoSuchElementException();
                }
                return nextNode.getData();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
