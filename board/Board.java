package board;

import java.util.Random;
import java.io.Serializable;

import coordinates.Coordinates;
import ships.Ship;


/**
 * Abstract Board containing all basic methods to manage a board
 * BoardPieces are fixed
 * @author Ben
 *
 */
public abstract class Board implements Serializable {
	
	public enum BoardPiece {
		SHIP, EMPTY, HIT, MISS, DEAD;
	}
	
	// Instance Variables 
	private BoardPiece[][] board;
	
	/**
	 * initializes a new 9x9 empty board
	 */
	public Board() {
		this(9);
	}
	
	/**
	 * initializes a new nxn empty board
	 */
	public Board(int dimension) {
		this.board = new BoardPiece[dimension][dimension];
		for (int i=0; i<dimension; i++) {
			for (int j=0; j<dimension; j++) {
				this.board[i][j] = BoardPiece.EMPTY;
			}
		}
	}
	
	/**
	 * uses input as new board
	 * @param inputBoard - boardPiece 2d array
	 */
	public Board (BoardPiece[][] inputBoard) {
		this.board = inputBoard.clone();
	}
	
	
	
	/**
	 * sets a piece on a specific coordinate
	 * overwrites previous piece
	 * @param newPiece
	 * @param coords - row and column on the board
	 * @throws Exception - from invalid coordinates
	 */
	public void setPieceAt(BoardPiece newPiece, Coordinates coords) throws Exception {
		int targetRow = coords.getRow();
		int targetColumn = coords.getCol();
		
		try {
			board[targetRow][targetColumn] = newPiece;
			
		} catch (Exception e) {
			throw new Exception("Error, coordinates out of range of board");
		}
	}
	
	/**
	 * return piece at a specific coordinate
	 * @param coords - row and column on the board
	 * @return
	 * @throws Exception - from invalid coordinates
	 */
	public BoardPiece getPieceAt(Coordinates coords) throws Exception {
		int targetRow = coords.getRow();
		int targetColumn = coords.getCol();
	
		try {
			return board[targetRow][targetColumn];
			
		} catch (Exception e) {
			throw new Exception("Error, coordinates out of range of board");
		}
	}
	
	/**
	 * Create a new copy of the board instance variable
	 * 
	 * @return tempBoard; A new copy of board
	 */
	public BoardPiece[][] getBoard() {
		// Create a new 2d char array 
		BoardPiece[][] tempBoard = new BoardPiece[9][9];
		
		// Loop to copy contents of instance variable 
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				tempBoard[row][col] = board[row][col];
			}
		}
		return tempBoard;
	}
	
}