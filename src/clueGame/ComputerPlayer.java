package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

public class ComputerPlayer extends Player {
	private char mostRecentRoom;
	
	public ComputerPlayer(String playerName, Color color, int row, int column) {
		super(playerName, color, row, column);
	}
	
	public ComputerPlayer(String playerName, String color, int row, int column) {
		super(playerName, color, row, column);
	}

	public BoardCell pickLocation(Set<BoardCell> targets) {
		ArrayList<BoardCell> choices = new ArrayList<BoardCell>();
		for (BoardCell cell : targets) {
			if(cell.getInitial() != mostRecentRoom && cell.isDoorway()) {
				choices.add(cell);
			}
		}
		if (! choices.isEmpty()) {
			Collections.shuffle(choices);
			return choices.get(0);
		}
		choices.addAll(targets);
		Collections.shuffle(choices);
		return choices.get(0);
	}
	
	public Solution makeAccusation(String person, String room, String weapon) {
		return new Solution(person, room, weapon);
	}
	
	public Solution createSuggestion() {
		Board theInstance = Board.getInstance();
		String roomName = theInstance.getLegend().get(theInstance.getCellAt(getRow(), getColumn()).getInitial());
		return new Solution("",roomName,"");
	}

	public char getMostRecentRoom() {
		return mostRecentRoom;
	}

	public void setMostRecentRoom(char mostRecentRoom) {
		this.mostRecentRoom = mostRecentRoom;
	}
}
