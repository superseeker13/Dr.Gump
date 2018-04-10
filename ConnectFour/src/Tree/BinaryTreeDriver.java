package Tree;
/**
 *
 * @author Edward Conn
 */
public class BinaryTreeDriver {
    public static void main(String args[]){
        BinaryTree<String> treeL = new BinaryTree<>("L");
        BinaryTree<String> treeR = new BinaryTree<>("R");
        BinaryTree<String> tree = new BinaryTree<>("Root", treeL.getRoot(), treeR.getRoot());
        System.out.print(tree.getNumberOfNodes()+ " " + tree.getHeight() + " "
                            + tree.getRootData());
    }
}
