package Game;

import Lists.AList;
import java.util.Iterator;

/**
 *
 * @author Edward Conn
 */
public class Board {
    private AList grid;
    transient private final char playerColor = 'Y';
    transient private final char computerColor = 'R';
    private final byte columnNumber;
    private final byte rowNumber;
    private char lastPlayed;
    
    public Board(byte columnNumber, byte rowNumber){
        this.grid = new AList();
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }
    
    public Board(AList grid, byte columnNumber, byte rowNumber){
        this.grid = grid;
        this.columnNumber = columnNumber;
        this.rowNumber = rowNumber;
    }
    
    public AList getGrid() {
        return grid;
    }

    void setGrid(AList grid) {
        this.grid = grid;
    }

    public char getPlayerColor() {
        return playerColor;
    }

    public char getComputerColor() {
        return computerColor;
    }

    public byte getColumnNumber() {
        return columnNumber;
    }

    public byte getRowNumber() {
        return rowNumber;
    }
     
    public char getLastPlayed() {
        return lastPlayed;
    }
    
    public void setLastPlayed(char lastPlayed) {
        this.lastPlayed = lastPlayed;
    }
    
    public Object[][] gridToArray(AList grid){
        AList listZero = (AList) grid.getList()[0];
        AList listOne = (AList) grid.getList()[1];
        AList listTwo = (AList) grid.getList()[2];
        AList listThree = (AList) grid.getList()[3];
        AList listFour = (AList) grid.getList()[4];
        AList listFive = (AList) grid.getList()[5];
        Object[][] array = {(Object[]) listZero.getList(), (Object[]) listOne.getList(),
            (Object[]) listTwo.getList(), (Object[]) listThree.getList(),
            (Object[]) listFour.getList(), (Object[]) listFive.getList()};
        return array;
    }
    
         /*
    * Searches grid for a line of four of the same color.
    * @return True if a winning line is found.
     */
    public byte checkForWin() {
        return  (byte) (columnCheck() + rowCheck() + diagonialCheck());
    }

    private byte columnCheck() {

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
                    return ((byte) ((((Character) playerColor)
                            .equals(oneNext)) ? 1 : -1));
                }
            }
        }
        return 0;
    }

    private byte rowCheck() {
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
                        return ((byte) ((((Character) playerColor)
                                .equals(currentRow.getList()[j])) ? 1 : -1));
                    }
                }
            }
        }
        return 0;
    }

    private byte diagonialCheck() {
        Object[][] board = gridToArray(grid);
        for (int i = 3; i < rowNumber; i++) {
            for (int j = 0; j < 4; j++) {
                if (!(board[i][j]).equals((Character) ' ')
                        && ((Character)board[i][j]).equals((Character)board[i - 1][j + 1])
                        && ((Character)board[i][j]).equals((Character)board[i - 2][j + 2])
                        && ((Character)board[i][j]).equals((Character)board[i - 3][j + 3])) {
                    return (byte) ((((Character) playerColor)
                            .equals(board[i][j])) ? 1 : -1);
                }
            }
        }
        
        for (int i = 3; i < rowNumber; i++) {
            for (int j = 3; j < columnNumber; j++) {
                if (!(board[i][j]).equals((Character) ' ')
                        && ((Character)board[i][j]).equals((Character)board[i - 1][j - 1])
                        && ((Character)board[i][j]).equals((Character)board[i - 2][j - 2])
                        && ((Character)board[i][j]).equals((Character)board[i - 3][j - 3])) {
                    return (byte) ((((Character) playerColor).equals(
                            board[i][j])) ? 1 : -1);
                }
            }
        }
        
        return 0;
    }

    public Object[] possibleMove() {
        AList listZero = (AList) grid.getList()[0];
        Iterator it = listZero.getIterator();
        AList moves = new AList<>();
        for(int i = 0; it.hasNext(); i++){
            if((Character)it.next() == ' '){
                moves.add(i);
            }
        }
        return (Object[])moves.toArray();
    }

    public int winningMove(Character[] possibleMove) {
        //TODO
        return -1;
    }
}