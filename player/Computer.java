package player;

import java.util.ArrayList;
import java.util.Random;
import coordinates.Coordinates;
//board piece enum
import board.Board.BoardPiece;

/**
 * AI's algorithm where the appropriate move is determined
 * It contains all the necessary methods to calculate the appropriate move
 * @author Ben, Andy
 * @version 1.7
 */
public class Computer extends Player {
	
	// Enum representing difficulty
	public enum Difficulty {
		EASY, MEDIUM, HARD;
	}
	
	// constants
	public static final char SHIP = 'S';
	public static final char EMPTY = ' ';
	public static final char HIT = 'X';
	public static final char MISS = 'O';
	
	// used for the probability of the computer making the right choice, the higher the better the chance
	private int difficultyNumber; 
	
	/**
	 * easy has 50% chance to make the right move
	 * medium has 75%
	 * hard has 100%
	 * or else the computer picks a random coordinate
	 * @param difficulty enum
	 */
	public Computer(Difficulty diff) {
		if (diff == Difficulty.HARD) {
			difficultyNumber = 4;
		} else if (diff == Difficulty.MEDIUM) {
			difficultyNumber = 3;
		} else if (diff == Difficulty.EASY) {
			difficultyNumber = 2;
		}
	}
	
	/*
	 * checks if a coordinate is available
	 * throws an exception when the parameters row or column is not within 0 to 8 (inclusive)
	 * 
	 * returns true if the coordinate is either empty or a ship, else it returns false
	 */
	private boolean legalMove(int row, int column, BoardPiece[][] playerBoard) {
		boolean validity = false;
		
		try {
			validity = playerBoard[row][column] == BoardPiece.SHIP || playerBoard[row][column] == BoardPiece.EMPTY;
		} catch (IndexOutOfBoundsException exception) {
			// coordinates are out of bounds, validity is already set to false so no need to do anything
		} 
		
		return validity;
	}
	
	// checks for hits to the left and right of a hit coordinate
	// playerBoard - grid to check on
	// return - the number of horizontal hits 1 space apart
	private int checkRow(int row, int column, BoardPiece[][] playerBoard) {
		int counter = 0;
		boolean loopSwitch;
			
		// these loops go through the specified direction
		// scans left from the given coordinate and checks for hits 1 space apart
		loopSwitch = true;
		int columnCopy = column;
		while (loopSwitch && columnCopy >= 1) {
			if (playerBoard[row][columnCopy] == BoardPiece.HIT) {
				counter++;
				columnCopy--;
			} else {
				loopSwitch = false;
			}
		}
			
		// scans right from the given coordinate and checks for hits 1 space apart
		loopSwitch = true;
		columnCopy = column;
		while (loopSwitch && columnCopy <= 8) {
			if (playerBoard[row][columnCopy] == BoardPiece.HIT) {
				counter++;
				columnCopy++;
			} else {
				loopSwitch = false;
			}
		}
		
		if (counter != 0) { // compensates for the starting coordinate being counted twice assuming its a hit
			counter--;
		}
		return counter;
	}
	
	// checks for hits above and below a hit coordinate
	// playerBoard - grid to check on
	// return - the number of hits vertically 1 space apart
	private int checkCol(int row, int column, BoardPiece[][] playerBoard) {
		int counter = 0;
		boolean loopSwitch;			
	
		// these loops go through the specified direction
		// scans down from the given coordinate for hits 1 space apart
		loopSwitch = true;
		int rowCopy = row;
		while (loopSwitch && rowCopy <= 8) {
			if (playerBoard[rowCopy][column] == BoardPiece.HIT) {
				counter++;
				rowCopy++;
			} else {
				loopSwitch = false;
			}
		}
		
		// scans up from the given coordinate for hits 1 space apart
		loopSwitch = true;
		rowCopy = row;
		while (loopSwitch && rowCopy >= 1) {
			if (playerBoard[rowCopy][column] == BoardPiece.HIT) {
				counter++;
				rowCopy--;
			} else {
				loopSwitch = false;
			}
		}
		
		if (counter != 0) { // compensates for the starting coordinate being counted twice assuming its a hit
			counter--;
		}
		return counter;
	}
	
	// finds all empty coordinates in four direction(up,down,left,right) then appends them to an arrayList
	// @param uses the player's board for evaluation
	private void fourDirectionGuess(int row, int column, BoardPiece[][] playerBoard, ArrayList<Coordinates> possibleMoves) {
		if (legalMove(row+1,column,playerBoard)) {
			Coordinates moveDown = new Coordinates(row+1,column);
			possibleMoves.add(moveDown);
		}
		if (legalMove(row-1,column,playerBoard)) {
			Coordinates moveUp = new Coordinates(row-1,column);
			possibleMoves.add(moveUp);
		}
		if (legalMove(row,column+1,playerBoard)) {
			Coordinates moveRight = new Coordinates(row,column+1);
			possibleMoves.add(moveRight);
		}
		if (legalMove(row,column-1,playerBoard)) {
			Coordinates moveLeft = new Coordinates(row,column-1);
			possibleMoves.add(moveLeft);
		}		
	}

	// checks if a coordinate is either both even or odd
	// if a coordinate meets the requirement, it is appended to a parameter ArrayList
	private void guess(int row, int column, BoardPiece[][] playerBoard, ArrayList<Coordinates> possibleMoves) {		
		if (row % 2 == column % 2 && legalMove(row,column,playerBoard)) {
			possibleMoves.add(new Coordinates(row,column));
		}
	}
	
	// generates a random coordinate
	private Coordinates randomCoord(BoardPiece[][] playerBoard) {
		int randRow, randCol;
		Coordinates randCoord;
		
		// makes sure that the value generated isn't already taken
		do {
			randRow = new Random().nextInt(8) + 1; // adds 1 to the generated number to increase range from (0,7) to (1,8)
			randCol = new Random().nextInt(8) + 1;
		} while (!legalMove(randRow,randCol,playerBoard));
		
		randCoord = new Coordinates(randRow,randCol);
		return randCoord;
	}
	
	/*
	 * makes a guess only if there is a connected straight line of hits
	 * it guesses perpendicularly to the straight line of hits
	 */
	private void adjacentGuess(int row, int column, BoardPiece[][] playerBoard, ArrayList<Coordinates> possibleMoves) {
		int downRow = row + 1, upRow = row - 1;
		int leftCol = column - 1, rightCol = column + 1;
		
		// checks if there is a vertically connected series of hits
		// if this condition is met, it then tries to append valid coordinates to the left and right of the hit coordinate to an arrayList
		if (checkCol(row,column,playerBoard) > 1) {
			if (legalMove(row,leftCol,playerBoard)) {
				possibleMoves.add(new Coordinates(row,leftCol));
			}
			if (legalMove(row,rightCol,playerBoard)) {
				possibleMoves.add(new Coordinates(row,rightCol));
			}
		}
		
		// checks if there is a horizontally connected series of hits
		// if this condition is met, it then tries to append valid coordinates above and below the hit coordinate to an arrayList
		if (checkRow(row,column,playerBoard) > 1) { 
			if (legalMove(upRow,column,playerBoard)) {
				possibleMoves.add(new Coordinates(upRow,column));
			} 
			if (legalMove(downRow,column,playerBoard)) {
				possibleMoves.add(new Coordinates(downRow,column));
			}
		}
	}
	
	/**
	 * This is for sinking ships that has a center (orbiter is an exception but works nonetheless)
	 * 
	 * depending on difficultyNumber, this could make a proper move or a completely random one
	 * @param playerBoard - 2d array of the player's
	 * @return - a coordinates object containing an index for a 2d array
	 */
	public Coordinates getMove(BoardPiece[][] playerBoard) {
		int loopCounter = 1;
		Coordinates nextMove = null;
		ArrayList<Coordinates> bestPossibleMoves = new ArrayList<>();
		
		int diceRoll = new Random().nextInt(4);
		if (difficultyNumber > diceRoll ) {
			while (loopCounter <= 4) {
				for (int row = 1; row <= 8; row++) {
					for (int column = 1; column <= 8; column++) {
						
						// first loop, it looks to completely sink an already found ship
						if (loopCounter == 1 && playerBoard[row][column] == BoardPiece.HIT) {
							if (checkRow(row,column,playerBoard) > 1 && checkCol(row,column,playerBoard) > 1) {
								fourDirectionGuess(row,column,playerBoard,bestPossibleMoves);
							}
						// second loop, it makes a move perpendicularly to 2 connected hits
						} else if (loopCounter == 2 && playerBoard[row][column] == BoardPiece.HIT) {
							adjacentGuess(row,column,playerBoard,bestPossibleMoves);
						// third loop, it guesses where the next hit is of a single hit coordinate
						} else if (loopCounter == 3 && playerBoard[row][column] == BoardPiece.HIT) {
							fourDirectionGuess(row,column,playerBoard,bestPossibleMoves);
						// fourth loop, it makes a move that is either both even or odd 
						} else if (loopCounter == 4) {
							guess(row,column,playerBoard,bestPossibleMoves);
						}
					}
				}
				loopCounter += bestPossibleMoves.isEmpty() ? 1:4;
			}
			
			int randomPick = new Random().nextInt(bestPossibleMoves.size());
			nextMove = bestPossibleMoves.get(randomPick);
		} else {
			nextMove = randomCoord(playerBoard);
		}
		
		return nextMove;
	}
}