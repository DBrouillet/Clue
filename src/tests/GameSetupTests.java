package tests;

import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class GameSetupTests {
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Rooms.txt", "Players.txt", "Weapons.txt");		
		// Initialize will load all config files 
		board.initialize();
	}
	/**
	 * Test to see if the players were loaded correctly,
	 * by testing players 1, 3, and 6. Player 1 should
	 * be a human player, and the rest should be computers.
	 * Need to test if their name, location, and color is correct.
	 */
	@Test
	public void testPlayerLoading() {
		ArrayList<Player> testList = board.getPlayers();
		
		// Make sure there are 6 players
		assertEquals(6, testList.size());
		
		// Check that first player is a human player
		assert(testList.get(0).getClass() == HumanPlayer.class);
		
		// Check that players 3 and 6 are computers
		assert(testList.get(2).getClass() == ComputerPlayer.class);
		assert(testList.get(5).getClass() == ComputerPlayer.class);
		
		// Check names of players
		assertEquals(testList.get(0).getPlayerName(), "Bob");
		assertEquals(testList.get(2).getPlayerName(), "Fred");
		assertEquals(testList.get(5).getPlayerName(), "John");
		
		// Check locations of players
		assertEquals(testList.get(0).getRow(), 13);
		assertEquals(testList.get(2).getRow(), 18);
		assertEquals(testList.get(5).getRow(), 10);
		assertEquals(testList.get(0).getColumn(), 7);
		assertEquals(testList.get(2).getColumn(), 15);
		assertEquals(testList.get(5).getColumn(), 11);
		
		// Check color of players
		assertEquals(Color.GREEN, testList.get(0).getColor());
		assertEquals(Color.RED, testList.get(2).getColor());
		assertEquals(Color.ORANGE, testList.get(5).getColor());
	}
	
	/**
	 * Test to see if the deck is loaded correctly. 
	 * We make sure that there are the correct number of total cards
	 * and also the correct number of rooms, weapon, and people.
	 * Check one of each type of cards to see that
	 * the information (card names) are loaded properly.
	 */
	@Test
	public void testDeckLoading() {
		ArrayList<Card> testList = board.getDeck();
		
		// Make sure there are 6 + 6 + 9 cards
		assertEquals(6 + 6 + 9, testList.size());
		
		int numRooms = 0;
		int numWeapons = 0;
		int numPeople = 0;
		
		// Determine the number of each card in the deck
		for (Card card : testList) {
			if (card.getCardType() == CardType.PERSON) {
				numPeople++;
			}
			
			if (card.getCardType() == CardType.WEAPON) {
				numWeapons++;
			}
			
			if (card.getCardType() == CardType.ROOM) {
				numRooms++;
			}
		}
		
		// Ensure there are 6 people
		assertEquals(numPeople, 6);
		// Ensure there are 6 weapons
		assertEquals(numWeapons, 6);
		// Ensure there are 9 rooms
		assertEquals(numRooms, 9);
		
		// Check to make sure Fred, Knife, and Kitchen are in the deck
		boolean hasFred = false;
		boolean hasKnife = false;
		boolean hasKitchen = false;
		for (Card card : testList) {
			if (card.getCardName().equals("Fred")) {
				hasFred = true;
			}
			
			if (card.getCardName().equals("Knife")) {
				hasKnife = true;
			}
			
			if (card.getCardName().equals("Kitchen")) {
				hasKitchen = true;
			}
		}
		
		assert(hasFred == true);
		assert(hasKnife == true);
		assert(hasKitchen == true);
	}
	
	/**
	 * Test to make sure the cards were dealt correctly.
	 * This is done by making sure each player was dealt
	 * roughly the same amount of cards, each card was dealt
	 * to exactly one player, and each card was dealt exactly
	 * once (or is a part of the solution).
	 */
	@Test 
	public void testEachPlayerHand() {
		// ArrayList containing each card that has been seen
		ArrayList<Card> seenCards = new ArrayList<Card>(); 
		
		// The expected number of cards each player should have
		int numCardsExpected = board.getDeck().size() / board.getPlayers().size();
		for (Player player : board.getPlayers()) {
			// Check to see if the dealt hand is roughly what we expect, +/- 1
			assertEquals(numCardsExpected, player.getMyCards().size(), 1);
			
			for (Card card : player.getMyCards()) {
				// Check to see that the card has not already been dealt (to another player)
				assert(seenCards.contains(card) == false);
				
				// Add the card to the list of seen cards
				seenCards.add(card);
			}
		}

		// Make sure that each card has been dealt OR is part of the solution
		for (Card card : board.getDeck()) {
			assert(seenCards.contains(card) ||
					board.inAnswer(card));
		}
		
		// Make sure the correct number of cards was dealt
		assertEquals(board.getDeck().size(), seenCards.size() - 3);
	}

}
