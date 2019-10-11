package ships;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class BattleCruiserTest {
	
	private BattleCruiser bc;
	
	@Before
	public void setup() {
		bc = new BattleCruiser();
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_name() {
		// check if name is right
		String name = bc.getName();
		String correctName = "Battle Cruiser";
		assertEquals(correctName, name);
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_size() {
		// check if size is right
		int size = bc.getSize();
		int correct_size = 4;
		assertEquals(correct_size, size);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_alive_variable() {
		// check if units are considered alive
		boolean isOnBoard = bc.isShipOnBoard();
		boolean correct_isOnBoard = false;
		assertEquals(correct_isOnBoard, isOnBoard);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_number_of_units_alive() {
		// check if correct number of units alive
		int units_alive = bc.getUnitsAlive();
		int correct_units_alive = 4;
		assertEquals(correct_units_alive, units_alive);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_orientation_initialization() {
		// check if ship is initially oriented to the right
		Orientation orient = bc.getOrientation();
		Orientation correct_orient = Orientation.RIGHT;
		assertEquals(correct_orient, orient);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_onBoard_intialization() {
		// ensure ship is not initialize on board
		boolean onBoard = bc.isShipOnBoard();
		boolean correct_onBoard = false;
		assertEquals(correct_onBoard, onBoard);
	}
	
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_right_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		bc.setOrientation(Orientation.RIGHT);
		bc.placeShip(ship_coordinates);
		Coordinates[] location = bc.getLocation();
		
		// ship must look like
		//    u
		//    c r
		//    d 
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(4, 5);
		Coordinates d = new Coordinates(5, 4);
		Coordinates u = new Coordinates(3, 4);
		
		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
		assertTrue(location[2].equals(d));
		assertTrue(location[3].equals(u));
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the left
	 */
	@Test
	public void test_correct_left_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		bc.setOrientation(Orientation.LEFT);
		bc.placeShip(ship_coordinates);
		Coordinates[] location = bc.getLocation();
		
		// ship must look like
		//    d
		//  r c 
		//    u 
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(4, 3);
		Coordinates u = new Coordinates(5, 4);
		Coordinates d = new Coordinates(3, 4);
		
		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
		assertTrue(location[2].equals(d));
		assertTrue(location[3].equals(u));
	}
	
	/*
	 * checks that the ship has the right shape when oriented up
	 */
	@Test
	public void test_correct_up_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		bc.setOrientation(Orientation.UP);
		bc.placeShip(ship_coordinates);
		Coordinates[] location = bc.getLocation();
		
		// ship must look like
		//    r
		//  d c u
		//     
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(3, 4);
		Coordinates u = new Coordinates(4, 5);
		Coordinates d = new Coordinates(4, 3);


		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
		assertTrue(location[2].equals(u));
		assertTrue(location[3].equals(d));
	}
	
	/*
	 * checks that the ship has the right shape when oriented down
	 */
	@Test
	public void test_correct_down_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		bc.setOrientation(Orientation.DOWN);
		bc.placeShip(ship_coordinates);
		Coordinates[] location = bc.getLocation();
		
		// ship must look like
		//    
		//  u c d
		//    r 
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(5, 4);
		Coordinates u = new Coordinates(4, 3);
		Coordinates d = new Coordinates(4, 5);
		
		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
		assertTrue(location[2].equals(u));
		assertTrue(location[3].equals(d));
	}
	
	
	/*
	 * checks if check bounds recognizes correct bounds
	 */
	@Test
	public void test_correct_bounds() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		bc.placeShip(ship_coordinates);
		assertFalse(bc.checkOutOfBounds());
	}
	
	/*
	 * checks if check bounds recognizes incorrect bounds
	 */
	@Test
	public void test_wrong_bounds() {
		Coordinates ship_coordinates = new Coordinates(8, 8);

		bc.placeShip(ship_coordinates);
		assertTrue(bc.checkOutOfBounds());
	}

}
