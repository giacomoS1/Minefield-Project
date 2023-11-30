//Import Section
import java.util.Arrays;
import java.util.Random;
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
        int difficulty = -1;
        while(difficulty < 0 || difficulty > 2) {
            System.out.println("Choose your difficulty\n" +
                    "[0]: Easy     [1]: Medium     [2]: Hard");
            difficulty = scanner.nextInt();
        }
                    /*
                    - Easy: Rows: 5 Columns: 5 Mines: 5 Flags: 5
                    – Medium: Rows: 9 Columns: 9 Mines: 12 Flags: 12
                    – Hard: Rows: 20 Columns: 20 Mines: 40 Flags: 40
                     */
        //Set Difficulty
        int numMines = 0;
        Minefield minefield;
        if (difficulty == 0) {
            //Easy
            minefield = new Minefield(5, 5, 5);
            numMines = 5;
        } else if (difficulty == 1) {
            //Medium
            minefield = new Minefield(9, 9, 12);
            numMines = 12;
        } else {
            //Hard
            minefield = new Minefield(20, 20, 40);
            numMines = 40;
        }
        String[] response = new String[0];
        System.out.println(minefield);
        do {
            System.out.println("Enter Starting Coordinates [x] [y]:");
            response = scanner.nextLine().split(" ");
            System.out.println(Arrays.toString(response));
        } while(response.length < 2);
        int startX = Integer.parseInt(response[0]);
        int startY = Integer.parseInt(response[1]);
        minefield.createMines(startX, startY, numMines);
        minefield.evaluateField();
        minefield.revealStartingArea(startX, startY);
        minefield.debug();

        while ( ! minefield.gameOver()) {
            System.out.println(minefield);
            System.out.println("Enter Coordinates [x] [y] (add ' [F]' to your response for flag, remaining: " + minefield.getFlags() + "):");
            response = scanner.nextLine().split(" ");
            int x = Integer.parseInt(response[0]);
            int y = Integer.parseInt(response[1]);
            //if the guess() function returns false, there was an error in the input.
            if(!minefield.guess(x, y, (response.length > 2))) {
                System.out.println("Try again, input invalid.");
            }
        }
    }
}
