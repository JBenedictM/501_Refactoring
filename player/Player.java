package player;

import coordinates.Coordinates;
import board.Board;
import java.io.Serializable;
// board piece enum
import board.Board.BoardPiece;

/**
 * Class representing a battleship player
 * Gets a move and displays if it was a hit or a miss.
 * @author Andy Ma
 * Date of last change: Apr. 9, 2017
 */
public abstract class Player implements Serializable {
	
	/**
	 * Prompt user for input, validate input, tell user if they hit or if they missed
	 * @param targetBoard; object containing the opponent's ship locations
	 * @return a Coordinates object representing the location targeted by the user
	 */
	public abstract Coordinates getMove(Board targetBoard);
	
	/*
	 * checks if a coordinate is available
	 * 
	 * returns true if the coordinate is either empty or a ship, else it returns false
	 */
	public static boolean isLegalMove(Coordinates coord, BoardPiece[][] playerBoard) {
		boolean validity = true;
		if (!coord.isInBounds()) {
			validity = false;
		}
		
		int row = coord.getRow();
		int col = coord.getCol();
		if (playerBoard[row][col] != BoardPiece.SHIP && playerBoard[row][col] != BoardPiece.EMPTY) {
			validity = false;
		}
		
		return validity;
	}
	
}