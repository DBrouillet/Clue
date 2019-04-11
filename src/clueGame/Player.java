package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class Player {
	// Color used to draw a player's border
	public static final Color PLAYER_BORDER = Color.BLACK;
	// Pixel offset to make it so the player circle isn't intersecting the board cell
	public static final int PLAYER_PIXEL_OFFSET = 4;
	
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards = new ArrayList<Card>();
	private Map<Card, Boolean> seenCards = new HashMap<Card, Boolean>();

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
	
	public void initializeSeenCards() {
		Board theInstance = Board.getInstance();
		for(Card c : theInstance.getWeaponsDeck()) {
			seenCards.put(c, false);
		}
		for(Card c : theInstance.getPlayersDeck()) {
			seenCards.put(c, false);
		}
		for(Card c : theInstance.getPlacesDeck()) {
			seenCards.put(c, false);
		}
		seenCards.put(theInstance.getTheAnswer().personCard, false);
		seenCards.put(theInstance.getTheAnswer().weaponCard, false);
		seenCards.put(theInstance.getTheAnswer().roomCard, false);
	}
	
	/**
	 * @param g = Graphics object that we are drawing on
	 * Draws the player as a circle with appropriate color
	 */
	public void draw(Graphics g) {
		int size = BoardCell.CELL_DIMENSION;
		
		g.setColor(color);
		g.fillOval(size * column + PLAYER_PIXEL_OFFSET/2, size * row + PLAYER_PIXEL_OFFSET/2, size - PLAYER_PIXEL_OFFSET, size - PLAYER_PIXEL_OFFSET);
		
		g.setColor(PLAYER_BORDER);
		g.drawOval(size * column + PLAYER_PIXEL_OFFSET/2, size * row + PLAYER_PIXEL_OFFSET/2, size - PLAYER_PIXEL_OFFSET, size - PLAYER_PIXEL_OFFSET);
	}

	
	public void addCard(Card c) {
		myCards.add(c);
	}
	
	public void addSeenCard(Card c) {
		seenCards.put(c, true);
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
	
	public Map<Card, Boolean> getSeenCards() {
		return seenCards;
	}
	
	// ONLY USED FOR JUNIT TESTS
	public void setMyCards(ArrayList<Card> myCards) {
		this.myCards = myCards;
	}
	
	public abstract void move(Set<BoardCell> targets);


}
