package TrainerAI;

import Game.Board;
import Lists.AList;
import Tree.GeneralNode;
import Tree.GeneralTree;
import java.io.Serializable;
import java.util.Iterator;

/**
 * @author Edward Conn
 * @author Micheal Schultz
 */
public class GameTree implements Serializable {

    private final char computerColor, playerColor;
    private GeneralTree<Board> gridTree;

    public GameTree(Board grid) {
        this.computerColor = 'R';
        this.playerColor = 'Y';
        gridTree = new GeneralTree(grid);
    }

    public GameTree(Board grid, char color) {
        this.computerColor = 'R';
        this.playerColor = 'Y';
        gridTree = new GeneralTree(grid);
        populateTree(gridTree.getRoot(), color, 0);
    }

    public GeneralTree getGridTree() {
        return gridTree;
    }

    public void setGridTree(GeneralTree gridTree) {
        this.gridTree = gridTree;
    }

    public int getNumberOfNodes() {
        return gridTree.getNumberOfNodes();
    }

    public char getComputerColor() {
        return computerColor;
    }

    public char getPlayerColor() {
        return playerColor;
    }

//    public GeneralNode findNode(Board b) {
//        Iterator it = gridTree.getLevelOrderIterator();
//        while (it.hasNext()) {
//            GeneralNode<Board> currentNode = (GeneralNode) it.next();
//            if (currentNode.getData().getGrid().equals(b.getGrid())) {
//                return currentNode;
//            }
//        }
//        return null;
//    }
    public Integer findBestBranch() {
        if (gridTree.getNumberOfNodes() > 1) {
            final AList<GeneralNode<Board>> children
                    = gridTree.getRoot().getChildren();
            final int pWinningMove = winningPlayerMove(children);
            if (pWinningMove >= 0) {
                return pWinningMove;
            } else {
                final int close = closestVictory(children);
                final int most = mostVictories(children);
                if (most > 0 || close > 0) {
                    return most > 0 ? most : close;
                }
            }
        }
        return randomMove();
    }

    private int winningPlayerMove(AList<GeneralNode<Board>> nodes) {
        for(GeneralNode<Board> childOne : nodes){
            for (GeneralNode<Board> childTwo : childOne.getChildren()) {
                if(childTwo != null && childTwo.getData().checkForWin() > 0){
                    return childTwo.getData().getLastPlayed()[0];
                }
            }
        }
        return -1;
    }

    private int mostVictories(AList<GeneralNode<Board>> children) {
        GeneralTree bestBranch = gridTree;
        int bestBranchNumber = 0;
        for (GeneralNode<Board> child : children) {
            if (child != null) {
                GeneralTree currentSubTree = new GeneralTree((GeneralNode) child);
                Iterator childIt = currentSubTree.getLevelOrderIterator();
                int counter = 0;
                while (childIt.hasNext()) {
                    Board currentBoard = (Board) (((GeneralNode) childIt.next()).getData());
                    if (currentBoard.checkForWin() < (byte) 0) {
                        counter++;
                    }
                    if (currentBoard.checkForWin() > (byte) 0) {
                        counter--;
                    }
                }
                if (counter > bestBranchNumber) {
                    bestBranchNumber = counter;
                    bestBranch = currentSubTree;
                }
            }
        }
        return bestBranch.equals(gridTree) ? -1
                : ((Board) bestBranch.getRootData()).getLastPlayed()[0];
    }

    private int closestVictory(AList<GeneralNode<Board>> nodes) {
        for (GeneralNode<Board> child : nodes) {
            GeneralTree currentSubTree = new GeneralTree((GeneralNode) child);
            Iterator childIt = currentSubTree.getLevelOrderIterator();
            while (childIt.hasNext()) {
                Board currentBoard = (Board) (((GeneralNode) childIt.next()).getData());
                if (currentBoard.checkForWin() < (byte) 0) {
                    return currentBoard.getLastPlayed()[0];
                }
            }
        }

        return -1;
    }

    private Integer randomMove() {
        Board currentBoard = (Board) gridTree.getRootData();
        Object[] moves = currentBoard.possibleMove();
        return (Integer) moves[(int) (Math.random() * moves.length)];
    }

//    private int nearVictory() {
//        Iterator it = gridTree.getLevelOrderIterator();
//        while (it.hasNext()) {
//            Board currentBoard = (Board) (((GeneralNode) it.next()).getData());
//            if (currentBoard.checkForWin() < (byte) 0) {
//                return currentBoard.getLastPlayed()[0];
//            }
//        }
//        return -1;
//    }
    private void populateTree(GeneralNode<Board> node, char color, int counter) {
        if (counter < 6) {
            fillinChildren(node, color);
            final char newColor
                    = (color == computerColor) ? playerColor : computerColor;
            node.getChildren().stream().filter((GeneralNode<Board> child)
                    -> (child != null && child.getData().checkForWin() == 0))
                    .forEachOrdered((GeneralNode<Board> child) -> {
                        populateTree(child, newColor, counter + 1);
                    });
        }
    }

    private void fillinChildren(GeneralNode<Board> node, char color) {
        AList<AList<Character>> currentGrid = (node.getData()).getGrid();
        Board currentBoard = new Board(currentGrid);
        for (Object move : currentBoard.possibleMove()) {
            if (move != null) {
                Board newBoard = new Board(currentBoard, (Integer) move, color);
                node.addChild(newBoard);
            }
        }
    }
}
