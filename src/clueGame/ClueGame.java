package clueGame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	
	public ClueGame() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
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
	

	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new ClueGame();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(1000, 900);
		
		// Create and initialize the board; add to the JFrame
		Board board = Board.getInstance();
		board.setConfigFiles("BoardLayout.csv", "Rooms.txt", "Players.txt", "Weapons.txt");		
		board.initialize();
		frame.add(board, BorderLayout.CENTER);
		
		// Create the control GUI and add it to the JFrame
		ControlGUI controlGUI = new ControlGUI();
		frame.getContentPane().add(controlGUI, BorderLayout.SOUTH);
		
		// Now let's view it
		frame.setVisible(true);
	}
}
