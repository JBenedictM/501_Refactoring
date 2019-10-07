package ships;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import coordinates.Coordinates;
import ships.Ship.Orientation;

public class OrbiterTest {

	private Orbiter ob;
	
	@Before
	public void setup() {
		ob = new Orbiter();
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_name() {
		// check if name is right
		String name = ob.getName();
		String correctName = "Orbiter";
		assertEquals(correctName, name);
		
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_size() {
		// check if size is right
		int size = ob.getSize();
		int correct_size = 2;
		assertEquals(correct_size, size);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_alive_variable() {
		// check if units are considered alive
		boolean isAlive = ob.isAlive();
		boolean correct_isAlive = true;
		assertEquals(correct_isAlive, isAlive);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_basic_number_of_units_alive() {
		// check if correct number of units alive
		int units_alive = ob.getUnitsAlive();
		int correct_units_alive = 2;
		assertEquals(correct_units_alive, units_alive);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_orientation_initialization() {
		// check if ship is initially oriented to the right
		Orientation orient = ob.getOrientation();
		Orientation correct_orient = Orientation.RIGHT;
		assertEquals(correct_orient, orient);
	}
	
	/*
	 * basic sanity checks
	 */
	@Test
	public void test_correct_onBoard_intialization() {
		// ensure ship is not initialize on board
		boolean onBoard = ob.isShipOnBoard();
		boolean correct_onBoard = false;
		assertEquals(correct_onBoard, onBoard);
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the right
	 */
	@Test
	public void test_correct_right_shape() {
		ob.setOrientation(Orientation.RIGHT);
		ob.placeShip(4, 4);
		Coordinates[] location = ob.getLocation();
		
		// ship must look like
		//    
		//    c r
		//    
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(4, 5);
		
		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
	
	}
	
	/*
	 * checks that the ship has the right shape when oriented to the left
	 */
	@Test
	public void test_correct_left_shape() {
		ob.setOrientation(Orientation.LEFT);
		ob.placeShip(4, 4);
		Coordinates[] location = ob.getLocation();
		
		// ship must look like
		//   
		//  r c 
		//    
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(4, 3);

		
		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
		
	}
	
	/*
	 * checks that the ship has the right shape when oriented up
	 */
	@Test
	public void test_correct_up_shape() {
		ob.setOrientation(Orientation.UP);
		ob.placeShip(4, 4);
		Coordinates[] location = ob.getLocation();
		
		// ship must look like
		//    r
		//    c 
		//     
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(3, 4);

		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));

	}
	
	/*
	 * checks that the ship has the right shape when oriented down
	 */
	@Test
	public void test_correct_down_shape() {
		ob.setOrientation(Orientation.DOWN);
		ob.placeShip(4, 4);
		Coordinates[] location = ob.getLocation();
		
		// ship must look like
		//    
		//    c 
		//    r 
		Coordinates c = new Coordinates(4, 4);
		Coordinates r = new Coordinates(5, 4);
		
		assertTrue(location[0].equals(c));
		assertTrue(location[1].equals(r));
	
	}
	
	
	/*
	 * checks if check bounds recognizes correct bounds
	 */
	@Test
	public void test_correct_bounds() {
		ob.placeShip(4,4);
		assertFalse(ob.checkOutOfBounds());
	}
	
	/*
	 * checks if check bounds recognizes incorrect bounds
	 */
	@Test
	public void test_wrong_bounds() {
		ob.placeShip(8,8);
		assertTrue(ob.checkOutOfBounds());
	}


}
