package ships;

import coordinates.Coordinates;

import java.io.Serializable;

/**
 * This is the class that represents a ship.
 * Can return whether this object's location is in bounds of the board
 * @author Faiyaz and Andy
 * @version Date modified: April 11, 2017
 */
public abstract class Ship implements Serializable {
	
	public enum Orientation {
		RIGHT, LEFT, UP, DOWN;
	}
	
	private String shipName;
	private Orientation orientation;
	private int shipSize;
	private Coordinates[] location;
	private int unitsAlive;
	private boolean onBoard;

	
	/**
	 * A constructor that initializes:
	 * - shipName; name of the ship
	 * - ShipLength; length of the ship
	 * - orientation; the orientation of the ship
	 * - shipUnitsAlive; the health of the ship
	 * - location; An array of Coordinate objects
	 * and add a new Coordinate up to the length of the ship
	 * @param shipName; The name of the ship of type String
	 * @param shipLength; the length of the ship of type int
	 */
	public Ship(String shipName, int shipSize) {
		this.shipName = shipName;
		this.shipSize = shipSize;
		this.orientation = Orientation.RIGHT;
		this.unitsAlive = this.shipSize;
		this.onBoard = false;
		
		location = new Coordinates[shipSize];
		for (int index = 0; index < shipSize; index++) {
			location[index] = new Coordinates(0,0);
		}	
	}
	
	/**
	 * A copy constructor that copy's another ship
	 * using setters and getters
	 * @param ship; an object of type Ship
	 */
	public Ship(Ship ship) {
		this.shipName = ship.getName();
		this.orientation = ship.getOrientation();
		this.shipSize = ship.getShipSize();
		this.location = ship.getLocation();
		this.unitsAlive = ship.getUnitsAlive();
		this.onBoard = ship.isShipOnBoard();
	}
	
	/**
	 * Sets location of the ship 
	 * 
	 * @param row; The row value of type int
	 * @param col; The column value of type int
	 */
	public void placeShip(int row, int col) {
		// Get the new location 
		Coordinates[] newLocation = getShipCoordinates(row,col);
		// for each index input new Coordinates
		for (int index = 0; index < shipSize; index++) {
			location[index] = newLocation[index];
		}
	}
	
	/**
	 * returns ship coordinates at the specified location
	 * this method defines the shape of the ship 
	 * and its appearance relative to its orientation
	 * @param row - array index
	 * @param col - array index
	 * @return - Coordinates[] where the the whole ship would be
	 */
	protected abstract Coordinates[] getShipCoordinates(int row, int col);
	
	
	/*
	 * Returns a Coordinates array of the current orientation
	 * 
	 * @param row; An integer row used as a reference point for the ship
	 * @param col; An integer col used as a reference point for the ship
	 * @return newLocation; A Coordinates array of the current orientation
	 */
//	private Coordinates[] createNewLocation(int row, int col) {
//		Coordinates[] newLocation = new Coordinates[5];
//		Coordinates center = new Coordinates(row,col);
//		Coordinates up = new Coordinates(row - 1,col);
//		Coordinates down = new Coordinates(row + 1,col);
//		Coordinates right = new Coordinates(row,col + 1);
//		Coordinates left = new Coordinates(row,col - 1);
//		// Place Coordinates depending on the current Orientation
//		if (getOrientation().equals("down")) {
//			newLocation[0] = center;
//			newLocation[1] = down;
//			newLocation[2] = left;
//			newLocation[3] = right;
//			newLocation[4] = up;
//		} else if (getOrientation().equals("up")) {
//			newLocation[0] = center;
//			newLocation[1] = up;
//			newLocation[2] = right;
//			newLocation[3] = left;
//			newLocation[4] = down;
//		} else if (getOrientation().equals("right")) {
//			newLocation[0] = center;
//			newLocation[1] = right;
//			newLocation[2] = down;
//			newLocation[3] = up;
//			newLocation[4] = left;
//		} else if (getOrientation().equals("left")) {
//			newLocation[0] = center;
//			newLocation[1] = left;
//			newLocation[2] = up;
//			newLocation[3] = down;
//			newLocation[4] = right;
//		}
//		return newLocation;
//	}
	
	/**
	 * Check if ship is within boundaries
	 * @return outOfBounds; returns boolean 'true' if out of bounds and vice versa
	 */
	public boolean checkOutOfBounds() {
		boolean outOfBounds = false;
		
		// if the value is in bounds for every coordinate in location
		for (int index = 0; index < shipSize; index++) {
			if (!location[index].isInBounds()) {
				outOfBounds = true;
			}
		}
		// return the outOfBounds boolean
		return outOfBounds;
	}
	
	/**
	 * Decrements the units of the object
	 * @param row; the row that is targeted of type int
	 * @param col; the col this is targeted of type int
	 */
	public void hitShip(int row, int col) {
		// decrements the units
		for (int index = 0; index < shipSize; index++) {
			if (location[index].getRow() == row && location[index].getCol() == col) {
				unitsAlive--;
			}
		}
		
		
//		// checks if object is still alive
//		if (unitsAlive == 0) {
//			this.setAlive(false);
//		}
	}
	
	/**
	 * Returns the index of the location at row and col 
	 * @param coord - index to the board array
	 * @return indexNum; returns the index if true and -1 if false
	 */
	public int indexOfLocation(Coordinates coord) {
		int indexNum = -1;
		for (int index = 0; index < shipSize; index++) {
			if (location[index].equals(coord)) {
				indexNum = index;
			}
		}
		return indexNum;
	}
	
	
	/**
	 * gets the length of the ship
	 * @return returns the length as an integer
	 */
	public int getShipSize() {
		return shipSize;
	}
	
	/**
	 * get the location integer array for the ship
	 * and creates a new location
	 * @return returns the clone array as an integer
	 */
	public Coordinates[] getLocation() {
		Coordinates[] newLocation = new Coordinates[shipSize];
		
		for (int index = 0; index < shipSize; index++) {
			newLocation[index] = location[index];
		}
		return newLocation;
	}
	
	/**
	 * Returns the orientation
	 * @return orientation of type String
	 */
	public Orientation getOrientation() {
		return orientation;
	}
	
	/**
	 * Sets the orientation
	 * @param orientation; String orientation
	 */
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Changes the orientation of the ship
	 */
	public void rotateShipClockwise90() {
		// Rotate the ship 90 degrees clockwise
		switch (orientation) {
			case RIGHT:
				orientation = Orientation.DOWN;
				break;
			case DOWN:
				orientation = Orientation.LEFT;
				break;
			case LEFT:
				orientation = Orientation.UP;
				break;
			case UP:
				orientation = Orientation.RIGHT;
				break;
				
				
//			default:
//				orientation = orientation;
		}
	}
	
	/**
	 * returns the name of the ship's kind
	 * needed to identify different ships in the code
	 * @return - the ship's kind in string
	 */
	public String getName() {
		return this.shipName;
	}
	
	/**
	 * Returns the number of units
	 * @return units; number units of type int
	 */
	public int getUnitsAlive() {
		return unitsAlive;
	}
	
	/**
	 * 
	 * @return - size of the ship
	 */
	public int getSize() {
		return this.shipSize;
	}

	
	/**
	 * 
	 * @return - true if the ship still has units that are alive, otherwise false
	 */
	public boolean isAlive() {
		
		if (unitsAlive != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isShipOnBoard() {
		return this.onBoard;
	}
	
	public void removeShipFromBoard() {
		this.onBoard = false;
	}
	
	public void setShipOnBoard() {
		this.onBoard = true;
	}

//	
//	/**
//	 * Sets the variable alive of type boolean
//	 * @param isAlive; of type boolean
//	 */
//	public void setAlive(boolean isAlive) {
//		this.alive = isAlive;
//	}
}