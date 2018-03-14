package TrainerAI;

import Game.Board;
import Tree.Tree;
import java.util.Iterator;

/**
 *
 * @author Edward Conn
 */
public class GameTree {

    private final int numberOfChildren;
    private final char computerColor;
    private final char playerColor;
    private Tree gridTree, closestWin;

    public GameTree(Character[][] grid) {
        this.computerColor = 'R';
        this.playerColor = 'Y';
        this.numberOfChildren = grid.length;
        gridTree = new Tree();
        gridTree.setRoot(grid);

    }

    public Tree getGridTree() {
        return gridTree;
    }

    private void setGridTree(Tree gridTree) {
        this.gridTree = gridTree;
    }

    public Tree getClosestWin() {
        return closestWin;
    }

    private void setClosestWin(Tree closestWin) {
        this.closestWin = closestWin;
    }

    
    
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public char getComputerColor() {
        return computerColor;
    }

    public char getPlayerColor() {
        return playerColor;
    }

    private int findBestBranch() {
        if (gridTree.size() == 1) {
            if (this.closestWin.getHeight() < 5) {
                return closestWin.getBranch();
            } else {
                return mostVictories();
            }
        }else{
            return 0;
        }
    }

//    private int closetVictory() {
//        for(Tree child : (LList) gridTree.getChildren()){
//        Iterator it = child.LevelOrderIterator();
//        int counter = 0;
//        while(it.hasNext()){
//            if(((Tree) it.next()).getData().checkWin()){
//                return 
//            }
//        }
//    }
//        return 0;
//    }

    private int mostVictories() {
    Iterator it = gridTree.LevelOrderIterator();
        int counter = 0;
        while(it.hasNext()){
            if(((Board)((Tree) it.next()).getData()).checkForWin() != 0){
                counter++;
            }
        }
        return 0;    }

}
