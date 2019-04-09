package clueGame;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
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
		
		JPanel weaponBoxes = new JPanel();
		weaponBoxes.setName("Weapons");
		add(weaponBoxes);
	}
}
