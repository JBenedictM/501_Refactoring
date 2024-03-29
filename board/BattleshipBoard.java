package board;

import java.io.Serializable;
import java.util.Random;

import board.Board.BoardPiece;
import coordinates.Coordinates;
import ships.AssaultCarrier;
import ships.BattleCruiser;
import ships.Orbiter;
import ships.Ship;
import ships.StarShip;
//enum for ship orientations
import ships.Ship.Orientation;


/**
 * Class representing a battleship game board
 * 
 * Manages ships and ensures that they are correctly placed
 * 
 * Will create the game board, and modify it's contents when setting ships
 * and showing hits/misses
 * 
 * Added method to change dead ships to appropriate constant 
 * 
 * @author Karndeep Dhami and Faiyaz Momen
 * @version date: Apr. 11, 2017
 */
public class BattleshipBoard implements Serializable{
	private Board board;
	private String title;
	
	// number of ships alive
	private int shipsAlive;
	// all possible ships
	private Ship star;
	private Ship battle;
	private Ship assault;
	private Ship orbiter;

	
	/**
	 * Constructor that initialises the title of the board, the number of shipsAlive,
	 * and an empty 9x9 board 
	 * @param title; A String
	 * @param inputBoard; board pointer
	 */
	public BattleshipBoard(String title) {
		this.board = new Board(9, 9);
		setTitle(title);
		
		this.shipsAlive = 4;
		this.star = new StarShip();
		this.battle = new BattleCruiser();
		this.assault = new AssaultCarrier();
		this.orbiter = new Orbiter();
	}
	
	public BattleshipBoard(BattleshipBoard boardClone) {
		this.board = new Board(boardClone.getBoardArray());
		setTitle(boardClone.getTitle());
		
		this.shipsAlive = boardClone.getShipsAlive();
		this.star = boardClone.getStarShip();
		this.battle = boardClone.getBattleCruiser();
		this.assault = boardClone.getAssaultCarrier();
		this.orbiter = boardClone.getOrbiter();
	}
	
	/**
	 * targets a coordinate 
	 * updates board correctly based on the piece at the coordinate
	 * coordinate can either land on a ship or miss
	 * 
	 * @param targetCoord; coordinate that will be changed
	 * @throws Exception; if Coordinates Object is out of the range of the board
	 */
	public void targetCoordinate(Coordinates targetCoord) throws Exception {
		//int targetRow = coordinates.getRow(), targetColumn = coordinates.getCol();

		// Hits
		if (board.getPieceAt(targetCoord) == BoardPiece.SHIP) {
			board.setPieceAt(BoardPiece.HIT, targetCoord);
			
			// Update shipsAlive based on hits
			numShipsAlive(targetCoord);
		} 
		// Misses
		else if (board.getPieceAt(targetCoord) == BoardPiece.EMPTY) {
			board.setPieceAt(BoardPiece.MISS, targetCoord);
		} 
		else {
			throw new Exception("Error, coordinates out of range of board");
		}
	}
	
	
	/*
	 * Update shipsAlive count
	 *
	 * @param targetCoord; target coordinate
	 * 
	 */
	private void numShipsAlive(Coordinates targetCoord) {
		// Array to keep track of whether each ship is alive
		Ship[] shipStates = new Ship [] {star, orbiter, battle, assault};
		int count = 0;
		
		// Counts number of true booleans in shipStates array
		
		for (Ship element : shipStates) {
			element.hitShip(targetCoord);
			if (element.getUnitsAlive() > 0) {
				count++;
			}
			else {
				setShipChar(element.getSize(), element.getLocation(), BoardPiece.DEAD);
			}
		}

		// Pass count to setShipsAlive
		setShipsAlive(count);
	}
	
	
	/*
	 * Sets the ship on the board with the given character
	 * 
	 * @param shipLength; An int representing the length of a ship
	 * @param shipLocation; A Coordinates array of a ships location
	 */
	private void setShipChar(int shipLength, Coordinates[] shipLocation, BoardPiece new_piece) {
		
		// Extracts coordinates of the units of a ship
		for (int index = 0; index < shipLength; index++) {
			
			try {
				// Appends the units into the board
				board.setPieceAt(new_piece, shipLocation[index]);
			} catch (Exception e) {
				System.out.println("Incorrect ship coordinates, this exception should never happen");
			}
			
		}
	}
	
	/*
	 * Randomly places ships onto the board
	 * 
	 * @param ship; The ship of type Ship to be placed onto the board
	 */
	private void autoPlaceShip(Ship ship) {
		Random generator = new Random();
		int direction, row, col;
		Orientation shipDir;
		boolean inputValid = false;
		
		// Loop that automatically places ships
		while (!inputValid) {
			// Generate random values
			direction = generator.nextInt(4);
			row = generator.nextInt(8) + 1;
			col = generator.nextInt(8) + 1;
			if (direction == 0) {
				shipDir = Orientation.LEFT;
			} else if (direction == 1) {
				shipDir = Orientation.RIGHT;
			} else if (direction == 2) {
				shipDir = Orientation.UP;
			} else {
				shipDir = Orientation.DOWN;
			}
			
			ship.setOrientation(shipDir);
			Coordinates ship_coordinates = new Coordinates(row, col);
			ship.placeShip(ship_coordinates);
			// Checks if the ship is in bounds and does not overlap with other ships
			inputValid = !ship.checkOutOfBounds() && !checkShipOverlap(ship);
		}
		// Places ships onto the user's board
		
		// TODO : see if setAlive() is relevant
//		ship.setAlive(true);
		setShipChar(ship.getSize(), ship.getLocation(), BoardPiece.SHIP);
	}
	
	/**
	 * Automatic placement of ships
	 */
	public void autoShipSetup() {
		autoPlaceShip(star);
		autoPlaceShip(battle);
		autoPlaceShip(assault);
		autoPlaceShip(orbiter);
	}
	
	/**
	 * The method that handle ship placement
	 * 
	 * @param actionCommand; A String containing logic information
	 * @param shipSelection; A String name of the ship to determine the ship object used
	 * @throws Exception; When setValidShip returns exception when ship is out of bounds 
	 * or overlapping with another
	 */
	public void placePlayerShip(String actionCommand, String shipSelection) throws Exception {
		int row = 0, col = 0;
		Ship ship = null;
		
		// If the button pressed was on the board
		if (actionCommand.substring(3,4).equals("|")) { // When String formula is <row>,<col>|<shipName>,<boardTitle>
			row = Integer.parseInt(actionCommand.substring(0, 1));
			col = Integer.parseInt(actionCommand.substring(2,3));
		}
		
		// Determine the ship object
		if (shipSelection.equals(star.getName())) {
			ship = star;
		} else if (shipSelection.equals(battle.getName())) {
			ship = battle;
		} else if (shipSelection.equals(assault.getName())) {
			ship = assault;
		} else if (shipSelection.equals(orbiter.getName())) {
			ship = orbiter;
		}
		
		// Determine whether to rotate the ship
		if (actionCommand.substring(0, 6).equals("rotate")) { // When String formula is <rotate>|<shipName>,<boardTitle>
			ship.rotateShipClockwise90();
			row = ship.getLocation()[0].getRow();
			col = ship.getLocation()[0].getCol();
		}
		
		setValidShip(ship, row, col);
	}
	
	/*
	 * Sets a ship onto the board
	 * 
	 * @param ship; The Ships object to be placed
	 * @param row; The integer of the row where the ship is to be placed
	 * @param col; The integer of the column where the ship is to be placed
	 * @throws Exception; when ship is out of bounds or overlapping with another
	 */
	private void setValidShip(Ship ship, int row, int col) throws Exception {
		// remove the ship if already placed
		if (ship.isShipOnBoard()) {
			setShipChar(ship.getSize(), ship.getLocation(), BoardPiece.EMPTY);
			ship.removeShipFromBoard();
		}
		// set ship's location
		Coordinates ship_coordinates = new Coordinates(row, col);
		ship.placeShip(ship_coordinates);
		
		// Check if ship's location is in bounds and does not overlap
		if (!ship.checkOutOfBounds()) {
            if (!checkShipOverlap(ship)) {
				// Places ships onto the user's board
				ship.setShipOnBoard();
				setShipChar(ship.getSize(), ship.getLocation(), BoardPiece.SHIP);
			} else { // If the ship overlaps with another
				throw new Exception("You have overlapped with another Ship!");
			}
		} else { // If the ship is out of bounds
			throw new Exception("You have placed your ship out of bounds!");
		}
	}
	
	/*
	 * Checks if this ship overlaps with others
	 * @param ship; Takes a ship object of type Ship
	 * @return overlap; Returns boolean 'true' if it overlaps and vice versa
	 */
	private boolean checkShipOverlap(Ship ship) {	
		boolean overlap = false;
		
		// Check if another ship already occupies one of the ship's locations
		for (int index = 0; index < ship.getSize(); index++) {
			try {
				if (board.getPieceAt(ship.getLocation()[index]) == BoardPiece.SHIP) {
					overlap = true;
				}
			} catch (Exception e) {
				System.out.println("Incorrect ship coordinates, this exception should never happen");

			}
			
		}
		
		// return the overlap boolean
		return overlap;
	}
	
	public BoardPiece getBoardPieceAt(Coordinates coord) throws Exception {
		return board.getPieceAt(coord);
	}
	
	public void setBoardPieceAt(BoardPiece new_piece, Coordinates coord) throws Exception {
		board.setPieceAt(new_piece, coord);
	}
	
	public Board getBoard() {
		return board.getBoard();
	}
	
	/**
	 * Create a new copy of the board instance variable
	 * 
	 * @return - copy of the underlying board array
	 */
	public BoardPiece[][] getBoardArray() {
		return this.board.getBoardArray();
	}
	
	/*
	 * @param count; An int to set the number of shipsAlive
	 */
	private void setShipsAlive(int count) {
		if (count >= 0 && count <= 4) {
			shipsAlive = count;
		}
	}
	
	/**
	 * @return shipsAlive; An int representing the number of shipsAlive
	 */
	public int getShipsAlive() {
		return shipsAlive;
	}
	
	/**
	 * @return star; A copy of object star of type Ship
	 */
	public Ship getStarShip() {
		return new StarShip((StarShip) star);
	}
	
	/**
	 * @return battle; A copy of object battle of type Ship
	 */
	public Ship getBattleCruiser() {
		return new BattleCruiser((BattleCruiser) battle);
	}
	
	/**
	 * @return assault; A copy of object assault of type Ship
	 */
	public Ship getAssaultCarrier() {
		return new AssaultCarrier((AssaultCarrier) assault);
	}
	
	/**
	 * @return orbiter; A copy of object orbiter of type Ship
	 */
	public Ship getOrbiter() {
		return new Orbiter((Orbiter) orbiter);
	}
	
	/**
	 * @return title; A String for the title of a board
	 */
	public String getTitle() {
		return title;
	}
	
	/*
	 * @param title; A String to set a title to a board
	 */
	private void setTitle(String title) {
		this.title = title;
	}
}
