package clueGame;

import java.awt.Color;
import java.util.Set;

public class HumanPlayer extends Player {

	public HumanPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	public HumanPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
	}

	// Display these targets
	@Override
	public void move(Set<BoardCell> targets) {
		
	}
	
}
