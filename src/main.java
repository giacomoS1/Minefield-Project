//Import Section

/*
 * Provided in this class is the necessary code to get started with your game's implementation
 * You will find a while loop that should take your minefield's gameOver() method as its conditional
 * Then you will prompt the user with input and manipulate the data as before in project 2
 * 
 * Things to Note:
 * 1. Think back to project 1 when we asked our user to give a shape. In this project we will be asking the user to provide a mode. Then create a minefield accordingly
 * 2. You must implement a way to check if we are playing in debug mode or not.
 * 3. When working inside your while loop think about what happens each turn. We get input, user our methods, check their return values. repeat.
 * 4. Once while loop is complete figure out how to determine if the user won or lost. Print appropriate statement.
 */

import java.util.Scanner;

public class main {

    public static void main(String[] args) {
        boolean debug;
        Minefield minefield = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a difficulty (easy, medium, hard). To use debugging, add ' debug' to your input");
        while (minefield == null) { // allowing infinite inputs until correct
            String mode = scanner.next();
            if (mode.equals("easy") || mode.equals("easy debug")) {
                if (mode.equals("easy debug")) debug = true;
                minefield = new Minefield(5, 5, 5);
            }
            else if (mode.equals("medium") || mode.equals("medium debug")) {
                if (mode.equals("medium debug")) debug = true;
                minefield = new Minefield(9, 9, 12);
            }

            else if ((mode.equals("hard")  || mode.equals("hard debug"))) {
                if (mode.equals("hard debug")) debug = true;
                minefield = new Minefield(20, 20, 40);
            }
            else {
                System.out.println("Error in input, please try again."); // only reached if board is not initialized
            }
        }

        while(!minefield.gameOver()){
            // game
        }
    }

    
}
