package player;

import coordinates.Coordinates;
//board piece enum
import board.Board.BoardPiece;

/**
 * Class representing a human battleship player
 * Gets a move and displays if it was a hit or a miss.
 * Keeps track of how many times the player hit a ship and how many times the player missed.
 * @author Andy Ma
 * Date of last change: Apr. 9, 2017
 */
public class User extends Player {
	private static final char SHIP = 'S';
	
	private int numHits;
	private int numMisses;
	private Coordinates move;
	
	/**
	 * Initialize numHits and numMisses to 0 and move to null
	 */
	public User() {
		numHits = 0;
		numMisses = 0;
		move = null;
	}
	
	/**
	 * @return the number of times the player hit (part of) a ship
	 */
	public int getHits() {
		return numHits;
	}
	
	/**
	 * @return the number of times the player missed (targeted an empty location)
	 */
	public int getMisses() {
		return numMisses;
	}
	
	/**
	 * @return the proportion of the player's targets that were hits
	 */
	public double getHitRate() {
		double result = (double)numHits / (numHits + numMisses);
		return result;
	}
	
	/**
	 * Sets move to null at the beginning of a turn, indicating the user has not yet made a move
	 */
	public void resetMove() {
		move = null;
	}
	
	/**
	 * Sets the move and updates hits and misses according to the row and column targeted by the user
	 * @param moveAsString; string containing row and column targeted by the user
	 * @param targetBoard; an array containing the opponent's ship locations
	 */
	public void updateMoveAndStats(String moveAsString, BoardPiece[][] targetBoard) {
		int guessRow = Integer.parseInt(moveAsString.substring(0,1));
		int guessCol = Integer.parseInt(moveAsString.substring(2));
		
		// Check if user hit or missed and update counters
		if (targetBoard[guessRow][guessCol] == BoardPiece.SHIP) { // User hit
			numHits++;
			move = new Coordinates(guessRow,guessCol);
		} else { // User missed
			numMisses++;
			move = new Coordinates(guessRow,guessCol);
		}
	}
	
	/**
	 * Return the user's move
	 * @param targetBoard; an array containing the opponent's ship locations
	 * @return a Coordinates object representing the location targeted by the user
	 */
	public Coordinates getMove(BoardPiece[][] targetBoard) {
		return move;
	}
}
