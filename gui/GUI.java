package gui;

import game.Game;
import coordinates.Coordinates;
import player.User;
import board.Board;
import statistics.Statistics;
import ships.Ship;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.Timer;
import java.io.File;
import java.io.Serializable;

/**
 * Graphical User Interface for a Battleship game.
 * @author Group 37
 * Date of last change: Apr. 11, 2017
 */
public class GUI extends JFrame implements Serializable {
	// Constants
	public static final char SHIP = 'S';
	public static final char EMPTY = ' ';
	public static final char HIT = 'X';
	public static final char MISS = 'O';
	public static final char DEAD = 'D';
	
	private static final Dimension WINDOW_SIZE = new Dimension(960, 540);
	private static final Dimension BOARD_BUTTON_SIZE = new Dimension(40, 40);
	
	private Container contentPane;
	private JPanel leftBoardPanel;
	private JPanel rightBoardPanel;
	private JLabel shipBox1;
	private JLabel shipBox2;
	private JLabel messageBox1;
	private JLabel messageBox2;
	private JButton continueButton;
	private JButton saveButton;
	
	/**
	 * Constructor initializes labels, panels and buttons to null and creates the window
	 */
	public GUI() {		
		// Initialize window
		setSize(WINDOW_SIZE);
		setMinimumSize(WINDOW_SIZE);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Battleship");
		
		// Initialize content pane
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new GridBagLayout());
		
		leftBoardPanel = null;
		rightBoardPanel = null;
		shipBox1 = null;
		shipBox2 = null;
		messageBox1 = null;
		messageBox2 = null;
		continueButton = null;
		saveButton = null;
	}
	
	/**
	 * Shows the title screen
	 * @param game; listener for buttons
	 */
	public void titleScreen(Game game) {
		// Add label for name of screen
		JLabel titleLabel = new JLabel("BATTLESHIP");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(titleLabel, constraints);
		
		// Add button to start game
		JButton startButton = new JButton("Start");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(250, 0, 150, 0);
		contentPane.add(startButton, constraints);
		startButton.addActionListener(game);
	}
	
	/**
	 * Shows the main menu
	 * @param game; listener for buttons
	 */
	public void mainMenu(Game game) {	
		// Add label for name of screen
		JLabel mainMenuLabel = new JLabel("MAIN MENU");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(mainMenuLabel, constraints);
		
		// Add button to play a game
		JButton playButton = new JButton("Play");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(250, 0, 0, 0);
		contentPane.add(playButton, constraints);
		playButton.addActionListener(game);
		
		// Add button to view statistics
		JButton statsButton = new JButton("Statistics");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(25, 0, 100, 0);
		contentPane.add(statsButton, constraints);
		statsButton.addActionListener(game);
	}
	
	/**
	 * Shows the screen for selecting player vs. player or player vs. computer
	 * @param game; listener for buttons
	 */
	public void modeSelection(Game game) {
		// Add label for name of screen
		JLabel modeSelectionLabel = new JLabel("MODE SELECTION");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(modeSelectionLabel, constraints);
		
		// Add button for player vs. player mode
		JButton onePlayerButton = new JButton("Player vs. Player");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(250, 0, 0, 0);
		contentPane.add(onePlayerButton, constraints);
		onePlayerButton.addActionListener(game);
		
		// Add button for player vs. computer mode
		JButton twoPlayerButton = new JButton("Player vs. Computer");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(25, 0, 0, 0);
		contentPane.add(twoPlayerButton, constraints);
		twoPlayerButton.addActionListener(game);
		
		// Add load button 
		JButton loadButton = new JButton("Load Game");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(25, 0, 0, 0);
		contentPane.add(loadButton, constraints);
		loadButton.addActionListener(game);
		
		// Add button to go back to main menu
		displayBackButton(game, 25);
	}
	
	/**
	 * Display Statistics GUI using JLabels 
	 * @param game; A listener for back button 
	 * @param stats; A Statistics object used to get instance variables to display
	 */
	public void displayStats(Game game, Statistics stats) {
		// Add statistics menu title label
		JLabel title = new JLabel("STATISTICS");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.CENTER;
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(title, constraints);
		
		// Add Hits statistics label
		JLabel hitsLabel = new JLabel("Hits: " + stats.getHits());
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.insets = new Insets(150,0,0,0);
		contentPane.add(hitsLabel, constraints);
		
		// Add Misses statistics label
		JLabel missesLabel = new JLabel("Misses: " + stats.getMisses());
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.insets = new Insets(25, 0, 0, 0);
		contentPane.add(missesLabel, constraints);

		// Add Hit Rate statistics label
		JLabel hitRateLabel = new JLabel(String.format("Hit Rate: %.2f", stats.getHitRate()));
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.LINE_START;
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.insets = new Insets(25, 0, 0, 0);
		contentPane.add(hitRateLabel, constraints);
		
		// Add back button to statistics menu 
		displayBackButton(game, 50);
	}
	
	// Back Button
	private void displayBackButton(Game game, int insets) {
		JButton backButton = new JButton("Back");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(insets, 0, 0, 0);
		contentPane.add(backButton, constraints);
		backButton.addActionListener(game);
	}
	
	/**
	 * Shows the screen that gets input from the user to determine the difficulty of the AI
	 * @param game; listener for buttons
	 */
	public void difficultySetting(Game game) {
		// Add label for name of screen
		JLabel difficultyLabel = new JLabel("AI DIFFICULTY SETTING");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		contentPane.add(difficultyLabel, constraints);

		// Add button for easy difficulty
		JButton easyButton = new JButton("Easy");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(250, 0, 0, 0);
		contentPane.add(easyButton, constraints);
		easyButton.addActionListener(game);
		
		// Add button for medium difficulty
		JButton mediumButton = new JButton("Medium");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(25, 0, 0, 0);
		contentPane.add(mediumButton, constraints);
		mediumButton.addActionListener(game);
		
		// Add button for hard difficulty
		JButton hardButton = new JButton("Hard");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.insets = new Insets(25, 0, 50, 0);
		contentPane.add(hardButton, constraints);
		hardButton.addActionListener(game);
	}
	
	// Shows the game screen
	// Parameter is the listener for buttons
	private void gameScreen(Game game) {
		createBoards();
		createShipCountBoxes();
		
		// Add the first message box to the content pane
		messageBox1 = new JLabel(" ");
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 3;
		constraints.gridwidth = 2;
		contentPane.add(messageBox1, constraints);
		
		// Add the second message box to the content pane
		messageBox2 = new JLabel(" ");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.gridwidth = 2;
		contentPane.add(messageBox2, constraints);
		
		// Add the button to transition between turns to the content pane
		continueButton = new JButton();
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 5;
		constraints.gridwidth = 2;
		contentPane.add(continueButton, constraints);
		continueButton.addActionListener(game);
		
		// Add Save button 
		saveButton = new JButton("Save Game");
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 6;
		constraints.gridwidth = 2;
		contentPane.add(saveButton, constraints);
		saveButton.addActionListener(game);
	}
	
	// Create board displays
	private void createBoards() {
		// Create a panel where the left board will be displayed
		leftBoardPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 0, 25);
		contentPane.add(leftBoardPanel, constraints);
		
		// Create a panel where the right board will be displayed
		rightBoardPanel = new JPanel();
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 25, 0, 0);
		contentPane.add(rightBoardPanel, constraints);
	}
	
	// Create ship count displays
	private void createShipCountBoxes() {
		// Create a label where player 1 ship counts will be displayed
		shipBox1 = new JLabel();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 1;
		constraints.gridwidth = 2;
		contentPane.add(shipBox1, constraints);
		
		// Create a label where player 2 ship counts will be displayed
		shipBox2 = new JLabel();
		constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 2;
		constraints.gridwidth = 2;
		contentPane.add(shipBox2, constraints);
	}
	
	/*
	 * Display objects own board
	 * 
	 * @param aPanel; A JPanel to which an objects board of buttons will be created
	 * @param boardObject; A Board object used to get the character array to determine what is displayed 
	 * and String title used to label the JPanel
	 * @param use; A String to decide whether buttons will be enabled or disabled
	 * @param game; A Game object used to add as an action listener
	 */
	private void displayBoard(JPanel aPanel, Board boardObject, String use, Game game) {
		// Create panel
		aPanel = createBoardPanel(aPanel, boardObject.getTitle());
		aPanel.removeAll();
		// Loop to create grid of JButtons and JLabels
		for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
            	if (row == 0 || col == 0) { // Row and column labels
            		JLabel rowColOneLabel = new JLabel("" + boardObject.getBoard()[row][col], JLabel.CENTER);
            		aPanel.add(rowColOneLabel);
            	} else {
            		JButton boardButton = new JButton();
  
            		// Create border for buttons
            		boardButton.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLUE));
            		boardButton.setMinimumSize(BOARD_BUTTON_SIZE);
            		boardButton.setPreferredSize(BOARD_BUTTON_SIZE);
            		
            		// Set background based on hits/misses by opponent
            		setButtonBackground(boardButton, boardObject, row, col, use);
					
					if (!use.equals("Ship")) { // Enable buttons for setup or for player to target
						boardButton.addActionListener(game);
                		if (use.equals("Target")) {
							boardButton.setActionCommand(row + "," + col + " " + boardObject.getTitle());
						} else {
							boardButton.setActionCommand(row + "," + col + "|" + use + "," + boardObject.getTitle());
						}
            		} else { // Disable buttons for match 
                		boardButton.setEnabled(false);
            		}
					
            		aPanel.add(boardButton);
            	}
            } 
        }
	}
	
	/*
	 * Creates JPanel with border
	 * 
	 * @param aPanel; A JPanel 
	 * @param aStringTitle; A String used to title the JPanel
	 * @return aPanel; A JPanel with a border and a title
	 */
	private JPanel createBoardPanel(JPanel aPanel, String aStringTitle) {
		aPanel.setLayout(new GridLayout(9, 9, 0, 0));
		
		// Add border with title corresponding to title
		Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
        Border blackLine = BorderFactory.createLineBorder(Color.black);
        CompoundBorder line = new CompoundBorder(empty, blackLine);
        Border buttonBorder = BorderFactory.createTitledBorder(line, aStringTitle);
        aPanel.setBorder(buttonBorder);
        
        return aPanel;
	}
	
	/*
	 * Sets the background of buttons
	 * 
	 * @param boardButton; A JButton whose background will be changed
	 * @param boardObject; A Board object to used to get the board to retrieve a character
	 * @param row; An int representing the target row
	 * @param col; An int representing the target column
	 * @param use; A String to determine if ship is displayed
	 */
	private void setButtonBackground(JButton boardButton, Board boardObject, int row, int col, String use) {
		char[][] board = boardObject.getBoard();
		// Hits
		if (board[row][col] == HIT) {
			boardButton.setBackground(Color.RED);
			boardButton.setOpaque(true);
			boardButton.setEnabled(false);
		} 
		// Show players own ships
		else if (board[row][col] == SHIP && !use.equals("Target")) {
			setAliveShipIcons(boardButton, boardObject, row, col);
		}
		// Dead
		else if (board[row][col] == DEAD) {
			setDeadShipIcons(boardButton, boardObject, row, col);
		}
		// Misses
		else if (board[row][col] == MISS) {
			boardButton.setBackground(Color.BLUE);
			boardButton.setOpaque(true);
			boardButton.setEnabled(false);
		}
		// Error message
		else if (board[row][col] != EMPTY && board[row][col] != SHIP) {
			exceptionMessage("Error changing button colour");
		}
	}
	
	/*
	 * Sets Icons of buttons for ships that are alive
	 * 
	 * @param boardButton; The JButton that the icon will be set onto
	 * @param boardObject; The Board object used to get the Ships objects
	 * @param row; The row that the button is assigned to
	 * @param col; The column that the button is assigned to
	 */
	private void setAliveShipIcons(JButton boardButton, Board boardObject, int row, int col) {
		// Determine the ship in this location
		Ship aShip = boardObject.getStarShip();
		if (boardObject.getBattleCruiser().indexOfLocation(row, col) != -1 && boardObject.getBattleCruiser().isAlive()) {
			aShip = boardObject.getBattleCruiser();
		} else if (boardObject.getAssaultCarrier().indexOfLocation(row, col) != -1 && boardObject.getAssaultCarrier().isAlive()) {
			aShip = boardObject.getAssaultCarrier();
		} else if (boardObject.getOrbiter().indexOfLocation(row, col) != -1 && boardObject.getOrbiter().isAlive()) {
			aShip = boardObject.getOrbiter();
		}
		
		setShipIcons(aShip, boardButton, row, col);
	}
	
	/*
	 * Sets Icons of buttons for ships that are dead
	 * 
	 * @param boardButton; The JButton that the icon will be set onto
	 * @param boardObject; The Board object used to get the Ships objects
	 * @param row; The row that the button is assigned to
	 * @param col; The column that the button is assigned to
	 */
	private void setDeadShipIcons(JButton boardButton, Board boardObject, int row, int col) {
		// Determine the ship in this location
		Ship aShip = boardObject.getStarShip();
		if (boardObject.getBattleCruiser().indexOfLocation(row, col) != -1) {
			aShip = boardObject.getBattleCruiser();
		} else if (boardObject.getAssaultCarrier().indexOfLocation(row, col) != -1) {
			aShip = boardObject.getAssaultCarrier();
		} else if (boardObject.getOrbiter().indexOfLocation(row, col) != -1) {
			aShip = boardObject.getOrbiter();
		}
		
		setShipIcons(aShip, boardButton, row, col);
	}
	
	/*
	 * Sets Icons of buttons for ships
	 * 
	 * @param aShip; The Ship that the icon will be an image of
	 * @param boardButton; The JButton that the icon will be set onto
	 * @param row; The row that the button is assigned to
	 * @param col; The column that the button is assigned to
	 */
	private void setShipIcons(Ship aShip, JButton boardButton, int row, int col) {
		// Information used to determine the path of the image
		String shipName = aShip.getShipName();
		String orientation = aShip.getOrientation();
		int index = aShip.indexOfLocation(row, col);
		String path;
		if (aShip.isAlive()) {
			path = "./images/alive/" + shipName + "_" + orientation + index + ".png";
		} else {
			path = "./images/dead/" + shipName + "_" + orientation + index + ".png";
		}
		// Setting the icon if it exists
		File image = new File(path);
		if (image.exists()) {
			boardButton.setIcon(new ImageIcon(path));
			boardButton.setDisabledIcon(new ImageIcon(path));
			boardButton.setOpaque(false);
		} else { // If image does not exist
			if (aShip.isAlive()) {
				boardButton.setBackground(Color.GREEN);
			} else {
				boardButton.setBackground(Color.BLACK);
			}
			boardButton.setOpaque(true);
		}
		if (!aShip.isAlive()) {
			boardButton.setEnabled(false);
		}
	}
	
	/**
	 * Displays the setup screen for a player
	 * 
	 * @param game; A Game object used as an ActionListener handler
	 * @param aBoard; A board Object to get Ships objects and String title used
	 * for adding ships and setting the ActionCommand
	 * @param shipSelection; The String used in setting the ActionCommand
	 */
	public void shipSetupScreen(Game game, Board aBoard, String shipSelection) {
		contentPane.removeAll();
		
		// Add new JPanel leftBoardPanel to hold the board
		leftBoardPanel = new JPanel();
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 0;
		constraints.insets = new Insets(0, 0, 25, 50);
		contentPane.add(leftBoardPanel, constraints);
		displayBoard(leftBoardPanel, aBoard, shipSelection, game);
		
		// Add new JPanel shipLegend to hold ships
		JPanel shipLegend = new JPanel();
		shipLegend.setLayout(new GridBagLayout());
		shipLegend.setBorder(BorderFactory.createTitledBorder("Ships"));
		shipLegend.setBackground(Color.GRAY);
		shipLegend.setPreferredSize(new Dimension(280, 340));
		shipLegend.setMinimumSize(new Dimension(280, 340));
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 0;
		contentPane.add(shipLegend, constraints);
		
		// Add setup components to shipLegend
		addSetupButtons(game, aBoard, shipSelection, shipLegend);
		addShipLabels(aBoard.getStarShip().getShipName(), shipLegend, 0, 0);
		addShipLabels(aBoard.getBattleCruiser().getShipName(), shipLegend, 1, 0);
		addShipLabels(aBoard.getAssaultCarrier().getShipName(), shipLegend, 0, 2);
		addShipLabels(aBoard.getOrbiter().getShipName(), shipLegend, 1, 2);
		addShipButton(game, aBoard.getTitle(), shipSelection, shipLegend, aBoard.getStarShip(), 0, 1);
		addShipButton(game, aBoard.getTitle(), shipSelection, shipLegend, aBoard.getBattleCruiser(), 1, 1);
		addShipButton(game, aBoard.getTitle(), shipSelection, shipLegend, aBoard.getAssaultCarrier(), 0, 3);
		addShipButton(game, aBoard.getTitle(), shipSelection, shipLegend, aBoard.getOrbiter(), 1, 3);
		
		contentPane.repaint();
	}
	
	/*
	 * Adds the setup buttons onto the ship legend
	 * 
	 * @param game; The Game object used for the Action Listener handler
	 * @param aBoard; The Board object used to obtain ships and and its title
	 * @param shipSelection; The String used in setting the ActionCommand
	 * @param shipLegend; The JPanel that the buttons are added to
	 */
	private void addSetupButtons(Game game, Board aBoard, String shipSelection, JPanel shipLegend) {
		// Add button for rotating ship
		JButton rotateButton = new JButton("rotate");
		rotateButton.setActionCommand("rotate" + "|" + shipSelection + "," + aBoard.getTitle());
		rotateButton.addActionListener(game);
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = 4;
		constraints.insets = new Insets(0, 0, 5, 0);
		shipLegend.add(rotateButton, constraints);
		
		// Add the continue button
		continueButton = new JButton("Continue");
		continueButton.addActionListener(game);
		continueButton.setActionCommand(aBoard.getTitle());
		continueButton.setVisible(false);
		if (aBoard.getStarShip().isAlive() && aBoard.getBattleCruiser().isAlive() && 
				aBoard.getAssaultCarrier().isAlive() && aBoard.getOrbiter().isAlive()) {
			continueButton.setVisible(true);
		}
		constraints = new GridBagConstraints();
		constraints.gridx = 1;
		constraints.gridy = 4;
		constraints.insets = new Insets(0, 0, 5, 0);
		shipLegend.add(continueButton, constraints);
	}
	
	/*
	 * Add ship names onto the ship legend
	 * 
	 * @param shipName; a String name displayed on the label
	 * @param shipLegend; The JPanel that the JLabel is added to
	 * @param gridX; An integer for the gridX constraint
	 * @param gridY; An integer for the gridY constraint
	 */
	private void addShipLabels(String shipName, JPanel shipLegend, int gridX, int gridY) {
		JLabel label = new JLabel(shipName);
		label.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		label.setMinimumSize(new Dimension(((int)BOARD_BUTTON_SIZE.getWidth() * 3), 16));
		label.setPreferredSize(new Dimension(((int)BOARD_BUTTON_SIZE.getWidth() * 3), 16));
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		shipLegend.add(label, constraints);
	}
	
	/*
	 * Adds the ship onto the Ship Legend
	 * 
	 * @param game; The Game object used for the Action Listener handler
	 * @param title; A String used in the Action Command
	 * @param shipSelection; A String used to determine the boarder of the JButton
	 * @param shipLegend; The JPanel that the ship is added to
	 * @param ship; The Ship object that the button represents
	 * @param gridX; An integer for the gridX constraint
	 * @param gridY; An integer for the gridY constraint
	 */
	private void addShipButton(Game game, String title, String shipSelection, JPanel shipLegend, Ship ship, int gridX, int gridY) {
		// Creates the ship button
		JButton button = new JButton();
		Dimension buttonSize = new Dimension((int)BOARD_BUTTON_SIZE.getWidth() * 3, (int)BOARD_BUTTON_SIZE.getHeight() * 3);
		button.setMinimumSize(new Dimension(buttonSize));
		button.setPreferredSize(new Dimension(buttonSize));
		button.setBorderPainted(false);
		button.addActionListener(game);
		button.setActionCommand("|" + ship.getShipName() + "," + title);
		
		// Setting the icon if it exists
		String path = "./images/" + ship.getShipName() + "_" + ship.getOrientation() + ".png";
		File image = new File(path);
		if (image.exists()) {
			button.setIcon(new ImageIcon(path));
			button.setOpaque(false);
		} else { // If image does not exist
			button.setBackground(Color.GREEN);
			button.setOpaque(true);
		}
		
	    // Creates border if ship is clicked on
	    if (shipSelection.equals(ship.getShipName())) { // If the Ship is clicked on
	    	button.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED));
	    	button.setBorderPainted(true);
	    }
		
		// Adds the ship button
	    GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = gridX;
		constraints.gridy = gridY;
		constraints.insets = new Insets(0, 10, 5, 10);
		shipLegend.add(button, constraints);
	}
	
	/**
	 * Begins a player vs. player match
	 * @param game; listener for buttons
	 */
	public void playerVsPlayerSetup(Game game) {
		gameScreen(game);
		enterTransitionScreen("Player 1");
	}
	
	/**
	 * Displays victory dialog for player vs. player
	 * @param player2ShipCount; number of ships alive of player 2
	 * @param player1; player 1
	 * @param player2; player 2
	 */
	public void playerVsPlayerVictory(int player2ShipCount, User player1, User player2) {
		// Announce winner
		String victoryMessage;
		if (player2ShipCount == 0) {
			victoryMessage = "PLAYER 1 WINS!\n";
		} else {
			victoryMessage = "PLAYER 2 WINS!\n";
		}
		
		// Show statistics of both players
		victoryMessage += "\nPLAYER 1 STATISTICS\n" +
						  "\nNumber of player hits: " + player1.getHits() + 
						  "\nNumber of player misses: " + player1.getMisses() +
						  String.format("\nPlayer hit rate: %.2f",player1.getHitRate()) + "\n" +
						  "\nPLAYER 2 STATISTICS\n" +
						  "\nNumber of player hits: " + player2.getHits() +
						  "\nNumber of player misses: " + player2.getMisses() +
						  String.format("\nPlayer hit rate: %.2f",player2.getHitRate());
		JOptionPane.showMessageDialog(null, victoryMessage);
	}
	
	/**
	 * Begins a player vs. computer match
	 * @param game; listener for buttons
	 */
	public void playerVsAISetup(Game game) {
		gameScreen(game);
		continueButton.setVisible(false);
		promptForMove("Player");
	}
	
	/**
	 * Displays victory dialog for player vs. computer
	 * @param player2ShipCount; number of ships alive of player 2
	 * @param player1; player 1
	 */
	public void playerVsAIVictory(int player2ShipCount, User player1) {
		// Announce winner
		String victoryMessage;
		if (player2ShipCount == 0) {
			victoryMessage = "SUCCESS! YOU HAVE SUNK ALL OF THE COMPUTER'S SHIPS!\n";
		} else {
			victoryMessage = "FAIL! THE COMPUTER HAS SUNK ALL OF YOUR SHIPS.\n";
		}
		
		// Show player statistics
		victoryMessage += "\nSTATISTICS\n" + 
						  "\nNumber of player hits: " + player1.getHits() + 
						  "\nNumber of player misses: " + player1.getMisses() + 
						  String.format("\nPlayer hit rate: %.2f",player1.getHitRate());
		JOptionPane.showMessageDialog(null, victoryMessage);
	}
	
	/**
	 * Updates the screen; for player 1
	 * @param player1Board; player 1's board
	 * @param player2Board; player 2's board
	 * @param game; listener for board buttons
	 */
	public void updatePlayer1Screen(Board player1Board, Board player2Board, Game game) {
		displayBoard(leftBoardPanel, player1Board, "Ship", game);
		displayBoard(rightBoardPanel, player2Board, "Target", game);
		shipBox1.setText(player1Board.getTitle() + " Targets Remaining: " + player1Board.getShipsAlive());
		shipBox2.setText(player2Board.getTitle() + " Targets Remaining: " + player2Board.getShipsAlive());
	}
	
	/**
	 * Updates the screen for player 2
	 * @param player1Board; player 1's board
	 * @param player2Board; player 2's board
	 * @param game; listener for board buttons
	 */
	public void updatePlayer2Screen(Board player1Board, Board player2Board, Game game) {
		displayBoard(leftBoardPanel, player2Board, "Ship", game);
		displayBoard(rightBoardPanel, player1Board, "Target", game);
		shipBox1.setText(player1Board.getTitle() + " Targets Remaining: " + player1Board.getShipsAlive());
		shipBox2.setText(player2Board.getTitle() + " Targets Remaining: " + player2Board.getShipsAlive());
	}
	
	/**
	 * Prompts the user for a move
	 * @param name; the name of the user
	 */
	public void promptForMove(String name) {
		JLabel messageBox;
		
		if (name.equals("Player 2")) {
			messageBox = messageBox2;
		} else {
			messageBox = messageBox1;
		}
		messageBox.setText(name + ", click on a target location on the target grid.");
	}
	
	/**
	 * delays the game for 0.75 seconds before the ai makes a move
	 * @param game - actionListener for the timer
	 */
	public void aiThink(Game game) {
		Timer timer = new Timer(750, game);
		timer.setActionCommand("timer");
		
		messageBox2.setText("Computer is thinking...");
		timer.setRepeats(false); // ensures that the timer stops after 0.75 seconds
		timer.start();
	}
	
	/**
	 * Shows whether player 1's move is a hit or a miss
	 * @param move; the move made by the player as a Coordinates object
	 * @param targetBoard; an array containing the opponent's ship locations
	 * @param name; the name of the player
	 */
	public void displayPlayer1Result(Coordinates move, char[][] targetBoard, String name) {
		if (targetBoard[move.getRow()][move.getCol()] == SHIP) {
			messageBox1.setText(name + " got a hit!");
		} else {
			messageBox1.setText(name + " missed.");
		}
	}
	
	/**
	 * Shows whether player 2's move is a hit or a miss
	 * @param move; the move made by the player as a Coordinates object
	 * @param targetBoard; an array containing the opponent's ship locations
	 * @param name; the name of the player
	 */
	public void displayPlayer2Result(Coordinates move, char[][] targetBoard, String name) {
		if (targetBoard[move.getRow()][move.getCol()] == SHIP) {
			messageBox2.setText(name + " got a hit!");
		} else {
			messageBox2.setText(name + " missed.");
		}
	}
	
	/**
	 * Shows a transition screen for player vs. player
	 * @param nextPlayer; the player whose turn is next
	 */
	public void enterTransitionScreen(String nextPlayer) {
		leftBoardPanel.setVisible(false);
		rightBoardPanel.setVisible(false);
		shipBox1.setVisible(false);
		shipBox2.setVisible(false);
		messageBox1.setVisible(false);
		messageBox2.setVisible(false);
		continueButton.setText("Continue to " + nextPlayer + "'s Turn");
		continueButton.setActionCommand("Continue to " + nextPlayer + "'s Turn");
		saveButton.setVisible(true);
	}
	
	/**
	 * Leaves the transition screen for player vs. player
	 * @param nextPlayer; the player whose turn is next
	 */
	public void exitTransitionScreen(String nextPlayer) {
		leftBoardPanel.setVisible(true);
		rightBoardPanel.setVisible(true);
		shipBox1.setVisible(true);
		shipBox2.setVisible(true);
		messageBox1.setVisible(true);
		messageBox2.setVisible(true);
		continueButton.setText("End Turn");
		continueButton.setEnabled(false);
		continueButton.setActionCommand("End " + nextPlayer + " Turn");
		saveButton.setVisible(false);	
	}
	
	/**
	 * Enables the continue button
	 */
	public void enableContinueButton() {
		continueButton.setEnabled(true);
	}
	
	/**
	 * Displays a pop-up message when an exception is caught  
	 * @param message; A String to be displayed
	 */
	public static void exceptionMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Displays a pop-up notifying the user that highscores are being updated
	 */
	public void updatingStatsMessage() {
		JOptionPane.showMessageDialog(null, "Updating high scores...\nDone");
	}
}
