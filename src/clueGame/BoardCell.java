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
	
	// Colors used to draw walkways and rooms
	public static final Color WALKWAY_OUTLINE = Color.BLACK;
	public static final Color WALKWAY_FILL = Color.YELLOW;
	public static final Color ROOM_OUTLINE = Color.GRAY;
	public static final Color ROOM_FILL = Color.GRAY;
	
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
	
	/**
	 * @param g = Graphics object to draw on
	 * @param border = color of the border
	 * @param fill = color of the interior of the cell
	 * Draws the cell with specified border and fill colors
	 * in the correct location.
	 */
	private void drawCell(Graphics g, Color border, Color fill) {
		g.setColor(fill);
		g.fillRect(column * CELL_DIMENSION, row * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
		g.setColor(border);
		g.drawRect(column * CELL_DIMENSION, row * CELL_DIMENSION, CELL_DIMENSION, CELL_DIMENSION);
	}


	/**
	 * @param g = Graphics object to draw on
	 * Draws the current board cell.
	 * Draws as a square, which is either a
	 * walkway or not a walkway (i.e. a room).
	 * These are both handled separately.
	 */
	public void draw(Graphics g) {
		if (isWalkway()) {
			drawCell(g, WALKWAY_OUTLINE, WALKWAY_FILL);
		} else {
			drawCell(g, ROOM_OUTLINE, ROOM_FILL);
		}
		
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
