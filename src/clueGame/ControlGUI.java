package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class ControlGUI extends JPanel {
	private JTextField name;

	public ControlGUI()
	{
		// Create a layout with 2 rows
		setLayout(new GridLayout(2,0));
		JPanel panel = createNamePanel();
		add(panel);
		panel = createTextPanel();
		add(panel);
	}

	 private JPanel createNamePanel() {
		 	JPanel panel = new JPanel();
		 	// Use a grid layout, 1 row, 2 elements (label, text)
			panel.setLayout(new GridLayout(1,2));
			JPanel subPanel = new JPanel();
			subPanel.setLayout(new GridLayout(2,1));
		 	JLabel nameLabel = new JLabel("Current Player");
			name = new JTextField(20);
			name.setEditable(false);
			subPanel.add(nameLabel);
			subPanel.add(name);
			subPanel.setBorder(new TitledBorder (new EtchedBorder(), "Whose turn is it?"));
			panel.add(subPanel);
			JButton nextPlayer = new JButton("Next Player");
			JButton makeAccusation = new JButton("Make an Accusation");
			panel.add(nextPlayer);
			panel.add(makeAccusation);
			return panel;
	}
	 
	private JPanel createTextPanel() {
		JPanel panel = new JPanel();
		JPanel sub1 = new JPanel();
		sub1.setLayout(new GridLayout(1,2));
		JLabel nameLabel = new JLabel("Roll");
		name = new JTextField(3);
		name.setEditable(false);
		sub1.add(nameLabel);
		sub1.add(name);
		sub1.setBorder(new TitledBorder (new EtchedBorder(), "Die"));
		JPanel sub2 = new JPanel();
		sub2.setLayout(new GridLayout(2,1));
		JLabel nameLabel2 = new JLabel("Guess");
		name = new JTextField(25);
		name.setEditable(false);
		sub2.add(nameLabel2);
		sub2.add(name);
		sub2.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		JPanel sub3 = new JPanel();
		sub3.setLayout(new GridLayout(1,2));
		JLabel nameLabel3 = new JLabel("Response");
		name = new JTextField(12);
		name.setEditable(false);
		sub3.add(nameLabel3);
		sub3.add(name);
		sub3.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		panel.add(sub1);
		panel.add(sub2);
		panel.add(sub3);
		return panel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame with all the normal functionality
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("GUI Example");
		frame.setSize(750, 200);	
		// Create the JPanel and add it to the JFrame
		ControlGUI gui = new ControlGUI();
		frame.add(gui, BorderLayout.CENTER);
		// Now let's view it
		frame.setVisible(true);
	}


}
