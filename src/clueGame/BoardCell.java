package clueGame;

import java.awt.Graphics;

/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to create a simple BoardCell consisting of integers
 * (i.e. only a row and a column), an initial, and a DoorDirection.
 *
 */
public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	public static final char walkway = 'W';
	
	public BoardCell() {
		
	}
	
	public BoardCell(int row, int column, char initial, DoorDirection doorDirection) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
	}
	
	// Method stub - called from paintComponent in Board. 
	public void draw(Graphics g) {
		
	}
	
	public boolean isDoorway() {
		return doorDirection != DoorDirection.NONE;
	}
	
	public boolean isWalkway() {
		return Character.toUpperCase(initial) == walkway;
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public char getInitial() {
		return initial;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

}
