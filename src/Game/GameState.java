package Game;

import Lists.AList;
import TrainerAI.GameTree;
import java.io.Serializable;
import java.util.Iterator;

/**
 * @author sjw
 * @author Edward Conn
 * @param <T>
 */
public class GameState<T> implements Serializable {

    private static Board board;
    private static boolean playerTurn;
    private boolean twoPlayer;
    private byte winner;
    private static GameTree currentTree;
//    private final static  String PLAYER_TREE_FILE_NAME = "ptree.dat";
//    private final static  String COMPUTER_TREE_FILE_NAME = "ctree.dat";

    Board getBoard() {
        return board;
    }

    boolean isTwoPlayer() {
        return twoPlayer;
    }

    void setTwoPlayer(boolean twoPlayer) {
        this.twoPlayer = twoPlayer;
    }

    byte getWinner() {
        return winner;
    }

    void setWinner(byte winner) {
        this.winner = winner;
    }

    AList getGrid() {
        return board.getGrid();
    }

    boolean isPlayerTurn() {
        return playerTurn;
    }

    void setPlayerTurn(boolean playerTurn) {
        GameState.playerTurn = playerTurn;
    }

    GameTree getCurrentTree() {
        return currentTree;
    }

    void setCurrentTree(GameTree currentTree) {
        GameState.currentTree = currentTree;
    }

    GameState() {
        twoPlayer = false;
        winner = 0;
        board = new Board((byte) 7, (byte) 6);
        for (int i = 0; i < board.getRowNumber(); i++) {
            AList<Character> list = new AList<>(7);
            for (int j = 0; j < board.getColumnNumber(); j++) {
                list.add(' ');
            }
            board.getGrid().add(list);
        }

        for (Iterator it = board.getGrid().getIterator(); it.hasNext();) {
            AList gridColunm = (AList) it.next();
            gridColunm.forEach((gridRow) -> {
                gridRow = ' ';
            });
        }

        GameState.playerTurn = ((Math.random() * 2) >= 1);
        currentTree = new GameTree(board);
    }

    GameState(AList grid, boolean twoPlayer) {
        this.winner = 0;
        GameState.board = new Board(grid); // 6X7 board
        this.twoPlayer = twoPlayer;
    }

    /*
    * Inserts a Character color into the list in the desinated column of the list matrix.
    * @return True if the piece was inserted 
     */
    boolean insertPiece(int column, char color) throws NullPointerException,
            ArrayIndexOutOfBoundsException {
        return board.insertPiece(column, color);
    }

    boolean isGridFull() {
        return board.isBoardFull();
    }

    static void generateTree() {
        final char color = (playerTurn) ? board.getPlayerColor()
                : board.getComputerColor();
        currentTree = new GameTree(board, color);
    }
}
