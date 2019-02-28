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
		super();
		grid = new BoardCell[x][y];
		adjMtx = new HashMap<BoardCell, Set<BoardCell>> ();
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				grid[i][j] = new BoardCell(i, j);
				adjMtx.put(grid[i][j], new HashSet<BoardCell> ());
			}
		}
		
		calcAdjacencies();
	}

	public void calcAdjacencies() {
		for (BoardCell[] row : grid) {
			for (BoardCell cell : row) {
				int x = cell.getRow();
				int y = cell.getColumn();
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
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		
		findAllTargets(startCell, pathLength);
	}
	
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
