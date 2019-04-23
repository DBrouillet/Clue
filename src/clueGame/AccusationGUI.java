package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson
 * Class used to create and display the GUI for creating suggestions
 */
public class AccusationGUI extends JDialog {
	public AccusationGUI() {
		Board board = Board.getInstance();
		setTitle("Make an Accusation");
		setSize(300, 200);
		setLayout(new GridLayout(4,2));
		setModalityType(ModalityType.APPLICATION_MODAL);

		// Block of code to create and add the your room message.
		JPanel yourRoom = new JPanel();
		yourRoom.setName("Room");
		JLabel text = new JLabel("Room");
		yourRoom.add(text);
		add(yourRoom);

		// Block of code to create and add the Rooms combo box.
		JPanel roomGuess = new JPanel();
		Vector<String> rooms = new Vector<String>();
		for(String s : board.getRoomNames()) {
			rooms.add(s);
		}
		JComboBox<String> guessBoxR = new JComboBox<String>(rooms);
		roomGuess.add(guessBoxR);
		add(roomGuess);

		// Block of code to create and add the person message.
		JPanel personMessage = new JPanel();
		personMessage.setName("Person");
		JLabel textPerson = new JLabel("Person");
		personMessage.add(textPerson);
		add(personMessage);

		// Block of code to create and add the Rooms combo box.
		JPanel playerGuess = new JPanel();
		Vector<String> players = new Vector<String>();
		for(Player p : board.getPlayers()) {
			players.add(p.getPlayerName());
		}
		JComboBox<String> guessBoxP = new JComboBox<String>(players);
		playerGuess.add(guessBoxP);
		add(playerGuess);

		// Block of code to create and add the Weapons checkboxes.
		JPanel weaponMessage = new JPanel();
		weaponMessage.setName("Weapon");
		JLabel textWeapon = new JLabel("Weapon");
		weaponMessage.add(textWeapon);
		add(weaponMessage);

		// Block of code to create and add the Weapons combo box.
		JPanel weaponsGuess = new JPanel();
		Vector<String> weapons = new Vector<String>();
		for(String weapon : board.getWeapons()) {
			weapons.add(weapon);
		}
		JComboBox<String> guessBoxW = new JComboBox<String>(weapons);
		weaponsGuess.add(guessBoxW);
		add(weaponsGuess);

		JPanel submitButton = new JPanel();
		JButton submit = new JButton("Submit");
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Card roomCard = board.getCard(guessBoxR.getItemAt(guessBoxR.getSelectedIndex()));
				Card personCard = board.getCard(guessBoxP.getItemAt(guessBoxP.getSelectedIndex()));
				Card weaponCard = board.getCard(guessBoxW.getItemAt(guessBoxW.getSelectedIndex()));
				Solution suggestion = new Solution(personCard, roomCard, weaponCard);
				board.handleAccusation(suggestion);
				setVisible(false);
				dispose();
			}

		});
		submitButton.add(submit);
		add(submitButton);
	}
}
