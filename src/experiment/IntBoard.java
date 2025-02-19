package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to create a simple board of integers
 * and calculate their targets and all of their adjacencies.
 *
 */
public class IntBoard {
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	
	/**
	 * @param x = size of board in the x direction
	 * @param y = size of board in the y direction
	 * Initialize all BoardCells and place them in the grid.
	 * Also, initialize all of the elements of adjMtx.
	 * Then, call calcAdajacencies to populate adjMtx.
	 */
	public IntBoard(int x, int y) {
		grid = new BoardCell[x][y]; // Initialize the grid
		adjMtx = new HashMap<BoardCell, Set<BoardCell>> (); // Initialize the adjacency matrix
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				/* 
				 * Iterate through all possible cells in the x by y grid, adding each cell to the grid
				 * and then adding that cell to the adjMtx with an empty set of neighbors
				 */
				grid[i][j] = new BoardCell(i, j);
				adjMtx.put(grid[i][j], new HashSet<BoardCell> ());
			}
		}
		// Finish initializing the board by calling calcAdjacencies to populate the adjMtx
		calcAdjacencies();
	}
	
	/** 
	 * calcAdjacencies iterates through all cells in the grid
	 * for each cell, it adds each not-out-of-bounds neighbor to its set of neighbors in the adjMtx
	 */
	public void calcAdjacencies() {
		// Iterate through each cell
		for (BoardCell[] row : grid) {
			for (BoardCell cell : row) {
				int x = cell.getRow();
				int y = cell.getColumn();
				
				// Check to see if a neighboring cell is in bounds
				// If it is, then we add it to the adjMtx
				if (x - 1 >= 0) {
					adjMtx.get(cell).add(getCell(x-1, y));
				}
				if (y - 1 >= 0) {
					adjMtx.get(cell).add(getCell(x, y-1));
				}
				if (x + 1 < grid.length) {
					adjMtx.get(cell).add(getCell(x+1, y));
				}
				if (y + 1 < row.length) {
					adjMtx.get(cell).add(getCell(x, y+1));
				}
			}
		}
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell) {
		return adjMtx.get(cell);
	}
	
	/**
	 * @param startCell = starting cell
	 * @param pathLength = length of desired path
	 * Initializes empty sets of visited and targets, 
	 * then adds the start cell to the visited list and calls the recursive findAllTargets function
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
		for (BoardCell cell : adjMtx.get(startCell)) {
			if (visited.contains(cell)) {
				continue;
			}
			
			visited.add(cell);
			if (pathLength == 1) {
				targets.add(cell);
			} else {
				findAllTargets(cell, pathLength - 1);
			}
			visited.remove(cell);
		}
		
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell(int i, int j) {
		return grid[i][j];
	}
}
