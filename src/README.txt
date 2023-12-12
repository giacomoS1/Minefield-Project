* Made by Luke Matuza - matuz005 and Giacomo Siniscalchi - sinis006
CONTRIBUTIONS
Minefield.java:
 getFlags(), guess(), revealMines(), revealStartingArea() - Luke Matuza, matuz005
 Minefield(), evaluateField(), createMines(), revealZeroes(), gameOver()  - Giacomo Siniscalchi, sinis006
 debug(), toString() - Luke Matuza, matuz005 and Giacomo Siniscalchi, sinis006
main.java - Luke Matuza, matuz005 and Giacomo Siniscalchi, sinis00
Cell.java: 
*Added for the additional feature of unflagging
 setFlagged(), getFlagged() - Luke Matuza, matuz005 


INSTRUCTIONS/INFO
To start the game, choose a difficulty from 0-2 (inclusive), and add " debug" after your coordinate to play in debug mode, and enter a starting coordinate to begin the game. 
While playing, to flag a coordinate add " F" to your input, and to unflag, enter the coordinate of a flagged space.
To win, either flag all mines correctly, or reveal every non-mine cell.

ADDITIONAL FEATURES
To make the game more like minesweeper, unflagging was added by creating a "setFlagged" and "getFlagged" function in the Cell class. 
To also add another way of winning besides revealing every non-mine cell, players who flag every mine cell will win. 
RevealZeros() reveals the border of the zeros including diagonals, unlike the example shown in the project file, to be more like minesweeper and for convenience.
Losing reveals mines with the added revealMines() function, similarily to losing in minesweeper.

I certify that the information contained in this README
file is complete and accurate. I have both read and followed the course policies
in the ‘Academic Integrity - Course Policy’ section of the course syllabus. 
Luke Matuza, Giacomo Siniscalchi