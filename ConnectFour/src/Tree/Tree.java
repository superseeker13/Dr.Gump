package Tree;

import Lists.AList;

/**
 *
 * @author
 * @param <T>
 */
public class Tree<T> {

    private TreeNode<T> root;

    public Tree(T rootData) {
        root = new TreeNode<>();
        root.data = rootData;
        root.children = new AList<>();
    }

    private static class TreeNode<T> {

        private T data;
        private TreeNode<T> parent;
        private AList children;
    }
}
