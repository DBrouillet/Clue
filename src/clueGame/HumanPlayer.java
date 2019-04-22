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

	//TODO A player may not reenter the same room on the same turn.
	//TODO A player may not stop on or move through a non-room location occupied by another player (rooms may be occupied by multiple players).
	/**
	 * @param target = BoardCell to move to
	 */
	public void move(BoardCell target) {
		setCurrentCell(target);
		if(target.isDoorway()) {
			ClueGame.getInstance().displaySuggestionGUI(this);
		}
	}
	
}
