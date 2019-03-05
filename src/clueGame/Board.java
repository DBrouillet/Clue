package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Load the room information from proper file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		legend = new HashMap<Character, String> ();
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(", ");
			legend.put(splitLine[0].charAt(0), splitLine[1]);
			if (splitLine[2] != "Card" && splitLine[2] != "Other") {
				throw new BadConfigFormatException("Legend config file has room type that is not card or other.");
			}
		}
	}
	
	/**
	 * Load board information from proper file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		ArrayList<String[]> collectedRows = new ArrayList<String[]>(); 
		numRows = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(",");
			numColumns = splitLine.length;
			collectedRows.add(splitLine);
			numRows++;
		}
		
		board = new BoardCell[numRows][numColumns];
		for (int i = 0; i < numRows; i++) {
			if (collectedRows.get(i).length != numColumns) {
				throw new BadConfigFormatException("Number of columns is not constant across all rows."); 
			}
		}
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				DoorDirection direction = DoorDirection.NONE;
				if (collectedRows.get(i)[j].length() == 2) {
					switch(collectedRows.get(i)[j].charAt(1)) {
					case 'U':
						direction = DoorDirection.UP;
						break;
					case 'D':
						direction = DoorDirection.DOWN;
						break;
					case 'L':
						direction = DoorDirection.LEFT;
						break;
					case 'R':
						direction = DoorDirection.RIGHT;
						break;
					}
				}
				board[i][j] = new BoardCell(i, j, collectedRows.get(i)[j].charAt(0), direction);
			}
		}
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
		return legend;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int i, int j) {
		return board[i][j];
	}
	
	

}
