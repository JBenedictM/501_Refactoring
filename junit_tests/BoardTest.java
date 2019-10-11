package junit_tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import board.Board;
import board.Board.BoardPiece;
import coordinates.Coordinates;

public class BoardTest {
	
	private BoardPiece[][] testBoardArray;
	
	
	@Before
	public void setup() {
		testBoardArray = new BoardPiece[][] { 
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.HIT, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
			}; 
	}
	
		
	/*
	 * checks if an empty constructor creates an empty 9x9 board
	 */
	@Test
	public void test_board_no_input() {
		Board testBoard = new Board();
		
		assertEquals(9, testBoard.getRow());
		assertEquals(9, testBoard.getColumn());
		
		for (int i=0; i<9; i++) {
			for (int j=0; j<9; j++) {
				try {
					assertEquals(BoardPiece.EMPTY, testBoard.getPieceAt(new Coordinates(i, j)));
				} catch (Exception e) {
					System.out.println("This should not be reached");
				}
			}
		}
	}
	
	/*
	 * tests board construction with varying dimensions
	 */
	@Test
	public void test_different_dimensions() {
		
		Board testBoard;
		
		for (int i=1; i<12; i++) {
			for (int j=1; j<12; j++) {
				testBoard = new Board(i, j);
				
				assertEquals(i, testBoard.getRow());
				assertEquals(j, testBoard.getColumn());
				
				for (int k=0; k<i; k++) {
					for (int l=0; l<j; l++) {
						try {
							assertEquals(BoardPiece.EMPTY, testBoard.getPieceAt(new Coordinates(k, l)));
						} catch (Exception e) {
							System.out.println("This should not be reached");
						}
					}
				}
			}
		}
		
		
	}
	
	/*
	 * test if array is properly copied and cloned
	 */
	@Test
	public void testBoardBoardPieceArrayArray() {
			
		Board testBoard = new Board(testBoardArray);
		
		assertEquals(9, testBoard.getRow());
		assertEquals(9, testBoard.getColumn());
		
		try {
			
			// checks that all elements are the same
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					try {
						assertEquals(testBoardArray[i][j], testBoard.getPieceAt(new Coordinates(i, j)));
					} catch (Exception e) {
						System.out.println("This should not be reached");
					}
				}
			}
			
			// checks that array was cloned and no data leak exist
			Coordinates testCoord = new Coordinates(0, 0);
			testBoard.setPieceAt(BoardPiece.SHIP, testCoord);
			
			assertNotEquals(testBoardArray[0][0], testBoard.getPieceAt(testCoord));
		} catch (Exception e) {
			System.out.println("This should not be reached");
		}
		
	}
	
	
	/*
	 * test if Board object is properly copied and cloned
	 */
	@Test
	public void test_board_clone_constructor() {
		Board testBoard = new Board(testBoardArray);
		Board testBoard2 = new Board(testBoard);
		
		try {
			for (int i=0; i<9; i++) {
				for (int j=0; j<9; j++) {
					Coordinates currentCoord = new Coordinates(i, j);
					assertEquals(testBoard.getPieceAt(currentCoord), testBoard2.getPieceAt(currentCoord));
				}
			}
			
			Coordinates testCoord = new Coordinates(4,4);
			testBoard.setPieceAt(BoardPiece.SHIP, testCoord);
			assertNotEquals(testBoard.getPieceAt(testCoord), testBoard2.getPieceAt(testCoord));
			
		} catch (Exception e) {
			System.out.println("This should not be reached");
		}
		
	}
	
	/*
	 * test if set/get piece works
	 */
	@Test
	public void test_set_get_piece() {
		Board testBoard = new Board(testBoardArray);
		// contains hit piece
		Coordinates testCoord = new Coordinates(3, 4);
		
		try {
			
			// checks that get piece works
			assertEquals(BoardPiece.HIT, testBoard.getPieceAt(testCoord));
			
			// if get piece works then check if set piece works
			testBoard.setPieceAt(BoardPiece.SHIP, testCoord);
			assertEquals(BoardPiece.SHIP, testBoard.getPieceAt(testCoord));
		} catch (Exception e) {
			
		}
		
	}
	
	@Test
	public void test_set_out_of_bounds_exception() {
		Board testBoard = new Board(testBoardArray);
		boolean result = false;
		
		try {
			testBoard.setPieceAt(BoardPiece.SHIP, new Coordinates(10, 10));
		} catch (Exception e) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void test_set_out_of_bounds_exception2() {
		Board testBoard = new Board(testBoardArray);
		boolean result = false;
		
		try {
			testBoard.setPieceAt(BoardPiece.SHIP, new Coordinates(-1, -1));
		} catch (Exception e) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void test_get_out_of_bounds_exception() {
		Board testBoard = new Board(testBoardArray);
		boolean result = false;
		
		try {
			testBoard.getPieceAt(new Coordinates(10, 10));
		} catch (Exception e) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void test_get_out_of_bounds_exception2() {
		Board testBoard = new Board(testBoardArray);
		boolean result = false;
		
		try {
			testBoard.getPieceAt(new Coordinates(-1, -1));
		} catch (Exception e) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	
	
	/*
	 * checks that getBoard() does not have data leak
	 */
	@Test
	public void test_get_board_leak() {
		Board testBoard = new Board(testBoardArray);
		
		try {
			Board boardClone = testBoard.getBoard();
			
			Coordinates testCoord = new Coordinates(3, 4);
			testBoard.setPieceAt(BoardPiece.SHIP, testCoord);
			
			assertNotEquals(testBoard.getPieceAt(testCoord), boardClone.getPieceAt(testCoord));
		} catch (Exception e) {
			System.out.println("this should not be reached");
		}
	}
	
	@Test
	public void test_get_array_leak() {
		Board testBoard = new Board(testBoardArray);
		
		try {
			
			Coordinates testCoord = new Coordinates(3, 4);
			testBoard.setPieceAt(BoardPiece.SHIP, testCoord);
			
			BoardPiece[][] boardArray = testBoard.getBoardArray();
			assertNotEquals(boardArray[3][4], testBoardArray[3][4]);
		} catch (Exception e) {
			System.out.println("this should not be reached");
		}
	}
	
	

}
