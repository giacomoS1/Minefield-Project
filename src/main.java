//Import Section
import java.util.Scanner;
/*
 * Provided in this class is the neccessary code to get started with your game's implementation
 * You will find a while loop that should take your minefield's gameOver() method as its conditional
 * Then you will prompt the user with input and manipulate the data as before in project 2
 * 
 * Things to Note:
 * 1. Think back to project 1 when we asked our user to give a shape. In this project we will be asking the user to provide a mode. Then create a minefield accordingly
 * 2. You must implement a way to check if we are playing in debug mode or not.
 * 3. When working inside your while loop think about what happens each turn. We get input, user our methods, check their return values. repeat.
 * 4. Once while loop is complete figure out how to determine if the user won or lost. Print appropriate statement.
 */

public class main {

    public static void main (String[] args) { //Giacomo & Luke
        //Prompt user for difficulty, make sure it's between 0 and 2 (inclusive)
        Scanner scanner = new Scanner(System.in);
        // Tracker for debug mode
        boolean debug = false;
        System.out.println("Choose your difficulty,\n[0]: Easy     [1]: Medium     [2]: Hard\nAdd ' debug' to your response to play in debug mode.");
                    /*
                    - Easy: Rows: 5 Columns: 5 Mines: 5 Flags: 5
                    – Medium: Rows: 9 Columns: 9 Mines: 12 Flags: 12
                    – Hard: Rows: 20 Columns: 20 Mines: 40 Flags: 40
                     */
        //Set Difficulty
        int numMines = 0;
        Minefield minefield = null;
        while (minefield == null) { // allowing infinite inputs until correct
            String difficulty = scanner.nextLine();
            if (difficulty.equals("0") || difficulty.toLowerCase().equals("0 debug")) {
                if (difficulty.toLowerCase().equals("0 debug")) debug = true;
                minefield = new Minefield(5, 5, 5);
                numMines = 5;
            } else if (difficulty.equals("1") || difficulty.toLowerCase().equals("1 debug")) {
                if (difficulty.toLowerCase().equals("1 debug")) debug = true;
                minefield = new Minefield(9, 9, 12);
                numMines = 12;
            } else if ((difficulty.equals("2") || difficulty.toLowerCase().equals("2 debug"))) {
                if (difficulty.toLowerCase().equals("2 debug")) debug = true;
                minefield = new Minefield(20, 20, 40);
                numMines = 40;
            } else {
                System.out.println("Error in input, please try again."); // only reached if board is not initialized
            }
        }
        String[] response;
        System.out.println(minefield);
        System.out.println("Enter Starting Coordinates [row] [col]:");
        response = scanner.nextLine().split(" ");
        int startCol, startRow;
        while (true) { // infinite loop to allow infinite retries for incorrect entry
            try { // using try-catch so game doesn't shut down with incorrect input
                startRow = Integer.parseInt(response[0]);
                startCol = Integer.parseInt(response[1]);
                break;
            } catch (Exception e) {
                System.out.println("Error reading move, please try again. (format: [row] [col]"); // if there is an error in move detection (ex. wrong format, letters in string)
                response = scanner.nextLine().split(" ");
            }
        }
        minefield.createMines(startRow, startCol, numMines);
        minefield.evaluateField();
        if (debug) minefield.debug();
        minefield.revealStartingArea(startRow, startCol);

        while ( ! minefield.gameOver()) {
            System.out.println(minefield);
            System.out.println("Enter Coordinates [row] [col] (add ' F' to your response for flag, remaining: " + minefield.getFlags() + "), enter flagged coordinates to unflag:");
            response = scanner.nextLine().split(" ");
            while (true) { // infinite loop to allow infinite retries for invalid move
                try { // using try-catch so game doesn't shut down when invalid move is entered
                    if (response[0].toLowerCase().equals("end")) break;// extra condition for ending game
                    int row = Integer.parseInt(response[0]);
                    int col = Integer.parseInt(response[1]);
                    boolean flag = false;
                    if ((response.length > 2) && response[2].toUpperCase().equals("F")) flag = true; // flag detection
                    if (minefield.guess(row, col, flag)) break;
                    System.out.println("Try again, input invalid."); // reached if there is an illegal move
                    response = scanner.nextLine().split(" ");
                }
                catch(Exception e) {
                    System.out.println("Error reading move, please try again. (format: [row] [col] flag: [F]"); // if there is an error in move detection (ex. wrong format, letters in string)
                    response = scanner.nextLine().split(" ");
                }
            }
            if (debug) minefield.debug(); // for debug mode
            //if the guess() function returns false, there was an error in the input.
        }
        System.out.println(minefield); // for game over
    }
}
