package junit_tests;

import static org.junit.Assert.*;

import java.io.Serializable;

import org.junit.Before;
import org.junit.Test;

import board.BattleshipBoard;
import board.Board;
import board.Board.BoardPiece;
import coordinates.Coordinates;

public class BattleshipBoardTest{
	
	private BattleshipBoard testBattleshipBoard;
	private String boardName;
	
	@Before
	public void setup() {
		boardName = "testBoard";
		testBattleshipBoard = new BattleshipBoard(boardName);
	}
	
	@Test
	public void test_for_correct_title() {
		assertEquals(boardName, testBattleshipBoard.getTitle());
	}
	
	
	@Test
	public void test_for_correct_dimension() {
		Board testUnderlyingBoard = testBattleshipBoard.getBoard();
		
		int correctDimension = 9;
		assertEquals(correctDimension, testUnderlyingBoard.getRow());
		assertEquals(correctDimension, testUnderlyingBoard.getColumn());
		

	}
	
	/*
	 * test if the total number of ship pieces are correct
	 * does not check for correct shape
	 */
	@Test
	public void test_automatic_ship_setup() {
		// in total there should be 5+4+3+2 ship pieces on the board
		testBattleshipBoard.autoShipSetup();
		Board testBoard = testBattleshipBoard.getBoard();
		BoardPiece[][] testBoardArray = testBoard.getBoardArray();
		
		int shipCounter = 0;
		for (int i=0; i<testBoard.getRow(); i++) {
			for (int j=0; j<testBoard.getColumn(); j++) {
				if (testBoardArray[i][j] == BoardPiece.SHIP) {
					shipCounter++;
				}
			}
		}
		
		int correctShipCount = 5+4+3+2;
		assertEquals(correctShipCount, shipCounter);
	}
	
	
	
	
	@Test
	public void test_if_target_ship_correctly_marked_as_hit() {
		testBattleshipBoard.autoShipSetup();
		Board testBoard = testBattleshipBoard.getBoard();
		BoardPiece[][] testBoardArray = testBoard.getBoardArray();
		
		Coordinates shipCoord = null;
		for (int i=0; i<testBoard.getRow(); i++) {
			for (int j=0; j<testBoard.getColumn(); j++) {
				
				if (testBoardArray[i][j] == BoardPiece.SHIP) {
					shipCoord = new Coordinates(i, j);
					break;
				}
			}
			
			if (shipCoord != null) {
				break;
			}
		}
		try {
			testBattleshipBoard.targetCoordinate(shipCoord);

		} catch (Exception e) {
			System.out.println("This should not happen");
		}
		
		BoardPiece resultPiece = null;
		try {
			resultPiece = testBattleshipBoard.getBoardPieceAt(shipCoord);
			
		} catch (Exception e) {
			System.out.println("This should not happen");
		}
		
		assertEquals(BoardPiece.HIT, resultPiece);
	}
	
	@Test
	public void test_if_wrong_target_correctly_marked_as_miss() {
		testBattleshipBoard.autoShipSetup();
		Board testBoard = testBattleshipBoard.getBoard();
		BoardPiece[][] testBoardArray = testBoard.getBoardArray();
		
		Coordinates shipCoord = null;
		for (int i=1; i<testBoard.getRow(); i++) {
			for (int j=1; j<testBoard.getColumn(); j++) {
				
				if (testBoardArray[i][j] == BoardPiece.EMPTY) {
					shipCoord = new Coordinates(i, j);
					break;
				}
			}
			
			if (shipCoord != null) {
				break;
			}
		}
		try {
			testBattleshipBoard.targetCoordinate(shipCoord);

		} catch (Exception e) {
			System.out.println("This should not happen");
		}
		
		BoardPiece resultPiece = null;
		try {
			resultPiece = testBattleshipBoard.getBoardPieceAt(shipCoord);
			
		} catch (Exception e) {
			System.out.println("This should not happen");
		}
		
		assertEquals(BoardPiece.MISS, resultPiece);
	}
	
	
	@Test
	public void test_if_one_ship_alive_is_tracked_correctly() {
		// shoot down all ships except 1
		int shipToShootDown = (5+4+3+2) -1;
		
		testBattleshipBoard.autoShipSetup();
		Board testBoard = testBattleshipBoard.getBoard();
		BoardPiece[][] testBoardArray = testBoard.getBoardArray();
		
		int shipHitCounter = 0;
		for (int i=1; i<testBoard.getRow(); i++) {
			for (int j=1; j<testBoard.getColumn(); j++) {
				if (testBoardArray[i][j] == BoardPiece.SHIP) {
					
					try {
						testBattleshipBoard.targetCoordinate(new Coordinates(i, j));
						shipHitCounter++;
					} catch (Exception e) {
						System.out.println("This should not be reached");
					}
				}
				
				if (shipHitCounter == shipToShootDown) break;
				
			}
			if (shipHitCounter == shipToShootDown) break;
		}
		
		int correctNumOfShipsAlive = 1;
		assertEquals(correctNumOfShipsAlive, testBattleshipBoard.getShipsAlive());
	}
	
	@Test
	public void test_for_no_ships_alive() {
		// shoot down all ships except 1
		int shipToShootDown = (5+4+3+2);
		
		testBattleshipBoard.autoShipSetup();
		Board testBoard = testBattleshipBoard.getBoard();
		BoardPiece[][] testBoardArray = testBoard.getBoardArray();
		
		int shipHitCounter = 0;
		for (int i=1; i<testBoard.getRow(); i++) {
			for (int j=1; j<testBoard.getColumn(); j++) {
				if (testBoardArray[i][j] == BoardPiece.SHIP) {
					
					try {
						testBattleshipBoard.targetCoordinate(new Coordinates(i, j));
						shipHitCounter++;
					} catch (Exception e) {
						System.out.println("This should not be reached");
					}
				}
				
				if (shipHitCounter == shipToShootDown) break;
				
			}
			if (shipHitCounter == shipToShootDown) break;
		}
		
		int correctNumOfShipsAlive = 0;
		assertEquals(correctNumOfShipsAlive, testBattleshipBoard.getShipsAlive());
	}
	
	@Test
	public void test_for_clone_board_leak() {
		testBattleshipBoard.autoShipSetup();
		
		BattleshipBoard battleBoard2 = new BattleshipBoard(testBattleshipBoard);
		
		Coordinates testCoord = new Coordinates(4,4);
		try {
			testBattleshipBoard.targetCoordinate(testCoord);
			
			assertNotEquals(testBattleshipBoard.getBoardPieceAt(testCoord), battleBoard2.getBoardPieceAt(testCoord));
		} catch (Exception e) {
			System.out.println("This should not be reached");
		}
		
	}
	
	


}
