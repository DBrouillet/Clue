package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to create and manipulate the board
 *
 */
public class Board {
	private int numRows;
	private int numColumns;
	public final static int MAX_BOARD_SIZE = 50;
	private BoardCell[][] board;
	private Map<Character, String> legend;
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	
	private static Board theInstance = new Board();
	
	private Board() {}
	
	/**
	 * @return because this class is a Singleton, we only create one instance of the Board
	 */
	public static Board getInstance() {
		return theInstance;
	}
	
	/**
	 * Initialize the board to its beginning state
	 */
	public void initialize() {
		try {
			loadRoomConfig();
			loadBoardConfig();
		}
		catch (FileNotFoundException e) {
			System.out.println("Input file not found.");
		}
	}
	
	/**
	 * Load the room information from proper file
	 * @throws FileNotFoundException 
	 */
	public void loadRoomConfig() throws FileNotFoundException {
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(", ");
			legend.put(splitLine[0].charAt(0), splitLine[1]);
		}
	}
	
	/**
	 * Load board information from proper file
	 */
	public void loadBoardConfig() {
		
	}
	
	/**
	 * Create the adjacency matrix
	 */
	public void calcAdjacencies() {
		
	}
	
	/**
	 * @param cell = initial cell
	 * @param pathLength = length of path
	 * Calculate all of the targets pathLength away from cell
	 */
	public void calcTargets(BoardCell cell, int pathLength) {
		
	}

	
	/**
	 * @param layout = name of config file corresponding to the layout of the board
	 * @param legend = name of config file corresponding to the legend (i.e. each room)
	 */
	public void setConfigFiles(String layout, String legend) {
		boardConfigFile = layout;
		roomConfigFile = legend;
	}
	
	public Map<Character, String> getLegend() {
		return new HashMap<Character, String> ();
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		return new BoardCell(i, j);
	}
	
	

}
