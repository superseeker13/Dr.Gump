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
 * @author Edward Conn
 */
public class Game {

    private static GameState state;
    private static final String SAVE_FILE_NAME = "save.dat";
    private static final String RECORD_FILE_NAME = "record.dat";
    private static int[] record;
    private static final Scanner IN = new Scanner(System.in);
    private static char choice;
    

    public static void main(String[] args) {
        System.out.print("Welcome to Java ConnectFour.");
        record = new int[2];
        try {
            loadRecordFrom(RECORD_FILE_NAME);
            menu();
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        }

    }

    public static void menu() throws ClassNotFoundException {
        while (choice != '5') {
            System.out.println("\nPress 1 to create a new game.");
            System.out.println("Press 2 to load an old game.");
            System.out.println("Press 3 to view player records.");
            System.out.println("Press 4 to clear player records.");
            System.out.println("Press 5 to quit.");
            System.out.print("Choice: ");
            try {
                choice = IN.nextLine().charAt(0);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Invaild input");
            }

            switch (choice) {

                case '1':
                    state = new GameState();
                    System.out.print("Do you want to play against a friend or the computer? \n");
                    System.out.print("C for computer, F for friend:  ");
                    while (choice != 'C' && choice != 'F') {
                        try {
                            choice = IN.nextLine().charAt(0);
                        } catch (StringIndexOutOfBoundsException e) {
                            choice = 'a';
                        }

                        switch (choice) {
                            case 'F':
                                state.setTwoPlayer(true);
                                break;
                            case 'C':
                                state.setTwoPlayer(false);
                                //GameState.loadTree(state.isPlayerTurn());
                                break;
                            default:
                                System.out.println("Invalid input");
                                break;
                        }
                    }
                    runGame();
                    break;

                case '2':
                    if (loadStateFrom(SAVE_FILE_NAME)) {
                        runGame();
                    }
                    break;

                case '3':
                    System.out.printf("You have won %d and have lost %d games.", record[0], record[1]);
                    break;

                case '4':
                    System.out.print("Are you sure?  ");
                    if ('Y' == (IN.nextLine().charAt(0))) {
                        record = new int[2];
                        saveRecordTo(RECORD_FILE_NAME);
                    }
                    break;

                case '5':
                    saveRecordTo(SAVE_FILE_NAME);
                    System.exit(0);
                    break;
            }
            choice = ' ';
        }
    }

    private static boolean runGame() throws ClassNotFoundException {
        displayGrid();
        while (state.getWinner() == 0
                && !state.isGridFull()) {
            if (state.isPlayerTurn()) {
                System.out.println("Choose a column, Player One:");
                playersMove(state.getBoard().getPlayerColor());
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
            if (state.getWinner() > 0) {
                record[0]++;
            }
            if (state.getWinner() < 0) {
                record[1]++;
            }
        }
        displayWinner();
        return true;
    }
    //Prints ConnectFour board to screen

    protected static void displayGrid() {
        final String line = "______________________________";
        System.out.println("\n  0   1   2   3   4   5   6");
        System.out.println(line);
        state.getGrid().stream().map((grid) -> {
            for (int j = 0; j < state.getGrid().size() + 1; j++) {
                System.out.printf("| %c ", ((AList) grid).get(j));
            }
            return grid;
        }).forEachOrdered((_position) -> {
            System.out.println("|");
        });
        System.out.println(line);
    }

    private static void playersMove(char color) throws ClassNotFoundException {
        try {
            choice = IN.nextLine().charAt(0);
        } catch (StringIndexOutOfBoundsException e) {
            choice = ')';
        }

        switch (choice) {

            case 's':
            case 'S':
                saveStateTo(SAVE_FILE_NAME);
                state.setPlayerTurn(!state.isPlayerTurn());
                break;

            case 'q':
            case 'Q':
                saveRecordTo(RECORD_FILE_NAME);
                System.exit(0);
                break;
                
            case 'l':
            case 'L':
                loadStateFrom(SAVE_FILE_NAME);
                System.out.println("\nLoaded Game:");
                state.setPlayerTurn(!state.isPlayerTurn());
                break;

            default:
                try {
                    if (!state.insertPiece(choice - 48, color)) {
                        System.out.println("That column is full.");
                        state.setPlayerTurn(!state.isPlayerTurn());
                    }
                } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                    System.out.println("Invaild column number.\n");
                    state.setPlayerTurn(!state.isPlayerTurn());
                } catch (StringIndexOutOfBoundsException e) {
                    System.out.println("Invaild input");
                }
                break;
        }
    }

    private static void computersMove() {
        System.out.println("Dr. Gump is thinking...");
        //state.updateCurrentTree();
        state.setCurrentTree(new GameTree(state.getBoard(),
                state.getBoard().getComputerColor()));
        int column = state.getCurrentTree().findBestBranch();
        state.setCurrentTree(null);
        System.gc();
        System.out.printf("I chose: %d\n", column);
        state.insertPiece(column, state.getBoard().getComputerColor());
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
            try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(file))) {
                fout.writeObject(Game.record);
                fout.flush();
            }
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
                try (ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file))) {
                    record = ((int[]) fin.readObject());
                }

            } else {
                System.out.println("Record file not found.");
                saveRecordTo(RECORD_FILE_NAME);
                System.out.println("New record file created.");
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found.");
        } catch (IOException ex) {
            System.err.println("IOException.");
        }
    }
    
    /*
    * Saves gamestate to a .dat file
    * @param SAVE_FILE_NAME
     */
    public static void saveStateTo(String filename) {
        File file = new File(filename);
        try {
            try (ObjectOutputStream fout = new ObjectOutputStream(new FileOutputStream(file))) {
                fout.writeObject(state);
            }
        } catch (FileNotFoundException ex) {
            System.err.println("File write error");
        } catch (IOException ex) {
            System.err.println("File write error!!");
        }
    }

    /*
    * Loads a gamestate from a given .dat file
    * @param SAVE_FILE_NAME
     */
    public static boolean loadStateFrom(String filename) throws ClassNotFoundException {
        File file = new File(filename);
        try {
            if (file.exists()) {
                try (ObjectInputStream fin = new ObjectInputStream(new FileInputStream(file))) {
                    Object temp = fin.readObject();
                    state = ((GameState)temp);
                }
                return true;
            } else {
                System.out.println("File not found.");
                saveStateTo(filename);
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found ex.");
        } catch (IOException ex) {
            System.err.println("IOException.");
        }
        return false;
    }
}
