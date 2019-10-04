package statistics;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import player.User;

/**
 * Class representing high score statistics
 * 
 * Removed try-catch blocks and added throws 
 * 
 * @author Karn
 * @version 9 Date: Apr. 9, 2017
 */

public class Statistics implements Serializable {
	private int hits;
	private int misses;
	private double hitRate;

	// Constant name/path for file
	public static final String FILE_NAME = "./statistics/highscore.dat";

	/**
	 * Constructor; reads statistics from file and set instance variables
	 * @throws FileNotFoundException; if attempt to open FILE_NAME fails
	 * @throws EOFException; if end of FILE_NAME or end of stream has been reached during output
	 * @throws IOException; general exceptions if failed or interrupted I/O operations occurs
	 */
	public Statistics() throws FileNotFoundException, EOFException, IOException {
		readStatFile();
	}

	/**
	 * @return hits
	 */
	public int getHits() {
		return hits;
	}

	/*
	 * @param player; A User object used to get their hits
	 */
	private void setHits(User player) {
		if (player.getHits() > hits) {
			hits = player.getHits();
		}
	}

	/**
	 * @return misses
	 */
	public int getMisses() {
		return misses;
	}
	
	/*
	 * @param player; A User object used to get their misses
	 */
	private void setMisses(User player) {
		if (player.getMisses() < misses) {
			misses = player.getMisses();
		}
	}

	/**
	 * @return hitRate
	 */
	public double getHitRate() {
		return hitRate;
	}

	/*
	 * @param player; A User object used to get their hit rate
	 */
	private void setHitRate(User player) {
		if (player.getHitRate() > hitRate) {
			hitRate = player.getHitRate();
		}
	}

	/*
	 * Read current statistics in file, set instance variables to those statistics
	 * Throws FileNotFoundException, EOFException, and IOException
	 */
	private void readStatFile() throws FileNotFoundException, EOFException, IOException {
		// Open file for reading data
		ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));

		// Initialise instance variables
		hits = inputStream.readInt();
		misses = inputStream.readInt();
		hitRate = inputStream.readDouble();

		// Closing file
		inputStream.close();
	}

	/**
	 * Updates and writes statistics in file
	 * 
	 * @param player; A User object that will be passed onto another method
	 * @throws FileNotFoundException; if attempt to open FILE_NAME fails
	 * @throws EOFException; if end of FILE_NAME or end of stream has been reached during output
	 * @throws IOException; general exceptions if failed or interrupted I/O operations occurs
	 */
	public void updateStatFile(User player) throws FileNotFoundException, EOFException, IOException {
		// Get statistics from player
		// Set corresponding instance variables
		setHits(player);
		setMisses(player);
		setHitRate(player);

		// Open file for writing; will erase all contents
		ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));

		// Writes variables values to file
		outputStream.writeInt(getHits());
		outputStream.writeInt(getMisses());
		outputStream.writeDouble(getHitRate());

		// Close file
		outputStream.close();
	}
}