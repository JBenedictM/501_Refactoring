package battleship.game;

import battleship.gui.GUI;
import battleship.coordinates.Coordinates;
import battleship.player.Player;
import battleship.player.User;
import battleship.player.Computer;
import battleship.board.Board;
import battleship.statistics.Statistics;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This is a battleship game where a user competes against a computer or another player.
 * In player vs. AI mode, the user and computer take turns inputting and randomly generating coordinates,
 * respectively, to target each other's ships until one of them have destroyed all of their opponent's ships.
 * In player vs. player mode, the two players take turns inputting coordinates
 * to target each other's ships until one of them have destroyed all of their opponent's ships.
 * @author Group 37
 * Date of last change: Apr. 11, 2017
 */
public class Game implements ActionListener, Serializable {
	private Player player1;
	private Player player2;
	private Board player1Board;
	private Board player2Board;
	private Statistics stats;
	private GUI gui;
	
	public static final String GAME_SAVE = "battleship/game/gameSave.ser";
	
	/**
	 * Constructor initializes players and boards to null and stats and gui to new objects
	 */
	public Game() {
		player1 = null;
		player2 = null;
		player1Board = null;
		player2Board = null;
		
		try {
			stats = new Statistics();
		} catch (FileNotFoundException openingFileException) {
			GUI.exceptionMessage("Problem opening " + Statistics.FILE_NAME + "\nFile not found");
		} catch (EOFException endOfFileException) {
			GUI.exceptionMessage("Problem reading from file " + Statistics.FILE_NAME + ".\nEnd of file");
		} catch (IOException writingFileException) {
			GUI.exceptionMessage("Problem with file " + Statistics.FILE_NAME);
		}
		
		gui = new GUI();
	}
	
	/**
	 * Starts the game.
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	/**
	 * Starts the game.
	 */
	public void start() {
		gui.titleScreen(this);
		gui.setVisible(true);
	}
	
	// Player vs. player mode
	private void playerVsPlayer() {
		player1 = new User();
		player2 = new User();
		player1Board = new Board("Player 1");
		player2Board = new Board("Player 2");
		
		gui.shipSetupScreen(this, player1Board, player1Board.getStarShip().getShipName());
	}
	
	// Finish a game of player vs. player
	private void endPlayerVsPlayer() {
		gui.playerVsPlayerVictory(player2Board.getShipsAlive(), (User)player1,(User)player2);
		
		// Update statistics in file
		try {
			if (player2Board.getShipsAlive() == 0) {
				stats.updateStatFile((User)player1);
			} else {
				stats.updateStatFile((User)player2);
			}
		} catch (FileNotFoundException openingFileException) {
			GUI.exceptionMessage("Problem opening " + Statistics.FILE_NAME + "\nFile not found");
		} catch (IOException writingFileException) {
			GUI.exceptionMessage("Problem with file " + Statistics.FILE_NAME);
		}
		gui.updatingStatsMessage();
		
		changeScreens("Start");
	}
	
	// Player vs. computer mode
	// Parameter is a string representing the difficulty of the AI ("E"/"M"/"H")
	private void playerVsAI(String difficulty) {		
		player1 = new User();
		player2 = new Computer(difficulty);
		player1Board = new Board("Player");
		player2Board = new Board("Computer");
		
		gui.shipSetupScreen(this, player1Board, player1Board.getStarShip().getShipName());
	}
	
	// Finish a game of player vs. computer
	private void endPlayerVsAI() {
		gui.playerVsAIVictory(player2Board.getShipsAlive(), (User)player1);
		
		// Update statistics in file
		try {
			stats.updateStatFile((User)player1);
		} catch (FileNotFoundException openingFileException) {
			GUI.exceptionMessage("Problem opening " + Statistics.FILE_NAME + "\nFile not found");
		} catch (IOException writingFileException) {
			GUI.exceptionMessage("Problem with file " + Statistics.FILE_NAME);
		}
		gui.updatingStatsMessage();
		
		changeScreens("Start");
	}
	
	// Shows the results of the move made by player 1
	// Parameter is the name of player 1
	private void showPlayer1Move(String name) {
		// Get move from player 1
		Coordinates move = player1.getMove(player2Board.getBoard());
		gui.displayPlayer1Result(move, player2Board.getBoard(), name);
		
		// Modify the board
		try {
			player2Board.modifyBoard(move);
		} catch (Exception exception) {
			GUI.exceptionMessage(exception.getMessage());
		}
		gui.updatePlayer1Screen(player1Board, player2Board, this);
	}
	
	// Shows the results of the move made by player 2
	// Parameter is the name of player 2
	private void showPlayer2Move(String name) {
		// Get move from player 2
		Coordinates move = player2.getMove(player1Board.getBoard());
		gui.displayPlayer2Result(move, player1Board.getBoard(), name);
		
		// Modify the board
		try {
			player1Board.modifyBoard(move);
		} catch (Exception exception) {
			GUI.exceptionMessage(exception.getMessage());
		}
	}
	
	/**
	 * Do something based on the timer event or the button the user clicked
	 * @param event; a timer event or an event of a button being clicked
	 */
	public void actionPerformed(ActionEvent event) {
		String actionCommand = event.getActionCommand();
		
		// Player clicked a button in the board setup screen
		if (actionCommand.contains("|")) {
			shipSetupProcess(actionCommand);
		} else if (actionCommand.substring(1,2).equals(",")) { // Player targeted a location on the board
			processPlayerMove(actionCommand);
		} else if (actionCommand.equals("End Player 1 Turn")) {
			// Switch to player 2 turn transition screen
			gui.enterTransitionScreen("Player 2");
		} else if (actionCommand.equals("End Player 2 Turn")) {
			// Switch to player 1 turn transition screen
			gui.enterTransitionScreen("Player 1");
		} else if (actionCommand.equals("Continue to Player 1's Turn")) {
			// Switch to player 1 turn
			gui.exitTransitionScreen("Player 1");
			gui.updatePlayer1Screen(player1Board, player2Board, this);
			gui.promptForMove("Player 1");
			((User)player1).resetMove();
		} else if (actionCommand.equals("Continue to Player 2's Turn")) {
			// Switch to player 2 turn
			gui.exitTransitionScreen("Player 2");
			gui.updatePlayer2Screen(player1Board, player2Board, this);
			gui.promptForMove("Player 2");
			((User)player2).resetMove();
		} else if (actionCommand.equals("Save Game")) {
			saveGame();
		} else if (actionCommand.equals("timer")) { // Time for computer to move
			showPlayer2Move("Computer");
			gui.updatePlayer1Screen(player1Board, player2Board, this);
			if (player1Board.getShipsAlive() != 0) {
				// Back to player's turn
				gui.promptForMove("Player");
				((User)player1).resetMove();
			} else { // Game is over
				endPlayerVsAI();
			}
		} else { // Button clicked was in the menu system or setup
			changeScreens(actionCommand);
		}
	}
	
	// Changes screens depending on which button was clicked
	// Parameter is the action command of the button
	private void changeScreens(String actionCommand) {
		// Use the action command of the button that was clicked to determine which screen to change to
		gui.getContentPane().removeAll();
		if (actionCommand.equals("Start") || actionCommand.equals("Back")) {
			gui.mainMenu(this);
		} else if (actionCommand.equals("Play")) {
			gui.modeSelection(this);
		} else if (actionCommand.equals("Statistics")) {
			gui.displayStats(this,stats);
		} else if (actionCommand.equals("Player vs. Player")) {
			playerVsPlayer();
		} else if (actionCommand.equals("Player vs. Computer")) {
			gui.difficultySetting(this);
		} else if (actionCommand.equals("Easy") || actionCommand.equals("Medium") || actionCommand.equals("Hard")) {
			playerVsAI(actionCommand.substring(0, 1)); // Get first letter
		} else if (actionCommand.equals("Player 1")) { // Proceed to player 2 setup
			gui.shipSetupScreen(this, player2Board, player2Board.getStarShip().getShipName());
		} else if (actionCommand.equals("Player")) { // Setup computer board and proceed to game
			player2Board.autoShipSetup();
			gui.playerVsAISetup(this);
			gui.updatePlayer1Screen(player1Board, player2Board, this);
		} else if (actionCommand.equals("Player 2")) { // Proceed to game
			gui.playerVsPlayerSetup(this);
		} else if (actionCommand.equals("Load Game")) {
			loadGame();
		} else {
			GUI.exceptionMessage("Error 404 location not found");
			gui.titleScreen(this);
		}
		gui.repaint();
		gui.setVisible(true);
	}
	
	// Gets the location targeted by the user based on which button was clicked.
	// Parameter is the action command of the button being clicked
	private void processPlayerMove(String actionCommand) {
		// Format of the string is <row>,<col> <targetBoardTitle>
		String moveAsString = actionCommand.substring(0,3);
		String boardTitle = actionCommand.substring(4);
		
		if (boardTitle.equals("Computer") && player1.getMove(player2Board.getBoard()) == null) {
			// Get move from player 1 and modify the board
			((User)player1).updateMoveAndStats(moveAsString, player2Board.getBoard());
			showPlayer1Move("Player");
			if (player2Board.getShipsAlive() != 0) {
				// Continue to computer's turn
				gui.aiThink(this);
			} else { // Game is over
				endPlayerVsAI();
			}
		} else if (boardTitle.equals("Player 1") && player2.getMove(player1Board.getBoard()) == null) {
			// Get move from player 2 and modify the board
			((User)player2).updateMoveAndStats(moveAsString, player1Board.getBoard());
			showPlayer2Move("Player 2");
			gui.updatePlayer2Screen(player1Board, player2Board, this);
			if (player1Board.getShipsAlive() != 0) {
				// Enable continue button
				gui.enableContinueButton();
			} else { // Game is over
				endPlayerVsPlayer();
			}
		} else if (boardTitle.equals("Player 2") && player1.getMove(player2Board.getBoard()) == null) {
			// Get move from player 1 and modify the board
			((User)player1).updateMoveAndStats(moveAsString, player2Board.getBoard());
			showPlayer1Move("Player 1");
			if (player2Board.getShipsAlive() != 0) {
				// Enable continue button
				gui.enableContinueButton();
			} else { // Game is over
				endPlayerVsPlayer();
			}
		}
	}
	
	// Get the information for placing a ship and updates the Setup Screen
	// Parameter is the a action command for the buttons regarding the setup
	private void shipSetupProcess(String actionCommand) {
		String setupInfo = actionCommand.substring(actionCommand.indexOf("|")+1);
		String shipSelection = setupInfo.substring(0,setupInfo.indexOf(","));
		String boardName = setupInfo.substring(setupInfo.indexOf(",")+1);
		
		// Determine the board object to use
		Board currentBoard = player1Board;		
		if (boardName.equals(player2Board.getTitle())) {
			currentBoard = player2Board;
		}
		
		// the action the player wants to take
		if (actionCommand.substring(3,4).equals("|") || actionCommand.substring(0, 6).equals("rotate")) {
			try {
				currentBoard.placePlayerShip(actionCommand, shipSelection);
			} catch (Exception exception) {
				GUI.exceptionMessage(exception.getMessage());
			}
		}
		
		// Update screen
		gui.shipSetupScreen(this, currentBoard, shipSelection);
	}
	
	// Saves the game to a file
	private void saveGame() {
		// Create file output stream
		FileOutputStream fos = null;
		
		try {
			fos = new FileOutputStream(GAME_SAVE);
			
			// Byte stream creation
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			
			// Write the stream
			oos.flush();
			oos.close();
		} catch (IOException writingFileException) {
			GUI.exceptionMessage("Problem with file " + GAME_SAVE);
		}
	}

	// Loads the game from a file
	private void loadGame() {
		Game loadedGame;
		
		// Create file input stream
		FileInputStream fis = null;
		
		if (new File(GAME_SAVE).isFile()) {
			try {
				fis = new FileInputStream(GAME_SAVE);
				ObjectInputStream ois = new ObjectInputStream(fis);
				
				// Create object from stream, cast it to type Game
				loadedGame = (Game) ois.readObject();
				
				ois.close();
				fis.close();
				
				// Copy data from loaded game to this game, close old window
				gui.setVisible(false);
				player1 = loadedGame.player1;
				player2 = loadedGame.player2;
				player1Board = loadedGame.player1Board;
				player2Board = loadedGame.player2Board;
				stats = loadedGame.stats;
				gui = loadedGame.gui;
			} catch (ClassNotFoundException classNotFoundException) {
				GUI.exceptionMessage("Problem loading saved game " + GAME_SAVE);
				gui.modeSelection(this);
			} catch (EOFException eOFException) {
				GUI.exceptionMessage("Problem reading from file " + GAME_SAVE + ".\nEnd of file");
				gui.modeSelection(this);
			} catch (IOException readingFileException) {
				GUI.exceptionMessage("Problem with file " + GAME_SAVE);
				gui.modeSelection(this);
			}
		} else {
			GUI.exceptionMessage("No save file.");
			gui.modeSelection(this);
		}		
	}
}
