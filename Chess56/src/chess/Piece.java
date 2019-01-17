/* 
 * Piece.java
 * 
 * Authors:
 * - Andrew Lowe (all157)
 * - Ronak Gandhi (rjg184)
 */

package chess;

import java.util.ArrayList;

/**
 * Piece class that has a constructor for the piece object and methods that retrieve any of the Piece object's information.
 * @author Ronak Gandhi(rjg184) and Andrew Lowe(all157)
 * @version 1.0
 *
 */
public abstract class Piece {
	
	/**
	 * The color of the piece
	 */
	protected char color; //specific color of piece
	/**
	 * The Player that owns this piece
	 */
	protected Player player; //specific player's piece
	/**
	 * The board that the piece belongs to
	 */
	protected Board board; //specific board the piece is on
	/**
	 * The name of the Piece, this name will depend on the piece's dynamic type
	 */
	char name; //specific name of piece (e.g. 'R' = rook, 'Q' = Queen, etc.)
	/**
	 * Boolean notifying whether or not this piece has been moved or not
	 */
	boolean isMoved;
	/**
	 * The x position of the piece.
	 */
	private int xpos;
	/**
	 * The y position of the piece.
	 */
	private int ypos;
	
	/**
	 * Constructor for the Piece class
	 * @param player the player to which the Piece belongs to
	 * @param board the board to which the Piece belongs to
	 * @param color the color of the Piece
	 * @param xposition the index x of the Piece
	 * @param yposition the index y of the Piece
	 */
	public Piece(Player player, Board board, char color, int xposition, int yposition) {
		this.player = player;
		this.board = board;
		this.color = color;
		this.isMoved = false;
		this.xpos = xposition;
		this.ypos = yposition;
	}
	
	/**
	 * Abstract method so every Piece object can call generateMoves()
	 * @return returns the list of all possible moves in ArrayList<String form>
	 */
	abstract ArrayList<String> generateMoves();
	
	// Converts 2D Array values into FileRank values
	/**
	 * method that converts an x index and y index into a String filerank
	 * @param row the int row value of the piece
	 * @param column the int column value of the piece
	 * @return returns the String filerank value
	 */
	public String convertToFileRank(int row, int column) {
		
		String result = "";
		
		char file = (char)('a' + column);
		int rank = 8 - row;
		
		result = file + "" + rank;
		
		return result;
		
	}
	
	/**
	 * method that prints out the Piece
	 * @return returns a String for the Piece
	 */
	public String toString() {
		return "" + this.color + this.name + " ";
	}
	
	//getters and setters
	/**
	 * Gets the x index of a Piece
	 * @return returns an int value
	 */
	public int getX() {
		return xpos;
	}
	/**
	 * Gets the y index of a Piece
	 * @return returns an int value
	 */
	public int getY() {
		return ypos;
	}
	/**
	 * Sets the xpos of a Piece
	 * @param x the value to set the xpos of a Piece to
	 */
	public void setX(int x) {
		this.xpos = x;
	}
	/**
	 * Sets the ypos of a Piece
	 * @param y the value to set the ypos of a Piece to
	 */
	public void setY(int y) {
		this.ypos = y;
	}

}
