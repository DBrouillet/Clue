package clueGame;

import java.awt.Color;
import java.util.Set;

public class ComputerPlayer extends Player {
	public ComputerPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	public ComputerPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		return new BoardCell();
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
}
