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
public class Board implements Serializable {
	
	public enum BoardPiece {
		SHIP, EMPTY, HIT, MISS, DEAD;
	};
	
	// Instance Variables 
	private BoardPiece[][] board;
	private int row;
	private int column;
	
	/**
	 * initializes a new 9x9 empty board
	 */
	public Board() {
		this(9, 9);
	}
	
	/**
	 * initializes a new nxm empty board
	 */
	public Board(int row, int column) {
		this.row = row;
		this.column = column;
		this.board = new BoardPiece[this.row][this.column];
		
		for (int i=0; i<row; i++) {
			for (int j=0; j<column; j++) {
				this.board[i][j] = BoardPiece.EMPTY;
			}
		}
	}
	
	/**
	 * uses input as new board
	 * @param inputBoard - boardPiece 2d array
	 */
	public Board (BoardPiece[][] inputBoard) {
		this.row = inputBoard[0].length;
		this.column = inputBoard.length;
		this.board = new BoardPiece[this.row][this.column];
		
		for (int i=0; i<this.row; i++) {
			for (int j=0; j<this.column; j++) {
				this.board[i][j] = inputBoard[i][j];
			}
		}
	}
	
	public Board (Board inputBoard) {
		this(inputBoard.getBoardArray());
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
	
	public Board getBoard() {
		return new Board(this);
	}
	
	/**
	 * Create a new copy of the board instance variable
	 * 
	 * @return tempBoard; A new copy of board
	 */
	public BoardPiece[][] getBoardArray() {
		// Create a new 2d char array 
		BoardPiece[][] tempBoard = new BoardPiece[this.row][this.column];
		
		// Loop to copy contents of instance variable 
		for (int i = 0; i < this.row; i++) {
			for (int j = 0; j < this.column; j++) {
				tempBoard[i][j] = board[i][j];
			}
		}
		return tempBoard;
	}
	
	/**
	 * 
	 * @return row dimension of the board
	 */
	public int getRow() {
		return this.row;
	}
	
	/**
	 * 
	 * @return column dimension of the board
	 */
	public int getColumn() {
		return this.column;
	}
	
	
}