package battleship.board;

import java.util.Random;
import java.io.Serializable;

import battleship.coordinates.Coordinates;
import battleship.ships.Ship;


/**
 * Class representing a battleship game board
 * 
 * Will create the game board, and modify it's contents when setting ships
 * and showing hits/misses
 * 
 * Added method to change dead ships to appropriate constant 
 * 
 * @author Karndeep Dhami and Faiyaz Momen
 * @version date: Apr. 11, 2017
 */
public class Board implements Serializable {
	// Constants
	public static final char SHIP = 'S';
	public static final char EMPTY = ' ';
	public static final char HIT = 'X';
	public static final char MISS = 'O';
	public static final char DEAD = 'D';

	// Instance Variables 
	private int shipsAlive;
	private char[][] board = new char [9][9];
	private String title;
	private Ship star;
	private Ship battle;
	private Ship assault;
	private Ship orbiter;

	/**
	 * Constructor that initialises the title of the board, the number of shipsAlive,
	 * an empty game board, creates objects of each ship  
	 * @param title; A String
	 */
	public Board(String title) {
		setTitle(title);
		this.shipsAlive = 4;
		this.board = new char[][] { 
				{ ' ', '1', '2', '3', '4', '5', '6', '7', '8' },					   
				{ '1', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }, 							   
				{ '2', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },							   
				{ '3', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }, 							   
				{ '4', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },							   
				{ '5', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }, 							   
				{ '6', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },							   
				{ '7', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' }, 							   
				{ '8', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' } 
				};
		this.star = new Ship("Star Ship", 5);
		this.battle = new Ship("Battle Cruiser", 4);
		this.assault = new Ship("Assault Carrier", 3);
		this.orbiter = new Ship("Orbiter", 2);
	}
	
	/**
	 * Modifying boards and update ship counts
	 * 
	 * @param coordinate; A Coordinates Object used to get row and column of target
	 * @throws Exception; if Coordinates Object is out of the range of the board
	 */
	public void modifyBoard(Coordinates coordinates) throws Exception {
		int targetRow = coordinates.getRow(), targetColumn = coordinates.getCol();

		// Hits
		if (board[targetRow][targetColumn] == SHIP) {
			board[targetRow][targetColumn] = HIT;
			
			// Update shipsAlive based on hits
			numShipsAlive(targetRow, targetColumn);
		} 
		// Misses
		else if (board[targetRow][targetColumn] == EMPTY) {
			board[targetRow][targetColumn] = MISS;
		} 
		else {
			throw new Exception("Error, coordinates out of range of board");
		}
	}
	
	/*
	 * Update shipsAlive count
	 * 
	 * @param row; An int representing a row value
	 * @param col; An int representing a column value
	 */
	private void numShipsAlive(int row, int col) {
		// Array to keep track of whether each ship is alive
		Ship[] shipStates = new Ship [] {star, orbiter, battle, assault};
		int count = 0;
		
		// Counts number of true booleans in shipStates array
		for (Ship element : shipStates) {
			element.hitShip(row, col);
			if (element.isAlive()) {
				count++;
			}
			else {
				setShipChar(element.getShipLength(), element.getLocation(), DEAD);
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
	private void setShipChar(int shipLength, Coordinates[] shipLocation, char character) {
		int shipRow, shipCol;
		
		// Extracts coordinates of the units of a ship
		for (int index = 0; index < shipLength; index++) {
			shipRow = shipLocation[index].getRow();
			shipCol = shipLocation[index].getCol();
			
			// Appends the units into the board
			board[shipRow][shipCol] = character;
		}
	}

	/**
	 * Create a deep copy of the board instance variable
	 * 
	 * @return tempBoard; A deep copy of board
	 */
	public char[][] getBoard() {
		// Create a new 2d char array 
		char[][] tempBoard = new char[9][9];
		
		// Loop to copy contents of instance variable 
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				tempBoard[row][col] = board[row][col];
			}
		}
		return tempBoard;
	}
	
	/*
	 * Randomly places ships onto the board
	 * 
	 * @param ship; The ship of type Ship to be placed onto the board
	 */
	private void autoPlaceShip(Ship ship) {
		Random generator = new Random();
		int direction, row, col;
		String shipDir;
		boolean inputValid = false;
		
		// Loop that automatically places ships
		while (!inputValid) {
			// Generate random values
			direction = generator.nextInt(4);
			row = generator.nextInt(8) + 1;
			col = generator.nextInt(8) + 1;
			if (direction == 0) {
				shipDir = "left";
			} else if (direction == 1) {
				shipDir = "right";
			} else if (direction == 2) {
				shipDir = "up";
			} else {
				shipDir = "down";
			}
			ship.setOrientation(shipDir);
			ship.placeShip(row, col);
			// Checks if the ship is in bounds and does not overlap with other ships
			inputValid = !ship.checkOutOfBounds() && !checkShipOverlap(ship);
		}
		// Places ships onto the user's board
		ship.setAlive(true);
		setShipChar(ship.getShipLength(), ship.getLocation(), SHIP);
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
		if (shipSelection.equals(star.getShipName())) {
			ship = star;
		} else if (shipSelection.equals(battle.getShipName())) {
			ship = battle;
		} else if (shipSelection.equals(assault.getShipName())) {
			ship = assault;
		} else if (shipSelection.equals(orbiter.getShipName())) {
			ship = orbiter;
		}
		
		// Determine whether to rotate the ship
		if (actionCommand.substring(0, 6).equals("rotate")) { // When String formula is <rotate>|<shipName>,<boardTitle>
			ship.rotateShip();
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
		if (ship.isAlive()) {
			setShipChar(ship.getShipLength(), ship.getLocation(), EMPTY);
			ship.setAlive(false);
		}
		// set ship's location
		ship.placeShip(row, col);
		
		// Check if ship's location is in bounds and does not overlap
		if (!ship.checkOutOfBounds()) {
            if (!checkShipOverlap(ship)) {
				// Places ships onto the user's board
				ship.setAlive(true);
				setShipChar(ship.getShipLength(), ship.getLocation(), SHIP);
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
		for (int index = 0; index < ship.getShipLength(); index++) {
			int row = ship.getLocation()[index].getRow();
			int col = ship.getLocation()[index].getCol();
			if (board[row][col] == SHIP) {
				overlap = true;
			}
		}
		
		// return the overlap boolean
		return overlap;
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
		return new Ship(star);
	}
	
	/**
	 * @return battle; A copy of object battle of type Ship
	 */
	public Ship getBattleCruiser() {
		return new Ship(battle);
	}
	
	/**
	 * @return assault; A copy of object assault of type Ship
	 */
	public Ship getAssaultCarrier() {
		return new Ship(assault);
	}
	
	/**
	 * @return orbiter; A copy of object orbiter of type Ship
	 */
	public Ship getOrbiter() {
		return new Ship(orbiter);
	}
}