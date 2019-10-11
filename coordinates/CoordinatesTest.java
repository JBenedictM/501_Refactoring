package coordinates;

import static org.junit.Assert.*;

import org.junit.Test;

public class CoordinatesTest {

	/*
	 * test coorrect initialization
	 */
	@Test
	public void test_correct_init_values() {
		Coordinates coord = new Coordinates(0, 0);
		int rightRow = 0;
		int rightCol = 0;
	
		assertEquals(rightRow, coord.getRow());
		assertEquals(rightCol, coord.getCol());

	}
	
	@Test
	public void test_for_correct_bounds() {
		Coordinates coord = new Coordinates(1,2);
		
		assertTrue(coord.isInBounds());
	}
	
	@Test
	public void test_for_incorrect_bounds() {
		Coordinates coord = new Coordinates(-1, -1);
		
		assertFalse(coord.isInBounds());
	}
	
	@Test
	public void test_for_correct_equals() {
		Coordinates coord = new Coordinates(8,8);
		Coordinates coord_copy = new Coordinates(8,8);
		
		assertTrue(coord.equals(coord_copy));

	}
	
	@Test
	public void test_for_incorrect_equals() {
		Coordinates coord = new Coordinates(8,8);
		Coordinates coord_copy = new Coordinates(1,1);
		
		assertFalse(coord.equals(coord_copy));

	}
}
