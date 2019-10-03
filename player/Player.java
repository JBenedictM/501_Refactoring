package battleship.player;

import battleship.coordinates.Coordinates;

import java.io.Serializable;

/**
 * Class representing a battleship player
 * Gets a move and displays if it was a hit or a miss.
 * @author Andy Ma
 * Date of last change: Apr. 9, 2017
 */
public abstract class Player implements Serializable {
	/**
	 * Prompt user for input, validate input, tell user if they hit or if they missed
	 * @param targetBoard; an array containing the opponent's ship locations
	 * @return a Coordinates object representing the location targeted by the user
	 */
	public abstract Coordinates getMove(char[][] targetBoard);
}