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

	private int row;
	private int column;
	
	public BoardCell(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}
}
