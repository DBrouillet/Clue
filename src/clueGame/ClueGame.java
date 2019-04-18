package clueGame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson
 * Class used to create and display all graphics (i.e.
 * the graphics window). Used as the main class
 * to control all functionality of the game and handle
 * all interactions appropriately.
 *
 */

public class ClueGame extends JFrame {
	private DetectiveNotes detectiveNotes;
	private Board board;
	private ControlGUI controlGUI;
	
	private ClueGame() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
	}
	
	private static ClueGame theInstance = new ClueGame();
	
	public static ClueGame getInstance() {
		return theInstance;
	}
	
	/**
	 * @return File menu bar
	 */
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(createNotesItem());
		menu.add(createFileExitItem());
		return menu;
	}
	
	/**
	 * @return Exit functionality of file bar
	 * Listen for action events, specifically for
	 * when the user clicks on file and then exit.
	 * Exits the program once this is pressed by the user.
	 */
	private JMenuItem createFileExitItem() {
		JMenuItem item = new JMenuItem("Exit");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		
		item.addActionListener(new MenuItemListener());
		
		return item;
	}
	
	/**
	 * @return Detective notes functionality of file bar
	 * Listen for action events, specifically for
	 * when the user clicks on file and then Detective Notes.
	 * Opens detective notes once this is pressed by the user.
	 */
	private JMenuItem createNotesItem() {
		JMenuItem item = new JMenuItem("Detective Notes");
		class MenuItemListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				detectiveNotes = new DetectiveNotes();
				detectiveNotes.setVisible(true);
			}
		}
		
		item.addActionListener(new MenuItemListener());
		
		return item;
	}
	
	/**
	 * @return JPanel which contains the right panel
	 * Creates each of the components of the right panel
	 * and then adds them to the right panel.
	 */
	private JPanel createRightPanel() {
		JTextField name;
		Board board = Board.getInstance();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6,1));
		
		// Loop through players cards, add them to the panel
		for(Card card : board.getPlayers().get(0).getMyCards()) { // 0th index of getPlayers is the human player.
			JPanel cPanel = new JPanel();
			name = new JTextField(10);
			name.setText(card.getCardName());
			name.setEditable(false);
			cPanel.setLayout(new FlowLayout());
			cPanel.add(name);
			cPanel.setBorder(new TitledBorder (new EtchedBorder(), card.getCardType().toString()));
			panel.add(cPanel);
		}
		panel.setBorder(new TitledBorder (new EtchedBorder(), "My Cards"));
		return panel;
	}

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = theInstance;
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1000, 900);
		
		// Create and initialize the board; add to the JFrame
		theInstance.board = Board.getInstance();
		theInstance.board.setConfigFiles("BoardLayout.csv", "Rooms.txt", "Players.txt", "Weapons.txt");		
		theInstance.board.initialize();
		theInstance.add(theInstance.board, BorderLayout.CENTER);
		
		frame.add(((ClueGame) frame).createRightPanel(), BorderLayout.EAST);
		
		// Create the control GUI and add it to the JFrame
		theInstance.controlGUI = new ControlGUI(frame);
		frame.getContentPane().add(theInstance.controlGUI, BorderLayout.SOUTH);
		
		// Now let's view it
		frame.setVisible(true);
		
		// Show the splash message
		String message = "You are " + theInstance.board.getPlayers().get(0).getPlayerName() + ", press Next Player to begin play.";
		JOptionPane.showMessageDialog(frame, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}

	public ControlGUI getControlGUI() {
		return controlGUI;
	}
}
