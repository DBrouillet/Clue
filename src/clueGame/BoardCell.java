package clueGame;
/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to create a simple BoardCell consisting of integers
 * (i.e. only a row and a column), an initial, and a DoorDirection.
 *
 */
public class BoardCell {
	public int getRow() {
		return row;
	}

	public BoardCell(int row, int column, char initial, DoorDirection doorDirection) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
	}

	public BoardCell() {
		
	}

	public int getColumn() {
		return column;
	}

	public char getInitial() {
		return initial;
	}

	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	
	public DoorDirection getDoorDirection() {
		return null;
		//return doorDirection;
	}

	public boolean isDoorway() {
		return false;
		//return doorDirection != DoorDirection.NONE;
	}
}
