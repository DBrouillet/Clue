package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JFrame game;

	private JTextField name;

	private JPanel topPanel;
	private JPanel bottomPanel;

	private JTextField currentPlayer;
	private JTextField dieRollText;

	private JTextField guessText;
	private JTextField guessResultText;

	/**
	 * Create the overall GUI and add the relevant panels.
	 */
	public ControlGUI(JFrame frame) {
		game = frame;
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
				game.repaint();
				updateDieRoll();
				updateCurrentPlayer();
			}

		});

		// Button to click to make an accusation
		JButton makeAccusation = new JButton("Make an Accusation");
		makeAccusation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				if(Board.getInstance().getCurrentPlayer() instanceof HumanPlayer && !Board.getInstance().isNextPlayerIsValid()) {
					AccusationGUI accusation = new AccusationGUI();
					accusation.setVisible(true);
				} else {
					ClueGame.getInstance().displayErrorMessage();
				}
			}

		});
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		return panel;
	}

	private void updateDieRoll() {
		dieRollText.setText(Integer.toString(Board.getInstance().getDieRoll()));
	}

	private void updateCurrentPlayer() {
		currentPlayer.setText(Board.getInstance().getCurrentPlayer().getPlayerName());
	}

	public void updateGuess(Solution guess) {
		guessText.setText(guess.toString());
	}

	public void updateGuess(String textString) {
		guessText.setText(textString);
	}

	public void updateResult(Card guess) {
		guessResultText.setText(guess.toString());
	}

	public void updateResult(String textString) {
		guessResultText.setText(textString);
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
		dieRollText = new JTextField(3);
		dieRollText.setEditable(false);
		dieRoll.add(nameLabel);
		dieRoll.add(dieRollText);
		dieRoll.setBorder(new TitledBorder (new EtchedBorder(), "Die"));

		// Create the subpanel which contains the guess
		JPanel guess = new JPanel();
		guess.setLayout(new GridLayout(2,1));
		JLabel nameLabel2 = new JLabel("Guess");
		guessText = new JTextField(25);
		guessText.setEditable(false);
		guess.add(nameLabel2);
		guess.add(guessText);
		guess.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));

		// Create the subpanel which contains the result of the guess
		JPanel guessResult = new JPanel();
		guessResult.setLayout(new GridLayout(1,2));
		JLabel nameLabel3 = new JLabel("Response");
		guessResultText = new JTextField(12);
		guessResultText.setEditable(false);
		guessResult.add(nameLabel3);
		guessResult.add(guessResultText);
		guessResult.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));

		panel.add(dieRoll);
		panel.add(guess);
		panel.add(guessResult);
		return panel;
	}
}
