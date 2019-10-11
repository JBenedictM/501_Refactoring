package ships;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class StarShipTest {
	
	private StarShip ss;
	
	@Before
	public void setup() {
		ss = new StarShip();
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_name() {
		// check if name is right
		String name = ss.getName();
		String correctName = "Star Ship";
		assertEquals(correctName, name);
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_size() {
		// check if size is right
		int size = ss.getSize();
		int correct_size = 5;
		assertEquals(correct_size, size);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_alive_variable() {
		// check if units are considered alive
		boolean isOnBoard = ss.isShipOnBoard();
		boolean correct_isOnBoard = false;
		assertEquals(correct_isOnBoard, isOnBoard);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_number_of_units_alive() {
		// check if correct number of units alive
		int units_alive = ss.getUnitsAlive();
		int correct_units_alive = 5;
		assertEquals(correct_units_alive, units_alive);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_orientation_initialization() {
		// check if ship is initially oriented to the right
		Orientation orient = ss.getOrientation();
		Orientation correct_orient = Orientation.RIGHT;
		assertEquals(correct_orient, orient);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_onBoard_intialization() {
		// ensure ship is not initialize on board
		boolean onBoard = ss.isShipOnBoard();
		boolean correct_onBoard = false;
		assertEquals(correct_onBoard, onBoard);
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_right_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		ss.setOrientation(Orientation.RIGHT);
		ss.placeShip(ship_coordinates);
		Coordinates[] location = ss.getLocation();
		
		// ship must look like
		//    u
		//  l c r
		//    d 
		Coordinates center = new Coordinates(4, 4);
		Coordinates right = new Coordinates(4, 5);
		Coordinates down = new Coordinates(5, 4);
		Coordinates up = new Coordinates(3, 4);
		Coordinates left = new Coordinates(4, 3);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(right));
		assertTrue(location[2].equals(down));
		assertTrue(location[3].equals(up));
		assertTrue(location[4].equals(left));
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_left_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		ss.setOrientation(Orientation.LEFT);
		ss.placeShip(ship_coordinates);
		Coordinates[] location = ss.getLocation();
		
		// ship must look like
		//    d
		//  r c l
		//    u 
		Coordinates center = new Coordinates(4, 4);
		Coordinates left = new Coordinates(4, 3);
		Coordinates up = new Coordinates(3, 4);
		Coordinates down = new Coordinates(5, 4);
		Coordinates right = new Coordinates(4, 5);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(left));
		assertTrue(location[2].equals(up));
		assertTrue(location[3].equals(down));
		assertTrue(location[4].equals(right));
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_up_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		ss.setOrientation(Orientation.UP);
		ss.placeShip(ship_coordinates);
		Coordinates[] location = ss.getLocation();
		
		// ship must look like
		//    l
		//  d c u
		//    r 
		Coordinates center = new Coordinates(4, 4);
		Coordinates up = new Coordinates(3, 4);
		Coordinates right = new Coordinates(4, 5);
		Coordinates left = new Coordinates(4, 3);
		Coordinates down = new Coordinates(5, 4);

		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(up));
		assertTrue(location[2].equals(right));
		assertTrue(location[3].equals(left));
		assertTrue(location[4].equals(down));
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_down_shape() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		ss.setOrientation(Orientation.DOWN);
		ss.placeShip(ship_coordinates);
		Coordinates[] location = ss.getLocation();
		
		// ship must look like
		//    r
		//  u c d
		//    l 
		Coordinates center = new Coordinates(4, 4);
		Coordinates down = new Coordinates(5, 4);
		Coordinates left = new Coordinates(4, 3);
		Coordinates right = new Coordinates(4, 5);
		Coordinates up = new Coordinates(3, 4);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(down));
		assertTrue(location[2].equals(left));
		assertTrue(location[3].equals(right));
		assertTrue(location[4].equals(up));
	}
	
	
	/*
	 * checks if check bounds recognizes correct bounds
	 */
	@Test
	public void test_correct_bounds() {
		Coordinates ship_coordinates = new Coordinates(4, 4);

		ss.placeShip(ship_coordinates);
		assertFalse(ss.checkOutOfBounds());
	}
	
	/*
	 * checks if check bounds recognizes incorrect bounds
	 */
	@Test
	public void test_wrong_bounds() {
		Coordinates ship_coordinates = new Coordinates(8, 8);

		ss.placeShip(ship_coordinates);
		assertTrue(ss.checkOutOfBounds());
	}
	
	
	
	
}
