package player;

import java.util.ArrayList;
import java.util.Random;
import coordinates.Coordinates;
import board.Board;
//board piece enum
import board.Board.BoardPiece;

/**
 * AI's algorithm where the appropriate move is determined
 * It contains all the necessary methods to calculate the appropriate move
 * @author Ben, Andy
 * @version 1.7
 */
public class AI extends Player {
	
	// Enum representing difficulty
	public enum Difficulty {
		EASY, MEDIUM, HARD;
	}
	
	// used for the probability of the computer making the right choice, the higher the better the chance
	private int difficultyNumber; 
	
	/**
	 * easy has 50% chance to make the right move
	 * medium has 75%
	 * hard has 100%
	 * or else the computer picks a random coordinate
	 * @param difficulty enum
	 */
	public AI(Difficulty diff) {
		if (diff == Difficulty.HARD) {
			difficultyNumber = 4;
		} else if (diff == Difficulty.MEDIUM) {
			difficultyNumber = 3;
		} else if (diff == Difficulty.EASY) {
			difficultyNumber = 2;
		}
	}
	
	
	// checks for hits to the left and right of a hit coordinate
	// playerBoard - grid to check on
	// return - the number of horizontal hits 1 space apart
	private int checkRow(Coordinates coord, BoardPiece[][] playerBoard) {
		int counter = 0;
		boolean loopSwitch;
		int row = coord.getRow();
		int column = coord.getCol();
			
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
	private int checkCol(Coordinates coord, BoardPiece[][] playerBoard) {
		int counter = 0;
		int row = coord.getRow();
		int column = coord.getCol();
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
	private void fourDirectionGuess(Coordinates coord, BoardPiece[][] playerBoard, ArrayList<Coordinates> possibleMoves) {
		int row = coord.getRow();
		int column = coord.getCol();
		Coordinates temp;
		
		// check if space below is valid
		temp = new Coordinates(row+1,column);
		if (Player.isLegalMove(temp, playerBoard)) {
			possibleMoves.add(temp);
		}
		
		// check if space above is valid
		temp = new Coordinates(row-1,column);
		if (Player.isLegalMove(temp, playerBoard)) {
			possibleMoves.add(temp);
		}
		
		// check if space to the right is valid
		temp = new Coordinates(row,column+1);
		if (Player.isLegalMove(temp, playerBoard)) {
			possibleMoves.add(temp);
		}
		
		// check if space to the left is valid
		temp = new Coordinates(row,column-1);
		if (Player.isLegalMove(temp, playerBoard)) {
			possibleMoves.add(temp);
		}		
	}

	// checks if a coordinate is either both even or odd
	// if a coordinate meets the requirement, it is appended to a parameter ArrayList
	private void guess(Coordinates coord, BoardPiece[][] playerBoard, ArrayList<Coordinates> possibleMoves) {		
		int row = coord.getRow();
		int column = coord.getCol();
		
		if (row % 2 == column % 2 && Player.isLegalMove(coord,playerBoard)) {
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
		} while (!Player.isLegalMove(new Coordinates(randRow,randCol), playerBoard));
		
		randCoord = new Coordinates(randRow,randCol);
		return randCoord;
	}
	
	/*
	 * makes a guess only if there is a connected straight line of hits
	 * it guesses perpendicularly to the straight line of hits
	 */
	private void adjacentGuess(Coordinates coord, BoardPiece[][] playerBoard, ArrayList<Coordinates> possibleMoves) {
		int row = coord.getRow();
		int column = coord.getCol();
		
		int downRow = row + 1, upRow = row - 1;
		int leftCol = column - 1, rightCol = column + 1;
		
		// checks if there is a vertically connected series of hits
		// if this condition is met, it then tries to append valid coordinates to the left and right of the hit coordinate to an arrayList
		if (checkCol(coord,playerBoard) > 1) {
			Coordinates tempCoord = new Coordinates(row,leftCol);
			if (Player.isLegalMove(tempCoord, playerBoard)) {
				possibleMoves.add(tempCoord);
			}
			
			tempCoord = new Coordinates(row,rightCol);
			if (Player.isLegalMove(tempCoord, playerBoard)) {
				possibleMoves.add(tempCoord);
			}
		}
		
		// checks if there is a horizontally connected series of hits
		// if this condition is met, it then tries to append valid coordinates above and below the hit coordinate to an arrayList
		if (checkRow(coord, playerBoard) > 1) { 
			Coordinates tempCoord = new Coordinates(upRow, column);
			if (Player.isLegalMove(tempCoord, playerBoard)) {
				possibleMoves.add(tempCoord);
			} 
			
			tempCoord = new Coordinates(downRow, column);
			if (Player.isLegalMove(tempCoord, playerBoard)) {
				possibleMoves.add(tempCoord);
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
	public Coordinates getMove(Board playerBoard) {
		int loopCounter = 1;
		Coordinates nextMove = null;
		ArrayList<Coordinates> bestPossibleMoves = new ArrayList<>();
		BoardPiece[][] playerBoardArray = playerBoard.getBoardArray();
		
		int diceRoll = new Random().nextInt(4);
		if (difficultyNumber > diceRoll ) {
			while (loopCounter <= 4) {
				for (int row = 1; row <= 8; row++) {
					for (int column = 1; column <= 8; column++) {
						
						Coordinates targetCoord = new Coordinates(row, column);
						// first loop, it looks to completely sink an already found ship
						if (loopCounter == 1 && playerBoardArray[row][column] == BoardPiece.HIT) {
							if (checkRow(targetCoord, playerBoardArray) > 1 && checkCol(targetCoord, playerBoardArray) > 1) {
								fourDirectionGuess(targetCoord, playerBoardArray, bestPossibleMoves);
							}
						// second loop, it makes a move perpendicularly to 2 connected hits
						} else if (loopCounter == 2 && playerBoardArray[row][column] == BoardPiece.HIT) {
							adjacentGuess(targetCoord, playerBoardArray, bestPossibleMoves);
						// third loop, it guesses where the next hit is of a single hit coordinate
						} else if (loopCounter == 3 && playerBoardArray[row][column] == BoardPiece.HIT) {
							fourDirectionGuess(targetCoord, playerBoardArray, bestPossibleMoves);
						// fourth loop, it makes a move that is either both even or odd 
						} else if (loopCounter == 4) {
							guess(targetCoord, playerBoardArray, bestPossibleMoves);
						}
					}
				}
				loopCounter += bestPossibleMoves.isEmpty() ? 1:4;
			}
			
			int randomPick = new Random().nextInt(bestPossibleMoves.size());
			nextMove = bestPossibleMoves.get(randomPick);
		} else {
			nextMove = randomCoord(playerBoardArray);
		}
		
		return nextMove;
	}
}