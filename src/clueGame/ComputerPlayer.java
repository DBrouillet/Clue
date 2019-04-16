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
		Solution theAnswer = theInstance.getTheAnswer();
		String roomName = theInstance.getLegend().get(theInstance.getCellAt(getRow(), getColumn()).getInitial());
		ArrayList<Card> unseenWeapons = new ArrayList<Card>();
		ArrayList<Card> unseenPlayers = new ArrayList<Card>();
		for(Card weapon : theInstance.getWeaponsDeck()) {
			if(!getSeenCards().get(weapon)) {
				unseenWeapons.add(weapon);
			}
		}
		for(Card player : theInstance.getPlayersDeck()) {
			if(!getSeenCards().get(player)) {
				unseenPlayers.add(player);
			}
		}
		if(!getSeenCards().get(theAnswer.weaponCard)) {
			unseenWeapons.add(theAnswer.weaponCard);
			
		}
		if(!getSeenCards().get(theAnswer.personCard)) {
			unseenPlayers.add(theAnswer.personCard);
		}
		Collections.shuffle(unseenWeapons);
		Collections.shuffle(unseenPlayers);
		return new Solution(unseenPlayers.get(0).getCardName(),roomName,unseenWeapons.get(0).getCardName());
	}

	public char getMostRecentRoom() {
		return mostRecentRoom;
	}

	public void setMostRecentRoom(char mostRecentRoom) {
		this.mostRecentRoom = mostRecentRoom;
	}
	
	/**
	 * @param targets = Set of BoardCells that are valid to move to
	 * Picks a (valid) location to move to, then updates the computer player
	 * to that BoardCell and (if it was a room) the most recent room variable.
	 */
	public void move(Set<BoardCell> targets) {
		BoardCell nextMove = pickLocation(targets);
		
		setCurrentCell(nextMove);
		if(this.getCurrentCell().isDoorway()) {
			this.setMostRecentRoom(this.getCurrentCell().getInitial());
		}
	}
}
