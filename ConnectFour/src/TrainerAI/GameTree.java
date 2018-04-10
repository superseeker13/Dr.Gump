package TrainerAI;

import Game.Board;
import Tree.BinaryNode;
import Tree.BinaryTree;
import java.util.Iterator;

/**
 *
 * @author Edward Conn
 */
public class GameTree {

    private int numberOfChildren;
    private final char computerColor, playerColor;
    private BinaryTree gridTree;

    public GameTree(Board grid) {
        this.computerColor = 'R';
        this.playerColor = 'Y';
        gridTree = new BinaryTree(grid);
        populateTree();
    }

    public BinaryTree getGridTree() {
        return gridTree;
    }

    public void setGridTree(BinaryTree gridTree) {
        this.gridTree = gridTree;
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

    public char findBestBranch() {
        if (gridTree.getNumberOfNodes() > 1) {
            Board currentBoard = ((Board) gridTree.getRootData());
            char winMove = (char) currentBoard.winningMove((Character[]) currentBoard.possibleMove());
            if(winMove >  -1){
                return winMove;
            }    
            return mostVictories();
        }
        Board currentBoard = (Board) gridTree.getRootData();
        Character[] moves = (Character[]) currentBoard.possibleMove();
        return moves[(char) (Math.random() * moves.length)];
    }

    private char mostVictories() {
        Iterator it = gridTree.getRoot().getLeftChildren().getIterator();
        BinaryTree bestBranch = gridTree;
        char bestBranchNumber = 0;
        while (it.hasNext()) {
            BinaryTree currentSubTree = (BinaryTree) it.next();
            Iterator childIt = (currentSubTree).getInOrderIterator();
            char counter = 0;
            while (childIt.hasNext()) {
                Board currentBoard = (Board) ((BinaryTree) childIt.next()).getRootData();
                if (((Board) ((BinaryTree) childIt.next()).getRootData()).checkForWin() != 0) {
                    counter++;
                }
            }
            if(counter > bestBranchNumber){
                bestBranchNumber = counter;
                bestBranch = currentSubTree;
            }
        }
        return ((Board) bestBranch.getRootData()).getLastPlayed();
    }

    public BinaryTree getNode(Board board) {
        Iterator it = gridTree.getInOrderIterator();
        while(it.hasNext()){
            BinaryNode currentNode = (BinaryNode) it.next();
            if(((Board) currentNode.getData()).equals(board)){
                return new BinaryTree(currentNode.getData()
                        , currentNode.getLeft(), currentNode.getRight());
            }
        }
        return null;
    }

    private void populateTree() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
