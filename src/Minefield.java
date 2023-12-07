import java.util.Random;

//

public class Minefield {
    /**
     * Global Section
     */
    public static final String ANSI_YELLOW_BRIGHT = "\u001B[33;1m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE_BRIGHT = "\u001b[34;1m";
    public static final String ANSI_BLUE = "\u001b[34m";
    public static final String ANSI_RED_BRIGHT = "\u001b[31;1m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_PURPLE = "\u001b[35m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001b[47m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001b[45m";
    public static final String ANSI_GREY_BACKGROUND = "\u001b[0m";

    /*
     * Class Variable Section
     * 
     */

    /*
     * Things to Note:
     * Please review ALL files given before attempting to write these functions.
     * Understand the Cell.java class to know what object our array contains and
     * what methods you can utilize
     * Understand the StackGen.java class to know what type of stack you will be
     * working with and methods you can utilize
     * Understand the QGen.java class to know what type of queue you will be working
     * with and methods you can utilize
     */

    private Cell[][] board;
    private int rows, columns;
    private int flags;
    private boolean gameOver = false;

    /**
     * Minefield
     * 
     * Build a 2-d Cell array representing your minefield.
     * Constructor
     * 
     * @param rows    Number of rows.
     * @param columns Number of columns.
     * @param flags   x Number of flags, should be equal to mines
     */

    public static void main(String[] args) {
        Minefield test = new Minefield(10, 10, 10);
    }

    public Minefield(int rows, int columns, int flags) { // Giacomo
        this.rows = rows;
        this.columns = columns;
        this.flags = flags;

        // Initialize Board
        board = new Cell[rows][columns];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                board[row][col] = new Cell(false, "-");
            }
        }

    }

    // used to tell how many flags left in-game
    public int getFlags() {
        return flags;
    }

    /**
     * evaluateField
     *
     *
     * @function:
     *            Evaluate entire array.
     *            When a mine is found check the surrounding adjacent tiles. If
     *            another mine is found during this check, increment adjacent cells
     *            status by 1.
     *
     */
    public void evaluateField() { // Giacomo

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < columns; col++) {
                // If the pos is a mine, skip
                if (board[row][col].getStatus().equals("M"))
                    continue;
                int mines = 0;
                // Calculate # of mines
                if (row + 1 < rows) {
                    // Checks the cell directly to the right
                    mines += board[row + 1][col].getStatus().equals("M") ? 1 : 0;
                    if (col + 1 < columns) {
                        // Checks the cell below and to the right
                        mines += board[row + 1][col + 1].getStatus().equals("M") ? 1 : 0;
                    }
                    if (col - 1 >= 0) {
                        // Checks the cell above and to the right
                        mines += board[row + 1][col - 1].getStatus().equals("M") ? 1 : 0;
                    }
                }
                if (row - 1 >= 0) {
                    // Checks the cell directly to the left
                    mines += board[row - 1][col].getStatus().equals("M") ? 1 : 0;
                    if (col + 1 < columns) {
                        // Checks the cell below and to the left
                        mines += board[row - 1][col + 1].getStatus().equals("M") ? 1 : 0;
                    }
                    if (col - 1 >= 0) {
                        // Checks the cell above and to the left
                        mines += board[row - 1][col - 1].getStatus().equals("M") ? 1 : 0;
                    }
                }
                if (col + 1 < columns)
                    // Checks the cell directly below
                    mines += board[row][col + 1].getStatus().equals("M") ? 1 : 0;
                if (col - 1 >= 0)
                    // Checks the cell directly above
                    mines += board[row][col - 1].getStatus().equals("M") ? 1 : 0;
                board[row][col] = new Cell(false, mines + "");
            }
        }
    }

    /**
     * createMines
     *
     * Randomly generate coordinates for possible mine locations.
     * If the coordinate has not already been generated and is not equal to the
     * starting cell set the cell to be a mine.
     * utilize rand.nextInt()
     *
     * @param x     Start x, avoid placing on this square.
     * @param y     Start y, avoid placing on this square.
     * @param mines Number of mines to place.
     */
    public void createMines(int x, int y, int mines) { // Giacomo
        Random rand = new Random();
        for (int i = 0; i < mines; i++) {
            // Generate a position
            int newMineRow = rand.nextInt(rows);
            int newMineCol = rand.nextInt(columns);
            // If the random pos is already taken by a mine, generate a new pos
            while (board[newMineRow][newMineCol].getStatus().equals("M") || (newMineRow == x && newMineCol == y)) {
                newMineRow = rand.nextInt(rows);
                newMineCol = rand.nextInt(columns);
            }
            board[newMineRow][newMineCol] = new Cell(false, "M");
        }
    }

    /**
     * guess
     *
     * Check if the guessed cell is inbounds (if not done in the Main class).
     * Either place a flag on the designated cell if the flag boolean is true or
     * clear it.
     * If the cell has a 0 call the revealZeroes() method or if the cell has a mine
     * end the game.
     * At the end reveal the cell to the user.
     *
     *
     * @param x    The x value the user entered.
     * @param y    The y value the user entered.
     * @param flag A boolean value that allows the user to place a flag on the
     *             corresponding square.
     * @return boolean Return false if guess did not hit mine or if flag was placed,
     *         true if mine found.
     */
    public boolean guess(int x, int y, boolean flag) { //Luke
        if (x >= board.length || x < 0 || y >= board[x].length || y < 0) return false;
        if (board[x][y].getFlagged()) { // check if cell is flagged, if so, unflag, since you can't guess a flagged cell
            board[x][y].setFlagged(false);
            flags++;
            System.out.println("Removed flag at (" + x + ", " + y + ")");
            return true;
        }
        else if (flag && flags > 0 && !board[x][y].getRevealed())  {
            board[x][y].setFlagged(true);
            flags--;
            return true;
        }
        else if (!flag) {
            board[x][y].setRevealed(true);
            if (board[x][y].getStatus().equals("M")) gameOver = true;
            else if (board[x][y].getStatus().equals("0")) revealZeroes(x, y);
            return true;
        }
        return false;
    }

    /**
     * gameOver
     *
     * Ways a game of Minesweeper ends:
     * 1. player guesses a cell with a mine: game over -> player loses
     * 2. player has revealed the last cell without revealing any mines -> player
     * wins
     *
     * @return boolean Return false if game is not over and squares have yet to be
     *         revealed, otheriwse return true.
     */
    public boolean gameOver() {
        if(gameOver) {
            System.out.println("YOU LOST!");
        }
        return gameOver;
    }

    /**
     * Reveal the cells that contain zeroes that surround the inputted cell.
     * Continue revealing 0-cells in every direction until no more 0-cells are found
     * in any direction.
     * Utilize a STACK to accomplish this.
     *
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a stack be useful here rather than a queue?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealZeroes(int x, int y) {
        Stack1Gen<int[]> indices = new Stack1Gen<>();
        indices.push(new int[] {x, y});
        while(!indices.isEmpty()) {
            int[] cord = indices.pop();
            x = cord[0];
            y = cord[1];

            if(board[x][y].getStatus().equals("M")) continue;
            board[x][y].setRevealed(true);
            if(!board[x][y].getStatus().equals("0")) continue;

            if (x < board.length - 1 && !board[x + 1][y].getRevealed()) {
                indices.push(new int[]{x + 1, y});
            }
            if (x > 0 && !board[x - 1][y].getRevealed()) {
                indices.push(new int[]{x - 1, y});
            }
            if (y < board[0].length - 1 && !board[x][y + 1].getRevealed()) {
                indices.push(new int[]{x, y + 1});
            }
            if (y > 0 && !board[x][y - 1].getRevealed()) {
                indices.push(new int[]{x, y - 1});
            }

        }
    }

    /**
     * revealStartingArea
     *
     * On the starting move only reveal the neighboring cells of the inital cell and
     * continue revealing the surrounding concealed cells until a mine is found.
     * Utilize a QUEUE to accomplish this.
     *
     * This method should follow the psuedocode given in the lab writeup.
     * Why might a queue be useful for this function?
     *
     * @param x The x value the user entered.
     * @param y The y value the user entered.
     */
    public void revealStartingArea(int x, int y) { // Luke
        Q1Gen<int[]> indices = new Q1Gen<>();
        indices.add(new int[] { x, y });
        while (indices.length() != 0) {
            int[] curr = indices.remove();
            // get current coordinates to check surroundings
            x = curr[0];
            y = curr[1];
            board[x][y].setRevealed(true); // reveal
            if (board[x][y].getStatus().equals("M")) break; // break if mine is found
            // check surroundings
            if (x < board.length - 1 && !board[x + 1][y].getRevealed()) {
                indices.add(new int[] { x + 1, y });
            }
            if (x > 0 && !board[x - 1][y].getRevealed()) {
                indices.add(new int[] { x - 1, y });
            }
            if (y < board[0].length - 1 && !board[x][y + 1] .getRevealed()) {
                indices.add(new int[] { x, y + 1 });
            }
            if (y > 0 && !board[x][y - 1].getRevealed()) {
                indices.add(new int[] { x, y - 1 });
            }
        }
    }

    /**
     * For both printing methods utilize the ANSI colour codes provided!
     *
     *
     *
     *
     *
     * debug
     *
     * @function This method should print the entire minefield, regardless if the
     *           user has guessed a square.
     *           *This method should print out when debug mode has been selected.
     */
    public void debug() { // Giacomo & Luke
        // using toString() with no hidden tiles
        System.out.println("-------------------\nDEBUGGER:");
        for (int col = 0; col < columns; col++) { // printing to help with coordinates
            System.out.print(col + " ");
        }
        System.out.println();;
        for (int row = 0; row < rows; row++) {
            System.out.print(row + " "); // help with coordinates
            for (int col = 0; col < columns; col++) {
                    switch (board[row][col].getStatus()) {
                        case "M":
                            System.out.print(ANSI_RED_BRIGHT + "M " + ANSI_GREY_BACKGROUND);
                            break;
                        case "-":
                            System.out.print(ANSI_GREY_BACKGROUND + "- ");
                            break;
                        case "0":
                            System.out.print(ANSI_YELLOW + "0 " + ANSI_GREY_BACKGROUND);
                            break;
                        case "1":
                            System.out.print(ANSI_BLUE + "1 " + ANSI_GREY_BACKGROUND);
                            break;
                        case "2":
                            System.out.print(ANSI_GREEN + "2 " + ANSI_GREY_BACKGROUND);
                            break;
                        case "3":
                            System.out.print(ANSI_CYAN + "3 " + ANSI_GREY_BACKGROUND);
                            break;
                        case "4":
                            System.out.print(ANSI_RED + "4 " + ANSI_GREY_BACKGROUND);
                            break;
                        case "5":
                            System.out.print(ANSI_PURPLE + "5 " + ANSI_GREY_BACKGROUND);
                            break;
                        default:
                            System.out.print(ANSI_RED + board[row][col].getStatus() + " " + ANSI_GREY_BACKGROUND);
                    }
                    // s += cell.getStatus() + " ";
                }
            System.out.println();;
            }
        System.out.println("-------------------");
    }

    /**
     * toString
     *
     * @return String The string that is returned only has the squares that has been
     *         revealed to the user or that the user has guessed.
     */
    public String toString() { // Giacomo & Luke
        String s = "   ";
        for (int col = 0; col < columns; col++) { // printing to help with coordinates
            s += col + " ";
            if(col % 10 == col) s += " "; //to give single digit cols more padding, so they're aligned with double digit cols
        }
        s += "\n";
        for (int row = 0; row < rows; row++) {
            s += row + " "; // help with coordinates
            if(row % 10 == row) s += " "; //to give single digit rows more padding, so they're aligned with double digit rows
            for (int col = 0; col < columns; col++) {
                if (board[row][col].getRevealed() || board[row][col].getFlagged()) { // either will display if flagged or revealed
                    if (board[row][col].getFlagged()) s+= ANSI_YELLOW + "F  " + ANSI_GREY_BACKGROUND; // if flagged, display flag
                    else {
                        switch (board[row][col].getStatus()) {
//                            case "F":
//                                s += ANSI_YELLOW + "F " + ANSI_GREY_BACKGROUND;
//                                break;
                            case "M":
                                s += ANSI_RED_BRIGHT + "M" + ANSI_GREY_BACKGROUND;
                                break;
                            case "-":
                                s += ANSI_GREY_BACKGROUND + "-";
                                break;
                            case "0":
                                s += ANSI_YELLOW + "0" + ANSI_GREY_BACKGROUND;
                                break;
                            case "1":
                                s += ANSI_BLUE + "1" + ANSI_GREY_BACKGROUND;
                                break;
                            case "2":
                                s += ANSI_GREEN + "2" + ANSI_GREY_BACKGROUND;
                                break;
                            case "3":
                                s += ANSI_CYAN + "3" + ANSI_GREY_BACKGROUND;
                                break;
                            case "4":
                                s += ANSI_RED + "4" + ANSI_GREY_BACKGROUND;
                                break;
                            case "5":
                                s += ANSI_PURPLE + "5" + ANSI_GREY_BACKGROUND;
                                break;
                            default:
                                s += ANSI_RED + board[row][col].getStatus() + "" + ANSI_GREY_BACKGROUND;
                        }
                        s+= "  "; //padding
                    }
                    // s += cell.getStatus() + " ";
                } else {
                    s += "-  ";
                }
            }
            s += "\n";
        }
        return s;
    }

}