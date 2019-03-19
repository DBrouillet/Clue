package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	private ArrayList<Card> myCards;
	private ArrayList<Card> seenCards;
	
	public Card disproveSuggesstion(Solution suggestion) {
		return new Card();
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
