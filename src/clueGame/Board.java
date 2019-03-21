package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson
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
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private String boardConfigFile;
	private String roomConfigFile;
	private String playerConfigFile;
	private String weaponConfigFile;
	private Solution theAnswer;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	
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
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>> ();
		// Load the configuration files
		// Catch any exceptions in this method
		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPlayers();
			loadWeapons();
			createDeck();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			System.out.println(e.getMessage());
		}
		
		calcAdjacencies();
	}
	
	private void createDeck() {
		deck = new ArrayList<Card>();
	}

	/**
	 * Load the room information from proper file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		legend = new HashMap<Character, String> ();
		
		// Read in from roomConfig file
		FileReader reader = new FileReader(roomConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(", ");
			// Place elements into the Map (legend) and check to ensure they are valid
			if (!splitLine[2].equals("Card") && !splitLine[2].equals("Other")) {
				throw new BadConfigFormatException("Legend config file has room type that is not card or other.");
			}
			legend.put(splitLine[0].charAt(0), splitLine[1]);
		}
	}
	
	/**
	 * Load the player information from proper file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadPlayers() throws FileNotFoundException, BadConfigFormatException {
		players = new ArrayList<Player>();
		
		// Read in from playerConfig file
		FileReader reader = new FileReader(playerConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(", ");
			
			String name = splitLine[0];
			String color = splitLine[1];
			String type = splitLine[2];
			int row = Integer.parseInt(splitLine[3]);
			int column = Integer.parseInt(splitLine[4]);
			
			Player player;
			if (type.equals("Human")) {
				player = new HumanPlayer(name, color, row, column);
			} else if (type.equals("Comp")) {
				player = new ComputerPlayer(name, color, row, column);
			} else {
				throw new BadConfigFormatException("Player type " + type + " not recognized (must be Human or Comp).");
			}
			
			players.add(player);
		}
	}
	
	/**
	 * Load the weapons information from proper file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadWeapons() throws FileNotFoundException, BadConfigFormatException {
		
		// Read in from playerConfig file
		FileReader reader = new FileReader(weaponConfigFile);
		Scanner in = new Scanner(reader);
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(", ");
		}
	}
	
	/**
	 * Load board information from proper file
	 * @throws FileNotFoundException 
	 * @throws BadConfigFormatException 
	 */
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		// Read in from boardConfig file
		FileReader reader = new FileReader(boardConfigFile);
		Scanner in = new Scanner(reader);
		
		// Store each row into this ArrayList
		ArrayList<String[]> collectedRows = new ArrayList<String[]>(); 
		numRows = 0;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			String[] splitLine = line.split(",");
			// Store the number of columns as the length of the array
			numColumns = splitLine.length;
			
			// Store the row
			collectedRows.add(splitLine);
			numRows++; // Each time we read a line, that is the same as a row
		}
		
		board = new BoardCell[numRows][numColumns];
		
		// Check to make sure that the number of columns is consistent
		for (int i = 0; i < numRows; i++) {
			if (collectedRows.get(i).length != numColumns) {
				throw new BadConfigFormatException("Number of columns is not constant across all rows."); 
			}
		}
		
		// Populate board with BoardCells
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				// Specify the DoorDirection of the cell
				DoorDirection direction = DoorDirection.NONE;
				// If there is a second character in the cell,
				// then we specify the direction accordingly
				// (otherwise it is NONE)
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
				
				// Check to ensure each room is in the legend
				if (!legend.containsKey(collectedRows.get(i)[j].charAt(0))) {
					throw new BadConfigFormatException("Room does not exist in legend."); 
				}
				
				// Place the BoardCell into the board
				board[i][j] = new BoardCell(i, j, collectedRows.get(i)[j].charAt(0), direction);
				adjMatrix.put(board[i][j], new HashSet<BoardCell>());
			}
		}
	}
	
	/**
	 * @param current = cell to evaluate adjacencies of
	 * @param neighbor = neighboring cell
	 * @return = true, if neighbor is adjacent to current and false otherwise
	 * 
	 * If neighbor is a door, then it will only
	 * be adjacent to current if current is in the correct direction
	 * 
	 * If neighbor is not a door, then it will only be adjacent
	 * to current if it is a walkway
	 */
	private boolean validAdjacency(BoardCell current, BoardCell neighbor) {
		if (!current.isWalkway() && current.isDoorway() == false) {
			return false;
		}
		if (neighbor.isDoorway()) {
			switch(neighbor.getDoorDirection()) {
			case RIGHT:
				return (current.getRow() == neighbor.getRow() && current.getColumn() == neighbor.getColumn() + 1);
			case LEFT:
				return (current.getRow() == neighbor.getRow() && current.getColumn() == neighbor.getColumn() - 1);
			case UP:
				return (current.getRow() == neighbor.getRow() - 1 && current.getColumn() == neighbor.getColumn());
			case DOWN:
				return (current.getRow() == neighbor.getRow() + 1 && current.getColumn() == neighbor.getColumn());
			case NONE:
				break;
			default:
				break;
			}
		}
		return (neighbor.isWalkway());
	}
	
	/**
	 * Create the adjacency matrix
	 */
	public void calcAdjacencies() {
		// Iterate through each cell
		for (BoardCell[] row : board) {
			for (BoardCell cell : row) {
				int x = cell.getRow();
				int y = cell.getColumn();
				
				// If the cell is a doorway, it can only be adjacent to the cell that it is pointing to.
				if (cell.isDoorway()) {
					switch(cell.getDoorDirection()) {
					case DOWN:
						adjMatrix.get(cell).add(getCellAt(x + 1, y));
						break;
					case LEFT:
						adjMatrix.get(cell).add(getCellAt(x, y - 1));
						break;
					case RIGHT:
						adjMatrix.get(cell).add(getCellAt(x, y + 1));
						break;
					case UP:
						adjMatrix.get(cell).add(getCellAt(x - 1, y));
						break;
					case NONE:
						break;
					default:
						break;
					}
					continue;
				}

				// Check to see if a neighboring cell is in bounds
				// If it is, then we add it to the adjMtx
				// We also check to see if it is a valid adjacency first
				if (x - 1 >= 0) {
					if (validAdjacency(cell, getCellAt(x-1, y))) {
						adjMatrix.get(cell).add(getCellAt(x-1, y));
					}
				}
				if (y - 1 >= 0) {
					if (validAdjacency(cell, getCellAt(x, y-1))) {
						adjMatrix.get(cell).add(getCellAt(x, y-1));
					}
				}
				if (x + 1 < board.length) {
					if (validAdjacency(cell, getCellAt(x+1, y))) {
						adjMatrix.get(cell).add(getCellAt(x+1, y));
					}
				}
				if (y + 1 < row.length) {
					if (validAdjacency(cell, getCellAt(x, y+1))) {
						adjMatrix.get(cell).add(getCellAt(x, y+1));
					}
				}
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMatrix.get(cell);
	}
	
	public Set<BoardCell> getAdjList(int i, int j) {
		return adjMatrix.get(getCellAt(i,j));
	}
	
	/**
	 * @param startCell = initial cell
	 * @param pathLength = length of path
	 * Calculate all of the targets pathLength away from cell
	 */
	public void calcTargets(int i, int j, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		BoardCell startCell = getCellAt(i, j);
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}
	
	/**
	 * @param startCell = initial cell
	 * @param pathLength = length of path
	 * Calculate all of the targets pathLength away from cell
	 */
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		findAllTargets(startCell, pathLength);
	}

	/**
	 * @param startCell = starting cell
	 * @param pathLength = length of desired path
	 * Given a cell and a pathLength, adds the cells neighbors to targets if it is at the end of the path,
	 * otherwise recursively calls the same function on its neighbors to find the targets on those paths
	 */
	private void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell cell : adjMatrix.get(startCell)) {
			if (visited.contains(cell)) {
				continue;
			}
			
			visited.add(cell);
			
			if (pathLength == 1) {
				targets.add(cell);
			} else {
				findAllTargets(cell, pathLength - 1);
			}
			if (cell.isDoorway()) {
				if (validAdjacency(cell, startCell)) {
					targets.add(cell);
					continue;
				}
			}
			visited.remove(cell);
		}
		
	}
	
	public void selectAnswer() {
		
	}
	
	public Card handleSuggestion() {
		return new Card();
	}
	
	public boolean checkAccusation(Solution accusation) {
		return false;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	/**
	 * @param layout = name of config file corresponding to the layout of the board
	 * @param legend = name of config file corresponding to the legend (i.e. each room)
	 * @param playerFile = name of config file corresponding to the player information
	 * @param weaponsFile = name of config file corresponding to the weapon information
	 */
	public void setConfigFiles(String layout, String legend, String playerFile, String weaponsFile) {
		boardConfigFile = layout;
		roomConfigFile = legend;
		playerConfigFile = playerFile;
		weaponConfigFile = weaponsFile;
	}
	
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
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public ArrayList<Card> getDeck() {
		return deck;
	}
}
