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
 * Class used to create and display the detective
 * notes custom dialog to facilitate player note-taking.
 */
public class SuggestionGUI extends JDialog {
	public SuggestionGUI(Player player) {
		Board board = Board.getInstance();
		setTitle("Make a Guess");
		setSize(200, 200);
		setLayout(new GridLayout(4,2));
		setModalityType(ModalityType.APPLICATION_MODAL);
		
		// Block of code to create and add the your room message.
		JPanel yourRoom = new JPanel();
		yourRoom.setName("Your Room");
		JLabel text = new JLabel("Your Room");
		yourRoom.add(text);
		add(yourRoom);
		
		// Block of code to create and add the Person Guess combo box.
		JPanel roomGuess = new JPanel();
		JTextField roomTextField = new JTextField(board.getRoomString(player.getCurrentCell().getInitial()));
		roomTextField.setEditable(false);
		roomGuess.add(roomTextField);
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
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		
		JPanel submitButton = new JPanel();
		JButton submit = new JButton();
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				String roomCard = board.getRoomString(player.getCurrentCell().getInitial());
				String personCard = guessBoxP.getItemAt(guessBoxP.getSelectedIndex());
				String weaponCard = guessBoxW.getItemAt(guessBoxW.getSelectedIndex());
				Solution suggestion = new Solution(personCard, roomCard, weaponCard);
				board.handleSuggestion(suggestion);
			}
			
		});
	}
}
