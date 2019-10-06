import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import player.AI;
import player.AI.Difficulty;
import board.Board;
import board.Board.BoardPiece;
import coordinates.Coordinates;

public class AITest {
	
	// test will used a hard AI as the other difficulties have some randomness
	AI hardAI = new AI(Difficulty.HARD);
	
	
	
	/*
	 * keep making smart guess until there are no smart guess left
	 */
	@Test
	public void test_random_choice() {
		// smart guess should only choose coordinates that are both even or both odd
		// smart guess should create a checker board pattern
		BoardPiece[][] testBoard = new BoardPiece[][] { 
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
								}; 
								
		Board testBoardObj = new Board(testBoard);
		
		// half of all tiles are valid smart guess
		for (int i=1; i<=4; i++) {
			for (int j=1; j<=4; j++) {
				Coordinates result = hardAI.getMove(testBoardObj);
				
				assertTrue("smart guess was not both even or odd" ,result.getRow() % 2 == result.getCol() % 2 );
				try {
					testBoardObj.setPieceAt(BoardPiece.MISS, result);
				} catch (Exception e) {
					System.out.println("Invalid coordinates, this should not be reached");
				}
			}
		}
		
		
	}
	
	/*
	 * hard ai should be able to know the correct move given enough information
	 */
	@Test
	public void test_bottom_orientation_check() {
		// the right hit should be row = 4, column = 4
		BoardPiece[][] testBoard = new BoardPiece[][] { 
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
										
			
		Board testBoardObject = new Board(testBoard);
		Coordinates result = hardAI.getMove(testBoardObject);
		
		assertEquals("Bottom piece failed to hit", 4, result.getRow());
		assertEquals("Bottom piece failed to hit", 4, result.getCol());
	}
	
	/*
	 * hard ai should be able to know the correct move given enough information
	 */
	@Test
	public void test_top_orientation() {
		// the right hit should be row = 4, column = 2
		BoardPiece[][] testBoard = new BoardPiece[][] { 
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.HIT, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						}; 
										
			
		Board testBoardObject = new Board(testBoard);
		Coordinates result = hardAI.getMove(testBoardObject);
		
		
		assertEquals("Top piece failed to hit", 2, result.getRow());
		assertEquals("Top piece failed to hit", 4, result.getCol());

	}
	
	/*
	 * hard ai should be able to know the correct move given enough information
	 */
	@Test
	public void test_left_orientation_check() {
		// the right hit should be row = 3, column = 3
		BoardPiece[][] testBoard = new BoardPiece[][] { 
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						}; 
										
			
		Board testBoardObject = new Board(testBoard);
		Coordinates result = hardAI.getMove(testBoardObject);
		
		assertEquals("Left piece failed to hit", 3, result.getRow());
		assertEquals("Left piece failed to hit", 3, result.getCol());

	}
	
	/*
	 * hard ai should be able to know the correct move given enough information
	 */
	@Test
	public void test_right_orientation_check() {
		// the right hit should be row = 5, column = 3
		BoardPiece[][] testBoard = new BoardPiece[][] { 
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.HIT, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						{ BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY, BoardPiece.EMPTY},
						}; 
										
			
		Board testBoardObject = new Board(testBoard);
		Coordinates result = hardAI.getMove(testBoardObject);
		
		assertEquals("Right piece failed to hit", 3, result.getRow());
		assertEquals("Right piece failed to hit", 5, result.getCol());

	}

}
