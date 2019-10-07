package ships;

import coordinates.Coordinates;

public class StarShip extends Ship{
		
	/**
	 * Initialize a ship with a ship size of 5
	 * Shape looks like a cross
	 */
	public StarShip() {
		super("Star Ship", 5);
	}
	
	/**
	 * Clone constructor
	 * @param otherStarship
	 */
	public StarShip(StarShip otherStarship) {
		super((Ship)otherStarship);
	}
	
	
	/**
	 * Returns ship coordindates with the shape of a "StarShip"
	 * 		x
	 * 	  x c x   
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
			newLocation[4] = up;
		} else if (getOrientation().equals(Orientation.UP)) {
			newLocation[0] = center;
			newLocation[1] = up;
			newLocation[2] = right;
			newLocation[3] = left;
			newLocation[4] = down;
		} else if (getOrientation().equals(Orientation.RIGHT)) {
			newLocation[0] = center;
			newLocation[1] = right;
			newLocation[2] = down;
			newLocation[3] = up;
			newLocation[4] = left;
		} else if (getOrientation().equals(Orientation.LEFT)) {
			newLocation[0] = center;
			newLocation[1] = left;
			newLocation[2] = up;
			newLocation[3] = down;
			newLocation[4] = right;
		}
		
		return newLocation;
	}
	
	
}
