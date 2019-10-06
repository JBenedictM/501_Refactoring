package ships;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class Orbiter extends Ship{
	
	public Orbiter() {
		super("Orbiter", 2);
	}
	
	public Orbiter(Orbiter otherOrbiter) {
		super((Ship)otherOrbiter);
	}
	
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
		} else if (getOrientation().equals(Orientation.UP)) {
			newLocation[0] = center;
			newLocation[1] = up;
		} else if (getOrientation().equals(Orientation.RIGHT)) {
			newLocation[0] = center;
			newLocation[1] = right;
		} else if (getOrientation().equals(Orientation.LEFT)) {
			newLocation[0] = center;
			newLocation[1] = left;
		}
		
		return newLocation;
	}

}
