package clueGame;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;

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

	public Card disproveSuggesstion(Solution suggestion) {
		return new Card();
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


}
