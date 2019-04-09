package clueGame;

import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class DetectiveNotes extends JDialog {
	public DetectiveNotes() {
		Board board = Board.getInstance();
		setTitle("Detective Notes");
		setSize(600, 800);
		setLayout(new GridLayout(3,2));
		
		JPanel peopleBoxes = new JPanel();
		peopleBoxes.setName("People");
		int numBoxesPerRow = (int) Math.ceil(board.getPlayers().size() / 2.0);
		peopleBoxes.setLayout(new GridLayout(numBoxesPerRow,2));
		for(Player player : board.getPlayers()) {
			JCheckBox nameBox = new JCheckBox(player.getPlayerName());
			peopleBoxes.add(nameBox);
		}
		peopleBoxes.setBorder(new TitledBorder (new EtchedBorder(), "People"));
		add(peopleBoxes);
		
		JPanel playerGuess = new JPanel();
		Vector<String> players = new Vector<String>();
		players.add("None");
		for(Player player : board.getPlayers()) {
			players.add(player.getPlayerName());
		}
		JComboBox guessBox = new JComboBox(players);
		playerGuess.add(guessBox);
		playerGuess.setBorder(new TitledBorder (new EtchedBorder(), "Person Guess"));
		add(playerGuess);
		
		JPanel placesBoxes = new JPanel();
		placesBoxes.setName("Place");
		numBoxesPerRow = (int) Math.ceil(board.getRoomNames().size() / 2.0); 
		placesBoxes.setLayout(new GridLayout(numBoxesPerRow,2));
		for(String room : board.getRoomNames()) {
			JCheckBox nameBox = new JCheckBox(room);
			placesBoxes.add(nameBox);
		}
		placesBoxes.setBorder(new TitledBorder (new EtchedBorder(), "Rooms"));
		add(placesBoxes);
		
		JPanel placesGuess = new JPanel();
		Vector<String> places = new Vector<String>();
		places.add("None");
		for(String place : board.getRoomNames()) {
			places.add(place);
		}
		guessBox = new JComboBox(places);
		placesGuess.add(guessBox);
		placesGuess.setBorder(new TitledBorder (new EtchedBorder(), "Room Guess"));
		add(placesGuess);
		
		JPanel weaponBoxes = new JPanel();
		weaponBoxes.setName("Weapons");
		numBoxesPerRow = (int) Math.ceil(board.getWeapons().size() / 2.0); 
		weaponBoxes.setLayout(new GridLayout(numBoxesPerRow,2));
		for(String weapon : board.getWeapons()) {
			JCheckBox nameBox = new JCheckBox(weapon);
			weaponBoxes.add(nameBox);
		}
		weaponBoxes.setBorder(new TitledBorder (new EtchedBorder(), "Weapons"));
		add(weaponBoxes);
		
		JPanel weaponsGuess = new JPanel();
		Vector<String> weapons = new Vector<String>();
		weapons.add("None");
		for(String weapon : board.getWeapons()) {
			weapons.add(weapon);
		}
		guessBox = new JComboBox(weapons);
		weaponsGuess.add(guessBox);
		weaponsGuess.setBorder(new TitledBorder (new EtchedBorder(), "Weapon Guess"));
		add(weaponsGuess);
	}
}
