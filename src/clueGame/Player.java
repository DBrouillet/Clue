package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	private ArrayList<Card> seenCards = new ArrayList<Card>();

	public Player(String playerName, Color color, int row, int column) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = color;
	}

	public Player(String playerName, String color, int row, int column) {
		super();
		this.playerName = playerName;
		this.row = row;
		this.column = column;
		this.color = convertColor(color);
	}
	
	private boolean matching(String card, String solution) {
		return (card.equals(solution));
	}

	/**
	 * @param suggestion = suggestion to disprove by the player
	 * @return a Card which disproves the suggestion
	 */
	public Card disproveSuggesstion(Solution suggestion) {
		// Find all potential matches
		ArrayList<Card> matches = new ArrayList<Card>();
		for (Card card : myCards) {
			if (matching(card.getCardName(), suggestion.person)) {
				matches.add(card);
			}
			else if (matching(card.getCardName(), suggestion.room)) {
				matches.add(card);
			}
			else if (matching(card.getCardName(), suggestion.weapon)) {
				matches.add(card);
			}
		}
		
		// If there are no matches, return null
		if (matches.isEmpty()) {
			return null;
		}
		
		// Otherwise, return a random match
		Collections.shuffle(matches);
		return matches.get(0);
	}
	
	private Color convertColor(String strColor) {
		Color color;
		strColor = strColor.toUpperCase();
		try {
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor.trim());
			color = (Color)field.get(null);
		} catch (Exception e) {
			color = null; // Not defined
		}
		return color;
	}
	
	public void addCard(Card c) {
		myCards.add(c);
	}

	public Color getColor() {
		return color;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public String getPlayerName() {
		return playerName;
	}

	public ArrayList<Card> getMyCards() {
		return myCards;
	}

	// ONLY USED FOR JUNIT TESTS
	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}


}
