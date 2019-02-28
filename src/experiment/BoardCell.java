package experiment;

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
