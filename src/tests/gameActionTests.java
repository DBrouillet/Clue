package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

public class gameActionTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Rooms.txt", "Players.txt", "Weapons.txt");		
		// Initialize will load all config files 
		board.initialize();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
