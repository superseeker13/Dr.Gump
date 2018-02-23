package Game;

import Lists.AList;
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

    private AList grid;
    private boolean playerTurn;
    transient private boolean twoPlayer;
    transient private final char playerColor = 'Y';
    private final char computerColor = 'R';
    private final int columnNumber;
    private final int rowNumber;
    private byte winner;

    public boolean isTwoPlayer() {
        return twoPlayer;
    }

    public void setTwoPlayer(boolean twoPlayer) {
        this.twoPlayer = twoPlayer;
    }

    public byte getWinner() {
        return winner;
    }

    public void setWinner(byte winner) {
        this.winner = winner;
    }

    public AList getGrid() {
        return grid;
    }

    private void setGrid(AList grid) {
        this.grid = grid;
    }

    void setGridSection(AList grid, int x, int y, char c) {
        //grid[x][y] = c;
        this.grid = grid;
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public char getPlayerColor() {
        return playerColor;
    }

    public char getComputerColor() {
        return computerColor;
    }

    public GameState() {
        this.twoPlayer = false;
        this.winner = 0;
        this.rowNumber = 6;
        this.columnNumber = 7;
        this.grid = new AList<>();
        //Fills grid with spaces
        this.grid = new AList();
        for (int i = 0; i < rowNumber; i++) {
            AList<Character> list = new AList<>();
            for (int j = 0; j < columnNumber; j++) {
                list.add(' ');
            }
            grid.add(list);
        }
        for (Iterator it = grid.getIterator(); it.hasNext();) {
            AList gridColunm = (AList) it.next();
            for (Object gridRow : gridColunm) {
                gridRow = ' ';
            }
        }
        this.playerTurn = true;
    }

    public GameState(AList grid, boolean twoPlayer) {
        this.winner = 0;
        this.rowNumber = 6;
        this.columnNumber = 7;
        this.grid = new AList<>(); // AList of char[] cast to Object.
        setGrid(grid);
        this.twoPlayer = twoPlayer;
    }

    /*
    * Inserts a Character color into the list in the desinated column of the list matrix.
    * @return True if the piece was inserted 
     */
    boolean insertPiece(char column, char color) throws NullPointerException, ArrayIndexOutOfBoundsException {
        column -= 48; // Converts ASCII.
        int rowNum = rowNumber - 1;
        assert !isGridFull(); // Double-checking that the computer can only inserts one piece.
        for (int i = rowNum; i >= 0; i--) {
            if (((AList) grid.get(i)).get(column).equals((Character) ' ')) {
                ((AList) grid.get(i)).set(column, color);
                return true;
            }
        }
        return false;
    }

    /*
    * Searches grid for a line of four of the same color.
    * @return True if a winning line is found.
     */
    public boolean checkForWin() {
        return columnCheck() || rowCheck() || diagonialCheck();
    }

    private boolean columnCheck() {

        for (int i = 0; i < rowNumber - 3; i++) {
            AList listOne = (AList) grid.getList()[i];
            AList listTwo = (AList) grid.getList()[i + 1];
            AList listThree = (AList) grid.getList()[i + 2];
            AList listFour = (AList) grid.getList()[i + 3];

            //Assume every list is the same length.
            for (int j = 0; j < listOne.size(); j++) {
                Character oneNext = (Character) listOne.get(j);
                if ((!oneNext.equals((Character) ' '))
                        && oneNext.equals((Character) listTwo.get(j))
                        && oneNext.equals((Character) listThree.get(j))
                        && oneNext.equals((Character) listFour.get(j))) {
                    winner = (byte) ((((Character) playerColor).equals(oneNext)) ? 1 : -1);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean rowCheck() {
        for (int i = 0; i < rowNumber; i++) {
            //Casts the Object in grid's Object[] to AList 
            AList currentRow = (AList) grid.getList()[i];
            if (currentRow.numberOf('Y') > 3 || currentRow.numberOf('R') > 3) {
                for (int j = 0; j < columnNumber - 3; j++) {
                    //Checks for rows of four like colored pieces.
                    if (!((Character) currentRow.getList()[j]).equals((Character) ' ')
                            && ((Character) currentRow.getList()[j]).equals((Character) currentRow.getList()[j + 1])
                            && ((Character) currentRow.getList()[j]).equals((Character) currentRow.getList()[j + 2])
                            && ((Character) currentRow.getList()[j]).equals((Character) currentRow.getList()[j + 3])) {
                        winner = (byte) ((((Character) playerColor).equals(
                                currentRow.getList()[j])) ? 1 : -1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean diagonialCheck() {
        AList listZero = (AList) grid.getList()[0];
        AList listOne = (AList) grid.getList()[1];
        AList listTwo = (AList) grid.getList()[2];
        AList listThree = (AList) grid.getList()[3];
        AList listFour = (AList) grid.getList()[4];
        AList listFive = (AList) grid.getList()[5];
        Object[][] board = {(Object[]) listZero.getList(), (Object[]) listOne.getList(),
            (Object[]) listTwo.getList(), (Object[]) listThree.getList(),
            (Object[]) listFour.getList(), (Object[]) listFive.getList()};

        for (int i = 3; i < rowNumber - 3; i++) {
            for (int j = columnNumber; j > 3; j--) {
                if (!((Character) board[i][j]).equals((Character) ' ')
                        && ((Character) board[i][j]).equals((Character) board[i + 1][j - 1])
                        && ((Character) board[i][j]).equals((Character) board[i + 2][j - 2])
                        && ((Character) board[i][j]).equals((Character) board[i + 3][j - 3])) {
                    winner = (byte) ((((Character) playerColor).equals(
                            board[i][j])) ? 1 : -1);
                    return true;
                }
            }
        }
        
        for (int i = 3; i < rowNumber - 3; i++) {
            for (int j = columnNumber; j > 3; j--) {
                if (!((Character) board[i][j]).equals((Character) ' ')
                        && ((Character) board[i][j]).equals((Character) board[i - 1][j + 1])
                        && ((Character) board[i][j]).equals((Character) board[i - 2][j + 2])
                        && ((Character) board[i][j]).equals((Character) board[i - 3][j + 3])) {
                    winner = (byte) ((((Character) playerColor).equals(
                            board[i][j])) ? 1 : -1);
                    return true;
                }
            }
        }

        return false;
    }

    boolean isGridFull() {
        return !contains((Character) ' ');
    }

    //Searches the inner most lists for a like element c.
    private boolean contains(char c) {
        for (int i = 0; i < rowNumber; i++) {
            for (int j = 0; j < columnNumber; j++) {
                if ((((AList) grid.get(i)).get(j)).equals((Character) c)) {
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
            fout.flush();
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
