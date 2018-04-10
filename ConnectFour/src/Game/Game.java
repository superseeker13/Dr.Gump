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
import java.util.Scanner;

/**
 *
 * @author Edward Conn
 */
public class Game {

    private static GameState state;
    private static GameTree tree;
    private static String filename = "save.dat";
    private static final String RECORD_FILE_NAME = "record.dat";
    private static final String TREE_FILE_NAME = "gameTree.dat";
    static int[] record;
    static Scanner in = new Scanner(System.in);
    static char choice;
    

    public static void main(String[] args) {
        System.out.print("Welcome to Java ConnectFour. ");
        record = new int[2];
        try {
            loadRecordFrom(RECORD_FILE_NAME);
            menu();
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        }
        
        try {
            loadTreeFrom(TREE_FILE_NAME);
            menu();
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        }

    }

    public static void menu() throws ClassNotFoundException {
        while (choice != '5') {
            System.out.println("\nPress 1 to create a new game.");
            System.out.println("Press 2 to load an old game.");  //TODO: Load bot
            System.out.println("Press 3 to view player records.");
            System.out.println("Press 4 to clear player records.");
            System.out.println("Press 5 to quit.");
            System.out.print("Choice: ");
            try {
                setChoice(in.nextLine().charAt(0));
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invaild input");
            }

            switch (getChoice()) {

                case '1':
                    state = new GameState();
                    System.out.print("Do you want to play against a friend or the computer? \n");
                    System.out.print("C for computer, F for friend:  ");
                    while (getChoice() != 'C' && getChoice() != 'F') {
                        try {
                            setChoice(in.nextLine().charAt(0));
                        } catch (StringIndexOutOfBoundsException e) {
                            choice = '2';
                        }

                        switch (getChoice()) {
                            case 'F':
                            case 'f':
                                state.setTwoPlayer(true);
                                break;
                            case 'C':
                            case 'c':
                                state.setTwoPlayer(false);
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                    }
                    runGame();
                    saveRecordTo(RECORD_FILE_NAME);
                    break;
                    
                case '2':
                    try {
                        System.out.print("Which file do you wish to load?  ");

                        filename = in.nextLine() + ".dat";
                        state.loadFrom(filename);
                        runGame();
                    } catch (NullPointerException e) {
                        System.out.printf("Invalid filename: %s  %s", filename,e.toString());
                    }
                    break;
                    
                case '3':
                    System.out.printf("You have won %d and have lost %d games.", record[0], record[1]);
                    break;
                    
                case '4':
                    System.out.print("Are you sure?  ");
                    if ('Y' == (in.nextLine().charAt(0))) {
                        record = new int[2];
                        saveRecordTo(RECORD_FILE_NAME);
                    }
                    break;
                    
                case '5':
                    System.exit(0);
                    break;
            }
            choice = ' ';
        }
    }

    private static boolean runGame() {
        displayGrid();
        while (state.getWinner() == 0 
                && !state.isGridFull()) {
            if (state.isPlayerTurn()) {
                System.out.println("Choose a column, Player One:");
                playersMove(state.getBoard().getPlayerColor());
                state.getCurrentTree().setGridTree(state.getCurrentTree()
                        .getNode(state.getBoard()));
            } else {
                if (state.isTwoPlayer()) {
                    System.out.println("Choose a column, Player Two:");
                    playersMove(state.getBoard().getComputerColor());
                } else {
                    computersMove();
                }
            }
            state.setPlayerTurn(!state.isPlayerTurn());
            displayGrid();
            state.setWinner(state.getBoard().checkForWin());
        }
        displayWinner();
        return true;
    }
    //Prints ConnectFour board to screen

    protected static void displayGrid() {
        System.out.println("\n  0   1   2   3   4   5   6");
        System.out.println("______________________________");
        state.getGrid().stream().map((grid) -> {
            for (int j = 0; j < state.getGrid().size() + 1; j++) {
                System.out.printf("| %c ", ((AList) grid).get(j));
            }
            return grid;
        }).forEachOrdered((_item) -> {
            System.out.println("|");
        });
        System.out.println("______________________________");
    }

    private static void playersMove(char color) {
        try {
            setChoice((in.nextLine().charAt(0)));
        } catch (StringIndexOutOfBoundsException e) {
            choice = ')';
        }
        if (getChoice() == 's' || getChoice() == 'S') {
            System.out.print("Enter a filename: ");
            filename = (in.nextLine() + ".dat");
            state.saveTo(filename);
            state.setPlayerTurn(!state.isPlayerTurn());
        } else {
            try {
                if (!state.insertPiece(getChoice(), color)) {
                    System.out.println("That column is full.");
                    state.setPlayerTurn(!state.isPlayerTurn());
                }
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                System.out.println("Invaild column number.\n");
                state.setPlayerTurn(!state.isPlayerTurn());
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invaild input");
            }
        }
    }

    private static void computersMove() {
        System.out.println("Gump is thinking...");
        state.insertPiece((char) tree.findBestBranch(), state.getBoard().getComputerColor());
        state.setPlayerTurn(!state.isPlayerTurn());
    }

    private static void displayWinner() {
        assert state.getWinner() != 0;
        if (state.getWinner() > 0) {
            System.out.println("You Won, Player One");
        } else if (!state.isTwoPlayer()) {
            System.out.println("You Lost.");
        } else {
            System.out.println("You Won, Player Two");
        }
    }
    
    // Save the win lose record
    public static void saveRecordTo(String filename) {
        File file = new File(filename);

        try {
            ObjectOutputStream fout
                    = new ObjectOutputStream(new FileOutputStream(file));
            fout.writeObject(Game.getRecord());
            fout.flush();
            fout.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File write error");
        } catch (IOException ex) {
            System.err.println("File write error!");
        }
    }

    public static void loadRecordFrom(String filename) throws ClassNotFoundException {
        File file = new File(filename);
        try {
            if (file.exists()) {
                ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file));
                setRecord((int[]) fin.readObject());
                fin.close();

            } else {
                System.err.println("Record file not found");
                saveRecordTo(RECORD_FILE_NAME);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println("IOException");
        }
    }

    public static void saveTreeTo(String filename) {
        File file = new File(filename);

        try {
            ObjectOutputStream fout
                    = new ObjectOutputStream(new FileOutputStream(file));
            fout.writeObject(Game.getTree());
            fout.flush();
            fout.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File write error");
        } catch (IOException ex) {
            System.err.println("File write error!");
        }
    }
    
    public static void loadTreeFrom(String filename) throws ClassNotFoundException {
        File file = new File(filename);
        try {
            if (file.exists()) {
                ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file));
                setTree((GameTree) fin.readObject());
                fin.close();

            } else {
                System.err.println("Tree file not found");
                tree = new GameTree(new Board((byte) 7, (byte) 6));
                saveTreeTo(filename);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println("IOException");
        }
    }

    
    public static int[] getRecord() {
        return record;
    }

    private static void setRecord(int[] record) {
        Game.record = record;
    }

    public static GameState getState() {
        return state;
    }

    public static void setState(GameState state) {
        Game.state = state;
    }

    public static String getFilename() {
        return filename;
    }

    private static void setFilename(String filename) {
        Game.filename = filename;
    }

    public static char getChoice() {
        return choice;
    }

    static void setChoice(char choice) {
        Game.choice = choice;
    }

    public static GameTree getTree() {
        return tree;
    }

    public static void setTree(GameTree tree) {
        Game.tree = tree;
    }
}
