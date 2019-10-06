package ships;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class AssaultCarrier extends Ship{
	
	public AssaultCarrier() {
		super("Assault Carrier", 3);
	}
	
	public AssaultCarrier(AssaultCarrier otherAC) {
		super((Ship)otherAC);
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
			newLocation[2] = left;
		} else if (getOrientation().equals(Orientation.UP)) {
			newLocation[0] = center;
			newLocation[1] = up;
			newLocation[2] = right;
		} else if (getOrientation().equals(Orientation.RIGHT)) {
			newLocation[0] = center;
			newLocation[1] = right;
			newLocation[2] = down;
		} else if (getOrientation().equals(Orientation.LEFT)) {
			newLocation[0] = center;
			newLocation[1] = left;
			newLocation[2] = up;
		}
		
		return newLocation;
	}

}
