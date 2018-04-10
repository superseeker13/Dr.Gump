package Game;

import Lists.AList;
import TrainerAI.GameTree;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;

/**
 * @author sjw
 * @author Edward Conn
 * @author
 * @param <T>
 */
public class GameState<T> implements Serializable {
    private final Board board;
    private boolean playerTurn;
    private boolean twoPlayer;
    private byte winner;
    private GameTree currentTree;

    public Board getBoard() {
        return board;
    }
    
    boolean isTwoPlayer() {
        return twoPlayer;
    }

    public void setTwoPlayer(boolean twoPlayer) {
        this.twoPlayer = twoPlayer;
    }

    public byte getWinner() {
        return winner;
    }

    void setWinner(byte winner) {
        this.winner = winner;
    }

    public AList getGrid() {
        return board.getGrid();
    }

    private void setGrid(AList grid) {
        this.board.setGrid(grid);
    }

//    void setGridSection(AList grid, int x, int y, char c) {
//        //grid[x][y] = c;
//        this.grid.setGrid(grid);
//    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public GameTree getCurrentTree() {
        return currentTree;
    }

    public void setCurrentTree(GameTree currentTree) {
        this.currentTree = currentTree;
    }
    
    

    public GameState() {
        this.twoPlayer = false;
        this.winner = 0;
        board = new Board((byte) 7, (byte) 6);
        for (int i = 0; i < board.getRowNumber(); i++) {
            AList<Character> list = new AList<>();
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
        this.playerTurn = true;
        currentTree = Game.getTree();
    }

    public GameState(AList grid, boolean twoPlayer) {
        this.winner = 0;
        this.board = new Board( grid, (byte) 6, (byte) 7); // 6X7 board
        this.twoPlayer = twoPlayer;
    }

    /*
    * Inserts a Character color into the list in the desinated column of the list matrix.
    * @return True if the piece was inserted 
     */
    boolean insertPiece(char column, char color) throws NullPointerException, ArrayIndexOutOfBoundsException {
        column -= 48; // Converts ASCII.
        int rowNum = board.getRowNumber() - 1;
        assert !isGridFull(); // Double-checking that the computer can only inserts one piece.
        for (int i = rowNum; i >= 0; i--) {
            if (((AList) board.getGrid().get(i)).get(column).equals((Character) ' ')) {
                ((AList) board.getGrid().get(i)).set(column, color);
                board.setLastPlayed(column);
                return true;
            }
        }
        return false;
    }
        
    boolean isGridFull() {
        return !contains((Character) ' ');
    }

    //Searches the inner most lists for a like element c.
    private boolean contains(char c) {
        for (int i = 0; i < board.getRowNumber(); i++) {
            for (int j = 0; j < board.getColumnNumber(); j++) {
                if ((((AList) board.getGrid().get(i)).get(j)).equals((Character) c)) {
                    return true;
                }
            }
        }
        return false;
    }
     
    /*
    * Saves gamestate to a .dat file
    * @param filename
     */
    public void saveTo(String filename) {
        File file = new File(filename);
        try {
            ObjectOutputStream fout
                    = new ObjectOutputStream(new FileOutputStream(file));
            fout.writeObject(this);
            fout.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File write error");
        } catch (IOException ex) {
            System.err.println("File write error");
        }
    }

    /*
    * Loads a gamestate from a given .dat file
    * @param filename
     */
    public void loadFrom(String filename) throws ClassNotFoundException {
        File file = new File(filename);
        try {
            if (file.exists()) {
                ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file));
                GameState tempGS = (GameState) (fin.readObject());
                Game.setState(tempGS);
                fin.close();

            } else {
                System.err.println("File not found");
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println("IOException");
        }
    }
}
