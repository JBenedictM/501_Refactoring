package battleship.coordinates;

import java.io.Serializable;

/**
 * Class representing a set of row and column coordinates.
 * @author Andy Ma
 * Date of last change: Apr. 9, 2017
 */
public class Coordinates implements Serializable {
	private int rowCoord;
	private int colCoord;
	
	/**
	 * Create a coordinate with an row coordinate of row and column coordinate of column
	 * @param row; the row coordinate
	 * @param col; the column coordinate
	 */
	public Coordinates(int row, int col) {
		rowCoord = row;
		colCoord = col;
	}
	
	/**
	 * @return row coordinate
	 */
	public int getRow() {
		return rowCoord;
	}
	
	/**
	 * @return column coordinate
	 */
	public int getCol() {
		return colCoord;
	}
	
	/**
	 * Checks if the object is in the bounds of the Battleship grid
	 * @return true if coordinates of the object are in the bounds of the Battleship grid, otherwise false
	 */
	public boolean isInBounds() {
		boolean result = (rowCoord >= 1 && rowCoord <= 8) && (colCoord >= 1 && colCoord <= 8);
		return result;
	}

	/**
	 * Compares two instances of Coordinates to see if they are identical
	 * @param other; Coordinate to compare this one with
	 * @return true if rowCoord and colCoord instance variables are equal for both objects, otherwise false
	 */
	public boolean equals(Coordinates other) {
		return (rowCoord == other.rowCoord && colCoord == other.colCoord);
	}
}
