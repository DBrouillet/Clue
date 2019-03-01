package clueGame;
/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to create a simple BoardCell consisting of integers
 * (i.e. only a row and a column).
 *
 */
public class BoardCell {
	public int getRow() {
		return row;
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
		return doorDirection;
	}

	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	public boolean isDoorway() {
		return false;
	}
}
