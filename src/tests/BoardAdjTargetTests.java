package tests;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson 
 * Tests for our board layout
 *
 */

public class BoardAdjTargetTests {

	// We make the Board static because we can load it one time and 
		// then do all the tests. 
		private static Board board;
		@BeforeClass
		public static void setUp() {
			// Board is singleton, get the only instance
			board = Board.getInstance();
			board.setConfigFiles("BoardLayout.csv", "Rooms.txt");		
			// Initialize will load BOTH config files 
			board.initialize();
		}

		// Ensure that player does not move around within room
		// These cells are ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesInsideRooms()
		{
			// Test a corner
			Set<BoardCell> testList = board.getAdjList(0, 0);
			assertEquals(0, testList.size());
			// Test one that has walkway underneath
			testList = board.getAdjList(3, 0);
			assertEquals(0, testList.size());
			// Test one that has walkway above
			testList = board.getAdjList(17, 11);
			assertEquals(0, testList.size());
			// Test one that is in middle of room
			testList = board.getAdjList(8, 19);
			assertEquals(0, testList.size());
			// Test one beside a door
			testList = board.getAdjList(6, 17);
			assertEquals(0, testList.size());
		}

		// Ensure that the adjacency list from a doorway is only the
		// walkway. NOTE: This test could be merged with door 
		// direction test. 
		// These tests are PURPLE on the planning spreadsheet
		@Test
		public void testAdjacencyRoomExit()
		{
			// TEST DOORWAY RIGHT 
			Set<BoardCell> testList = board.getAdjList(8, 6);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(8, 7)));
			// TEST DOORWAY LEFT 
			testList = board.getAdjList(2, 15);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(2, 14)));
			//TEST DOORWAY DOWN
			testList = board.getAdjList(14, 10);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(15, 10)));
			//TEST DOORWAY UP
			testList = board.getAdjList(6, 10);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(5, 10)));
			//TEST DOORWAY RIGHT, WHERE THERE'S A WALKWAY BELOW
			testList = board.getAdjList(12, 4);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCellAt(12, 5)));
			
		}
		
		// Test adjacency at entrance to rooms
		// These tests are GREEN in planning spreadsheet
		@Test
		public void testAdjacencyDoorways()
		{
			// Test beside a door direction RIGHT
			Set<BoardCell> testList = board.getAdjList(9, 16);
			assertTrue(testList.contains(board.getCellAt(9, 17)));
			assertTrue(testList.contains(board.getCellAt(9, 15)));
			assertTrue(testList.contains(board.getCellAt(8, 16)));
			assertTrue(testList.contains(board.getCellAt(10, 16)));
			assertEquals(4, testList.size());
			// Test beside a door direction DOWN
			testList = board.getAdjList(5, 10);
			assertTrue(testList.contains(board.getCellAt(5, 9)));
			assertTrue(testList.contains(board.getCellAt(5, 11)));
			assertTrue(testList.contains(board.getCellAt(6, 10)));
			assertTrue(testList.contains(board.getCellAt(4, 10)));
			assertEquals(4, testList.size());
			// Test beside a door direction LEFT
			testList = board.getAdjList(7, 7);
			assertTrue(testList.contains(board.getCellAt(7, 8)));
			assertTrue(testList.contains(board.getCellAt(7, 6)));
			assertTrue(testList.contains(board.getCellAt(6, 7)));
			assertTrue(testList.contains(board.getCellAt(8, 7)));
			assertEquals(4, testList.size());
			// Test beside a door direction UP
			testList = board.getAdjList(15, 9);
			assertTrue(testList.contains(board.getCellAt(15, 10)));
			assertTrue(testList.contains(board.getCellAt(15, 8)));
			assertTrue(testList.contains(board.getCellAt(14, 9)));
			assertTrue(testList.contains(board.getCellAt(16, 9)));
			assertEquals(4, testList.size());
		}
		
		
		// Test a variety of walkway scenarios
		// These tests are GRAY BLUE on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// Test on top edge of board, two walkway pieces
			Set<BoardCell> testList = board.getAdjList(0, 6);
			assertTrue(testList.contains(board.getCellAt(0, 7)));
			assertTrue(testList.contains(board.getCellAt(1, 6)));
			assertEquals(2, testList.size());
			
			// Test on left edge of board, three walkway pieces
			testList = board.getAdjList(15, 0);
			assertTrue(testList.contains(board.getCellAt(14, 0)));
			assertTrue(testList.contains(board.getCellAt(16, 0)));
			assertTrue(testList.contains(board.getCellAt(15, 1)));
			assertEquals(3, testList.size());

			// Test between two rooms, walkways right and left
			testList = board.getAdjList(4, 20);
			assertTrue(testList.contains(board.getCellAt(4, 19)));
			assertTrue(testList.contains(board.getCellAt(4, 21)));
			assertEquals(2, testList.size());

			// Test surrounded by 4 walkways
			testList = board.getAdjList(13,7);
			assertTrue(testList.contains(board.getCellAt(13, 8)));
			assertTrue(testList.contains(board.getCellAt(13, 6)));
			assertTrue(testList.contains(board.getCellAt(14, 7)));
			assertTrue(testList.contains(board.getCellAt(12, 7)));
			assertEquals(4, testList.size());
			
			// Test on bottom edge of board, next to 1 room piece
			testList = board.getAdjList(21, 16);
			assertTrue(testList.contains(board.getCellAt(21, 15)));
			assertTrue(testList.contains(board.getCellAt(20, 16)));
			assertEquals(2, testList.size());
			
			// Test on right edge of board, next to 1 room piece
			testList = board.getAdjList(14, 21);
			assertTrue(testList.contains(board.getCellAt(13, 21)));
			assertTrue(testList.contains(board.getCellAt(14, 20)));
			assertEquals(2, testList.size());

			// Test on walkway next to  door that is not in the needed
			// direction to enter
			testList = board.getAdjList(13, 4);
			assertTrue(testList.contains(board.getCellAt(13, 3)));
			assertTrue(testList.contains(board.getCellAt(13, 5)));
			assertTrue(testList.contains(board.getCellAt(14, 4)));
			assertEquals(3, testList.size());
		}
		
		// Tests of just walkways, 1 step
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsOneStep() {
			board.calcTargets(14, 16, 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 17)));
			assertTrue(targets.contains(board.getCellAt(14, 15)));
			assertTrue(targets.contains(board.getCellAt(15, 16)));
			assertTrue(targets.contains(board.getCellAt(13, 16)));
		}	
		
		// Tests of just walkways plus one door, 3 steps
		// These are LIGHT BLUE on the planning spreadsheet

		@Test
		public void testTargetsThreeSteps() {
			board.calcTargets(13, 0, 3);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCellAt(13, 3)));
			assertTrue(targets.contains(board.getCellAt(14, 2)));	
			assertTrue(targets.contains(board.getCellAt(15, 1)));	
			assertTrue(targets.contains(board.getCellAt(16, 0)));	
			assertTrue(targets.contains(board.getCellAt(13, 1)));	
			assertTrue(targets.contains(board.getCellAt(14, 0)));
		}	
		
		// Test getting into a room
		// These are LIGHT BLUE on the planning spreadsheet
		// Also, test walkway targets

		@Test 
		public void testTargetsIntoRoom()
		{
			// One room is exactly 2 away
			board.calcTargets(17, 16, 2);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(5, targets.size());
			// directly left (can't go right 2 steps)
			assertTrue(targets.contains(board.getCellAt(17, 14)));
			// directly up and down
			assertTrue(targets.contains(board.getCellAt(15, 16)));
			assertTrue(targets.contains(board.getCellAt(19, 16)));
			// one up/down, one left/right
			assertTrue(targets.contains(board.getCellAt(18, 15)));
			assertTrue(targets.contains(board.getCellAt(16, 15)));
		}
		
		// Test getting into room, doesn't require all steps
		// These are LIGHT BLUE on the planning spreadsheet
		// Also test walkway targets
		@Test
		public void testTargetsIntoRoomShortcut() 
		{
			board.calcTargets(5, 7, 4);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(19, targets.size());
			// Only 18 assertTrue inclusions, what is the 19th?
			assertTrue(targets.contains(board.getCellAt(4, 4)));
			assertTrue(targets.contains(board.getCellAt(2, 6)));
			assertTrue(targets.contains(board.getCellAt(1, 7)));
			assertTrue(targets.contains(board.getCellAt(3, 7)));
			assertTrue(targets.contains(board.getCellAt(4, 6)));
			assertTrue(targets.contains(board.getCellAt(4, 10)));
			assertTrue(targets.contains(board.getCellAt(5, 9)));
			assertTrue(targets.contains(board.getCellAt(6, 8)));
			assertTrue(targets.contains(board.getCellAt(7, 7)));
			assertTrue(targets.contains(board.getCellAt(9, 7)));
			assertTrue(targets.contains(board.getCellAt(8, 8)));
			assertTrue(targets.contains(board.getCellAt(4, 8)));
			
			// into the rooms
			assertTrue(targets.contains(board.getCellAt(2, 8)));
			assertTrue(targets.contains(board.getCellAt(3, 8)));
			assertTrue(targets.contains(board.getCellAt(7, 6)));		
			assertTrue(targets.contains(board.getCellAt(8, 6)));	
			assertTrue(targets.contains(board.getCellAt(5, 11)));	
			assertTrue(targets.contains(board.getCellAt(6, 9)));
			assertTrue(targets.contains(board.getCellAt(6, 10)));
			
		}

		// Test getting out of a room
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testRoomExitUp()
		{
			// Take one step, essentially just the adj list
			board.calcTargets(15, 4, 1);
			Set<BoardCell> targets= board.getTargets();
			// Ensure doesn't exit through the wall
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 4)));
			// Take two steps
			board.calcTargets(15, 18, 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(14, 17)));
			assertTrue(targets.contains(board.getCellAt(14, 19)));
			assertTrue(targets.contains(board.getCellAt(13, 18)));
		}
		
		// Test getting out of a room
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testRoomExitLeft()
		{
			// Take one step, essentially just the adj list
			board.calcTargets(10, 17, 1);
			Set<BoardCell> targets= board.getTargets();
			// Ensure doesn't exit through the wall
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCellAt(10, 16)));
			// Take two steps
			board.calcTargets(10, 17, 2);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCellAt(9, 16)));
			assertTrue(targets.contains(board.getCellAt(10, 15)));
			assertTrue(targets.contains(board.getCellAt(11, 16)));
		}
}
