package clueGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author Miika Jarvela, Daniel Brouillet, Richard Figueroa Erickson
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
	public static final Color DOOR_COLOR = Color.cyan;
	public static final Color NAME_COLOR = Color.cyan;
	public static final int DOOR_THICKNESS = 3;
	
	private boolean isNameCell;
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	public static final char walkway = 'W';
	
	public BoardCell(int row, int column, char initial, DoorDirection doorDirection, boolean isNameCell) {
		super();
		this.row = row;
		this.column = column;
		this.initial = initial;
		this.doorDirection = doorDirection;
		this.isNameCell = isNameCell;
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
		if(isDoorway()) {
			switch(doorDirection) {
			case UP:
				drawDoor(g, column * CELL_DIMENSION, row * CELL_DIMENSION, (column + 1) * CELL_DIMENSION, row * CELL_DIMENSION);
				break;
			case RIGHT:
				drawDoor(g, (column + 1) * CELL_DIMENSION - (DOOR_THICKNESS - 1), row * CELL_DIMENSION, (column + 1) * CELL_DIMENSION - (DOOR_THICKNESS - 1), (row + 1) * CELL_DIMENSION);
				break;
			case LEFT:
				drawDoor(g, column * CELL_DIMENSION, row * CELL_DIMENSION, column * CELL_DIMENSION, (row + 1) * CELL_DIMENSION);
				break;
			case DOWN:
				drawDoor(g, column * CELL_DIMENSION, (row + 1) * CELL_DIMENSION - (DOOR_THICKNESS - 1), (column + 1) * CELL_DIMENSION, (row + 1) * CELL_DIMENSION - (DOOR_THICKNESS - 1));
				break;
			}
		}
		
	}
	
	public void drawName(Graphics g) {
		if(isNameCell) {
			g.setColor(NAME_COLOR);
			g.drawString(Board.getInstance().getRoomString(initial), (int) (column * CELL_DIMENSION + CELL_DIMENSION * 0.5), (int) (row * CELL_DIMENSION + CELL_DIMENSION * 0.5));
		}
	}
	
	public void drawDoor(Graphics g, int x1, int y1, int x2, int y2) {
		g.setColor(DOOR_COLOR);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(DOOR_THICKNESS));
		g2.drawLine(x1, y1, x2, y2);
		g2.setStroke(new BasicStroke(1));
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
