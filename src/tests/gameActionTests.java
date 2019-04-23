package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Player;
import clueGame.Solution;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson
 * Class used to test the basic actions of the game,
 * such as choosing a target to move to,
 * checking an accusation, creating a suggestion,
 * disproving a suggestion, and handling a suggestion.
 *
 */
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
	
	/**
	 * Check target selection
	 * when there are no rooms in the targets.
	 * We do this by picking a square where
	 * the player can only move up or down.
	 * We check to make sure it is moving up
	 * and down and nowhere else.
	 */
	@Test
	public void testRoomlessComputerTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 20, 8);
		int numDown = 0;
		int numUp = 0;
		// Run 100 trials by moving the player from (20,8) with a roll of 1
		for (int i = 0; i < 100; i++) {
			board.calcTargetsTest(20, 8, 1);
			BoardCell destination = testPlayer.pickLocation(board.getTargets());
			// Count the number of times that the player goes up and down
			if(destination.getRow() == 21 && destination.getColumn() == 8) {
				numDown++;
			}
			else if(destination.getRow() == 19 && destination.getColumn() == 8) {
				numUp++;
			} // If it ever goes to another BoardCell, fail
			else fail();
		}
		
		// Make sure each was chosen a reasonable number of times
		assertTrue(numDown > 20);
		assertTrue(numUp > 20);
	}
	
	/**
	 * Test to check the selected BoardCell when there
	 * is a room in the targets and it has not been visited.
	 */
	@Test
	public void testRoomNotVistitedComputerTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 17, 8);
		testPlayer.setMostRecentRoom('L');
		// Run 100 trials by moving the player at (17,8) (in the Living room) a distance of 1
		for (int i = 0; i < 100; i++) {
			board.calcTargetsTest(17, 8, 1);
			BoardCell destination = testPlayer.pickLocation(board.getTargets());
			// Make sure it is picking the doorway every time, otherwise fail
			if(destination.getRow() == 17 && destination.getColumn() == 9) {
			}
			else fail();
		}
	}
	
	/**
	 * Test to see if the BoardCells are
	 * chosen appropriately when there is
	 * a room in the targets, but it
	 * also was visited last.
	 */
	@Test
	public void testRoomVistitedComputerTargetSelection() {
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 17, 8);
		testPlayer.setMostRecentRoom('B');
		int numDown = 0;
		int numUp = 0;
		int numRight = 0;
		// Run 100 trials by moving the player from (17, 8) with a roll of 1
		for (int i = 0; i < 100; i++) {
			board.calcTargetsTest(17, 8, 1);
			BoardCell destination = testPlayer.pickLocation(board.getTargets());

			// Count the number of times that the player goes up, down, and left
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
		
		// Make sure each was chosen a reasonable number of times
		assertTrue(numDown > 10);
		assertTrue(numUp > 10);
		assertTrue(numRight > 10);
	}
	
	/**
	 * Test to see if checking the accusation
	 * is done correctly. We do this by creating
	 * a solution and setting that as the answer.
	 * We create a test player, and then check
	 * the accusation when it is correct and
	 * then with each individual component incorrect
	 */
	@Test
	public void testCheckAccusation() {
		// Create the solution and the test player
		Solution solution = new Solution("Bob", "Kitchen", "Knife");
		Solution oldAnswer = board.getTheAnswer();
		board.setTheAnswer(solution);
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 17, 8);
		
		// Check the accusation when it is correct
		Solution accusation = testPlayer.makeAccusation("Bob", "Kitchen", "Knife");
		assert(board.checkAccusation(accusation));
		
		// Check accusation when the person is incorrect
		accusation = testPlayer.makeAccusation("Joe", "Kitchen", "Knife");
		assert(board.checkAccusation(accusation) == false);
		
		// Check accusation when the room is incorrect
		accusation = testPlayer.makeAccusation("Bob", "Office", "Knife");
		assert(board.checkAccusation(accusation) == false);
		
		// Check accusation when the weapon is incorrect
		accusation = testPlayer.makeAccusation("Bob", "Kitchen", "Gun");
		assert(board.checkAccusation(accusation) == false);
		
		//Reset the solution to the removed cards
		board.setTheAnswer(oldAnswer);
	}
	
	/**
	 * Test to make sure that suggestions
	 * are disproved appropriately. We do
	 * this by testing the 3 separate cases
	 * of when there are 0 matching cards,
	 * 1 matching card, and >1 (2 specifically)
	 * matching cards.
	 */
	@Test
	public void testDisproveSuggestion() {
		/*
		 * Create the suggestion to test,
		 * a test player, and a pair of each type
		 * of card (one that matches the suggestion
		 * and one that doesn't for each type)
		 */
		Solution suggestion = new Solution("Joe", "Kitchen", "Knife");
		Player testPlayer = new ComputerPlayer("testPlayer", "green", 17, 8);

		Card personMatch = new Card("Joe", CardType.PERSON);
		Card personNoMatch = new Card("Bob", CardType.PERSON);
		
		Card roomMatch = new Card("Kitchen", CardType.ROOM);
		Card roomNoMatch = new Card("Study", CardType.ROOM);
		
		Card weaponMatch = new Card("Knife", CardType.WEAPON);
		Card weaponNoMatch = new Card("Gun", CardType.WEAPON);
		
		// Test the case that no cards are matching
		ArrayList<Card> noMatching = new ArrayList<Card>();
		noMatching.add(weaponNoMatch);
		noMatching.add(personNoMatch);
		noMatching.add(roomNoMatch);
		
		testPlayer.setMyCards(noMatching);
		
		assertEquals(testPlayer.disproveSuggesstion(suggestion), null);
		
		/*
		 * Test to make sure that when one
		 * card matches it returns that card.
		 * We test for the case that the weapon
		 * is matching, the person is matching,
		 * and the room is matching separately.
		 */
		
		// Test for matching weapon
		ArrayList<Card> oneMatchingWeapon = new ArrayList<Card>();
		oneMatchingWeapon.add(weaponMatch);
		oneMatchingWeapon.add(personNoMatch);
		oneMatchingWeapon.add(roomNoMatch);
		
		testPlayer.setMyCards(oneMatchingWeapon);
		
		assertEquals(testPlayer.disproveSuggesstion(suggestion), weaponMatch);
		
		// Test for matching person
		ArrayList<Card> oneMatchingPerson = new ArrayList<Card>();
		oneMatchingPerson.add(weaponNoMatch);
		oneMatchingPerson.add(personMatch);
		oneMatchingPerson.add(roomNoMatch);
		
		testPlayer.setMyCards(oneMatchingPerson);
		
		assertEquals(testPlayer.disproveSuggesstion(suggestion), personMatch);
		
		// Test for matching room
		ArrayList<Card> oneMatchingRoom = new ArrayList<Card>();
		oneMatchingRoom.add(weaponNoMatch);
		oneMatchingRoom.add(personNoMatch);
		oneMatchingRoom.add(roomMatch);
		
		testPlayer.setMyCards(oneMatchingRoom);
		
		assertEquals(testPlayer.disproveSuggesstion(suggestion), roomMatch);
		
		/*
		 * Test to see that when there
		 * are 2 (> 1) matching Cards
		 * in the player's hand, they
		 * choose randomly from the 2
		 * that match.
		 */
		
		ArrayList<Card> twoMatching = new ArrayList<Card>();
		twoMatching.add(weaponNoMatch);
		twoMatching.add(personMatch);
		twoMatching.add(roomMatch);
		
		testPlayer.setMyCards(twoMatching);
		
		int numRoomChosen = 0;
		int numPersonChosen = 0;
		
		// Run 100 trials of disproving the suggestion
		for (int i = 0; i < 100; i++) {
			Card match = testPlayer.disproveSuggesstion(suggestion);
			// Count the number of times we choose the person or room match
			if (match == personMatch) {
				numPersonChosen++;
			} else if (match == roomMatch) {
				numRoomChosen++;
			} else { // But if anything else is ever chosen, fail
				fail();
			}
		}
		
		// Make sure we have a reasonable number of each chosen
		assert(numRoomChosen > 20);
		assert(numPersonChosen > 20);
	}

	@Test
	public void testCreateSuggestion() {
		/*
		 * Test to see that the suggested room
		 * and the room the player is in are the same
		 */
		ComputerPlayer testPlayer = new ComputerPlayer("testPlayer", "green", 3, 3);
		testPlayer.initializeSeenCards();
		BoardCell location = board.getCellAt(testPlayer.getRow(), testPlayer.getColumn());
		Map<Character, String> legend = board.getLegend();
		ArrayList<Card> weaponsDeck = board.getWeaponsDeck();
		ArrayList<Card> playersDeck = board.getPlayersDeck();
		assertTrue(testPlayer.createSuggestion().room.equals(legend.get(location.getInitial())));
		
		/*
		 * Test to see that if only one weapon/player is unseen
		 * it will always be in the suggestion.
		 */
		for(Card c : weaponsDeck) {
			testPlayer.addSeenCard(c);
		}
		for(Card c : playersDeck) {
			testPlayer.addSeenCard(c);
		}
		assertTrue(testPlayer.createSuggestion().weapon.equals(board.getTheAnswer().weapon));
		assertTrue(testPlayer.createSuggestion().person.equals(board.getTheAnswer().person));
		
		/*
		 * Test to see that if multiple weapons/players 
		 * are unseen one is chosen randomly for the suggestion.
		 */
		ComputerPlayer testPlayer2 = new ComputerPlayer("testPlayer2", "green", 3, 3);
		testPlayer2.initializeSeenCards();
		for(int i = 0; i < weaponsDeck.size()-1; i++) {
			testPlayer2.addSeenCard(weaponsDeck.get(i));
		}
		for(int i = 0; i < playersDeck.size()-1; i++) {
			testPlayer2.addSeenCard(playersDeck.get(i));
		}
		int unseenPlayer1Chosen = 0;
		int unseenPlayer2Chosen = 0;
		int unseenWeapon1Chosen = 0;
		int unseenWeapon2Chosen = 0;
		//Run 100 trials to disprove the suggestion
		for(int i = 0; i < 100; i++) {
			Solution testSuggestion = testPlayer2.createSuggestion();
			// Count the number of times each possible weapon or player is chosen
			if(testSuggestion.weapon.equals(board.getTheAnswer().weapon)) {
				unseenWeapon1Chosen++;
			}
			else if(testSuggestion.weapon.equals(weaponsDeck.get(weaponsDeck.size()-1).getCardName())) {
				unseenWeapon2Chosen++;
			}
			if(testSuggestion.person.equals(board.getTheAnswer().person)) {
				unseenPlayer1Chosen++;
			}
			else if(testSuggestion.person.equals(playersDeck.get(playersDeck.size()-1).getCardName())) {
				unseenPlayer2Chosen++;
			}
			else fail();// But if anything else is ever chosen, fail
		}
		
		// Make sure we have a reasonable number of each chosen
		assert(unseenWeapon1Chosen > 10);
		assert(unseenWeapon2Chosen > 10);
		assert(unseenPlayer1Chosen > 10);
		assert(unseenPlayer2Chosen > 10);
	}
	
	@Test
	public void testhandleSuggestionTest() {
		
		/*
		 * Creates the hands of each player.
		 * The actual solution is Office, Bottle, Greg
		 */
		
		ArrayList<Card> p1Cards = new ArrayList<Card>();
		p1Cards.add(new Card("Kitchen", CardType.ROOM));
		p1Cards.add(new Card("Gun", CardType.WEAPON));
		p1Cards.add(new Card("Jack", CardType.PERSON));
		
		ArrayList<Card> p2Cards = new ArrayList<Card>();
		p2Cards.add(new Card("Bathroom", CardType.ROOM));
		p2Cards.add(new Card("Knife", CardType.WEAPON));
		p2Cards.add(new Card("Bob", CardType.PERSON));
		
		ArrayList<Card> p3Cards = new ArrayList<Card>();
		p3Cards.add(new Card("Conservatory", CardType.ROOM));
		p3Cards.add(new Card("Pipe", CardType.WEAPON));
		p3Cards.add(new Card("John", CardType.PERSON));
		
		ArrayList<Card> p4Cards = new ArrayList<Card>();
		p4Cards.add(new Card("Game room", CardType.ROOM));
		p4Cards.add(new Card("Candlestick", CardType.WEAPON));
		p4Cards.add(new Card("Fred", CardType.PERSON));
		
		ArrayList<Card> p5Cards = new ArrayList<Card>();
		p5Cards.add(new Card("Dressing room", CardType.ROOM));
		p5Cards.add(new Card("Golf Club", CardType.WEAPON));
		p5Cards.add(new Card("Joe", CardType.PERSON));
		
		ArrayList<Card> p6Cards = new ArrayList<Card>();
		p6Cards.add(new Card("Living room", CardType.ROOM));
		p6Cards.add(new Card("Study", CardType.ROOM));
		p6Cards.add(new Card("Hall", CardType.ROOM));
		
		int i = 1;
		for (Player player : board.getPlayers()) {
			switch(i) {
			case 1:
				player.setMyCards(p1Cards);
				break;
			case 2:
				player.setMyCards(p2Cards);
				break;
			case 3:
				player.setMyCards(p3Cards);
				break;
			case 4:
				player.setMyCards(p4Cards);
				break;
			case 5:
				player.setMyCards(p5Cards);
				break;
			case 6:
				player.setMyCards(p6Cards);
				break;
			}
			i++;
		}
		
		// Tests suggestion no one can disprove. 
		Solution suggestion = new Solution("Greg", "Office", "Bottle");
		Card result = board.handleSuggestionTest(suggestion, 0);
		assert(result == null);
		
		// Tests suggestion only accusing Player can disprove.
		// Player 1 with the hand Kitchen/Gun/Jack is the only one that can disprove this
		// Player 1 has index of 0 in players array
		Solution suggestion2 = new Solution("Jack", "Office", "Bottle");
		Card result2 = board.handleSuggestionTest(suggestion2, 0);
		assert (result == null);
		
		// Tests suggestion only human can disprove.
		// Player 1 is human with the hand Kitchen/Gun/Jack
		// Player 1 has index of 0 in players array - testing that player not being accuser
		// result should be the Jack card.
		Solution suggestion3 = new Solution("Jack", "Office", "Bottle");
		Card result3 = board.handleSuggestionTest(suggestion3, 1);
		assert (result3.getCardName() == "Jack");
		
		// Tests suggestion only human can disprove, human is accuser.
		// Player 1 is human with the hand Kitchen/Gun/Jack
		// Player 1 has index of 0 in players array
		// Should null
		Solution suggestion4 = new Solution("Jack", "Office", "Bottle");
		Card result4 = board.handleSuggestionTest(suggestion4, 0);
		assert (result4 == null);
		
		// Tests suggestion two players can disprove.
		// Player 3 is asking (index is 2)
		// Player 4 and 1 can disprove
		// Player 4 should be the one to disprove it
		// Result should be Game room
		Solution suggestion5 = new Solution("Jack", "Game room", "Bottle");
		Card result5 = board.handleSuggestionTest(suggestion5, 2);
		assert (result5.getCardName() == "Game room");
	}
	
}
