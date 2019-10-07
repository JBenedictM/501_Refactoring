package ships;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class BattleCruiser extends Ship{

	
	/**
	 * Initialize a ship with a ship size of 4
	 * Shape looks like a T
	 */
	public BattleCruiser() {
		super("Battle Cruiser", 4);
	}
	
	/**
	 * clone constructor
	 * @param otherBattleCruiser - object that will be cloned
	 */
	public BattleCruiser(BattleCruiser otherBattleCruiser) {
		super((Ship)otherBattleCruiser);
	}
	
	
	/**
	 * Returns ship coordindates with the shape of a "Battle Cruiser"
	 * 		x
	 * 	    c x   
	 *      x
	 * ship will be centered on the given coordinate
	 * Orientation is also factored in
	 * 
	 */
	@Override
	protected Coordinates[] getShipCoordinates(int row, int col) {
		Coordinates[] newLocation = new Coordinates[super.getSize()];
		Coordinates center = new Coordinates(row,col);
		Coordinates up = new Coordinates(row - 1,col);
		Coordinates down = new Coordinates(row + 1,col);
		Coordinates right = new Coordinates(row,col + 1);
		Coordinates left = new Coordinates(row,col - 1);
		
		// Place Coordinates depending on the current Orientation
		if (getOrientation().equals(Orientation.DOWN)) {
			newLocation[0] = center;
			newLocation[1] = down;
			newLocation[2] = left;
			newLocation[3] = right;
		} else if (getOrientation().equals(Orientation.UP)) {
			newLocation[0] = center;
			newLocation[1] = up;
			newLocation[2] = right;
			newLocation[3] = left;
		} else if (getOrientation().equals(Orientation.RIGHT)) {
			newLocation[0] = center;
			newLocation[1] = right;
			newLocation[2] = down;
			newLocation[3] = up;
		} else if (getOrientation().equals(Orientation.LEFT)) {
			newLocation[0] = center;
			newLocation[1] = left;
			newLocation[2] = up;
			newLocation[3] = down;
		}
		
		return newLocation;
	}


}
