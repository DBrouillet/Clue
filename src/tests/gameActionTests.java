package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.ComputerPlayer;
import clueGame.Player;

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
	public void testRoomlessComputerTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 20, 8);
		int numDown = 0;
		int numUp = 0;
		for (int i = 0; i < 100; i++) {
			board.calcTargets(20, 8, 1);
			BoardCell destination = testPlayer.pickLocation(board.getTargets());
			if(destination.getRow() == 21 && destination.getColumn() == 8) {
				numDown++;
			}
			else if(destination.getRow() == 19 && destination.getColumn() == 8) {
				numUp++;
			}
			else fail();
		}
		assertTrue(numDown > 20);
		assertTrue(numUp > 20);
	}
	
	@Test
	public void testRoomNotVistitedComputerTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 17, 8);
		testPlayer.setMostRecentRoom('L');
		for (int i = 0; i < 100; i++) {
			board.calcTargets(17, 8, 1);
			BoardCell destination = testPlayer.pickLocation(board.getTargets());
			if(destination.getRow() == 17 && destination.getColumn() == 9) {
			}
			else fail();
		}
	}
	
	@Test
	public void testRoomVistitedComputerTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 17, 8);
		testPlayer.setMostRecentRoom('B');
		int numDown = 0;
		int numUp = 0;
		int numRight = 0;
		for (int i = 0; i < 100; i++) {
			board.calcTargets(17, 8, 1);
			BoardCell destination = testPlayer.pickLocation(board.getTargets());
			if(destination.getRow() == 18 && destination.getColumn() == 8) {
				numDown++;
			}
			else if(destination.getRow() == 16 && destination.getColumn() == 8) {
				numUp++;
			}
			else if(destination.getRow() == 17 && destination.getColumn() == 9) {
				numRight++;
			}
			else fail();
		}
		assertTrue(numDown > 10);
		assertTrue(numUp > 10);
		assertTrue(numRight > 10);
	}
	
	@Test
	public void testCheckAccusation() {
		
	}

}
