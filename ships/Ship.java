package ships;

import coordinates.Coordinates;

import java.io.Serializable;

/**
 * This is the class that represents a ship.
 * Can return whether this object's location is in bounds of the board
 * @author Faiyaz and Andy
 * @version Date modified: April 11, 2017
 */
public class Ship implements Serializable {
	
	private String shipName, orientation;
	private int shipLength;
	private Coordinates[] location;
	private int units;
	private boolean alive;
	
	/**
	 * A constructor that initializes:
	 * - shipName; name of the ship
	 * - ShipLength; length of the ship
	 * - orientation; the orientation of the ship
	 * - units; the health of the ship
	 * - isAlive; If the ship alive
	 * - location; An array of Coordinate objects
	 * and add a new Coordinate up to the length of the ship
	 * @param shipName; The name of the ship of type String
	 * @param shipLength; the length of the ship of type int
	 */
	public Ship(String shipName, int shipLength) {
		this.shipName = shipName;
		this.shipLength = shipLength;
		this.orientation = "right";
		this.units = shipLength;
		this.alive = false;
		location = new Coordinates[shipLength];
		for (int index = 0; index < shipLength; index++) {
			location[index] = new Coordinates(0,0);
		}	
	}
	
	/**
	 * A copy constructor that copy's another ship
	 * using setters and getters
	 * @param ship; an object of type Ship
	 */
	public Ship(Ship ship) {
		this.shipName = ship.getShipName();
		this.orientation = ship.getOrientation();
		this.shipLength = ship.getShipLength();
		this.location = ship.getLocation();
		this.units = ship.getUnits();
		this.alive = ship.isAlive();
	}
	
	/**
	 * Sets location of the ship 
	 * 
	 * @param row; The row value of type int
	 * @param col; The column value of type int
	 */
	public void placeShip(int row, int col) {
		// Get the new location 
		Coordinates[] newLocation = createNewLocation(row,col);
		// for each index input new Coordinates
		for (int index = 0; index < shipLength; index++) {
			location[index] = newLocation[index];
		}
	}
	
	/*
	 * Returns a Coordinates array of the current orientation
	 * 
	 * @param row; An integer row used as a reference point for the ship
	 * @param col; An integer col used as a reference point for the ship
	 * @return newLocation; A Coordinates array of the current orientation
	 */
	private Coordinates[] createNewLocation(int row, int col) {
		Coordinates[] newLocation = new Coordinates[5];
		Coordinates center = new Coordinates(row,col);
		Coordinates up = new Coordinates(row - 1,col);
		Coordinates down = new Coordinates(row + 1,col);
		Coordinates right = new Coordinates(row,col + 1);
		Coordinates left = new Coordinates(row,col - 1);
		// Place Coordinates depending on the current Orientation
		if (getOrientation().equals("down")) {
			newLocation[0] = center;
			newLocation[1] = down;
			newLocation[2] = left;
			newLocation[3] = right;
			newLocation[4] = up;
		} else if (getOrientation().equals("up")) {
			newLocation[0] = center;
			newLocation[1] = up;
			newLocation[2] = right;
			newLocation[3] = left;
			newLocation[4] = down;
		} else if (getOrientation().equals("right")) {
			newLocation[0] = center;
			newLocation[1] = right;
			newLocation[2] = down;
			newLocation[3] = up;
			newLocation[4] = left;
		} else if (getOrientation().equals("left")) {
			newLocation[0] = center;
			newLocation[1] = left;
			newLocation[2] = up;
			newLocation[3] = down;
			newLocation[4] = right;
		}
		return newLocation;
	}
	
	/**
	 * Check if ship is within boundaries
	 * @return outOfBounds; returns boolean 'true' if out of bounds and vice versa
	 */
	public boolean checkOutOfBounds() {
		boolean outOfBounds = false;
		
		// if the value is in bounds for every coordinate in location
		for (int index = 0; index < shipLength; index++) {
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
		for (int index = 0; index < shipLength; index++) {
			if (location[index].getRow() == row && location[index].getCol() == col) {
				units--;
			}
		}
		// checks if object is still alive
		if (units == 0) {
			this.setAlive(false);
		}
	}
	
	/**
	 * Returns the index of the location at row and col 
	 * @param coord - index to the board array
	 * @return indexNum; returns the index if true and -1 if false
	 */
	public int indexOfLocation(Coordinates coord) {
		int indexNum = -1;
		for (int index = 0; index < shipLength; index++) {
			if (location[index].equals(coord)) {
				indexNum = index;
			}
		}
		return indexNum;
	}
	
	/**
	 * Gets the ship's name
	 * @return returns the name of the ship as a string
	 */
	public String getShipName() {
		return shipName;
	}
	
	/**
	 * gets the length of the ship
	 * @return returns the length as an integer
	 */
	public int getShipLength() {
		return shipLength;
	}
	
	/**
	 * get the location integer array for the ship
	 * and creates a new location
	 * @return returns the clone array as an integer
	 */
	public Coordinates[] getLocation() {
		Coordinates[] newLocation = new Coordinates[shipLength];
		
		for (int index = 0; index < shipLength; index++) {
			newLocation[index] = location[index];
		}
		return newLocation;
	}
	
	/**
	 * Returns the orientation
	 * @return orientation of type String
	 */
	public String getOrientation() {
		return orientation;
	}
	
	/**
	 * Sets the orientation
	 * @param orientation; String orientation
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Changes the orientation of the ship
	 */
	public void rotateShip() {
		// Rotate the ship 90 degrees clockwise
		switch (orientation) {
			case "right":
				orientation = "down";
				break;
			case "down":
				orientation = "left";
				break;
			case "left":
				orientation = "up";
				break;
			case "up":
				orientation = "right";
				break;
			default:
				orientation = orientation;
		}
	}
	
	/**
	 * Returns the number of units
	 * @return units; number units of type int
	 */
	public int getUnits() {
		return units;
	}

	
	/**
	 * Returns a boolean of alive
	 * @return isAlive; of type boolean
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Sets the variable alive of type boolean
	 * @param isAlive; of type boolean
	 */
	public void setAlive(boolean isAlive) {
		this.alive = isAlive;
	}
}