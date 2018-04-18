package Tree;

import Lists.AList;
import java.io.Serializable;
import java.util.Iterator;

/**
 *
 * @author Edward Conn
 * @param <T>
 */
public class GeneralTree<T> implements TreeInterface<T>, Serializable{
    GeneralNode<T> root;
    
    public GeneralTree(){
        root = new GeneralNode<>();
    }
    
    public GeneralTree(GeneralNode node){
        root = node;
    }
    
    public GeneralTree(T data){
        root = new GeneralNode<>(data);
    }
    
    public GeneralNode<T> getRoot() {
        return root;
    }
    
    @Override
    public T getRootData() {
        return root.getData();
    }

    @Override
    public int getHeight() {
        return getNodeHeight(root) + 1;
    }

    private int getNodeHeight(GeneralNode<T> root) {
        int height = 0;
        if (root != null) {
            int max = 0;
            root.getChildren().forEach((child) -> {
                Math.max(max, getNodeHeight((GeneralNode<T>)child));
            });
            height =  1 + max;
        }
        return height;
    }
    
    @Override
    public int getNumberOfNodes() {
        Iterator it = getLevelOrderIterator();
        int counter = 0;
        while(it.hasNext()){ 
            it.next();
            counter++;
        }
        return counter;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
        }

    @Override
    public void clear() {
        root = null;
    }
    
    public Iterator<T> getLevelOrderIterator() {
        return new Iterator<T>() {
            private AList<GeneralNode<T>> list = null;
            private Iterator it;
            int index = 0;

            private void levelOrderTransveral(GeneralNode<T> node){
                if(node != null){
                    list.add(node);
                    node.getChildren().forEach((GeneralNode<T> child) -> {
                        list.add((child));
                    });
                    if(index < list.getMaxCapacity()){
                    levelOrderTransveral((GeneralNode<T>) list.get(index++));
                    }
                }
            }
            
            @Override
            public boolean hasNext() {
                if(list == null){
                    list = new AList<>();
                    levelOrderTransveral(root);
                    it = list.iterator();
                }
                return it.hasNext();
            }

            @Override
            public T next() {
                if(list == null){
                    list = new AList<>();
                    levelOrderTransveral(root);
                    it = list.iterator();
                }
                return (T) it.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}
