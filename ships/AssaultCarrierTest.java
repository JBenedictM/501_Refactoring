package ships;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class AssaultCarrierTest {

	private AssaultCarrier ac;
	
	@Before
	public void setup() {
		ac = new AssaultCarrier();
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_name() {
		// check if name is right
		String name = ac.getName();
		String correctName = "Assault Carrier";
		assertEquals(correctName, name);
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_size() {
		// check if size is right
		int size = ac.getSize();
		int correct_size = 3;
		assertEquals(correct_size, size);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_alive_variable() {
		// check if units are considered alive
		boolean isAlive = ac.isAlive();
		boolean correct_isAlive = true;
		assertEquals(correct_isAlive, isAlive);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_number_of_units_alive() {
		// check if correct number of units alive
		int units_alive = ac.getUnitsAlive();
		int correct_units_alive = 3;
		assertEquals(correct_units_alive, units_alive);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_orientation_initialization() {
		// check if ship is initially oriented to the right
		Orientation orient = ac.getOrientation();
		Orientation correct_orient = Orientation.RIGHT;
		assertEquals(correct_orient, orient);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_onBoard_intialization() {
		// ensure ship is not initialize on board
		boolean onBoard = ac.isShipOnBoard();
		boolean correct_onBoard = false;
		assertEquals(correct_onBoard, onBoard);
	}
	
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_right_shape() {
		ac.placeShip(4, 4);
		Coordinates[] location = ac.getLocation();
		
		// ship must look like
		//  x x
		//  x
		Coordinates center = new Coordinates(4, 4);
		Coordinates right = new Coordinates(4, 5);
		Coordinates down = new Coordinates(5, 4);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(right));
		assertTrue(location[2].equals(down));
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the left
	 */
	@Test
	public void test_correct_left_shape() {
		ac.setOrientation(Orientation.LEFT);
		ac.placeShip(4, 4);
		Coordinates[] location = ac.getLocation();
		
		// ship must look like
		//    x
		//  x x
		Coordinates center = new Coordinates(4, 4);
		Coordinates left = new Coordinates(4, 3);
		Coordinates up = new Coordinates(3, 4);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(left));
		assertTrue(location[2].equals(up));
	}
	
	/*
	 * checks that the ship has the right shape when oriented up
	 */
	@Test
	public void test_correct_up_shape() {
		ac.setOrientation(Orientation.UP);
		ac.placeShip(4, 4);
		Coordinates[] location = ac.getLocation();
		
		// ship must look like
		//  x 
		//  x x
		Coordinates center = new Coordinates(4, 4);
		Coordinates up = new Coordinates(3, 4);
		Coordinates right = new Coordinates(4, 5);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(up));
		assertTrue(location[2].equals(right));
	}
	
	/*
	 * checks that the ship has the right shape when oriented up
	 */
	@Test
	public void test_correct_down_shape() {
		ac.setOrientation(Orientation.DOWN);
		ac.placeShip(4, 4);
		Coordinates[] location = ac.getLocation();
		
		// ship must look like
		//  x x
		//    x
		Coordinates center = new Coordinates(4, 4);
		Coordinates down = new Coordinates(5, 4);
		Coordinates left = new Coordinates(4, 3);
		
		assertTrue(location[0].equals(center));
		assertTrue(location[1].equals(down));
		assertTrue(location[2].equals(left));
	}
	
	/*
	 * checks if check bounds recognizes correct bounds
	 */
	@Test
	public void test_correct_bounds() {
		ac.placeShip(4,4);
		assertFalse(ac.checkOutOfBounds());
	}
	
	/*
	 * checks if check bounds recognizes incorrect bounds
	 */
	@Test
	public void test_wrong_bounds() {
		ac.placeShip(8,8);
		assertTrue(ac.checkOutOfBounds());
	}
	

}
