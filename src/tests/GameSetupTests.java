package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Rooms.txt", "Players.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	public void test() {
		ArrayList<Player> testList = board.getPlayers();
		assertEquals(6, testList.size());
		assert(testList.get(0).getClass() == HumanPlayer.class);
		assert(testList.get(2).getClass() == ComputerPlayer.class);
		assert(testList.get(5).getClass() == ComputerPlayer.class);
		assertEquals(testList.get(0).getPlayerName(), "Bob");
		assertEquals(testList.get(2).getPlayerName(), "Fred");
		assertEquals(testList.get(5).getPlayerName(), "John");
		assertEquals(testList.get(0).getRow(), 13);
		assertEquals(testList.get(2).getRow(), 18);
		assertEquals(testList.get(5).getRow(), 10);
		assertEquals(testList.get(0).getColumn(), 7);
		assertEquals(testList.get(2).getColumn(), 15);
		assertEquals(testList.get(5).getColumn(), 11);
		assertEquals(testList.get(0).getColor(), Color.GREEN);
		assertEquals(testList.get(2).getColor(), Color.RED);
		assertEquals(testList.get(5).getColor(), Color.ORANGE);
	}

}
