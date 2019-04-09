package clueGame;

import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Miika Jarvela and Daniel Brouillet
 * Class used to create a simple BoardCell consisting of integers
 * (i.e. only a row and a column), an initial, and a DoorDirection.
 *
 */
public class BoardCell {
	public static final int CELL_DIMENSION = 30; //Number of pixels in width and height.
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
		if(this.isWalkway()) {
			g.setColor(Color.yellow);
		}
		else {
			g.setColor(Color.GRAY);
		}
		g.drawRect(column * CELL_DIMENSION, row * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
		g.fillRect(column * CELL_DIMENSION, row * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
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
