package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson
 * Control panel GUI which contains all of the basic information
 * needed to control the game (as a human player).
 *
 */
public class ControlGUI extends JPanel {
	private JTextField name;
	
	private JPanel topPanel;
	private JPanel bottomPanel;
	
	private JTextField currentPlayer;
	
	// TODO: unknown if this actually does what we think
	public void updateName() {
		currentPlayer.setText(Board.getInstance().getCurrentPlayer().getPlayerName());
	}

	/**
	 * Create the overall GUI and add the relevant panels.
	 */
	public ControlGUI() {
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		topPanel = createTopPanel();
		add(topPanel);
		bottomPanel = createBottomPanel();
		add(bottomPanel);
	}

	/**
	 * @return JPanel which contains the top panel
	 * Creates each of the components of the top panel
	 * and then adds them to the top panel.
	 */
	private JPanel createTopPanel() {
		Board board = Board.getInstance();
		
		JPanel panel = new JPanel();
		// Use a grid layout, 1 row, 2 elements (label, text)
		panel.setLayout(new GridLayout(1,2));
		
		/* Create a subpanel so we can display
		 * whose turn it is in 2 rows
		 */
		
		JPanel turnPanel = new JPanel();
		turnPanel.setLayout(new GridLayout(2,1));
		JLabel nameLabel = new JLabel("Current Player");
		
		currentPlayer = new JTextField(20);
		currentPlayer.setEditable(false);
		currentPlayer.setText(board.getCurrentPlayer().getPlayerName());
		
		turnPanel.add(nameLabel);
		turnPanel.add(currentPlayer);
		turnPanel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn is it?"));
		panel.add(turnPanel);
		
		// Button to click for next player
		JButton nextPlayer = new JButton("Next Player");
		nextPlayer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Board.getInstance().nextPlayerClicked();
			}
			
		});
		
		// Button to click to make an accusation
		JButton makeAccusation = new JButton("Make an Accusation");
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}

	/**
	 * @return JPanel which contains the bottom panel
	 * Creates each of the components of the bottom panel
	 * and then adds them to the bottom panel.
	 */
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel();
		
		// Create the subpanel which contains the die roll
		JPanel dieRoll = new JPanel();
		dieRoll.setLayout(new GridLayout(1,2));
		JLabel nameLabel = new JLabel("Roll");
		name = new JTextField(3);
		name.setEditable(false);
		dieRoll.add(nameLabel);
		dieRoll.add(name);
		dieRoll.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		
		// Create the subpanel which contains the guess
		JPanel guess = new JPanel();
		guess.setLayout(new GridLayout(2,1));
		JLabel nameLabel2 = new JLabel("Guess");
		name = new JTextField(25);
		name.setEditable(false);
		guess.add(nameLabel2);
		guess.add(name);
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		
		// Create the subpanel which contains the result of the guess
		JPanel guessResult = new JPanel();
		guessResult.setLayout(new GridLayout(1,2));
		JLabel nameLabel3 = new JLabel("Response");
		name = new JTextField(12);
		name.setEditable(false);
		guessResult.add(nameLabel3);
		guessResult.add(name);
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		
		panel.add(dieRoll);
		panel.add(guess);
		panel.add(guessResult);
		return panel;
	}
}
